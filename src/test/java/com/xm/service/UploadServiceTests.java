package com.xm.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xm.repository.SymbolRepository;
import com.xm.repository.TickRepository;

@SpringBootTest
class UploadServiceTests {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private TickRepository tickRepository;

    @Test
    void testUpload() {
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/BTC_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/DOGE_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/ETH_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/LTC_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/XRP_values.csv"));

        assertThat(symbolRepository.count()).isEqualTo(5);
        assertThat(tickRepository.count()).isEqualTo(450);
    }

    // TODO add more test with uploading same file, inconsistent data, changed columns etc
}
