
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;

public class Oblig5Del1 {
    public static void main(String[] args) {
        try {
            SubsekvensRegister s = new SubsekvensRegister();
            s = flettFiler(args[0]);
            System.out.println(flestForekomster(s));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Vennligst spesifiser filnavn");
        }
    }


    // Henter data fra en mappe og fletter sekvensene, returnerer Subsekvensregister med én HashMap
    public static SubsekvensRegister flettFiler(String mappen) {
        SubsekvensRegister reg = new SubsekvensRegister();
        try {
            String mappe = mappen + "/metadata.csv";
            Scanner fil = new Scanner(new File(mappe));
            String linje = "";
            String[] data;
            HashMap<String, Subsekvens> sekvensFil, sekvensFlett = new HashMap<>();
            while(fil.hasNextLine()) {
                linje = fil.nextLine();
                data = linje.strip().split(",");
                sekvensFil = SubsekvensRegister.lesFil(mappen + "/" +data[0]);
                sekvensFlett = SubsekvensRegister.flettMaps(sekvensFil, sekvensFlett); 
            }
            reg.settInnHashMap(sekvensFlett);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reg;
    }


    //Returnerer subsekvensen med flest forekomster 
    public static Subsekvens flestForekomster(SubsekvensRegister reg) {
        Subsekvens b = new Subsekvens("");
        for(Subsekvens a:reg.hentHashMap(0).values()) {
            if(a.hentAntall() > b.hentAntall()) {
                b = a;
            }
        }
        return b;
    }
}
