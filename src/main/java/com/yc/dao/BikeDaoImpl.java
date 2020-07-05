package com.yc.dao;

import com.yc.bean.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author wys
 * @date 2020/6/13 - 20:13
 */
@Repository     //Dao层 由spring容器来托管
public class BikeDaoImpl implements BikeDao {
    //模板类
    public JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public Bike addBike(Bike bike) {
        String sql="insert into bike (latitude,longitude,status,qrcode) values(0.0,0.0,0,'')";
        KeyHolder keyHolder=new GeneratedKeyHolder();   //自增列
        this.jdbcTemplate.update(con -> {
            PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return  ps;
        },keyHolder);
        bike.setBid(keyHolder.getKey().longValue());//取出数据库设置的bid
        return bike;

    }

    @Override
    public void updateBike(Bike bike) {
        String sql="update bike set latitude=?,longitude=?,status=?,qrcode=? where bid=?";
        this.jdbcTemplate.update(con -> {
            PreparedStatement ps=con.prepareStatement(sql) ;
            ps.setDouble(1,bike.getLatitude());
            ps.setDouble(2,bike.getLongitude());
            ps.setInt(3,bike.getStatus());
            ps.setString(4,bike.getQrcode());
            ps.setLong(5,bike.getBid());
            return ps;
        });
    }

    @Override
    public Bike findBike(Long bid) {
        String sql="select * from bike where bid="+bid;

        return (Bike) this.jdbcTemplate.queryForObject(sql,(rs, rowNum) -> {
            Bike b=new Bike();
            b.setBid(rs.getLong("bid"));
            b.setLatitude(rs.getDouble("latitude"));
            b.setLongitude(rs.getDouble("longitude"));
            b.setQrcode(rs.getString("qrcode"));
            b.setStatus(rs.getInt("status"));
            return b;
        });
    }
}
