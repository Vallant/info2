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
            String fahrer, double achsenAbstand)
            throws Exception
    {
        super(hoehe, breite, gesamtGewicht, achsenAbstand);
        if (firma.isEmpty())
        {
            throw new Exception("firma darf nicht leer sein");
        }
        if (fahrer.isEmpty())
        {
            throw new Exception("fahrer darf nicht leer sein");
        }
        this.firma = firma;
        this.fahrer = fahrer;
    }

    public String getFirma()
    {
        return firma;
    }

    public String getFahrer()
    {
        return fahrer;
    }

    public void setFirma(String firma)
    {
        this.firma = firma;
    }

    public void setFahrer(String fahrer)
    {
        this.fahrer = fahrer;
    }

    @Override
    public String toString()
    {
        String output = super.toString();
        return (output += String.format("\nLKW --> Firma : %s, Fahrer : %s"
                + ", Achsenabstand : %.2f m, Last-vorderachse : %.2f kN, Last-Hinterachse : %.2f kN",
                firma, fahrer, achsLastVA(), achsLastHA()));
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
