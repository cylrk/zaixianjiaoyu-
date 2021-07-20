package com.chaohu.service.serviceOrder.service;

import com.chaohu.service.serviceOrder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lrk
 * @since 2021-05-05
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberIdByJwtToken);
}
