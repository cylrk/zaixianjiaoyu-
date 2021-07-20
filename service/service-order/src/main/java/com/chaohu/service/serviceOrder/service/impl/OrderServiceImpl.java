package com.chaohu.service.serviceOrder.service.impl;

import com.chaohu.ordervo.CourseWebVoOrder;
import com.chaohu.ordervo.UcenterMemberOrder;
import com.chaohu.service.serviceOrder.client.EduClient;
import com.chaohu.service.serviceOrder.client.UcenterClient;
import com.chaohu.service.serviceOrder.entity.Order;
import com.chaohu.service.serviceOrder.mapper.OrderMapper;
import com.chaohu.service.serviceOrder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chaohu.service.serviceOrder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lrk
 * @since 2021-05-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据课程id获取课信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);



        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
