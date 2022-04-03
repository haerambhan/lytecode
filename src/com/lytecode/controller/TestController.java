package com.lytecode.controller;

import java.util.Set;

import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import database.DBUtil;
import model.Test;

@RestController
public class TestController {
	
	@RequestMapping("/api/v2/tests")
	public String getTests() throws Exception {
		Set<Test> tests = DBUtil.getInstance().getTests();
		return new JSONArray(tests).toString();
	}

}