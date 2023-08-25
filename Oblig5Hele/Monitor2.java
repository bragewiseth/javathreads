import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.*;

public class Monitor2 {
    SubsekvensRegister register;
    Lock laas = new ReentrantLock(true);
    Condition minstTo = laas.newCondition();
        
    public Monitor2() {
        register = new SubsekvensRegister();
    }



    public void settInnHashMap(HashMap<String, Subsekvens> map) {
        laas.lock();
        try {
            register.settInnHashMap(map);
            if(register.antallMaps() > 1) {
                minstTo.signalAll();
            }
        } finally {laas.unlock();}
    }



    public void settInnForst(HashMap<String, Subsekvens> map) {
        laas.lock();
        try {
            register.settInnForst(map);
            if(register.antallMaps() > 1) {
                minstTo.signalAll();
            }
        } finally {laas.unlock();}
    }



    
    public HashMap<String, Subsekvens> hentHashMap(int indeks) {
        return register.hentHashMap(indeks);
    }





    public ArrayList<HashMap<String, Subsekvens>> hentUtTo() {
        ArrayList<HashMap<String, Subsekvens>> a = new ArrayList<HashMap<String, Subsekvens>>(2);
        laas.lock();
        try {
            while(register.antallMaps() < 2) {
                try {
                    minstTo.await();
                } catch (InterruptedException e) {return null;}
            }
            a.add(register.fjern(0)); a.add(register.fjern(0));
            return a;
        } finally {laas.unlock();}
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
