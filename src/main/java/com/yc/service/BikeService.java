package com.yc.service;

import com.yc.bean.Bike;

import java.util.List;

/**
 * @author wys
 * @date 2020/6/13 - 20:58
 */
public interface BikeService {
    /**
     *  开锁
     * 1. bid必须
     * 2. 根据bid查车
     * 3. 车的状态
     */
    public void open(Bike bike);

    /**
     * 根据bid查车
     * @param bid
     * @return
     */
    public Bike findByBid(Long bid);

    /**
     * 添加新车 生成bid，且根据bid生成二维码
     * @param bike
     * @return
     */
    public Bike addNewBike(Bike bike);

    public List<Bike>findNearAll(Bike bike);
}
