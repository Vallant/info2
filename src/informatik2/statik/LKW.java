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
public class LKW extends Fahrzeug
{
    private String firma;
    private String fahrer;
    
    public LKW(double hoehe, double breite, double gesamtGewicht, String firma, 
            String fahrer)
            throws Exception
    {
        super(hoehe, breite, gesamtGewicht);
        if(firma.isEmpty())
            throw new Exception("firma darf nicht leer sein");
        if(fahrer.isEmpty())
            throw new Exception("fahrer darf nicht leer sein");
        this.firma = firma;
        this.fahrer = fahrer;
    }
    
    String getFirma()
    {
        return firma;
    }
    String getFahrer()
    {
        return fahrer;
    }
    void setFirma(String firma)
    {
        this.firma = firma;
    }
    void setFahrer(String fahrer)
    {
        this.fahrer = fahrer;
    }
    
    @Override
    public String toString()
    {
        String output = super.toString();
        return (output += String.format("\nLKW (firma : %s, fahrer : %s)", 
                firma, fahrer));
    }
    
    public double achsLastVA()
    {
        return gesamtGewicht / 3.0;
    }
    public double achsLastHA()
    {
        return gesamtGewicht * 2 / 3.0;
    }
}
