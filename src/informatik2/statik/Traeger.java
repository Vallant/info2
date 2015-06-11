/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informatik2.statik;

/**
 *
 * @author Stephan
 */
public class Traeger 
{
    // m
    private final double laenge;
    
    // kg/dm^3
    private final double dichte;
    
    private final Querschnitt querschnitt;
    
    public Traeger(double laenge, double dichte, double hoehe, double breite, 
            double stegBreite, double stegHoehe)
            throws Exception
    {
        
        if(laenge <= 0)
            throw new Exception("laenge muss groesser 0 sein");
        if(dichte <= 0)
            throw new Exception("dichte muss groesser 0 sein");
        
        this.laenge = laenge;
        this.dichte = dichte;
        querschnitt = new Querschnitt(hoehe, breite, stegBreite, stegHoehe);
    }
    public Traeger(double laenge, double dichte, Querschnitt querschnitt)
            throws Exception
    {
        if(laenge <= 0)
            throw new Exception("laenge muss groesser 0 sein");
        if(dichte <= 0)
            throw new Exception("dichte muss groesser 0 sein");
        
        this.laenge = laenge;
        this.dichte = dichte;
        this.querschnitt = new Querschnitt(querschnitt);
    }
    
    public Traeger(Traeger other)
    {
        this.laenge = other.getLaenge();
        this.dichte = other.getDichte();
        this.querschnitt = new Querschnitt(other.getQuerschnitt());
    }
    
    // m
    public double getLaenge()
    {
        return laenge;
    }
    
    // kg/dm^3
    public double getDichte()
    {
        return dichte;
    }
    
    // kg
    public double getGewicht()
    {
        return querschnitt.getFlaeche() * laenge * dichte / 1000; // mm^2 / 10000 * (m * 10) * kg / dm^3
    }
    
    public Querschnitt getQuerschnitt()
    {
        return querschnitt;
    }

    
    @Override
    public String toString()
    {
        String output = querschnitt.toString();
        return (output += String.format("\nTraeger --> Laenge: %.2f m, Dichte: %.2f kg/dm^3"
                + ", Gewicht : %.2f kg)", 
                laenge, dichte, getGewicht()));
        
    }
}