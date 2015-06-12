/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informatik2.statikdemo;

import informatik2.statik.LKW;
import informatik2.statik.Querschnitt;
import informatik2.statik.Traeger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
    static private final int anzahlTraeger = 2;
    private double maxBiegespannung;
    
    
    private static int getCase(int position_va, int position_ha, int x, int laenge)
    {
        if     (position_ha <= 0 && x <= position_va) 
            return 1;
        else if(position_ha <= 0 && x >  position_va)
            return 2;
        else if(position_ha > 0 && position_va <= laenge && x <= position_ha)
            return 3;
        else if(position_ha > 0 && position_va <= laenge && x >  position_ha && x < position_va) 
            return 4;
        else if(position_ha > 0 && position_va <= laenge && x >  position_va)
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
         
        Scanner scan = new Scanner (System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //input
        double gesamt_gewicht_in = 0;
        double achsen_abstand_in = 0;
        double laenge_bruecke_in = 0;
        double breite_in = 0;
        double hoehe_in = 0;
        double b_in = 0;
        double h_in = 0;
        double s_in = 0;
        double t_in = 0;
        String querschnitt_in = "";
        String fahrer_in = "";
        String firma_in = "";
        

        do
        {
            System.out.print("Geben Sie das Gesamtgewicht des Zweiachsers ein [5 - 10 t]: ");
            if(!scan.hasNextDouble())
            {
                scan.next();
                continue;
            }
            gesamt_gewicht_in = scan.nextDouble();
        }  
        while(gesamt_gewicht_in < 5 || gesamt_gewicht_in > 10);
        
        do
        {
            System.out.print("Geben Sie den Achsabstand des Zweiachsers ein [4 - 10 m]: ");
            if(!scan.hasNextDouble())
            {
                scan.next();
                continue;
            }
            achsen_abstand_in = scan.nextDouble();
        }
        while (achsen_abstand_in < 4 || achsen_abstand_in > 10);
        
        do
        {
            System.out.print("Geben Sie die Breite des Fahrzeugs ein [1 - 5 m]: ");
            if(!scan.hasNextDouble())
            {
                scan.next();
                continue;
            }
            breite_in = scan.nextDouble();
        }
        while (breite_in < 1 || breite_in > 5);
        
                do
        {
            System.out.print("Geben Sie die Hoehe des Fahrzeugs ein [1 - 10 m]: ");
            if(!scan.hasNextDouble())
            {
                scan.next();
                continue;
            }
            hoehe_in = scan.nextDouble();
        }
        while (hoehe_in < 1 || hoehe_in > 10);
        
        do
        {
            System.out.print("Geben Sie die Länge der Brücke ein [5 - 20 m]: ");
            if(!scan.hasNextDouble())
            {
                scan.next();
                continue;
            }
            laenge_bruecke_in = scan.nextDouble();
        }
        while (laenge_bruecke_in < 5 || laenge_bruecke_in > 20);
        
        do
        {
            System.out.print("Geben Sie die Querschnittsmaße des Trägers ein [b h s t (in cm)]: ");
            querschnitt_in = in.readLine();

            String parts[] = querschnitt_in.split(" ");

            try
            {
                b_in = Double.parseDouble(parts[0]);
             h_in = Double.parseDouble(parts[1]);
             s_in = Double.parseDouble(parts[2]);
             t_in = Double.parseDouble(parts[3]);
            }
            catch(  NumberFormatException | ArrayIndexOutOfBoundsException ex)
            {}
        } while (b_in <= 0 || h_in <= 0 || s_in <= 0 || t_in <= 0 || b_in <= s_in || h_in <= 2*t_in);
        
        do
        {
            System.out.print("Geben Sie den Namen des Fahrers ein: ");
            fahrer_in = in.readLine();
        } while (fahrer_in.isEmpty());
        
        do
        {
            System.out.print("Geben Sie den Namen der Firma ein: ");
            firma_in = in.readLine();
        } while (firma_in.isEmpty());
        
        //fill objects

        lkw = new LKW(hoehe_in, breite_in, gesamt_gewicht_in * 1000, firma_in, fahrer_in, achsen_abstand_in);        
        querschnitt = new Querschnitt (b_in, h_in, s_in, t_in); 
        traeger = new Traeger(laenge_bruecke_in, traegerDichte, querschnitt);
        
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
        
        // N
        // * 10 : Weil brueckenlaenge in m, traegerDichte aber in kg / dm^3
        // / 10000 : Weil flaeche in mm^2, aber traegerDichte in kg / dm^3
        double gewichtskraft_traeger = traegerDichte * erdBeschleunigung * brueckenlaenge * 10 * traeger.getQuerschnitt().getFlaeche() / 10000;
;
        // N/m
        double streckenlast_traeger_gesamt = (eigenLast + gewichtskraft_traeger / brueckenlaenge) * sicherheitEigenlast;
        double p_z_va = lkw.achsLastVA() * erdBeschleunigung * sicherheitVerkehrslast / anzahlTraeger;
        double p_z_ha = lkw.achsLastHA() * erdBeschleunigung * sicherheitVerkehrslast / anzahlTraeger;
        
        for(position_va=0 ; position_va < max_position ; position_va+=10)
        {
            position_ha = position_va - lkwlaenge;
            last = ((position_va/lkwlaenge)* ((lkwlaenge-position_va) / lkwlaenge)) / 2 * 3000 * 
                     lkwlaenge*lkwlaenge;
            
            for(x = 0 ; x < max_position ; x+=10)
            {

                // Statisches Moment!!!
                // Nm
                double m_d = (x * (brueckenlaenge - x)) / 2.0 * streckenlast_traeger_gesamt;
                
                //Fall 1
                double m_z_va = p_z_va * (x / brueckenlaenge) * (brueckenlaenge - position_va);
                
                //Fall 2
                m_z_va = p_z_va * ((brueckenlaenge - x) / brueckenlaenge) * position_va;
                
                //Fall 3
                m_z_va = p_z_va * (x / brueckenlaenge) * (brueckenlaenge - position_va);
                double m_z_ha = p_z_ha * (x / brueckenlaenge) * (brueckenlaenge - position_va);
                
                // Fall 4
                m_z_va = p_z_va * (x / brueckenlaenge) * (brueckenlaenge - position_va);
                m_z_ha = p_z_ha * ((brueckenlaenge - x) / brueckenlaenge) * position_va;
                
                // Fall 5
                m_z_va = p_z_va * ((brueckenlaenge - x) / brueckenlaenge) * position_va;
                m_z_ha = p_z_ha * ((brueckenlaenge - x) / brueckenlaenge) * position_va;
                
                // Fall 6
                m_z_ha = p_z_ha * (x / brueckenlaenge) * (brueckenlaenge - position_va);
                
                //Fall 7
                m_z_ha = p_z_ha * ((brueckenlaenge - x) / brueckenlaenge) * position_va;
                
                
                // *1000 weil m_d.. in Nm, wir aber Nmm brauchen
                // Nmm / mm^4 * mm = N/mm^2 --> Spannung
                double biegeSpannung = (m_d + m_z_ha + m_z_va) * 1000 / 
                        traeger.getQuerschnitt().getIy() * 
                        traeger.getQuerschnitt().getHoehe() / 2;
                
                
                
                        
                
              auswahl = StatikApp.getCase(position_va, position_ha, x, brueckenlaenge);
              switch(auswahl)
              {
//                1. MZ_1 (x / brueckenlaenge) * (brueckenlaenge-position_va) * lkw.achsLastVA();
//                2. MZ_1 (brueckenlaenge-x) / brueckenlaenge) * position_va * lkw.achsLastVA();
//                3. MZ_2 (x / brueckenlaenge) * (brueckenlaenge-position_ha) * lkw.achslastHA();
//                4. MZ_2 ((brueckenlanege-x) / brueckenlaenge) * position_ha * lkw.achslastHA();
                  case 1:
                  {
                      belastung = (last * 1.5 + (((x / brueckenlaenge) * (brueckenlaenge-position_va) * lkw.achsLastVA()) * 2)) / 2;
                      break;
                  }
                  case 2:
                  {
                      belastung = (last * 1.5 + (((brueckenlaenge-x) / brueckenlaenge) * position_va * lkw.achsLastVA()) * 2) / 2;
                      break;
                  }
                  case 3:
                  {
                     // belastung = (last * 1,5 + M_Z_1 * 2 + M_Z_2 * 2) / 2 ;
                      break;
                  }
                  case 4:
                  {    
                    //  belastung = (last * 1,5 + M_Z_1 * 2 + M_Z_2 * 2) / 2 ;
                      break;
                  }
                  case 5:
                  {
                     // belastung = (last * 1,5 + M_Z_1 * 2 + M_Z_2 * 2) / 2 ;
                      break;
                  }
                  case 6:
                  {
                     // belastung = (last * 1,5 + M_Z_2 * 2) / 2;
                      break;
                  }
                  case 7:
                  {
                     // belastung = (last * 1,5 + M_Z_2 * 2) / 2;
                      break;
                  }
              }
              biegemoment_werte[position_va][x] = belastung;
              if(belastung > maxBiegespannung)
              {
                maxBiegespannung = belastung;
                position_max_biegemoment = x;
              }
              
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
        System.out.println(String.format("  * Hoehe: %.2f mm", traeger.getQuerschnitt().getHoehe()));
        System.out.println(String.format("  * Breite: %.2f mm", traeger.getQuerschnitt().getHoehe()));
        System.out.println(String.format("  * Stegbreite: %.2f mm", traeger.getQuerschnitt().getStegBreite()));
        System.out.println(String.format("  * Steghoehe: %.2f mm", traeger.getQuerschnitt().getStegBreite()));
        
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
