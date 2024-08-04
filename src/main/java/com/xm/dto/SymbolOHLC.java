package com.xm.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record SymbolOHLC(String symbol, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close) {

}
