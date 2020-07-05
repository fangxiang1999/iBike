import com.yc.bean.Bike;
import com.yc.bean.User;
import com.yc.config.AppConfig;
import com.yc.dao.BikeDao;
import com.yc.service.BikeService;
import com.yc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author wys
 * @date 2020/6/13 - 19:28
 */
@RunWith(SpringJUnit4ClassRunner.class) //测试
@ContextConfiguration(classes = AppConfig.class )
public class AppTest {
    @Autowired
    private BikeService bikeService;
    @Autowired
    private BikeDao bikeDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void testUserService() throws  Exception{
        userService.genVerifyCode("86","18890419118");
    }

    @Test
    public void testRedisTemplate(){
        System.out.println(redisTemplate);
    }

    @Test
    public void testNearBike(){
        Bike b=new Bike();
        b.setLatitude(28.189133);
        b.setLongitude(112.943867);
        List<Bike> list=bikeService.findNearAll(b);
        System.out.println(list);
    }

    @Test
    public void testlogServiceImpl() {
        User user=new User();
        user.setPhoneNum("13456789");
        user.setBalance(100);
        mongoTemplate.insert(user,"user");
        System.out.println("插入成功+++++++++++");
    }

    @Test
    public void testMongo(){
        System.out.println(  mongoTemplate.getDb().getName());
        System.out.println(mongoTemplate.getCollectionNames());

    }

    @Autowired
    //有多个的话，会冲突，可以加 @Qualifier
    private DataSource dataSource;
    @Test
    public void testDataSource() throws Exception{
        Assert.notNull(dataSource);

        Assert.notNull(dataSource.getConnection());
    }

    /**
     * 新车入库
     */
    @Test
    public void testAddBike(){
        Bike b=new Bike();
        Bike res=bikeDao.addBike(b);
        Assert.notNull(res.getBid());
        System.out.println(res.getBid());

    }

    @Test
    public void testUpdateBike(){
        Bike b=bikeDao.findBike(3L);
        b.setStatus(Bike.LOCK);
        b.setLatitude(20.1);
        b.setLongitude(22.2);

        bikeDao.updateBike(b);
    }

    @Test
    public void testFind(){
        Bike b=bikeDao.findBike(1L);
        Assert.notNull(b);

    }

    @Test
    public void testServiceOpen(){
        Bike b=bikeDao.findBike(3L);
        bikeService.open(b);
    }

    @Test
    public void testServiceAddBike(){
        Bike b=new Bike();
        Bike r=bikeService.addNewBike(b);
        System.out.println(r.getQrcode());
    }
}
