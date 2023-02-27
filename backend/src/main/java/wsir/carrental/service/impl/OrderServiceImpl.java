package wsir.carrental.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import wsir.carrental.dict.OrderStatus;
import wsir.carrental.dict.OrderStatusChgEvent;
import wsir.carrental.entity.Order;
import wsir.carrental.mapper.OrderMapper;
import wsir.carrental.service.OrderService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private StateMachine<OrderStatus, OrderStatusChgEvent> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderStatus, OrderStatusChgEvent, Order> persister;

    @Autowired
    private OrderMapper orderMapper;

    public Order create() {
        Order order = new Order();
        order.setStatus(OrderStatus.WAIT_DEPOSIT);
        return order;
    }

//    public Order pay(int id) {
//        Order order = orders.get(id);
//        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试支付，订单号：" + id);
//        Message message = MessageBuilder.withPayload(OrderStatusChgEvent.).setHeader("order", order).build();
//        if (!sendEvent(message, order)) {
//            System.out.println("线程名称：" + Thread.currentThread().getName() + " 支付失败, 状态异常，订单号：" + id);
//        }
//        return orders.get(id);
//    }
//
//    public Order deliver(int id) {
//        Order order = orders.get(id);
//        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试发货，订单号：" + id);
//        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChgEvent.DELIVERY).setHeader("order", order).build(), orders.get(id))) {
//            System.out.println("线程名称：" + Thread.currentThread().getName() + " 发货失败，状态异常，订单号：" + id);
//        }
//        return orders.get(id);
//    }
//
//    public Order receive(int id) {
//        Order order = orders.get(id);
//        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试收货，订单号：" + id);
//        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChgEvent.RECEIVED).setHeader("order", order).build(), orders.get(id))) {
//            System.out.println("线程名称：" + Thread.currentThread().getName() + " 收货失败，状态异常，订单号：" + id);
//        }
//        return orders.get(id);
//    }
//
//    public Map<Integer, Order> getOrders() {
//        return orders;
//    }
//
//    /**
//     * 发送订单状态转换事件
//     *
//     * @param message
//     * @param order
//     * @return
//     */
//    private synchronized boolean sendEvent(Message<OrderStatusChgEvent> message, Order order) {
//        boolean result = false;
//        try {
//            orderStateMachine.start();
//            //尝试恢复状态机状态
//            persister.restore(orderStateMachine, order);
//            //添加延迟用于线程安全测试
//            Thread.sleep(1000);
//            result = orderStateMachine.sendEvent(message);
//            //持久化状态机状态
//            persister.persist(orderStateMachine, order);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            orderStateMachine.stop();
//        }
//        return result;
//    }
}
