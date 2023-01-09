package com.noflyfre.bankmore.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.noflyfre.bankmore.Bilancio;

public class SaveActionListener implements ActionListener {

    private Bilancio myBudget;

    public SaveActionListener(Bilancio myBudget) {
        this.myBudget = myBudget;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salva con nome");
        fileChooser.setApproveButtonText("Salva");
        FileNameExtensionFilter binFilter = new FileNameExtensionFilter("File binario (*.bin)", "bin");
        fileChooser.addChoosableFileFilter(binFilter);
        fileChooser.setFileFilter(binFilter);
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (fileToSave.exists()) {
                int result = JOptionPane.showConfirmDialog(null, "Il file esiste già, vuoi sovrascriverlo?",
                        "File esistente", JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    // L'utente ha scelto di non sovrascrivere il file, quindi esci dal metodo
                    return;
                }
            }
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(fileToSave.toString().replaceAll(".bin$", "") + ".bin"))) {
                // Scrivere l'oggetto bilancio sullo stream
                out.writeObject(myBudget);
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(fileChooser, "Errore nel salvataggio del file.", "Errore salvataggio.",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
