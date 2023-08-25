import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.*;

public class Monitor1 {
    SubsekvensRegister register;
    Lock laas = new ReentrantLock(true);
    
    public Monitor1() {
        register = new SubsekvensRegister();
    }


    
    public void settInnHashMap(HashMap<String, Subsekvens> map) {
        laas.lock();
        try {
            register.settInnHashMap(map);
        } finally {laas.unlock();}
    }



    public HashMap<String, Subsekvens> hentHashMap(int indeks) {
        return register.hentHashMap(indeks);
    }



    public int antallMaps() {
        return register.antallMaps();
    }



    public void fjern(int index) {
        register.fjern(index);
    }



    public ArrayList<HashMap<String, Subsekvens>> beholder() {
        return register.beholder();
    }
}
