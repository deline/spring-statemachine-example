package com.delineneo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

import java.math.BigDecimal;

/**
 * Created by deline on 25/10/2015.
 */


public class RequiredAmountGuard implements Guard<States, Events> {

    private RequiredAmountChecker requiredAmountChecker = new RequiredAmountChecker();

    public boolean evaluate(StateContext context) {
        BigDecimal amountEntered = context.getExtendedState().get("amountEntered", BigDecimal.class);
        return requiredAmountChecker.requiredAmountEntered(amountEntered);
    }

}
