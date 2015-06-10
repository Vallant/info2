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
    private final Querschnitt querschnitt;
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

        lkw = new LKW(4.0, 2.6, gesamt_gewicht_in, "LKW Walter", "Josef", achsenabstand_in);        
        querschnitt = new Querschnitt (b_in, h_in, s_in, t_in); 
        traeger = new Traeger(laenge_bruecke_in, 7.85, querschnitt);
        
        position_max_biegemoment = -1;
        
    }
    
    
    public void berechne()
    {
        
        double M_D;
        double x;
        double L;
        double q;
        
        M_D = ((x/L) * ((L-x) / L)) / 2 * q * L*L; 
        
        
        plotter.addDataSet(biegemoment_werte[position_max_biegemoment], "Biegemomente");
    }
    
    public void print() throws Exception
    {
        //Phillip part
        //....
        System.out.println("----------Statikberechnung für:");
        
        lkw.toString();
        System.out.println("auf Brücke: Bruecke --> konst. Fahrbahndeckenlast: ??" + "Länge: " + traeger.getLaenge() + "m."); // Fahrbahndeckenlast?!
        System.out.println("Gesamt-Gleichlast: ??"); //Gesamtgleichlast?!
        querschnitt.toString();
        
        /* BEISPIEL TEXT
        
        LKW: LKW --> Achsabstand: 6,0m, Firma: BauInc, Fahrer: Max Mustermann, Last-Vorderachse: 32,70kN, Last-Hinterachse: 65,40kN.
	Fahrzeug --> Höhe: 3,8m, Breite: 2,6m, Gesamtgewicht: 10000,00kg.
	auf Brücke: Bruecke --> konst. Fahrbahndeckenlast: 3,00kN/m, Länge: 20,0m.
	Gesamt-Gleichlast: 6,15kN/m.
	Trägerpaar: Träger --> Länge: 20,0m, Dichte: 7,85kg/dm^3, Masse: 2242,27kg.
	Querschnitt --> b: 30,0cm, h: 30,0cm, s: 1,1cm, t: 1,9cm
	Querschnittsfläche: 142,82cm^2, Flächenträgheitsmoment: 24186,78cm^4.

        MaxMomente= 702,90kNm  bei Brückenposition= 9,40m  (LKW-VA Position=15,40m)
        Max. Biegespannung ist: 435,92N/mm^2
        LKW darf Brücke NICHT befahren!!
        
        */
                
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
