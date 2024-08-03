package com.xm.controller;

import java.io.IOException;
import java.time.LocalDate;
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

	@RequestMapping(value = "/normalizedRanges", method = RequestMethod.GET)
	public List<NormalizedRange> normalizedRanges() {
		return priceService.normalizedRanges();
	}

	@RequestMapping(value = "/maxNormalizedRangeForDay", method = RequestMethod.GET)
	public NormalizedRange maxNormalizedRangeForDay(@RequestParam(value = "date") LocalDate date) {
		return priceService.maxNormalizedRangeForDay(date);
	}

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
