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
import com.noflyfre.bankmore.Bilancio;
import com.noflyfre.bankmore.VoceBilancio;

public class ExportActionListener implements ActionListener {

    private Bilancio myBudget;

    public ExportActionListener(Bilancio myBudget) {
        this.myBudget = myBudget;
    }

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

                    csv.append("Data,Importo,Descrizione\n");

                    for (VoceBilancio voce : myBudget.getTransazioni()) {
                        String row = voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ","
                                + voce.getAmount() + "," + voce.getDescrizione() + "\n";
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

                    int rowNum = 1;
                    for (VoceBilancio voce : myBudget.getTransazioni()) {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0)
                                .setCellValue(voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        row.createCell(1).setCellValue(voce.getAmount());
                        row.createCell(2).setCellValue(voce.getDescrizione());
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

                    for (VoceBilancio voce : myBudget.getTransazioni()) {
                        String row = voce.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " \t"
                                + String.format("%.2f", voce.getAmount()) + "€\t" + voce.getDescrizione() + "\n";
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
