
import java.io.FileNotFoundException;

public class LesTrad implements Runnable {
    Monitor2 register;
    String filnavn;

    LesTrad(String filnavn, Monitor2 register) {
        this.filnavn = filnavn;
        this.register = register;
        this.run();
    }

    @Override
    public void run() {
        try {
            register.settInnForst(SubsekvensRegister.lesFil(filnavn));
        } catch (FileNotFoundException e) {e.printStackTrace();}
    }
    
}
