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
    private final double laenge;
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
    
    public double getLaenge()
    {
        return laenge;
    }
    public double getDichte()
    {
        return dichte;
    }
    public double getMasse()
    {
        return querschnitt.getFlaeche() * laenge * dichte;
    }
    
    @Override
    public String toString()
    {
        String output = querschnitt.toString();
        return (output += String.format("\nTraeger --> Laenge: %.2f m, Dichte: %.2f kg/dm^3"
                + ",Masse : %.2f kg)", 
                laenge, dichte, getMasse()));
        
    }
}