package informatik2.statikdemo;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author stephan
 */
public class Plotter
{  
    private final NumberAxis x_achsen_name;
    private final NumberAxis y_achsen_name;
    private final XYSplineRenderer spline_renderer;
    private XYSeriesCollection daten_kollection;
    private final String fenster_name;
    
    
    public Plotter(int punkt_groesse, String x_achsen_name, String y_achsen_name, String fenster_name) 
            throws Exception
    {
        if(x_achsen_name.isEmpty())
            throw new Exception("X-Achsen-Name darf nicht leer sein!");
        if(y_achsen_name.isEmpty())
            throw new Exception("Y-Achsen-Name darf nicht leer sein!");
        
        if(punkt_groesse <= 0)
            throw new Exception("Punktgröße muss größer 0 sein!");
        
        if(fenster_name.isEmpty())
            throw new Exception("Fenstername darf nicht leer sein!");
        this.x_achsen_name = new NumberAxis(x_achsen_name);
        this.y_achsen_name = new NumberAxis(y_achsen_name);
        spline_renderer = new XYSplineRenderer();
        this.fenster_name = fenster_name;
        daten_kollection = new XYSeriesCollection();
        
    }
    
    public void addDataSet(double[] daten, String name)
    {
        XYSeries serie = new XYSeries(name);
        for(int punkt_index = 0; punkt_index < daten.length; punkt_index ++)
        {
            serie.add(punkt_index, daten[punkt_index]);
        }
        daten_kollection.addSeries(serie);
    }
    
    public void plot() throws Exception
    {
        if(daten_kollection.getSeriesCount() == 0)
            throw new Exception("Kein Datenset zum Anzeigen!");
        
        ApplicationFrame frame = new ApplicationFrame(fenster_name);
        XYPlot plot = new XYPlot(daten_kollection, x_achsen_name, y_achsen_name, spline_renderer);
        JFreeChart chart = new JFreeChart(plot);
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
