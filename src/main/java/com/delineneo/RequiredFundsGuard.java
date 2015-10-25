package com.delineneo;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.math.BigDecimal;

/**
 * Created by deline on 25/10/2015.
 */


public class RequiredFundsGuard implements Guard<States, Events> {

    public RequiredFundsGuard() {
        System.out.println("**** constructor");
    }

    public boolean evaluate(StateContext context) {
        BigDecimal amountEntered = context.getExtendedState().get("amountEntered", BigDecimal.class);
        return amountEntered.compareTo(BigDecimal.valueOf(2)) >= 0;
    }
}
