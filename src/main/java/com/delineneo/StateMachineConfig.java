package com.delineneo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

import static com.delineneo.Events.COIN_ENTERED;
import static com.delineneo.Events.START_BUTTON_PUSHED;
import static com.delineneo.States.*;

/**
 * Created by deline on 23/10/2015.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
            .withConfiguration()
            .autoStartup(true)
            .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
            .withStates()
                .initial(AWAITING_COIN)
                .choice(States.COIN_ENTERED)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
            .withExternal()
                .source(AWAITING_COIN)
                .event(COIN_ENTERED)
                .target(States.COIN_ENTERED)
                .action(new CoinEnteredAction())
                .and()
            .withChoice()
                .source(States.COIN_ENTERED)
                .first(AWAITING_MACHINE_START, new RequiredAmountGuard())
                .last(AWAITING_COIN)
                .and()
            .withExternal()
                .source(AWAITING_MACHINE_START)
                .event(START_BUTTON_PUSHED)
                .target(MACHINE_STARTED);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                String fromStateId = from != null ? from.getId().toString() : "NO STATE";
                String toStateId = from != null ? to.getId().toString() : "NO STATE";

                String outputString = String.format("State change from: %s to: %s", fromStateId, toStateId);
                System.out.println(outputString);
            }
        };
    }
}
