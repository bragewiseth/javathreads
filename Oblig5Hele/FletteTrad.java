import java.util.HashMap;
import java.util.ArrayList;

public class FletteTrad implements Runnable {
    Monitor2 register;

    FletteTrad(Monitor2 register) {
        this.register = register;
    }

    // En traad vil i flette helt til det er igjen ett HashMap og til lesTraadene er ferdig
    // spesifisert av interruptflagget som kan settes i hovedprogrammet.
    @Override
    public void run() {
        HashMap<String, Subsekvens> sekvensFlett = new HashMap<>();
        HashMap<String, Subsekvens> a, b;
        while(register.antallMaps() > 1 || !Thread.currentThread().isInterrupted()) {
            try {
                ArrayList<HashMap<String, Subsekvens>> liste = register.hentUtTo();
                a = liste.get(0); b = liste.get(1);
                sekvensFlett = SubsekvensRegister.flettMaps(a, b);
                register.settInnHashMap(sekvensFlett);
            } catch(NullPointerException e) {break;}
        }
    }
}    

