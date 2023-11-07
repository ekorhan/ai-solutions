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

    private HashMap<Integer, Double> getCenterCoordinate() {
        HashMap<Integer, Double> center = new HashMap<>();
        for (Input row : data) {
            for (int j = 0; j < row.getValues().size(); j++) {
                center.putIfAbsent(j, 0d);
                center.put(j, center.get(j) + row.getValues().get(j) / numberOfData);
            }
        }
        return center;
    }

    private Integer[] findMostFarDistance(List<Integer> ignores) {
        double maxDistance = Double.MIN_VALUE;
        Integer[] maxIndex = {null, null};
        for (int i = 0; i < data.size() - 1; i++) {
            if (ignores.contains(i)) {
                continue;
            }
            List<Double> aData = data.get(i).getValues();
            for (int j = i + 1; j < data.size(); j++) {
                if (ignores.contains(j)) {
                    continue;
                }
                List<Double> bData = data.get(j).getValues();
                double distance = distanceForTwoPoint(aData, bData);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    maxIndex[0] = i;
                    maxIndex[1] = j;
                }
            }
        }
        return maxIndex;
    }

    private Map<Integer[], Double> findAllDistance() {
        Map<Integer[], Double> distances = new HashMap<>();
        for (int i = 0; i < data.size() - 1; i++) {
            List<Double> aData = data.get(i).getValues();
            for (int j = i + 1; j < data.size(); j++) {
                List<Double> bData = data.get(j).getValues();
                double distance = distanceForTwoPoint(aData, bData);
                Integer[] key = {i, j};
                distances.put(key, distance);
            }
        }
        return distances;
    }

    private List<Integer[]> findMostFarKPoint() {
        return findAllDistance()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer[], Double>comparingByValue().reversed())
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
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

    public HashMap<Integer, Double> getCenter() {
        return center;
    }

    private List<Double> midPointByTwoCoordinate(List<Double> a, List<Double> b) {
        int size = a.size();
        if (size != b.size()) {
            throw new IllegalStateException("");
        }
        List<Double> midPoint = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double aVal = a.get(i);
            double bVal = b.get(i);
            double mid = (aVal + bVal) / 2;
            midPoint.add(mid);
        }
        return midPoint;
    }
}
