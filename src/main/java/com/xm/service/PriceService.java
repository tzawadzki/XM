package com.xm.service;

import static com.xm.configuration.XmConstants.PRICE_SCALE;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xm.dto.NormalizedRange;
import com.xm.repository.TickRepository;
import com.xm.repository.helper.SymbolMinMax;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PriceService {

    private final TickRepository tickRepository;

    public List<NormalizedRange> normalizedRanges() {
        return convert(tickRepository.findSymbolMinMax());
    }

    public NormalizedRange maxNormalizedRangeForDay(LocalDate localDate) {
        var from = localDate.atStartOfDay();
        var to = from.plusDays(1L);
        return maxNormalizedRangeBetween(from, to);
    }

    public NormalizedRange maxNormalizedRangeBetween(LocalDateTime from, LocalDateTime to) {
        return normalizedRangesBetweenDates(from, to)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<NormalizedRange> normalizedRangesBetweenDates(LocalDateTime from, LocalDateTime to) {
        return convert(tickRepository.findSymbolMinMaxBetweenDates(from, to));
    }

    public List<NormalizedRange> convert(List<SymbolMinMax> symbolMinMaxList) {
        return symbolMinMaxList
                .stream()
                .map(symbolMinMax -> NormalizedRange.builder()
                        .symbol(symbolMinMax.symbol())
                        .normalizedRange(normalize(symbolMinMax.min(), symbolMinMax.max()))
                        .build()
                )
                .sorted(Comparator.comparing(NormalizedRange::normalizedRange).reversed())
                .toList();
    }

    private BigDecimal normalize(BigDecimal min, BigDecimal max) {
        return max.subtract(min).divide(max, PRICE_SCALE, RoundingMode.HALF_UP);
    }
}
