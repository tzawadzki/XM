package com.xm.service;

import static com.xm.service.TestUtils.FILE_BTC;
import static com.xm.service.TestUtils.getFileInputStream;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.xm.configuration.XmConstants;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class OHLCServiceTests {

    @Autowired
    private OHLCService ohlcService;

    @Autowired
    private UploadService uploadService;

    @Test
    void testOHLC() {
        uploadService.uploadFile(getFileInputStream(FILE_BTC));

        var ohlc = ohlcService.getOHLC("BTC");

        assertThat(ohlc).isNotNull();
        assertThat(ohlc.open()).isEqualTo(new BigDecimal("46813.21").setScale(XmConstants.PRICE_SCALE));
        assertThat(ohlc.high()).isEqualTo(new BigDecimal("47722.66").setScale(XmConstants.PRICE_SCALE));
        assertThat(ohlc.low()).isEqualTo(new BigDecimal("33276.59").setScale(XmConstants.PRICE_SCALE));
        assertThat(ohlc.close()).isEqualTo(new BigDecimal("38415.79").setScale(XmConstants.PRICE_SCALE));
    }

    // TODO add more tests cases
    @Test
    void testOHLCBetweenDates() {
        uploadService.uploadFile(getFileInputStream(FILE_BTC));

        var ohlc = ohlcService.getOHLC("BTC", LocalDateTime.parse("2022-01-01T04:00"), LocalDateTime.parse("2022-01-01T07:00"));

        assertThat(ohlc).isNotNull();
        assertThat(ohlc.open()).isEqualTo(new BigDecimal("46813.210000").setScale(XmConstants.PRICE_SCALE));
        assertThat(ohlc.high()).isEqualTo(new BigDecimal("46813.210000").setScale(XmConstants.PRICE_SCALE));
        assertThat(ohlc.low()).isEqualTo(new BigDecimal("46813.210000").setScale(XmConstants.PRICE_SCALE));
        assertThat(ohlc.close()).isEqualTo(new BigDecimal("46813.210000").setScale(XmConstants.PRICE_SCALE));
    }
}
