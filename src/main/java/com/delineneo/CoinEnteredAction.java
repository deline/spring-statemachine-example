package com.delineneo;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.math.BigDecimal;

/**
 * Created by deline on 26/10/2015.
 */
public class CoinEnteredAction implements Action<States, Events> {
    public void execute(StateContext context) {
        BigDecimal coinEntered = (BigDecimal) context.getMessageHeader("coinEntered");

        BigDecimal amountEntered = (BigDecimal) context.getExtendedState().getVariables().getOrDefault("amountEntered", BigDecimal.ZERO);
        amountEntered = amountEntered.add(coinEntered);
        context.getExtendedState().getVariables().put("amountEntered", amountEntered);
    }
}
