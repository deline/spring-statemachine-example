package com.delineneo;


import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.AbstractStateMachineTests;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

import java.math.BigDecimal;

import static com.delineneo.Events.COIN_ENTERED;
import static com.delineneo.States.AWAITING_COIN;
import static com.delineneo.States.AWAITING_MACHINE_START;

/**
 * Created by deline on 25/10/2015.
 */
public class StateMachineTest extends AbstractStateMachineTests {

    @SuppressWarnings("unchecked")
    @Test
    public void whenEnoughFundsAreEnteredStateTransitionsToAwaitingMachineStartState() throws Exception {
        registerAndRefresh(StateMachineConfig.class);

        Message<Events> oneDollarEntered = MessageBuilder
                .withPayload(COIN_ENTERED)
                .setHeader("coinEntered", new BigDecimal(1.00))
                .build();

        Message<Events> fiftyCentsEntered = MessageBuilder
                .withPayload(COIN_ENTERED)
                .setHeader("coinEntered", new BigDecimal(0.5))
                .build();

        StateMachine<States, Events> statemachine = context.getBean(StateMachine.class);
        StateMachineTestPlan<States, Events> plan =StateMachineTestPlanBuilder.<States, Events>builder()
                .stateMachine(statemachine)
                .step()
                    .sendEvent(oneDollarEntered)
                    .expectState(AWAITING_COIN)
                    .and()
                .step()
                    .sendEvent(fiftyCentsEntered)
                    .expectState(AWAITING_COIN)
                    .and()
                .step()
                    .sendEvent(fiftyCentsEntered)
                    .expectState(AWAITING_MACHINE_START)
                .and()
                .build();

        plan.test();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void notEnoughFundsRemainsInCoinEntryState() throws Exception {
        registerAndRefresh(StateMachineConfig.class);

        Message<Events> fiftyCentsEntered = MessageBuilder
                .withPayload(COIN_ENTERED)
                .setHeader("coinEntered", new BigDecimal(0.5))
                .build();

        StateMachine<States, Events> statemachine = context.getBean(StateMachine.class);
        StateMachineTestPlan<States, Events> plan =StateMachineTestPlanBuilder.<States, Events>builder()
                .stateMachine(statemachine)
                .step()
                    .expectState(AWAITING_COIN)
                    .and()
                .step()
                    .sendEvent(fiftyCentsEntered)
                    .expectState(AWAITING_COIN)
                    .and()
                .step()
                    .sendEvent(fiftyCentsEntered)
                    .expectState(AWAITING_COIN)
                    .and()
                .step()
                    .sendEvent(fiftyCentsEntered)
                    .expectState(AWAITING_COIN)
                    .and()
                .build();

        plan.test();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenStateIsAwaitingMachineStartTheStartButtonPushedEventTransitionsToMachineStartedState() throws Exception {
        registerAndRefresh(StateMachineConfig.class);

        Message<Events> twoDollarsEntered = MessageBuilder
                .withPayload(COIN_ENTERED)
                .setHeader("coinEntered", new BigDecimal(2.00))
                .build();

        StateMachine<States, Events> statemachine = context.getBean(StateMachine.class);
        StateMachineTestPlan<States, Events> plan =StateMachineTestPlanBuilder.<States, Events>builder()
                .stateMachine(statemachine)
                .step()
                    .expectState(AWAITING_COIN)
                    .and()
                .step()
                    .sendEvent(twoDollarsEntered)
                    .expectState(AWAITING_MACHINE_START)
                    .and()
                .step()
                .sendEvent(Events.START_BUTTON_PUSHED)
                    .expectState(States.MACHINE_STARTED)
                .and()
                .build();

        plan.test();
    }

    @Override
    protected AnnotationConfigApplicationContext buildContext() {
        return new AnnotationConfigApplicationContext();
    }

}
