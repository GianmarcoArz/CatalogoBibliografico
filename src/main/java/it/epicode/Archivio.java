package it.epicode;

import javax.xml.catalog.CatalogException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Archivio {

    private List<ElementoCatalogo> catalogo = new ArrayList<>();

    public void aggiungiElemento(ElementoCatalogo elemento) throws CatalogException {
        if (catalogo.stream().anyMatch(e -> e.getIsbn().equals(elemento.getIsbn()))) {
            throw new CatalogException("Elemento con ISBN " + elemento.getIsbn() + " gia' presente");
        }
        catalogo.add(elemento);
    }

    public ElementoCatalogo ricercaPerIsbn(String isbn) throws CatalogException {
        return catalogo.stream()
                .filter(e -> e.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new CatalogException("Nessun elemento trovato con ISBN" + isbn));
    }

    public void rimuoviElemento(String isbn) throws CatalogException {
        if (!catalogo.removeIf(e -> e.getIsbn().equals(isbn))) {
            throw new CatalogException("Nessun elemento trovato con ISBN " + isbn);
        }
    }


    public List<ElementoCatalogo> ricercaPerAnno(int anno) {
        return catalogo.stream()
                .filter(e -> e.getAnnoPubblicazione() == anno)
                .collect(Collectors.toList());

    }

    public List<ElementoCatalogo> ricercaPerAutore (String autore){
        return catalogo.stream()
                .filter(e-> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter( l -> l.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }

    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) throws CatalogException {
        int index = catalogo.indexOf(ricercaPerIsbn(isbn));
        catalogo.set(index,nuovoElemento);
    }

    public void stampaStatistiche() {
        long numLibri = catalogo.stream().filter(e -> e instanceof Libro).count();
        long numRiviste = catalogo.stream().filter(e -> e instanceof Rivista).count();

        ElementoCatalogo elementoConPiuPagine = catalogo.stream()
                .max((a,b) -> Integer.compare(a.getNumeroPagine(), b.getNumeroPagine()))
                .orElse(null);

        double mediaNumPagine = catalogo.stream()
                .mapToInt(ElementoCatalogo::getNumeroPagine)
                .average()
                .orElse(0);

        System.out.println("Statistiche Catalogo:");
        System.out.println("Numero Libri: " + numLibri);
        System.out.println("Numero Riviste: " + numRiviste);
        System.out.println("Elemento con più pagine: " +
                (elementoConPiuPagine != null ? elementoConPiuPagine.getTitolo() : "N/A"));
        System.out.printf("Media pagine: %.2f%n", mediaNumPagine);
    }
}
