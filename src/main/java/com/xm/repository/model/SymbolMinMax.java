package com.xm.repository.model;

import java.math.BigDecimal;

public record SymbolMinMax(String symbol, BigDecimal min, BigDecimal max) {

//    public SymbolMinMax(String symbol, String min, String max) {
//        this(symbol, new BigDecimal(min), new BigDecimal(max));
//    }
}
