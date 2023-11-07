package com.ekorhan.aisolutions.kmeans.model;

import lombok.Data;

import java.util.List;
import java.util.Random;

@Data
public class Input {
    private long id;
    private String name;
    private List<Double> values;

    public Input(List<Double> values) {
        id = new Random().nextInt(1000);
        this.values = values;
    }

    public Input(long id, String name, List<Double> values) {
        this.id = id;
        this.name = name;
        this.values = values;
    }
}
