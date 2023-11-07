package com.ekorhan.aisolutions.kmeans;

import com.ekorhan.aisolutions.kmeans.model.Input;

import java.util.List;
import java.util.Random;

public class Method2 extends KMeans {
    public Method2(List<Input> data, int k) {
        super(data, k);
    }

    @Override
    public void calculateFirstKCoordinates() {
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            int nextInt = random.nextInt(Long.valueOf(numberOfData).intValue());
            Input randomInput = data.get(nextInt);
            Double[] cord = new Double[randomInput.getValues().size()];
            for (int j = 0; j < randomInput.getValues().size(); j++) {
                cord[j] = randomInput.getValues().get(j);
            }
            kCoordinates.add(cord);
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
