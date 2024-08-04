package com.xm.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xm.dto.NormalizedRange;
import com.xm.dto.SymbolOHLC;
import com.xm.exception.XMException;
import com.xm.service.BarService;
import com.xm.service.PriceService;
import com.xm.service.UploadService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/xm")
public class XMController {

	private final UploadService uploadService;
	private final PriceService priceService;
	private final BarService barService;

	@Operation(summary = "Returns a descending sorted list of all the cryptos, comparing the normalized range (i.e. (max-min)/min)")
	@RequestMapping(value = "/normalizedRanges", method = RequestMethod.GET)
	public List<NormalizedRange> normalizedRanges() {
		return priceService.normalizedRanges();
	}

	@Operation(summary = "Returns a descending sorted list of all the cryptos, comparing the normalized range (i.e. (max-min)/min) for a specific period of time")
	@RequestMapping(value = "/normalizedRanges/betweenDates", method = RequestMethod.GET)
	public List<NormalizedRange> normalizedRangesBetweenDates(@RequestParam(value = "from") LocalDateTime from,
																														@RequestParam(value = "to") LocalDateTime to) {
		return priceService.normalizedRangesBetweenDates(from, to);
	}

	@Operation(summary = "Returns the crypto with the highest normalized range for a specific day")
	@RequestMapping(value = "/maxNormalizedRange/day", method = RequestMethod.GET)
	public NormalizedRange maxNormalizedRangeForDay(@RequestParam(value = "date") LocalDate date) {
		return priceService.maxNormalizedRangeForDay(date);
	}

	@Operation(summary = "Returns the crypto with the highest normalized range for a specific period of time")
	@RequestMapping(value = "/maxNormalizedRange/betweenDates", method = RequestMethod.GET)
	public NormalizedRange maxNormalizedRangeBetween(@RequestParam(value = "from") LocalDateTime from,
																									 @RequestParam(value = "to") LocalDateTime to) {
		return priceService.maxNormalizedRangeBetween(from, to);
	}

	@Operation(summary = "Returns the oldest/newest/min/max values for a requested crypto")
	@RequestMapping(value = "/ohlc", method = RequestMethod.GET)
	public SymbolOHLC bar(@RequestParam(value = "symbol") String symbol) {
		return barService.bar(symbol);
	}

	@Operation(summary = "Returns the oldest/newest/min/max values for a specific period of time")
	@RequestMapping(value = "/ohlc/betweenDates", method = RequestMethod.GET)
	public SymbolOHLC bar(@RequestParam(value = "symbol") String symbol,
												@RequestParam(value = "from") LocalDateTime from,
												@RequestParam(value = "to") LocalDateTime to) {
		return barService.bar(symbol, from, to);
	}

	@Operation(summary = "Uploads csv file with timestamp, symbol, column values")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFiles(@RequestParam("file") MultipartFile file) {
      try {
          uploadService.uploadFile(file.getInputStream());
      } catch (IOException e) {
          throw new XMException(e);
      }
      return "OK";
	}

}
