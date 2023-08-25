import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Oblig5Hele {
    public static void main(String[] args) {
        Monitor2 hattSykdom = new Monitor2();
        Monitor2 ikkeHattSykdom = new Monitor2();
        final int flettetraader = Integer.parseInt("20");


        //lager lesetråder
        ArrayList<Thread> lesTraader = lesFiler("../data", hattSykdom, ikkeHattSykdom);
       

        //lager flettetråder
        ArrayList<Thread> fletteTraader = new ArrayList<>();
        ArrayList<Thread> fletteTraader2 = new ArrayList<>();
        for(int i = 0;i<flettetraader;i++) {
            Thread t1 = new Thread(new FletteTrad(hattSykdom));
            Thread t2 = new Thread(new FletteTrad(ikkeHattSykdom));
            t1.start(); t2.start();
            fletteTraader.add(t1); fletteTraader2.add(t2);
        }


        //sjekker om alle lesTraader har fullført
        for(Thread a : lesTraader) {
            if(a != null) {try {a.join();} catch (InterruptedException e) {e.printStackTrace();}}
        }

        //Hvis alle lesTraader har fullført skal alle fletteTraader flagges med interrupt
        for(Thread a : fletteTraader) {a.interrupt();}
        for(Thread a : fletteTraader2) {a.interrupt();}


        //sjekker om alle flettetråder har fullført
        for(Thread a : fletteTraader) {
            if(a != null) {try {a.join();} catch (InterruptedException e) {e.printStackTrace();}}
        }
        for(Thread a : fletteTraader2) {
            if(a != null) {try {a.join();} catch (InterruptedException e) {e.printStackTrace();}}
        }

       
        Monitor2 map  = dominanteSubsekvenser(hattSykdom, ikkeHattSykdom);
        for(Subsekvens a : map.hentHashMap(0).values()) {
            if(a.hentAntall() > 6) {System.out.println(a);}
        }
        System.out.println(flestForekomster(map));
    }





    //Returnerer en liste med lesTraader for å skjekke om de er ferdig senere
    static ArrayList<Thread> lesFiler(String mappen, Monitor2 hatt, Monitor2 ikkeHatt) {
        try {
            String mappe = mappen + "/metadata.csv";
            Scanner fil = new Scanner(new File(mappe));
            String linje = "";
            String[] data;
            ArrayList<Thread> traader = new ArrayList<>();
            while(fil.hasNextLine()) {
                linje = fil.nextLine();
                data = linje.strip().split(",");
                if(data[1].equalsIgnoreCase("true")) {
                    traader.add(new Thread(new LesTrad(mappen + "/" + data[0], hatt))); 
                }
                else {
                    traader.add(new Thread(new LesTrad(mappen + "/" + data[0], ikkeHatt)));
                }
            }
            return traader;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }





    static Subsekvens flestForekomster(Monitor2 reg) {
        Subsekvens b = new Subsekvens("");
        for(Subsekvens a:reg.hentHashMap(0).values()) {
            if(a.hentAntall() > b.hentAntall()) {
                b = a;
            }
        }
        return b;
    }



    static Monitor2 dominanteSubsekvenser(Monitor2 hatt, Monitor2 ikkehatt) {
        Monitor2 map = new Monitor2();
        map.settInnHashMap(new HashMap<String, Subsekvens>());
        map.hentHashMap(0).putAll(hatt.hentHashMap(0));
        for(Subsekvens skvB : ikkehatt.hentHashMap(0).values()) {
            //Hvis sekvensen allerede eksisterer skal antallet trekkes fra hverandre
            map.hentHashMap(0).merge(skvB.subsekvens, skvB, (oldValue, newValue) -> newValue.endreAntall(oldValue.hentAntall() - newValue.hentAntall()));
        }
        return map;
    }
}






