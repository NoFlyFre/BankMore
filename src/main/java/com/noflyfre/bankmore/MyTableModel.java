package com.noflyfre.bankmore;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Classe che definisce il modello della tabella.
 */
@SuppressWarnings("checkstyle:magicnumber")
public class MyTableModel extends AbstractTableModel {
    // definire le etichette per le colonne
    private String[] columnLabels = {"Data", "Importo", "Descrizione"};

    private List<VoceBilancio> dati;

    public MyTableModel(List<VoceBilancio> dati) {
        this.dati = dati;
    }

    // implementare i metodi del modello di tabella
    @Override
    public int getRowCount() {
        // il numero di righe corrisponde al numero di elementi nell'ArrayList
        return dati.size();
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

    @Override
    public Object getValueAt(int row, int column) {
        VoceBilancio voce = dati.get(dati.size() - row - 1);
        Object value = null;

        if (column == 0) {
            // restituire la data della voce
            value = voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else if (column == 2) {
            // restituire la descrizione della voce
            value = voce.getDescrizione();
        } else if (column == 1) {
            // restituire l'importo della voce
            if (voce.getClass() == Uscita.class) {
                value = "- €" + String.format("%.2f", voce.getAmount());
            } else {
                value = "+ €" + String.format("%.2f", voce.getAmount());
            }
        }
        return value;
    }
}
