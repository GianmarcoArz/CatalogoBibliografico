package it.epicode;

public class Rivista extends ElementoCatalogo {

    private Periodicita periodicita;

    public Rivista(String isbn, String titolo, int annoPubblicazione, int numeroPagine) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }
}
