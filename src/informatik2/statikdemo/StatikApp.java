/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informatik2.statikdemo;

import informatik2.statik.LKW;
import informatik2.statik.Querschnitt;
import informatik2.statik.Traeger;
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
    private boolean darfPassieren;
    static private final double erdBeschleunigung = 9.81; // m/s^2
    static private final double maxZulaessigBiegespannung = 300; // N/mm^2
    static private final double traegerDichte = 7.85; // kg/dm^3
    static private final double sicherheitEigenlast = 1.5;
    static private final double sicherheitVerkehrslast = 2;
    static private final double eigenLast = 3000; // N/m
    private double maxBiegespannung;
    
    
    private static int getCase(int position_va, int position_ha, int x, int laenge)
    {
        if     (position_ha < 0 && x < position_va) 
            return 1;
        else if(position_ha < 0 && x < position_va)
            return 2;
        else if(position_ha > 0 && position_va < laenge && x < position_ha)
            return 3;
        else if(position_ha > 0 && position_va < laenge && x > position_ha && x < position_va) 
            return 4;
        else if(position_ha > 0 && position_va < laenge && x > position_va)
            return 5;
        else if(position_va > laenge && x < position_ha)
            return 6;
        else if(position_va > laenge && x > position_ha)
            return 7;
        
        return 0;
    }
    
    
    
    public StatikApp() throws Exception
    {
                System.out.println("Programm zur Statikberechnung von Bruecken");
        System.out.println("=========================================\n");
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
        biegemoment_werte = new double[(int)(traeger.getLaenge()+
                        lkw.getAchsenAbstand())*10][(int)traeger.getLaenge()*10];
        
        
        position_max_biegemoment = -1;
        biegemoment_werte = new double[(int)(traeger.getLaenge()+
                        lkw.getAchsenAbstand())*10][(int)traeger.getLaenge()*10];
        
    }
    
    
    
    public void berechne()
    {
        
      
        int lkwlaenge = (int)lkw.getAchsenAbstand(); // LKW Länge
        int max_position = (int)(traeger.getLaenge()*10) + (lkwlaenge*10);
        int brueckenlaenge = (int)traeger.getLaenge();
        int position_va;
        int position_ha;
        int auswahl;
        double last = 0;
        double belastung = 0;

        int x = 0;
        
        for(position_va=0 ; position_va < max_position ; position_va+=10)
        {
            position_ha = position_va - lkwlaenge;
        
            for(x = 0 ; x < max_position ; x+=10)
            {
              last = ((position_va/lkwlaenge)* ((lkwlaenge-position_va) / lkwlaenge)) / 2 * 3000 * 
                       lkwlaenge*lkwlaenge;
                
              auswahl = StatikApp.getCase(position_va, position_ha, x, brueckenlaenge);
              switch(auswahl)
              {
                  case 1:
                      belastung = 0;
                  case 2:
                      belastung = 0;
                  case 3:
                      belastung = 0;
                  case 4:
                      belastung = 0;
                  case 5:
                      belastung = 0;
                  case 6:
                      belastung = 0;
                  case 7:
                      belastung = 0;
              }
              biegemoment_werte[position_va][x] = belastung;
              if(belastung > maxBiegespannung)
                  maxBiegespannung = belastung;
            }
           
        }
        
        if(maxBiegespannung > maxZulaessigBiegespannung)
            darfPassieren = false;
            
        


        plotter.addDataSet(biegemoment_werte[position_max_biegemoment], "Biegemomente");
    }
    
    public void print()
            throws Exception
    {    
        
        System.out.println("\nGetaetigte Eingaben");
        System.out.println("==================\n");
        
        System.out.println("LKW");
        System.out.println("* Firma: "+lkw.getFirma());
        System.out.println("* Fahrer: " + lkw.getFahrer());
        System.out.println(String.format("* Gesamtgewicht: %.2f kg", lkw.getGesamtGewicht()));
        System.out.println(String.format("* Achsenabstand: %.2f m", lkw.getAchsenAbstand()));        
        System.out.println();
        
        System.out.println("Traeger");
        System.out.println(String.format("* Laenge: %.2f m", traeger.getLaenge()));
        System.out.println(String.format("* Gewicht: %.2f kg", traeger.getGewicht()));
        System.out.println("* Querschnitt");
        System.out.println(String.format("  * Hoehe: %.2f cm", traeger.getQuerschnitt().getHoehe()));
        System.out.println(String.format("  * Breite: %.2f cm", traeger.getQuerschnitt().getHoehe()));
        System.out.println(String.format("  * Stegbreite: %.2f cm", traeger.getQuerschnitt().getStegBreite()));
        System.out.println(String.format("  * Steghoehe: %.2f cm", traeger.getQuerschnitt().getStegBreite()));
        
        System.out.println("Berechnungskonstanten");
        System.out.println("=====================\n");
        
        System.out.println(String.format("Erdbeschleunigung: %.2f m/s^2", erdBeschleunigung));
        System.out.println(String.format("Traegerdichte: %.2f kg/dm^3", traegerDichte));
        System.out.println(String.format("Sicherheit Eigenlast: %.2f", sicherheitEigenlast));
        System.out.println(String.format("Sicherheit Verkehrslast: %.2f", sicherheitVerkehrslast));
        System.out.println(String.format("Eigenlast: %.2f N/m", eigenLast));
        
        berechne();
        if(position_max_biegemoment == -1)
            throw new Exception("Fehler bei der Berechnung!");
        
        System.out.println("Berechnungsergebnisse");
        System.out.println("=====================\n");
        
        System.out.println(String.format("Gewichtskraft Hinterachse: %.2f N", lkw.achsLastHA() * erdBeschleunigung));
        System.out.println(String.format("Gewichtskraft Vorderachse: %.2f N", lkw.achsLastVA() * erdBeschleunigung));
        System.out.println(String.format("Querschnittsflaeche: %.2f mm^2", traeger.getQuerschnitt().getFlaeche()));
        System.out.println(String.format("Flächentraegheitsmoment: %.2f mm^4", traeger.getQuerschnitt().getIy()));
        System.out.println(String.format("Maximale Biegespannung: %.2f N/mm^2", maxBiegespannung));
        System.out.println(String.format("Vorderachsenposition bei dem auftretenden Biegemoment: %.2f m", position_max_biegemoment / 10.0));
        
        if(darfPassieren)
        {
            System.out.println("LKW darf passieren!");
        }
        else
        {
            System.out.println("LKW darf *nicht* passieren!");
        }
        
        System.out.println("Beliebige Taste druecken, um das Diagramm anzuzeigen...");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        plotter.plot();
    }
    
    public static void main(String[] args)
    {
        try
        {
            StatikApp app = new StatikApp();
            app.print();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    
}
