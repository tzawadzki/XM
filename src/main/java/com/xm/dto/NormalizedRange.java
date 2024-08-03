package com.xm.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalizedRange {

    private String symbol;

    // TODO can be changed to % for better user readability
    private BigDecimal normalizedRange;
}
