package com.noflyfre.bankmore.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.noflyfre.bankmore.gui.AppFrame;
import com.noflyfre.bankmore.gui.MyTableModel;
import com.noflyfre.bankmore.gui.SerializablePieDataset;
import com.noflyfre.bankmore.logic.Bilancio;

/**
 * Classe che gestisce il caricamento di un bilancio da file.
 */
public class LoadActionListener implements ActionListener {
    private Bilancio myBudget;
    private MyTableModel budgetTableModel;
    private SerializablePieDataset dataset;
    private JLabel bilancioValue;

    /**
     * Costruttore della classe LoadActionListener.
     *
     * @param myBudget
     * @param budgetTableModel
     * @param dataset
     * @param bilancioValue
     */
    public LoadActionListener(Bilancio myBudget, MyTableModel budgetTableModel, SerializablePieDataset dataset,
            JLabel bilancioValue) {
        this.myBudget = myBudget;
        this.budgetTableModel = budgetTableModel;
        this.dataset = dataset;
        this.bilancioValue = bilancioValue;
    }

    /**
     * Metodo che gestisce il caricamento di un file. Fa scegliere all'utente il file da caricare, ed una volta
     * selezinoato, se esiste, viene deserializzato ed importato come oggetto Bilancio. La table model viene aggiornata
     * di conseguenza.
     */
    public void actionPerformed(ActionEvent e) {
        Bilancio budgetCaricato = new Bilancio();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Apri file");
        fileChooser.setApproveButtonText("Apri");
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileToOpen))) {
                // Leggere l'oggetto bilancio dallo stream e assegnarlo a myBudget
                budgetCaricato = (Bilancio) in.readObject();
                myBudget.setTransazioni(budgetCaricato.getTransazioni());;
                dataset.setValue("Entrate", budgetCaricato.totaleEntrate());
                dataset.setValue("Uscite", budgetCaricato.totaleUscite());
                bilancioValue.setText(String.format("%.2f", myBudget.totaleBilancio()) + "€");
                budgetTableModel.setOriginalData(budgetCaricato.getTransazioni());
                budgetTableModel.setDati(budgetCaricato.getTransazioni());
                budgetTableModel.fireTableDataChanged();
                System.out.println(budgetTableModel.getRowCount());
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(fileChooser, "File non esistente.", "Errore di caricamento",
                        JOptionPane.ERROR_MESSAGE);
                        exc.printStackTrace();
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(fileChooser, "File non supportato.", "Errore di caricamento",
                        JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
            }
        }
    }
}
