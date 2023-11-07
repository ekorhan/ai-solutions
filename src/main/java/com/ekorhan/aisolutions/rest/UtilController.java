package com.ekorhan.aisolutions.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("util")
public class UtilController {
    @GetMapping("title")
    public String getTitle() {
        return "MEK";
    }
}
