package com.delineneo;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by deline on 29/12/2015.
 */
public class RequiredAmountCheckerTest {

    private RequiredAmountChecker checker = new RequiredAmountChecker(BigDecimal.valueOf(2));

    @Test
    public void valueLessThanRequiredAmountShouldReturn() {
        assertThat(checker.requiredAmountEntered(BigDecimal.ZERO), is(false));
        assertThat(checker.requiredAmountEntered(BigDecimal.ONE), is(false));
    }

    @Test
    public void valueEqualToRequiredAmountShouldReturnTrue() {
        assertThat(checker.requiredAmountEntered(BigDecimal.valueOf(2)), is(true));
    }

    @Test
    public void valueGreaterThanRequiredAmountShouldReturnTrue() {
        assertThat(checker.requiredAmountEntered(BigDecimal.valueOf(2.1)), is(true));
    }

    @Test
    public void returnsRemainingAmountRequired() {
        assertThat(checker.requiredAmountRemaining(BigDecimal.valueOf(1.5)), is(BigDecimal.valueOf(0.5)));
    }
}
