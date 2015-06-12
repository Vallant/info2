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
public class Querschnitt
{

    private final double breite;
    private final double hoehe;
    private final double stegBreite;
    private final double stegHoehe;

    public Querschnitt(double breite, double hoehe,
            double stegBreite, double stegHoehe)
            throws Exception
    {

        if (breite < stegBreite)
        {
            throw new Exception("breite muss groesser sein als stegBreite!");
        }
        if (hoehe < 2 * stegHoehe)
        {
            throw new Exception("hoehe muss groesser sein als 2* steghoehe");
        }

        if (hoehe <= 0 || breite <= 0 || stegBreite <= 0 || stegHoehe <= 0)
        {
            throw new Exception("Alle parameter muessen groesser 0 sein");
        }

        //Eingabewerte in CM
        this.breite = breite * 10;
        this.hoehe = hoehe * 10;
        this.stegBreite = stegBreite * 10;
        this.stegHoehe = stegHoehe * 10;
    }

    public Querschnitt(Querschnitt other)
    {
        this.breite = other.breite;
        this.hoehe = other.hoehe;
        this.stegBreite = other.stegBreite;
        this.stegHoehe = other.stegHoehe;
    }

    // mm^2
    public double getFlaeche()
    {
        return breite * hoehe - (breite - stegBreite) * (hoehe - 2 * stegHoehe);
    }

    // mm^4
    public double getIy()
    {
        return (breite * Math.pow(hoehe, 3) - (breite - stegBreite)
                * Math.pow((hoehe - 2 * stegHoehe), 3)) / 12.0;
    }

    // mm
    public double getBreite()
    {
        return breite;
    }

    // mm
    public double getHoehe()
    {
        return hoehe;
    }

    // mm
    public double getStegBreite()
    {
        return stegBreite;
    }

    // mm
    public double getStegHoehe()
    {
        return stegHoehe;
    }

    @Override
    public String toString()
    {
        return String.format("Querschnitt: b: %.2f, h: %.2f, "
                + "s: %.2f, t: %.2f)", breite, hoehe,
                stegBreite, stegHoehe);
    }
}
