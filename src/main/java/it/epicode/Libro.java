package it.epicode;

    public class Libro extends ElementoCatalogo {
        private String autore;
        private String genere;

        public Libro(String isbn, String titolo, int annoPubblicazione, int numeroPagine) {
            super(isbn, titolo, annoPubblicazione, numeroPagine);
        }

        public String getAutore() {
            return autore;
        }

        public void setAutore(String autore) {
            this.autore = autore;
        }

        public String getGenere() {
            return genere;
        }

        public void setGenere(String genere) {
            this.genere = genere;
        }
    }

