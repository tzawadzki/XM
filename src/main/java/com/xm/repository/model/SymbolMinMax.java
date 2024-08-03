package com.xm.repository.model;

import java.math.BigDecimal;

public record SymbolMinMax(String symbol, BigDecimal min, BigDecimal max) {

}
