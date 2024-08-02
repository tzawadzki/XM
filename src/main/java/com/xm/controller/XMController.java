package com.xm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/xm")
public class XMController {


	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "OK";
	}


}
