package software.ulpgc.Kata7.application;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import software.ulpgc.Kata7.viewModel.Histogram;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static MainFrame create(){
        return new MainFrame();
    }

    private MainFrame() throws HeadlessException {
        this.setTitle("Histogram kata4");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public MainFrame display(Histogram histogram){
        this.getContentPane().add(addChartPanelWith(histogram));
        return this;
    }

    private ChartPanel addChartPanelWith(Histogram histogram) {
        return new ChartPanel(chartPanelWith(histogram));
    }

    private JFreeChart chartPanelWith(Histogram histogram) {
        return ChartFactory.createHistogram(
                histogram.title(),
                histogram.x(),
                histogram.y(),
                datasetOf(histogram)
        );
    }

    private XYSeriesCollection datasetOf(Histogram histogram) {
        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(seriesOf(histogram));
        return collection;
    }

    private XYSeries seriesOf(Histogram histogram) {
        XYSeries series = new XYSeries(histogram.legend());
        histogram.forEach(key -> series.add((double) key, histogram.count(key)));
        return series;
    }

}
