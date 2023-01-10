package com.noflyfre.bankmore.gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import com.noflyfre.bankmore.logic.Bilancio;
import com.noflyfre.bankmore.logic.Uscita;
import com.noflyfre.bankmore.logic.VoceBilancio;

/**
 * Classe che definisce il modello della tabella.
 */
@SuppressWarnings("checkstyle:magicnumber")
public class MyTableModel extends AbstractTableModel {
    private String[] columnLabels = { "Data", "Importo", "Descrizione" };

    private List<VoceBilancio> dati;
    private List<VoceBilancio> originalData;

    public MyTableModel(Bilancio myBudget) {
        dati = myBudget.getTransazioni();
        originalData = myBudget.getTransazioni();
    }

    public void setDati(List<VoceBilancio> dati) {
        this.dati = dati;
    }

    public List<VoceBilancio> getDati() {
        return dati;
    }

    public List<VoceBilancio> getOriginalData() {
        return originalData;
    }

    public void setOriginalData(List<VoceBilancio> originalData) {
        this.originalData = originalData;
    }

    @Override
    public int getRowCount() {
        return dati.size() + 1;
    }

    @Override
    public int getColumnCount() {
        // il numero di colonne è 3 (Data, Descrizione, Importo)
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        // restituire l'etichetta per la colonna specificata
        return columnLabels[column];
    }

    /**
     * Metodo che ritorna il totale di tutte le transazioni.
     *
     * @return totale della somma delle transazioni, formattato in maniera corretta
     */
    public String getTotale() {
        double totale = 0;
        for (VoceBilancio voce : dati) {
            if (voce.getClass() == Uscita.class) {
                totale -= voce.getAmount();
            } else {
                totale += voce.getAmount();
            }
        }
        if (totale >= 0) {
            return "+" + String.format("%.2f", totale) + "€";
        }
        return String.format("%.2f", totale) + "€";
    }

    /**
     * Il metodo si occupa di restituire il valore contenuto in una specifica cella della tabella. Prende in input due
     * parametri: l'indice della riga e l'indice della colonna della cella di cui si vuole ottenere il valore. Se la
     * riga è uguale alla lunghezza della lista dati, il metodo restituisce un valore specifico per la riga di totale,
     * altrimenti restituisce i valori contenuti nella classe VoceBilancio all'indice della lista corrispondente alla
     * riga specifica passata come parametro.
     */
    @Override
    public Object getValueAt(int row, int column) {
        Object value = null;
        if (row == dati.size()) {
            switch (column) {
            case 0:
                value = "Totale";
                break;
            case 1:
                value = getTotale();
                break;
            case 2:
                value = "Entrate - Uscite";
                break;
            default:
                break;
            }
        } else {
            VoceBilancio voce = dati.get(dati.size() - row - 1);
            switch (column) {
            case 0:
                value = voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            case 1:
                if (voce.getClass() == Uscita.class) {
                    value = "-" + String.format("%.2f", voce.getAmount()) + "€";
                } else {
                    value = "+" + String.format("%.2f", voce.getAmount()) + "€";
                }
                break;
            case 2:
                value = voce.getDescrizione();
                break;
            default:
                break;
            }
        }
        return value;
    }

    /**
     * Metodo che filtra le voci della tabella. Filtra le voci della tabella in base a due date, una di inizio e una di
     * fine. Verranno mostrate solo le voci la cui data si trova nell'intervallo specificato, estremi compresi.
     *
     * @param dataInizio
     * @param dataFine
     */
    public void filtraVoci(LocalDate dataInizio, LocalDate dataFine) {
        List<VoceBilancio> vociDaMostrare = new ArrayList<>();
        for (VoceBilancio voce : dati) {
            if (voce.getData().isAfter(dataInizio) && voce.getData().isBefore(dataFine)
                    || voce.getData().equals(dataInizio) || voce.getData().equals(dataFine)) {
                vociDaMostrare.add(voce);
            }
        }
        dati = vociDaMostrare;
        fireTableDataChanged();
    }

    public void resetTableData() {
        dati = originalData;
        fireTableDataChanged();
    }
}
