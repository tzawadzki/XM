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
import com.xm.exception.XMException;
import com.xm.service.PriceService;
import com.xm.service.UploadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/xm")
public class XMController {

	private final UploadService uploadService;
	private final PriceService priceService;

	/**
	 * Endpoint that will return a descending sorted list of all the cryptos,
	 * comparing the normalized range (i.e. (max-min)/min)
	 */
	@RequestMapping(value = "/normalizedRanges", method = RequestMethod.GET)
	public List<NormalizedRange> normalizedRanges() {
		return priceService.normalizedRanges();
	}

	/**
	 * Endpoint that will return the crypto with the highest normalized range for a
	 * specific day
	 * @param date
	 * @return
	 */
	@RequestMapping(value = "/maxNormalizedRangeForDay", method = RequestMethod.GET)
	public NormalizedRange maxNormalizedRangeForDay(@RequestParam(value = "date") LocalDate date) {
		return priceService.maxNormalizedRangeForDay(date);
	}

	/**
	 * Uploads csv file with timestamp, symbol, column values
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFiles(@RequestParam("file") MultipartFile file) {
      try {
          uploadService.uploadFile(file.getInputStream());
      } catch (IOException e) {
          throw new XMException(e);
      }
      return "OK";
	}

	// additional endpoints

	/**
	 * Endpoint that will return a descending sorted list of all the cryptos,
	 * comparing the normalized range (i.e. (max-min)/min)
	 * for a specific period of time
	 */
	@RequestMapping(value = "/normalizedRangesBetween", method = RequestMethod.GET)
	public List<NormalizedRange> normalizedRanges(@RequestParam(value = "from") LocalDateTime from, @RequestParam(value = "from") LocalDateTime to) {
		return priceService.normalizedRangesBetweenDates(from, to);
	}

	/**
	 * Endpoint that will return the crypto with the highest normalized range for a
	 * specific period of time
	 */
	@RequestMapping(value = "/maxNormalizedRangeBetween", method = RequestMethod.GET)
	public NormalizedRange maxNormalizedRangeBetween(@RequestParam(value = "from") LocalDateTime from, @RequestParam(value = "from") LocalDateTime to) {
		return priceService.maxNormalizedRangeBetween(from, to);
	}
}
