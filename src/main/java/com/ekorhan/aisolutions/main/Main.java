package com.ekorhan.aisolutions.main;

import com.ekorhan.aisolutions.kmeans.Method1;
import com.ekorhan.aisolutions.kmeans.Method2;
import com.ekorhan.aisolutions.kmeans.model.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Double> a = new ArrayList<>(Arrays.asList(2d, 2d, 2d));
        List<Double> b = new ArrayList<>(Arrays.asList(4d, 4d, 4d));
        List<Double> c = new ArrayList<>(Arrays.asList(1d, 1d, 1d));
        List<Double> d = new ArrayList<>(Arrays.asList(9d, 9d, 9d));
        List<Double> e = new ArrayList<>(Arrays.asList(12d, 12d, 12d));
        List<Double> f = new ArrayList<>(Arrays.asList(3d, 3d, 3d));
        List<Double> g = new ArrayList<>(Arrays.asList(7d, 7d, 7d));
        List<Double> h = new ArrayList<>(Arrays.asList(5d, 5d, 5d));
        int k = 3;
        List<Input> data = new ArrayList<>(Arrays.asList(
                new Input(1, "a", a),
                new Input(2, "b", b),
                new Input(3, "c", c),
                new Input(4, "d", d),
                new Input(5, "e", e),
                new Input(6, "f", f),
                new Input(7, "g", g),
                new Input(8, "h", h)
        ));
        Method1 method1 = new Method1(data, k);
        Method2 method2 = new Method2(data, k);
    }
}
