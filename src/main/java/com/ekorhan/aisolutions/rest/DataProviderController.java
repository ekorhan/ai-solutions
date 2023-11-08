package com.ekorhan.aisolutions.rest;


import com.ekorhan.aisolutions.api.apifootball.ApiFootballPlayersResponse;
import com.ekorhan.aisolutions.api.apifootball.ApiFootballProvider;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/dataProvider")
public class DataProviderController {
    private final ApiFootballProvider apiFootballProvider;

    public DataProviderController(ApiFootballProvider apiFootballProvider) {
        this.apiFootballProvider = apiFootballProvider;
    }

    @GetMapping("apiFootballManually")
    public void fetchWithApiFootballManually(@RequestParam("nop") int numberOfPage) throws IOException, InterruptedException {
        apiFootballProvider.saveBulkPlayer(numberOfPage);
    }

    @GetMapping("apiTest")
    public ApiFootballPlayersResponse apiTest() throws IOException, InterruptedException {
        return apiFootballProvider.getPlayers(1);
    }
}
