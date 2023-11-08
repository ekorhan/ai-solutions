package com.ekorhan.aisolutions.rest;


import com.ekorhan.aisolutions.api.apifootball.ApiFootballProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/dataProvider")
public class DataProviderController {
    @GetMapping("apiFootball")
    public void fetchWithApiFootball(@PathVariable int numberOfPage) throws IOException, InterruptedException {
        ApiFootballProvider.saveBulkPlayer(numberOfPage);
    }
}
