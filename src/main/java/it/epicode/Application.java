package it.epicode;

import java.util.*;
import java.util.stream.Collectors;


public class Application {
    private static final List<Map<String, Object>> catalogo = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("--- CATALOGO BIBLIOTECA ---");
            System.out.println("1. Aggiungi Libro");
            System.out.println("2. Aggiungi Rivista");
            System.out.println("3. Ricerca per ISBN");
            System.out.println("4. Rimuovi elemento");
            System.out.println("5. Ricerca per Anno");
            System.out.println("6. Ricerca per Autore");
            System.out.println("7. Aggiorna elemento");
            System.out.println("8. Mostra Statistiche");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (scelta) {
                    case 1 -> aggiungiLibro();
                    case 2 -> aggiungiRivista();
                    case 3 -> ricercaPerIsbn();
                    case 4 -> rimuoviElemento();
                    case 5 -> ricercaPerAnno();
                    case 6 -> ricercaPerAutore();
                    case 7 -> aggiornaElemento();
                    case 8 -> stampaStatistiche();
                    case 0 -> {
                        System.out.println("Arrivederci!");
                        System.exit(0);
                    }
                    default -> System.out.println("Opzione non valida");
                }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    private static void aggiungiLibro() {
        Map<String, Object> libro = new HashMap<>();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        // Controllo ISBN duplicato
        if (catalogo.stream().anyMatch(e -> e.get("isbn").equals(isbn))) {
            throw new RuntimeException("ISBN già presente nel catalogo");
        }

        libro.put("tipo", "libro");
        libro.put("isbn", isbn);

        System.out.print("Titolo: ");
        libro.put("titolo", scanner.nextLine());

        System.out.print("Anno Pubblicazione: ");
        libro.put("anno", scanner.nextInt());
        scanner.nextLine();

        System.out.print("Numero Pagine: ");
        libro.put("pagine", scanner.nextInt());
        scanner.nextLine();

        System.out.print("Autore: ");
        libro.put("autore", scanner.nextLine());

        System.out.print("Genere: ");
        libro.put("genere", scanner.nextLine());

        catalogo.add(libro);
        System.out.println("Libro aggiunto con successo!");
    }

    private static void aggiungiRivista() {
        Map<String, Object> rivista = new HashMap<>();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        // Controllo ISBN duplicato
        if (catalogo.stream().anyMatch(e -> e.get("isbn").equals(isbn))) {
            throw new RuntimeException("ISBN già presente nel catalogo");
        }

        rivista.put("tipo", "rivista");
        rivista.put("isbn", isbn);

        System.out.print("Titolo: ");
        rivista.put("titolo", scanner.nextLine());

        System.out.print("Anno Pubblicazione: ");
        rivista.put("anno", scanner.nextInt());
        scanner.nextLine();

        System.out.print("Numero Pagine: ");
        rivista.put("pagine", scanner.nextInt());
        scanner.nextLine();

        System.out.print("Periodicità (SETTIMANALE/MENSILE/SEMESTRALE): ");
        rivista.put("periodicita", Periodicita.valueOf(scanner.nextLine().toUpperCase()));

        catalogo.add(rivista);
        System.out.println("Rivista aggiunta con successo!");
    }

    private static void ricercaPerIsbn() {
        System.out.print("Inserisci ISBN: ");
        String isbn = scanner.nextLine();

        Map<String, Object> elemento = catalogo.stream()
                .filter(e -> e.get("isbn").equals(isbn))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nessun elemento trovato con questo ISBN"));

        System.out.println(elemento);
    }

    private static void rimuoviElemento() {
        System.out.print("Inserisci ISBN dell'elemento da rimuovere: ");
        String isbn = scanner.nextLine();

        boolean rimosso = catalogo.removeIf(e -> e.get("isbn").equals(isbn));

        if (!rimosso) {
            throw new RuntimeException("Nessun elemento trovato con questo ISBN");
        }

        System.out.println("Elemento rimosso con successo!");
    }

    private static void ricercaPerAnno() {
        System.out.print("Inserisci Anno: ");
        int anno = scanner.nextInt();
        scanner.nextLine();

        List<Map<String, Object>> risultati = catalogo.stream()
                .filter(e -> (int)e.get("anno") == anno)
                .collect(Collectors.toList());

        risultati.forEach(System.out::println);
    }

    private static void ricercaPerAutore() {
        System.out.print("Inserisci Autore: ");
        String autore = scanner.nextLine();

        List<Map<String, Object>> risultati = catalogo.stream()
                .filter(e -> "libro".equals(e.get("tipo")) &&
                        e.get("autore").toString().equalsIgnoreCase(autore))
                .collect(Collectors.toList());

        risultati.forEach(System.out::println);
    }

    private static void aggiornaElemento() {
        System.out.print("Inserisci ISBN dell'elemento da aggiornare: ");
        String isbn = scanner.nextLine();

        Map<String, Object> elemento = catalogo.stream()
                .filter(e -> e.get("isbn").equals(isbn))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nessun elemento trovato con questo ISBN"));

        System.out.println("Elemento corrente: " + elemento);
        System.out.println("Inserisci nuovi dati:");

        if ("libro".equals(elemento.get("tipo"))) {
            System.out.print("Nuovo Titolo (invio per mantenere attuale): ");
            String nuovoTitolo = scanner.nextLine();
            if (!nuovoTitolo.isEmpty()) elemento.put("titolo", nuovoTitolo);

            System.out.print("Nuovo Anno (0 per mantenere attuale): ");
            int nuovoAnno = scanner.nextInt();
            scanner.nextLine();
            if (nuovoAnno != 0) elemento.put("anno", nuovoAnno);

            System.out.print("Nuove Pagine (0 per mantenere attuale): ");
            int nuovePagine = scanner.nextInt();
            scanner.nextLine();
            if (nuovePagine != 0) elemento.put("pagine", nuovePagine);

            System.out.print("Nuovo Autore (invio per mantenere attuale): ");
            String nuovoAutore = scanner.nextLine();
            if (!nuovoAutore.isEmpty()) elemento.put("autore", nuovoAutore);

            System.out.print("Nuovo Genere (invio per mantenere attuale): ");
            String nuovoGenere = scanner.nextLine();
            if (!nuovoGenere.isEmpty()) elemento.put("genere", nuovoGenere);
        } else {
            // Aggiornamento Rivista
            System.out.print("Nuovo Titolo (invio per mantenere attuale): ");
            String nuovoTitolo = scanner.nextLine();
            if (!nuovoTitolo.isEmpty()) elemento.put("titolo", nuovoTitolo);

            System.out.print("Nuovo Anno (0 per mantenere attuale): ");
            int nuovoAnno = scanner.nextInt();
            scanner.nextLine();
            if (nuovoAnno != 0) elemento.put("anno", nuovoAnno);

            System.out.print("Nuove Pagine (0 per mantenere attuale): ");
            int nuovePagine = scanner.nextInt();
            scanner.nextLine();
            if (nuovePagine != 0) elemento.put("pagine", nuovePagine);

            System.out.print("Nuova Periodicità (invio per mantenere attuale): ");
            String nuovaPeriodicita = scanner.nextLine();
            if (!nuovaPeriodicita.isEmpty())
                elemento.put("periodicita", Periodicita.valueOf(nuovaPeriodicita.toUpperCase()));
        }

        System.out.println("Elemento aggiornato con successo!");
    }

    private static void stampaStatistiche() {
        long numLibri = catalogo.stream()
                .filter(e -> "libro".equals(e.get("tipo")))
                .count();

        long numRiviste = catalogo.stream()
                .filter(e -> "rivista".equals(e.get("tipo")))
                .count();

        Map<String, Object> elementoConPiuPagine = catalogo.stream()
                .max(Comparator.comparing(e -> (int)e.get("pagine")))
                .orElse(null);

        double mediaNumPagine = catalogo.stream()
                .mapToInt(e -> (int)e.get("pagine"))
                .average()
                .orElse(0);

        System.out.println("Statistiche Catalogo:");
        System.out.println("Numero Libri: " + numLibri);
        System.out.println("Numero Riviste: " + numRiviste);
        System.out.println("Elemento con più pagine: " +
                (elementoConPiuPagine != null ? elementoConPiuPagine.get("titolo") : "N/A"));
        System.out.printf("Media pagine: %.2f%n", mediaNumPagine);
    }
}

