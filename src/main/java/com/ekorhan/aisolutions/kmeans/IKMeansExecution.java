package com.ekorhan.aisolutions.kmeans;

import java.util.List;

public interface IKMeansExecution {
    void calculateFirstKCoordinates();

    double distanceForTwoPoint(List<Double> a, List<Double> b);

    double distanceForTwoPoint(List<Double> a, Double[] b);
}
