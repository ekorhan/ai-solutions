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

    public KMeans(List<Input> data, int k) {
        this.data = data;
        this.k = k;
        this.numberOfData = data.size();
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
        for (Map.Entry<Integer, List<Input>> e : result.entrySet()) {
            System.out.printf("%s. Flag, id-> %s \n", e.getKey(), e.getValue().stream().map(v -> String.valueOf(v.getId())).collect(Collectors.toList()));
        }
        System.out.println("\n\n");
    }
}
