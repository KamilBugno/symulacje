package miss;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.util.*;

public class CrossingsStatisticsHistogram extends ApplicationFrame {

    public CrossingsStatisticsHistogram(String applicationTitle, String chartTitle, List<Integer> carCrossingsStatistics) {
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Liczba skrzyżowań",
                "Liczba aut",
                createDataset(carCrossingsStatistics),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);

    }

    private CategoryDataset createDataset(List<Integer> carCrossingsStatistics) {
        final String fiat = "Auta, które pokonały daną liczbę skrzyżowań";

        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        Collections.sort(carCrossingsStatistics);

        List<Integer> carCrossingsStatisticsWithoutDuplicates = new ArrayList<>(carCrossingsStatistics);
        Set<Integer> hs = new HashSet<>();
        hs.addAll(carCrossingsStatisticsWithoutDuplicates);
        carCrossingsStatisticsWithoutDuplicates.clear();
        carCrossingsStatisticsWithoutDuplicates.addAll(hs);
        Collections.sort(carCrossingsStatisticsWithoutDuplicates);

        for(int i = 0; i < carCrossingsStatisticsWithoutDuplicates.size(); i++){
            dataset.addValue(Collections.frequency(carCrossingsStatistics,
                    carCrossingsStatisticsWithoutDuplicates.get(i)),
                    fiat,
                    Integer.toString(carCrossingsStatisticsWithoutDuplicates.get(i)));
        }
        return dataset;
    }


}