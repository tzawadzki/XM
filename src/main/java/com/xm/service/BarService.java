package com.xm.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.xm.dto.Bar;
import com.xm.repository.TickRepository;
import com.xm.repository.helper.OpenHighLowClose;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BarService {

    private final TickRepository tickRepository;

    public Bar bar(String symbol) {
        return convert(symbol, tickRepository.findOHLCBySymbol(symbol));
    }

    public Bar bar(String symbol, LocalDateTime from, LocalDateTime to) {
        return convert(symbol, tickRepository.findOHLCBySymbolBetweenDates(symbol, from, to));
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
