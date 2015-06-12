/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informatik2.statikdemo;

import informatik2.statik.Fahrzeug;
import informatik2.statik.LKW;
import informatik2.statik.Querschnitt;
import informatik2.statik.Traeger;

/**
 *
 * @author Stephan
 */
public class StatikApp1
{

    public static void main(String[] args)
    {
        Fahrzeug fahrzeug1;
        Fahrzeug fahrzeug2;
        LKW lkw1;
        LKW lkw2;
        Traeger t1;
        Traeger t2;
        Querschnitt q2;
        try
        {
            fahrzeug1 = new Fahrzeug(10, 20, 30, 1);
            LKW tmpLKW = new LKW(30, 40, 50, "Firma", "Fahrer", 1);
            fahrzeug2 = new Fahrzeug(tmpLKW);

            lkw1 = new LKW(50, 60, 70, "Firma2", "Fahrer2", 1);

            lkw2 = tmpLKW;
            Querschnitt q1;

            q1 = new Querschnitt(20, 30, 5, 10);
            t1 = new Traeger(100, 200, q1);
            t2 = new Traeger(300, 400, 50, 60, 5, 7);

            try
            {
                q2 = new Querschnitt(1, 2, 5, 10); //throws exception
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }

            System.out.println(fahrzeug1.toString());
            System.out.println("===============\n");

            // prints as well fahrer and firma
            // it's not possible to call the toString method of Fahrzeug,
            // if it is a LKW
            System.out.println(fahrzeug2.toString());
            System.out.println("===============\n");
            System.out.println(lkw1.toString());
            System.out.println("===============\n");
            System.out.println(lkw2.toString());
            System.out.println("===============\n");
            System.out.println(String.format("VA von lkw2: %.2f", lkw2.achsLastVA()));
            System.out.println(String.format("HA von lkw2: %.2f", lkw2.achsLastHA()));

            System.out.println("===============\n");
            System.out.println(q1.toString());
            System.out.println("flaeche: " + q1.getFlaeche());
            System.out.println("Traegheitsmoment " + q1.getIy());

            System.out.println("===============\n");
            System.out.println(t1.toString());
            System.out.println("masse: " + t1.getGewicht());

        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}
