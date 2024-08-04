package com.xm.dto;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record NormalizedRange(String symbol, BigDecimal normalizedRange) {

}
