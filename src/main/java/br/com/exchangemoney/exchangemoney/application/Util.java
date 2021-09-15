package br.com.exchangemoney.exchangemoney.application;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

public class Util {

    public static MonetaryAmount createMoney(BigDecimal amount, String currency) {
        return Monetary.getDefaultAmountFactory().setCurrency(currency).setNumber(amount).create();
    }

}
