import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Oblig5Del2A {
    public static void main(String[] args) {
        Monitor1 register = new Monitor1();
        try {
            lesFiler(args[0], register);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Vennligst spesifiser filnavn");
        }
        // fletter etter at trådene er ferdig med å lese filer
        flettMaps(register);
        System.out.println(flestForekomster(register));
    }


    static void lesFiler(String mappen, Monitor1 register) {
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
            // venter til traadene er ferdig før man kan gjøre noe annet
            for(Thread a:traader) {
                try {
                    if(a != null) {
                        a.join();
                    }
                } catch(InterruptedException e) {}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    static void flettMaps(Monitor1 register) {
        HashMap<String, Subsekvens> sekvensFlett = new HashMap<>();
        int antall = register.antallMaps();
        for(int i=0; i< antall ; i++) {
            sekvensFlett = SubsekvensRegister.flettMaps(register.hentHashMap(0), sekvensFlett);
            register.fjern(0);
        }
        register.settInnHashMap(sekvensFlett);
    }


    static Subsekvens flestForekomster(Monitor1 reg) {
        Subsekvens b = new Subsekvens("");
        for(Subsekvens a:reg.hentHashMap(0).values()) {
            if(a.hentAntall() > b.hentAntall()) {
                b = a;
            }
        }
        return b;
    }
}

