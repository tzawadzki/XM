package com.xm.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.xm.dto.SymbolOHLC;
import com.xm.repository.TickRepository;
import com.xm.repository.helper.OpenHighLowClose;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OHLCService {

    private final TickRepository tickRepository;

    public SymbolOHLC getOHLC(String symbol) {
        return convert(symbol, tickRepository.findOHLCBySymbol(symbol));
    }

    public SymbolOHLC getOHLC(String symbol, LocalDateTime from, LocalDateTime to) {
        return convert(symbol, tickRepository.findOHLCBySymbolBetweenDates(symbol, from, to));
    }

    private SymbolOHLC convert(String symbol, OpenHighLowClose openHighLowClose) {
        return SymbolOHLC.builder()
                .symbol(symbol)
                .open(openHighLowClose.open())
                .high(openHighLowClose.high())
                .low(openHighLowClose.low())
                .close(openHighLowClose.close())
                .build();
    }
}
