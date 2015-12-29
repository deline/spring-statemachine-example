package com.delineneo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Created by deline on 15/11/2015.
 */
public class CoinEnteredResponse {
    @JsonSerialize(using = MoneySerializer.class)
    @JsonProperty
    private BigDecimal amountEnteredSoFar;

    @JsonSerialize(using = MoneySerializer.class)
    @JsonProperty
    private BigDecimal amountOutstanding;

    @JsonProperty
    private boolean enoughFundsEntered;

    private CoinEnteredResponse() {
    }

    public CoinEnteredResponse(BigDecimal amountEnteredSoFar, BigDecimal amountOutstanding, boolean enoughFundsEntered) {
        this.amountEnteredSoFar = amountEnteredSoFar;
        this.amountOutstanding = amountOutstanding;
        this.enoughFundsEntered = enoughFundsEntered;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
