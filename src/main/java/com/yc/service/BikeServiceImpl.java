package com.yc.service;

import com.yc.bean.Bike;
import com.yc.dao.BikeDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author wys
 * @date 2020/6/13 - 21:32
 */
@Service
@Transactional  //事务
public class BikeServiceImpl implements BikeService {
   @Autowired
   private BikeDao bikeDao;
    @Autowired
    private MongoTemplate mongoTemplate;

   private Logger logger= LogManager.getLogger();

    @Override
    public void open(Bike bike) {
        if(bike.getBid()==null){
            throw new RuntimeException("缺少待开没单车编号");
        }
        Bike b=findByBid(bike.getBid());
        if(b==null){
            throw new RuntimeException("查无此车");
        }
        switch (b.getStatus()){
            case Bike.UNACTIVE:
                throw new RuntimeException("此车暂未启用,请更换一辆");
            case Bike.USING:
                throw new RuntimeException("此车正在使用,请更换一辆");
            case Bike.INTROUBLE:
                throw new RuntimeException("此车需要修理,请更换一辆");
        }
        bike.setStatus(Bike.USING);
        bikeDao.updateBike(bike);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS )     //查询指定为只读
    public Bike findByBid(Long bid) {
        Bike b=null;
        try {
            b=bikeDao.findBike(bid);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return b;
    }

    @Override
    public Bike addNewBike(Bike bike) {
        Bike b=bikeDao.addBike(bike);
        Long bid=b.getBid();    //获取到数据库的自增bid

        bike=findByBid(bid);    //查到刚添加的车的信息
        //todo 根据bid生成二维码
        String qrcode=bid+"";
        bike.setQrcode(qrcode);
        bikeDao.updateBike(bike);
        return bike;
    }

    @Override
    public List<Bike> findNearAll(Bike bike) {
        //db.bike.find( {loc:{$near:[28.189133,112.943868]},status:1})
        Query query=new Query();
        query.addCriteria(Criteria.where("status").is(bike.getStatus()))
              .addCriteria(Criteria.where("loc").near(new Point(bike.getLongitude(),bike.getLatitude())))
            .limit(10); //设置返回10条

        //{ "_id" : 100001, "status" : 1, "loc" : [ 28.189133, 112.943868 ], "qrcode" : "" }
        List<Bike>  list=this.mongoTemplate.find(query,Bike.class,"bike");

        //数据转换
        for (Bike b: list) {
            b.setBid(b.getId());
            b.setId(null);
            b.setLongitude(b.getLoc()[1]);
            b.setLatitude(b.getLoc()[0]);
            b.setLoc(null);
        }
        return list;

    }
}
