package com.lioncorp.dispatch.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.lioncorp.common.util.WebResult;

@Controller
@RequestMapping("/receive")
public class DispatchController {
    private static final Logger logger = LoggerFactory.getLogger(DispatchController.class);

	
	private static Gson gson = new Gson();
    
    @ResponseBody
    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    public WebResult videoNews(
            @RequestParam(value = "test1", required = false) String test1,
            @RequestParam(value = "test2", required = false) String test2) {
        WebResult result = new WebResult();
        result.setRequestId("");
        return result;
    }

}