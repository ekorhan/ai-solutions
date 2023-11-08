package com.ekorhan.aisolutions.kmeans;

import com.ekorhan.aisolutions.kmeans.model.Input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Method1 extends KMeans {

    private final HashMap<Integer, Double> center;

    public Method1(List<Input> data, int k) {
        super(data, k);
        center = getCenterCoordinate();
    }

    @Override
    public void calculateFirstKCoordinates() {
        for (Integer[] far : findMostFarKPoint()) {
            List<Double> mid = midPointByTwoCoordinate(data.get(far[0]).getValues(), data.get(far[1]).getValues());
            kCoordinates.add(mid.toArray(new Double[0]));
        }
    }

    @Override
    public double distanceForTwoPoint(List<Double> a, List<Double> b) {
        if (a.size() != b.size()) {
            throw new IllegalStateException("data is not same size");
        }
        int size = a.size();
        double totalDiff = 0d;
        for (int i = 0; i < size; i++) {
            double aVal = a.get(i);
            double bVal = b.get(i);
            double diff = Math.abs(aVal - bVal);
            totalDiff += Math.pow(diff, 2);
        }
        return Math.pow(totalDiff, 0.5);
    }

    @Override
    public double distanceForTwoPoint(List<Double> a, Double[] b) {
        if (a.size() != b.length) {
            throw new IllegalStateException("data is not same size");
        }
        int size = a.size();
        double totalDiff = 0d;
        for (int i = 0; i < size; i++) {
            double aVal = a.get(i);
            double bVal = b[i];
            double diff = Math.abs(aVal - bVal);
            totalDiff += Math.pow(diff, 2);
        }
        return Math.pow(totalDiff, 0.5);
    }
}
