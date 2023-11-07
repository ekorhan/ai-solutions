package com.ekorhan.aisolutions.ann.equation_solution;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

import java.util.ArrayList;
import java.util.List;

public class Execution {
    public List<String> getResult() {
        // Eğitim verilerini oluştur
        //(2x^2-3x+10)
        double[][] girdiVerileri = {{-1}, {0}, {1}, {10}};
        double[][] ciktiVerileri = {{15}, {10}, {9}, {180}};
        BasicMLDataSet veriSeti = new BasicMLDataSet(girdiVerileri, ciktiVerileri);

        // Yapay sinir ağı modelini oluştur
        BasicNetwork agModeli = new BasicNetwork();
        agModeli.addLayer(new BasicLayer(null, true, 1));
        agModeli.addLayer(new BasicLayer(new ActivationSigmoid(), true, 4));
        agModeli.addLayer(new BasicLayer(new ActivationLinear(), false, 1));
        agModeli.getStructure().finalizeStructure();
        agModeli.reset();

        // Geri yayılım algoritmasıyla ağı eğit
        Backpropagation egitici = new Backpropagation(agModeli, veriSeti);
        int epoch = 1;
        do {
            egitici.iteration();
            epoch++;
            if (epoch > 1000000)
                break;
        } while (egitici.getError() > 0.01);
        egitici.finishTraining();

        // Eğitim sonucunda elde edilen modeli kullanarak tahmin yap
        double tahminEdilenX0 = agModeli.compute(new BasicMLData(new double[]{-1})).getData(0);
        double tahminEdilenX1 = agModeli.compute(new BasicMLData(new double[]{1})).getData(0);
        double tahminEdilenX2 = agModeli.compute(new BasicMLData(new double[]{2})).getData(0);
        double tahminEdilenX3 = agModeli.compute(new BasicMLData(new double[]{3})).getData(0);
        for (double d : agModeli.compute(new BasicMLData(new double[]{-1})).getData())
            System.out.println("d: " + d);

        List<String> response = new ArrayList<>();
        response.add("Denklemin çözümü: x0 = " + tahminEdilenX0 + "\n");
        response.add("Denklemin çözümü: x1 = " + tahminEdilenX1 + "\n");
        response.add("Denklemin çözümü: x2 = " + tahminEdilenX2 + "\n");
        response.add("Denklemin çözümü: x3 = " + tahminEdilenX3 + "\n");
        System.out.println("Denklemin çözümü: x0 = " + tahminEdilenX0);
        System.out.println("Denklemin çözümü: x1 = " + tahminEdilenX1);
        System.out.println("Denklemin çözümü: x2 = " + tahminEdilenX2);
        System.out.println("Denklemin çözümü: x3 = " + tahminEdilenX3);

        Encog.getInstance().shutdown();

        return response;
    }
}
