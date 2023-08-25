
public class Subsekvens {
    public final String subsekvens;
    private int antall;

    public Subsekvens(String subsek) {
        subsekvens = subsek;
        antall = 1;
    }

    public void funnetSubsekvens() {
        antall++;
    }

    public int hentAntall() {
        return antall;
    }

    public String toString() {
        return subsekvens + "," + antall;
    }
}