package com.delineneo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by deline on 11/11/2015.
 */
@RestController
@RequestMapping(value = "/coinEntryService")
public class CoinEntryServiceController {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    private RequiredAmountChecker requiredAmountChecker = new RequiredAmountChecker();

    @RequestMapping(value = "/coinEntered", method = RequestMethod.PUT)
    public CoinEnteredResponse coinEntered(@RequestBody CoinEnteredEvent coinEnteredEvent) {


        Message<Events> event = MessageBuilder
                .withPayload(Events.COIN_ENTERED)
                .setHeader("coinEntered", coinEnteredEvent.getCoinValue())
                .build();

        boolean eventAccepted = stateMachine.sendEvent(event);

        ExtendedState extendedState = stateMachine.getExtendedState();
        BigDecimal amountEntered = (BigDecimal) extendedState.getVariables().get("amountEntered");

        boolean enoughFundsEntered = requiredAmountChecker.requiredAmountEntered(amountEntered);
        BigDecimal amountOutstanding = requiredAmountChecker.requiredAmountRemaining(amountEntered)
;
        return new CoinEnteredResponse(amountEntered, amountOutstanding, enoughFundsEntered);
    }
}
