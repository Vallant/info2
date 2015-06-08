/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informatik2.statikdemo;

import informatik2.statik.LKW;
import informatik2.statik.Querschnitt;
import informatik2.statik.Traeger;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;


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
         
        Scanner scan = new Scanner (System.in);
        //input
        System.out.print("Geben Sie das Gesamtgewicht des Zweiachsers ein [5 - 10 t]: ");
        double gesamt_gewicht_in = scan.nextDouble();

        System.out.print("Geben Sie den Achsabstand des Zweiachsers ein [4 - 10 m]: ");
        double achsenabstand_in = scan.nextDouble();

        System.out.print("Geben Sie die Länge der Brücke ein [5 - 20 m]: ");
        double laenge_bruecke_in = scan.nextDouble();

        scan.nextLine(); /* !!! */

        System.out.print("Geben Sie die Querschnittsmaße des Trägers ein [b h s t (in cm)]: ");
        String querschnitt_in = scan.nextLine();

        String parts[] = querschnitt_in.split(" ");

        double b_in = Double.parseDouble(parts[0]);
        double h_in = Double.parseDouble(parts[1]);
        double s_in = Double.parseDouble(parts[2]);
        double t_in = Double.parseDouble(parts[3]);
       
        //fill objects

        lkw = new LKW(1, 1, gesamt_gewicht_in, "X", "y");        
        Querschnitt querschnitt = new Querschnitt (b_in, h_in, s_in, t_in); 
        traeger = new Traeger(1, 1, querschnitt);
        
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
