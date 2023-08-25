
import java.io.FileNotFoundException;

public class LesTrad implements Runnable {
    Monitor1 register;
    String filnavn;

    LesTrad(String filnavn, Monitor1 register) {
        this.filnavn = filnavn;
        this.register = register;
        this.run();
    }

    @Override
    public void run() {
        try {
            register.settInnHashMap(SubsekvensRegister.lesFil(filnavn));
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }
    
}
