/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informatik2.statikdemo;

import informatik2.statik.LKW;
import informatik2.statik.Querschnitt;
import informatik2.statik.Traeger;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author stephan
 */
public class StatikApp
{
    private Plotter plotter;
    private final Traeger traeger;
    private final LKW lkw;
    private double[][] biegemoment_werte;
    private int position_max_biegemoment;
    
    
    public StatikApp() throws Exception
    {
        try
        {
            plotter = new Plotter(5, "Brückenposition in m", "Biegemoment in kNm",
                    "Diagramm zum maximalen Biegemoment");
        }
        catch (Exception ignore) { }
        
        //Lukas Part
        //testgit
        Querschnitt querschnitt = new Querschnitt(1, 1, 1, 1);
        traeger = new Traeger(1, 1, querschnitt);
        lkw = new LKW(1, 1, 1, "X", "y");
        position_max_biegemoment = -1;
    }
    
    
    public void berechne()
    {
        //Phillip part
        //....
        
        
        plotter.addDataSet(biegemoment_werte[position_max_biegemoment], "Biegemomente");
    }
    
    public void print() throws Exception
    {
        //Phillip part
        //....
        
        plotter.plot();
    }
    
    public static void main(String[] args)
    {
        try
        {
            StatikApp app = new StatikApp();
            app.berechne();
            app.print();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    
}
