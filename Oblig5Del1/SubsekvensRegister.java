import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SubsekvensRegister {
    private ArrayList<HashMap<String, Subsekvens>> hashBeholder;

    public SubsekvensRegister() {
        hashBeholder = new ArrayList<HashMap<String, Subsekvens>>();
    }

    public void settInnHashMap(HashMap<String, Subsekvens> map) {
        hashBeholder.add(map);
    }

    public HashMap<String, Subsekvens> hentHashMap(int indeks) {
        return hashBeholder.get(indeks);
    }

    public int antallMaps() {
        return hashBeholder.size();
    }


    public void fjern(int index) {
        hashBeholder.remove(index);
    }

    public ArrayList<HashMap<String, Subsekvens>> beholder() {
        return hashBeholder;
    }

    
    public static HashMap<String, Subsekvens> lesFil(String filnavn) throws FileNotFoundException {
        Scanner fil = new Scanner(new File(filnavn));
        String linje = "";
        HashMap<String, Subsekvens> person = new HashMap<>();
        while(fil.hasNextLine()) {
            linje = fil.nextLine();
            String skv = "";
            try {
                for(int i = 0; i < linje.length(); i++) {
                    skv += linje.charAt(i); skv += linje.charAt(i+1); skv += linje.charAt(i+2);  
                    person.put(skv, new Subsekvens(skv));
                    skv = "";
                }
            } catch(IndexOutOfBoundsException e) {
                continue;
            }
        }
        return person;
    }

    // Ikke så robust metode, spiller en rolle hvilken rekkefølge og kan ikke flette to flettede maps
    // Trenger oppdatert metode i senere oppgaver
    public static HashMap<String, Subsekvens> flettMaps(HashMap<String, Subsekvens> a, HashMap<String, Subsekvens> b) {
        HashMap<String, Subsekvens> flettetMap = new HashMap<>();
        flettetMap.putAll(a); 
        flettetMap.putAll(b);
        for(Subsekvens skvA : a.values()) {
            for(Subsekvens skvB : b.values()) {
                if(skvA.subsekvens.equals(skvB.subsekvens)) {
                    flettetMap.get(skvA.subsekvens).funnetSubsekvens();
                }
            }
        }
        return flettetMap;
    }
}

