package com.delineneo;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by deline on 29/12/2015.
 */
public class RequiredAmountChecker {

    private final BigDecimal requiredAmount;

    public RequiredAmountChecker() {
        this(BigDecimal.valueOf(2));
    }

    public RequiredAmountChecker(BigDecimal requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public boolean requiredAmountEntered(BigDecimal amountEntered) {
        return amountEntered.compareTo(requiredAmount) >= 0;
    }


    public BigDecimal requiredAmountRemaining(BigDecimal amountEntered) {
        return requiredAmount.subtract(amountEntered);
    }
}
