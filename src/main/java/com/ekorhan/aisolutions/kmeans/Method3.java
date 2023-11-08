package com.ekorhan.aisolutions.kmeans;

import com.ekorhan.aisolutions.kmeans.model.Input;

import java.util.*;

public class Method3 extends KMeans {

    public Method3(List<Input> data, int k) {
        super(data, k);
    }

    @Override
    public void calculateFirstKCoordinates() {
        List<Double> centerCoordinates = center.values().stream().toList();
        double far = Double.MIN_VALUE;
        List<Double> farCoordinate = new ArrayList<>();
        double near = Double.MAX_VALUE;
        List<Double> nearCoordinate = new ArrayList<>();
        for (Input i : data) {
            double distance = distanceForTwoPoint(i.getValues(), centerCoordinates);
            if (distance >= far) {
                far = distance;
                farCoordinate.clear();
                farCoordinate.addAll(i.getValues());
            }
            if (distance <= near) {
                near = distance;
                nearCoordinate.clear();
                nearCoordinate.addAll(i.getValues());
            }
        }
        Double[] arr = new Double[nearCoordinate.size()];
        arr = nearCoordinate.toArray(arr);
        kCoordinates.add(arr);

        arr = new Double[farCoordinate.size()];
        arr = farCoordinate.toArray(arr);
        kCoordinates.add(arr);

        if (k == 2) {
            return;
        }
        test(centerCoordinates, farCoordinate, nearCoordinate);
    }

    private void test(List<Double> a, List<Double> b, List<Double> c) {
        Random random = new Random();
        List<Double> all = new ArrayList<>(a);
        all.addAll(b);
        all.addAll(c);
        int size = all.size();
        for (int i = 2; i < k; i++) {
            List<Double> newK = new ArrayList<>();
            for (int j = 0; j < all.size() / 3; j++) {
                int numberIndex = random.nextInt(size);
                newK.add(all.get(numberIndex));
            }
            for (Double[] d : kCoordinates) {
                boolean isSame = true;
                for (int j = 0; j < d.length; j++) {
                    if (d[j].doubleValue() != newK.get(j)) {
                        isSame = false;
                    }
                }
                if (isSame) {
                    i--;
                } else {
                    Double[] arr = new Double[newK.size()];
                    arr = newK.toArray(arr);
                    kCoordinates.add(arr);
                }
            }
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
