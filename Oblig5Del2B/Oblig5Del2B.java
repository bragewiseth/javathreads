import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Oblig5Del2B {
    public static void main(String[] args) {
        Monitor2 register = new Monitor2();
        final int flettetraader = Integer.parseInt(args[1]);
        

        //lager lesetråder
        ArrayList<Thread> lesTraader = lesFiler(args[0], register);
        

        //lager flettetråder
        ArrayList<Thread> fletteTraader = new ArrayList<Thread>();
        for(int i = 0;i<flettetraader;i++) {
            Thread t1 = new Thread(new FletteTrad(register));
            t1.start();
            fletteTraader.add(t1);
        }


        //sjekker om alle lesTraader har fullført
        for(Thread a : lesTraader) {
            if(a != null) {try {a.join();} catch (InterruptedException e) {e.printStackTrace();}}
        }

        //Hvis alle lesTraader har fullført skal alle fletteTraader flagges med interrupt
        for(Thread a : fletteTraader) {a.interrupt();}

        //sjekker om alle flettetråder har fullført
        for(Thread a : fletteTraader) {
            if(a != null) {try {a.join();} catch (InterruptedException e) {e.printStackTrace();}}
        }



        System.out.println(flestForekomster(register));
    }



    //Returnerer en liste med lesTraader for å skjekke om de er ferdig senere
    static ArrayList<Thread> lesFiler(String mappen, Monitor2 register) {
        try {
            String mappe = mappen + "/metadata.csv";
            Scanner fil = new Scanner(new File(mappe));
            String linje = "";
            String[] data;
            ArrayList<Thread> traader = new ArrayList<>();
            while(fil.hasNextLine()) {
                linje = fil.nextLine();
                data = linje.strip().split(",");
                traader.add(new Thread(new LesTrad(mappen + "/" + data[0], register))); 
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

}

