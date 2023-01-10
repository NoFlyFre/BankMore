package com.noflyfre.bankmore.logic;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che definisce il bilancio. Crea la logica che sta dietro al bilancio, con tutti i metodi per la gestione delle
 * transazioni.
 */
public class Bilancio implements Serializable {

    private List<VoceBilancio> transazioni;

    public Bilancio() {
        transazioni = new ArrayList<>();
    }

    public void setTransazioni(List<VoceBilancio> nuoveTransazioni) {
        transazioni = nuoveTransazioni;
    }

    public void addTransazione(VoceBilancio transazione) {
        transazioni.add(transazione);
    }

    public void delTransazione(VoceBilancio transazione) {
        transazioni.remove(transazione);
    }

    /**
     * Metodo che aggiorna una transazione. Dati come parametri l'indice e la transazione aggiornata, la transazione di
     * indice specificato, viene aggiornata.
     *
     * @param index
     *            indice di transazione da modificare
     * @param transazioneAggiornata
     *            transazione con informazioni aggiornate
     */
    public void updateTransazione(int index, VoceBilancio transazioneAggiornata) {
        if (index >= 0 && index < transazioni.size()) {
            transazioni.set(index, transazioneAggiornata);
        }
    }

    /**
     * Metodo che calcola il totale delle entrate. Scorre tutte le voci del bilancio e se la voce è di classe Entrata,
     * allora aggiunge il valore del suo importo ad una variabile che somma il totale.
     *
     * @return ritrna il totale delle entrate
     */
    public double totaleEntrate() {
        double totale = 0;
        for (VoceBilancio transazione : transazioni) {
            if (transazione.getClass() == Entrata.class) {
                totale += transazione.getAmount();
            }
        }
        return totale;
    }

    /**
     * Metodo che calcola il totale delle uscite. Scorre tutte le voci del bilancio e se la voce è di classe Uscita,
     * allora aggiunge il valore del suo importo ad una variabile che somma il totale.
     *
     * @return ritorna il totale delle uscite
     */
    public double totaleUscite() {
        double totale = 0;
        for (VoceBilancio transazione : transazioni) {
            if (transazione.getClass() == Uscita.class) {
                totale += transazione.getAmount();
            }
        }
        return totale;
    }

    /**
     * Metodo che calcola il bilancio totale.
     *
     * @return ritorna la differenza tra le entrate e le uscite
     */
    public double totaleBilancio() {
        return totaleEntrate() - totaleUscite();
    }

    /**
     * Metodo che filtra le transazioni per periodo. Crea una lista d'appoggio in cui inserire tutte le voci che hanno
     * come data una data che rientra nei valori specificati come argomenti.
     *
     * @param startDate
     *            data di partenza
     * @param endDate
     *            data di fine
     *
     * @return lista con gli elementi con data compresa nell'intervallo
     */
    public List<VoceBilancio> transazioniPeriodo(LocalDate startDate, LocalDate endDate) {
        List<VoceBilancio> transazioniPeriodo = new ArrayList<>();

        for (VoceBilancio transazione : transazioni) {
            if (transazione.getData().isAfter(startDate) && transazione.getData().isBefore(endDate)) {
                transazioniPeriodo.add(transazione);
            }
        }
        return transazioniPeriodo;
    }

    /**
     * Metodo che stampa il bilancio nel terminale.
     */
    /*
    public void stampaBilancio() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (VoceBilancio transazione : transazioni) {
            int index = 0;
            System.out.print("Transazione " + index + ": " + transazione.getData().format(formatter) + "\t"
                    + transazione.getDescrizione() + "\t");
            if (transazione.getClass() == Uscita.class) {
                System.out.print("- ");
            } else {
                System.out.print("+ ");
            }
            System.out.println(transazione.getAmount() + "\u20ac");
            index++;
        }
    }
    */
    public List<VoceBilancio> getTransazioni() {
        return transazioni;
    }

}
