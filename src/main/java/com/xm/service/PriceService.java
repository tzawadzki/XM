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
import com.xm.exception.XMException;
import com.xm.repository.PriceRepository;
import com.xm.repository.model.SymbolMinMax;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public List<NormalizedRange> normalizedRanges() {
        return convert(priceRepository.findSymbolMinMax());
    }

    public NormalizedRange maxNormalizedRangeForDay(LocalDate localDate) {
        var from = localDate.atStartOfDay();
        var to = from.plusDays(1L);
        return normalizedRangesBetweenDates(from, to)
                .stream()
                .findFirst()
                .orElseThrow(() -> new XMException("No values for date: " + localDate));
    }

    public List<NormalizedRange> normalizedRangesBetweenDates(LocalDateTime from, LocalDateTime to) {
        return convert(priceRepository.findSymbolMinMaxBetweenDates(from, to));
    }

    public List<NormalizedRange> convert(List<SymbolMinMax> symbolMinMaxList) {
        return symbolMinMaxList
                .stream()
                .map(symbolMinMax -> NormalizedRange.builder()
                        .symbol(symbolMinMax.symbol())
                        .normalizedRange(normalize(symbolMinMax.min(), symbolMinMax.max()))
                        .build()
                )
                .sorted(Comparator.comparing(NormalizedRange::getNormalizedRange).reversed())
                .toList();
    }

    private BigDecimal normalize(BigDecimal min, BigDecimal max) {
        return max.subtract(min).divide(max, PRICE_SCALE, RoundingMode.HALF_UP);
    }
}
