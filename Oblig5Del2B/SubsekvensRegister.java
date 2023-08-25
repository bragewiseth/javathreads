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


    public void settInnForst(HashMap<String, Subsekvens> map) {
        hashBeholder.add(0, map);
    }


    public int antallMaps() {
        return hashBeholder.size();
    }


    //Returner og fjerner HashMap
    public HashMap<String, Subsekvens> fjern(int index) {
        HashMap<String, Subsekvens> a = hashBeholder.get(index);
        hashBeholder.remove(index);
        return a;
    }


    public ArrayList<HashMap<String, Subsekvens>> beholder() {
        return hashBeholder;
    }

    //Leser en fil og returnerer HashMap
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

    //Fletter HashMaps
    public static HashMap<String, Subsekvens> flettMaps(HashMap<String, Subsekvens> a, HashMap<String, Subsekvens> b) {
        HashMap<String, Subsekvens> flettetMap = new HashMap<>();
        flettetMap.putAll(a);
        for(Subsekvens skvB : b.values()) {
            //Hvis sekvensen allerede eksisterer skal antallet legges sammen
            flettetMap.merge(skvB.subsekvens, skvB, (oldValue, newValue) -> newValue.funnetSubsekvens(oldValue));
        }
        return flettetMap;
    }
}

