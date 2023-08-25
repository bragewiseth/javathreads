
public class Subsekvens {
    public final String subsekvens;
    private int antall;

    public Subsekvens(String subsek) {
        subsekvens = subsek;
        antall = 1;
    }

    
    //legger til antallet fra annen subsekvens
    public Subsekvens funnetSubsekvens(Subsekvens lik) {
        antall += lik.hentAntall();
        return this;
    }

    public int hentAntall() {
        return antall;
    }

    public String toString() {
        return subsekvens + "," + antall;
    }

    public Subsekvens endreAntall(int antall) {
        this.antall = antall;
        return this;
    }
}