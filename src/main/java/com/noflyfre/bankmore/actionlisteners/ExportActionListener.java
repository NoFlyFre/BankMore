package com.noflyfre.bankmore.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.noflyfre.bankmore.logic.Bilancio;
import com.noflyfre.bankmore.logic.Uscita;
import com.noflyfre.bankmore.logic.VoceBilancio;

/**
 * Classe che gestisce l'esportazione del bilancio.
 */
public class ExportActionListener implements ActionListener {

    private Bilancio myBudget;

    /**
     * Costruttore della classe ExportActionListener.
     *
     * @param myBudget
     */
    public ExportActionListener(Bilancio myBudget) {
        this.myBudget = myBudget;
    }

    /**
     * Il metodo è utilizzato per esportare il bilancio in diversi formati: CSV,
     * Excel, e txt. Viene utilizzato un
     * JFileChooser per selezionare il percorso dove salvare il file e il formato
     * desiderato. Il metodo crea un oggetto
     * Workbook per generare un file di Excel, oppure utilizza un FileWriter per
     * generare un file CSV o di testo
     * semplice. In base alla selezione dell'utente e al percorso selezionato, il
     * metodo scrive i dati del bilancio nel
     * file scelto, utilizzando una serie di istruzioni per formattare i dati
     * appropriatamente.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Esporta bilancio");
        fileChooser.setApproveButtonText("Esporta");
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("File CSV (*.csv)", "csv");
        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("File Excel (*.xlsx)", "xlsx");
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("File di testo (*.txt)", "txt");
        fileChooser.addChoosableFileFilter(csvFilter);
        fileChooser.addChoosableFileFilter(excelFilter);
        fileChooser.addChoosableFileFilter(txtFilter);
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File exportPath = fileChooser.getSelectedFile();
            if (exportPath.getName().endsWith(".csv") || fileChooser.getFileFilter().equals(csvFilter)) {
                try (FileWriter fileWriter = new FileWriter(
                        exportPath.getAbsolutePath().replaceAll(".csv", "") + ".csv")) {
                    StringBuilder csv = new StringBuilder();

                    String data;
                    String importo;
                    String descrizione;

                    csv.append("Data,Importo,Descrizione\n");

                    for (VoceBilancio voce : myBudget.getTransazioni()) {
                        data = voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        if (voce instanceof Uscita) {
                            importo = "-" + String.format("%.2f",(voce.getAmount()));
                        } else {
                            importo = String.format("%.2f",(voce.getAmount()));
                        }
                        descrizione = voce.getDescrizione();
                        String row = data + ","
                                + importo + "," + descrizione + "\n";
                        csv.append(row);
                    }

                    fileWriter.write(csv.toString());
                    fileWriter.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if (exportPath.getName().endsWith(".xlsx") || fileChooser.getFileFilter().equals(excelFilter)) {
                try (FileOutputStream fileOut = new FileOutputStream(
                        exportPath.getAbsolutePath().replaceAll(".xlsx", "") + ".xlsx")) {
                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("Bilancio");

                    Row headerRow = sheet.createRow(0);
                    headerRow.createCell(0).setCellValue("Data");
                    headerRow.createCell(1).setCellValue("Importo");
                    headerRow.createCell(2).setCellValue("Descrizione");

                    String data;
                    String importo;
                    String descrizione;

                    int rowNum = 1;
                    for (VoceBilancio voce : myBudget.getTransazioni()) {
                        data = voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        if (voce instanceof Uscita) {
                            importo = "-" + String.format("%.2f",(voce.getAmount()));
                        } else {
                            importo = String.format("%.2f",(voce.getAmount()));
                        }
                        descrizione = voce.getDescrizione();
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0)
                                .setCellValue(data);
                        row.createCell(1).setCellValue(importo);
                        row.createCell(2).setCellValue(descrizione);
                    }

                    Row totalRow = sheet.createRow(rowNum);
                    totalRow.createCell(0).setCellValue("Totle");
                    totalRow.createCell(1).setCellValue(myBudget.totaleBilancio());

                    workbook.write(fileOut);
                    fileOut.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if (exportPath.getName().endsWith(".txt") || fileChooser.getFileFilter().equals(txtFilter)) {
                try (FileWriter fileWriter = new FileWriter(
                        exportPath.getAbsolutePath().replaceAll(".txt", "") + ".txt")) {
                    StringBuilder txt = new StringBuilder();
                    String data;
                    String importo;
                    String descrizione;

                    for (VoceBilancio voce : myBudget.getTransazioni()) {
                        data = voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        if (voce instanceof Uscita) {
                            importo = "-" + String.format("%.2f",(voce.getAmount()));
                        } else {
                            importo = String.format("%.2f",(voce.getAmount()));
                        }
                        descrizione = voce.getDescrizione();
                        String row = data + " \t"
                                + importo + "€\t" + descrizione + "\n";
                        txt.append(row);
                    }

                    txt.append("Totale: " + String.format("%.2f", myBudget.totaleBilancio()) + "€");

                    fileWriter.write(txt.toString());
                    fileWriter.close();

                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(fileChooser, "Errore nell\'esportazione.", "Errore esportazione.",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }
}
