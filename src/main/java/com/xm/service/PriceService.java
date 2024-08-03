package com.xm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xm.dto.NormalizedRange;
import com.xm.repository.PriceRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public List<NormalizedRange> normalizedRanges() {

        return priceRepository.findSymbolMinMax()
                .stream()
                .map(symbolMinMax -> NormalizedRange.builder()
                        .symbol(symbolMinMax.symbol())
                        .highestNormalizedRange(symbolMinMax.max().subtract(symbolMinMax.min().divide(symbolMinMax.min())))
                        .build()
                ).toList();
    }
}
