package com.delineneo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
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

    @RequestMapping(value = "/coinEntered", method = RequestMethod.PUT)
    public CoinEnteredResponse coinEntered(@RequestBody CoinEnteredEvent coinEnteredEvent) {

        System.out.println("************ coinEnteredEvent = " + coinEnteredEvent);

        Message<Events> event = MessageBuilder
                .withPayload(Events.COIN_ENTERED)
                .setHeader("coinEntered", new BigDecimal(0))
                .build();

        boolean eventAccepted = stateMachine.sendEvent(event);
        if (eventAccepted) {

        } else {

        }

        return new CoinEnteredResponse(new BigDecimal(1.0), false);
    }

}
