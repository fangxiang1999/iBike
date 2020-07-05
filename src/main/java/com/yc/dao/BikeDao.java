package com.yc.dao;

import com.yc.bean.Bike;

/**
 * @author wys
 * @date 2020/6/13 - 20:10
 */
public interface BikeDao {
    /**
     * 新车入库
     * @param bike
     * @return
     */
    public Bike addBike(Bike bike);

    /**
     * 更新操作  ->对应业务层的入库，更新，解锁
     * @param bike
     */
    public void updateBike(Bike bike);

    /**
     * 根据bid查车
     * @param bid
     * @return
     */
    public Bike findBike(Long bid);


}
