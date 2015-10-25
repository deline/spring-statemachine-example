package com.delineneo;


import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.AbstractStateMachineTests;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

/**
 * Created by deline on 25/10/2015.
 */
public class StateMachineTest extends AbstractStateMachineTests {

//    @SuppressWarnings("unchecked")
//    @Test
//    public void notEnoughFundsRemainsInCoinEntryState() throws Exception {
//        registerAndRefresh(StateMachineConfig.class);
//
//        StateMachine<States, Events> statemachine = context.getBean(StateMachine.class);
//        StateMachineTestPlan<States, Events> plan =StateMachineTestPlanBuilder.<States, Events>builder()
//                .stateMachine(statemachine)
//                .step()
//                    .expectState(States.COIN_ENTRY_STATE)
//                    .and()
//                .step()
//                    .sendEvent(Events.COIN_ENTERED)
//                    .expectState(States.COIN_ENTRY_STATE)
//                    .and()
//                .build();
//
//        plan.test();
//    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenEnoughFundsAreEnteredStateTransitionsToAwaitingMachineStartState() throws Exception {
        registerAndRefresh(StateMachineConfig.class);
        StateMachine<States, Events> statemachine = context.getBean(StateMachine.class);
        StateMachineTestPlan<States, Events> plan =StateMachineTestPlanBuilder.<States, Events>builder()
                .stateMachine(statemachine)
                .step()
                    .sendEvent(Events.COIN_ENTERED)
                    .expectState(States.AWAITING_MACHINE_START)
                    .and()
                .build();

        plan.test();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void whenStateIsAwaitingMachineStartTheStartButtonPushedEventTransitionsToMachineStartedState() throws Exception {
        registerAndRefresh(StateMachineConfig.class);
        StateMachine<States, Events> statemachine = context.getBean(StateMachine.class);
        StateMachineTestPlan<States, Events> plan =StateMachineTestPlanBuilder.<States, Events>builder()
                .stateMachine(statemachine)
                .step().expectStateMachineStarted(1).and()
                .step()
                    .sendEvent(Events.COIN_ENTERED)
                    .expectState(States.AWAITING_MACHINE_START)
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
