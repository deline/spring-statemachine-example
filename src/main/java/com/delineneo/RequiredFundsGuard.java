package com.delineneo;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

/**
 * Created by deline on 25/10/2015.
 */


public class RequiredFundsGuard implements Guard<States, Events> {
    public boolean evaluate(StateContext context) {
        return false;
    }
}
