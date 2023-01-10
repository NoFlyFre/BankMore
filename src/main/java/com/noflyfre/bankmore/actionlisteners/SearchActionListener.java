package com.noflyfre.bankmore.actionlisteners;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Classe che gestisce il listener per la ricerca di testo nella tabella.
 */
public class SearchActionListener implements ActionListener {

    private JTextField searchInput;
    private String lastSearchText;
    private int searchRowIndex;
    private JTable tableBudget;

    /**
     * Costruttore della classe SearchActionListener.
     *
     * @param searchInput
     *            field di ricerca da cui ottenere il testo da cercare
     * @param lastSearchText
     *            ultima stringa cercata
     * @param searchRowIndex
     *            indice di riga in cui è stata trovata l'ultima corrispondenza
     * @param tableBudget
     *            tabella in cui effetturare la ricerca
     */
    public SearchActionListener(JTextField searchInput, String lastSearchText, int searchRowIndex, JTable tableBudget) {
        this.searchInput = searchInput;
        this.lastSearchText = lastSearchText;
        this.searchRowIndex = searchRowIndex;
        this.tableBudget = tableBudget;
    }

    /**
     * Metodo gestisce la ricerca nella tabella. La ricerca viene effettuata cercando la stringa specificata nel testo
     * di input nelle celle della tabella. Se la stringa viene trovata in una cella, viene evidenziata la riga
     * corrispondente. Se non viene trovata alcuna corrispondenza, viene mostrato un messaggio di errore. Se la stringa
     * di ricerca cambia, la ricerca viene ripresa dall'inizio della tabella.
     *
     * @param e
     *            evento di ricerca
     */
    public void actionPerformed(ActionEvent e) {
        String searchText = searchInput.getText();
        if (!lastSearchText.equals(searchText)) {
            searchRowIndex = -1;
        }
        if (searchRowIndex == tableBudget.getRowCount() - 1) {
            JOptionPane.showMessageDialog(null, "Non ci sono più corrispondenze da mostrare.");
            searchRowIndex = -1;
        }
        if (searchRowIndex == -1) {
            for (int row = 0; row < tableBudget.getRowCount(); row++) {
                System.out.println("Actual Row: " + row);
                if (iteraRigaTabella(tableBudget, searchText, row)) {
                    break;
                } else if (!iteraRigaTabella(tableBudget, searchText, row) && row == tableBudget.getRowCount() - 1) {
                    JOptionPane.showMessageDialog(null, "Non è stata trovata alcuna corrispondenza.");
                    searchRowIndex = -1;
                }
            }
        } else if (searchRowIndex != -1) {
            for (int row = searchRowIndex + 1; row < tableBudget.getRowCount(); row++) {
                System.out.println("Actual Row: " + row);
                if (iteraRigaTabella(tableBudget, searchText, row)) {
                    break;
                } else if (!iteraRigaTabella(tableBudget, searchText, row)) {
                    JOptionPane.showMessageDialog(null, "Non ci sono più corrispondenze da mostrare.");
                    searchRowIndex = -1;
                }
            }
        }
        lastSearchText = searchText;
    }

    /**
     * Metodo che itera la riga della tabella per trovare una corrispondenza.
     *
     * @param tableBudget
     *            tabella in cui iterare la riga
     * @param searchText
     *            testo da cercare
     * @param row
     *            riga da iterare
     *
     * @return true se c'è una corrispondenza, false altrimenti
     */
    public boolean iteraRigaTabella(JTable tableBudget, String searchText, int row) {
        for (int column = 0; column < tableBudget.getColumnCount(); column++) {
            Object cellValue = tableBudget.getValueAt(row, column);
            if (cellValue != null && cellValue.toString().toLowerCase().contains(searchText)) {
                tableBudget.setRowSelectionInterval(row, row);
                tableBudget.setColumnSelectionInterval(column, column);
                Rectangle cellRect = tableBudget.getCellRect(row, 0, true);
                tableBudget.scrollRectToVisible(cellRect);
                searchRowIndex = row;
                return true;
            }
        }
        return false;
    }

}
