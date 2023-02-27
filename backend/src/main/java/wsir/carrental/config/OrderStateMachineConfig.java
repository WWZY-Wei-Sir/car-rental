package wsir.carrental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import wsir.carrental.dict.OrderStatus;
import wsir.carrental.dict.OrderStatusChgEvent;
import wsir.carrental.entity.Order;

import java.util.EnumSet;

/**
 * 订单状态机配置
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStatus, OrderStatusChgEvent> {

    //  配置状态
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChgEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.WAIT_DEPOSIT)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    //  配置状态转换事件关系
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChgEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderStatus.WAIT_DEPOSIT).target(OrderStatus.WAIT_TAKE_CAR).event(OrderStatusChgEvent.PAY_DEPOSIT)
                .and()
                .withExternal().source(OrderStatus.WAIT_TAKE_CAR).target(OrderStatus.WAIT_RETURN_CAR).event(OrderStatusChgEvent.TAKE_CAR)
                .and()
                .withExternal().source(OrderStatus.WAIT_RETURN_CAR).target(OrderStatus.WAIT_REMAINING).event(OrderStatusChgEvent.RETURN_CAR)
                .and()
                .withExternal().source(OrderStatus.WAIT_REMAINING).target(OrderStatus.FINISH).event(OrderStatusChgEvent.PAY_REMAINING);
    }

    //  持久化配置
    @Bean
    public DefaultStateMachinePersister persister() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<OrderStatus, OrderStatusChgEvent, Order>() {
            @Override
            public void write(StateMachineContext<OrderStatus, OrderStatusChgEvent> stateMachineContext, Order order) throws Exception {

            }

            @Override
            public StateMachineContext<OrderStatus, OrderStatusChgEvent> read(Order order) throws Exception {
                //此处直接获取order中的状态，其实并没有进行持久化读取操作
                return new DefaultStateMachineContext(order.getStatus(), null, null, null);
            }
        });
    }
}
