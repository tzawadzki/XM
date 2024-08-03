package com.xm.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xm.repository.PriceRepository;
import com.xm.repository.SymbolRepository;

@SpringBootTest
class UploadServiceTests {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void testUpload() {
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/BTC_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/DOGE_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/ETH_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/LTC_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/XRP_values.csv"));

        assertThat(symbolRepository.count()).isEqualTo(5);
        assertThat(priceRepository.count()).isEqualTo(450);
    }

    // TODO add more test with uploading same file, inconsistent data, changed columns etc
}
