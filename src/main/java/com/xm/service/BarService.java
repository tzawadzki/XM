package com.xm.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.xm.dto.Bar;
import com.xm.repository.PriceRepository;
import com.xm.repository.model.OpenHighLowClose;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BarService {

    private final PriceRepository priceRepository;

    public Bar bar(String symbol) {
        return convert(symbol, priceRepository.findOHLCBySymbol(symbol));
    }

    public Bar bar(String symbol, LocalDateTime from, LocalDateTime to) {
        return convert(symbol, priceRepository.findOHLCBySymbolBetweenDates(symbol, from, to));
    }

    private Bar convert(String symbol, OpenHighLowClose openHighLowClose) {
        return Bar.builder()
                .symbol(symbol)
                .open(openHighLowClose.open())
                .high(openHighLowClose.high())
                .low(openHighLowClose.low())
                .close(openHighLowClose.close())
                .build();
    }
}
