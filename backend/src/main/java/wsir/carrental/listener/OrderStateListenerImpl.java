package wsir.carrental.listener;

import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;
import wsir.carrental.dict.OrderStatus;
import wsir.carrental.dict.OrderStatusChgEvent;
import wsir.carrental.entity.Order;

@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListenerImpl {

    @OnTransition(source = "WAIT_DEPOSIT", target = "WAIT_TAKE_CAR")
    public boolean depositTransition(Message<OrderStatusChgEvent> message) {
        return transition(message, OrderStatus.WAIT_TAKE_CAR);
    }

    @OnTransition(source = "WAIT_TAKE_CAR", target = "WAIT_RETURN_CAR")
    public boolean takeCarTransition(Message<OrderStatusChgEvent> message) {
        return transition(message, OrderStatus.WAIT_RETURN_CAR);
    }

    @OnTransition(source = "WAIT_RETURN_CAR", target = "WAIT_REMAINING")
    public boolean returnCarTransition(Message<OrderStatusChgEvent> message) {
        return transition(message, OrderStatus.WAIT_REMAINING);
    }

    @OnTransition(source = "WAIT_REMAINING", target = "FINISH")
    public boolean remainingTransition(Message<OrderStatusChgEvent> message) {
        return transition(message, OrderStatus.FINISH);
    }

    private Boolean transition(Message<OrderStatusChgEvent> message, OrderStatus orderStatus) {
        Order order = (Order) message.getHeaders().get("order");
        if (order != null) {
            order.setStatus(orderStatus);
            System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
            return true;
        }
        return false;
    }
}
