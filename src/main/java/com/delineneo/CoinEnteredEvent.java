package com.delineneo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Created by deline on 11/11/2015.
 */

public class CoinEnteredEvent {
    @JsonProperty
    private BigDecimal coinValue;

    private CoinEnteredEvent() {
    }

    public CoinEnteredEvent(BigDecimal coinValue) {
        this.coinValue = coinValue;
    }

    public BigDecimal getCoinValue() {
        return coinValue;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
