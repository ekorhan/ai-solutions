package com.ekorhan.aisolutions.kmeans;

import com.ekorhan.aisolutions.kmeans.model.Input;

import java.util.*;
import java.util.stream.Collectors;

public abstract class KMeans implements IKMeansExecution {
    protected final List<Input> data;
    protected final int k;
    protected final long numberOfData;
    protected List<Double[]> kCoordinates = new ArrayList<>();
    private Map<Integer, List<Input>> result = new HashMap<>();
    protected final HashMap<Integer, Double> center;

    public KMeans(List<Input> data, int k) {
        this.data = data;
        this.k = k;
        this.numberOfData = data.size();
        this.center = getCenterCoordinate();
        this.calculateFirstKCoordinates();
        this.placeInGroups();
        this.printResult();
    }

    protected void placeInGroups() {
        HashMap<Integer, List<Input>> result = new HashMap<>();
        boolean isChanged = false;
        for (Input row : data) {
            int flag = -1;
            double min = Double.MAX_VALUE;
            for (int i = 0; i < kCoordinates.size(); i++) {
                double distance = distanceForTwoPoint(row.getValues(), kCoordinates.get(i));
                if (distance < min) {
                    min = distance;
                    flag = i;
                }
            }
            result.putIfAbsent(flag, new ArrayList<>());
            result.get(flag).add(row);
            if (!this.result.isEmpty() && !isChanged) {
                isChanged = this.result.get(flag).stream().noneMatch(e -> e.getId() == row.getId());
            }
        }
        for (int i = 0; i < result.keySet().size(); i++) {
            this.result = result;
            if (!isChanged) {
                return;
            } else {
                placeInGroups();
            }
        }
    }

    private void printResult() {
        System.out.println(getClass().getSimpleName());
        for (Map.Entry<Integer, List<Input>> e : result.entrySet()) {
            System.out.printf("%s. Flag, id-> %s \n", e.getKey(), e.getValue().stream().map(v -> String.valueOf(v.getId())).collect(Collectors.toList()));
        }
        System.out.println("\n\n");
    }

    protected HashMap<Integer, Double> getCenterCoordinate() {
        HashMap<Integer, Double> center = new HashMap<>();
        for (Input row : data) {
            for (int j = 0; j < row.getValues().size(); j++) {
                center.putIfAbsent(j, 0d);
                center.put(j, center.get(j) + row.getValues().get(j) / numberOfData);
            }
        }
        return center;
    }

    protected List<Integer[]> findMostFarKPoint(int k) {
        return findAllDistance()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer[], Double>comparingByValue().reversed())
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    protected List<Integer[]> findMostFarKPoint() {
        return findMostFarKPoint(k);
    }

    protected Integer[] findMostFarDistance(List<Integer> ignores) {
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

    protected Map<Integer[], Double> findAllDistance() {
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

    protected List<Double> midPointByTwoCoordinate(List<Double> a, List<Double> b) {
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
