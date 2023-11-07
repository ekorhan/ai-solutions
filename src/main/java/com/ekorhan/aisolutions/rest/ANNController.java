package com.ekorhan.aisolutions.rest;

import com.ekorhan.aisolutions.ann.equation_solution.Execution;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ANNController {
    @GetMapping
    public List<String> getResult() {
        Execution execution = new Execution();
        return execution.getResult();
    }
}
