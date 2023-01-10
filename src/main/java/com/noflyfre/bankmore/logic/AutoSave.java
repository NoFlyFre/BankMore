package com.noflyfre.bankmore.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe che gestisce l'autosalvataggio tramite thread.
 */
@SuppressWarnings("checkstyle:magicnumber")
public class AutoSave {
    private Bilancio myBudget;

    public AutoSave(Bilancio myBudget) {
        this.myBudget = myBudget;
    }

    /**
     * Il metodo che crea un nuovo oggetto timer e un nuovo oggetto TimerTask. Il TimerTask viene eseguito ogni 10
     * minuti dal timer. All'interno del TimerTask, viene chiamato il metodo saveBudget per il salvataggio del bilancio.
     */
    public void start() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Codice per il salvataggio del bilancio
                saveBudget();
            }
        };
        // Imposta il timer per eseguire il task ogni 10 minuti
        timer.scheduleAtFixedRate(task, 0, 10 * 60 * 1000);
    }

    /**
     * Il metodo saveBudget salva il bilancio. Il nome del file viene generato utilizzando l'ora corrente. Il file viene
     * salvato nella stessa cartella in cui è in esecuzione il programma.
     */
    private void saveBudget() {
        String now = LocalDateTime.now().toString();
        String formattedDate = now.substring(0, 4) + now.substring(5, 7) + now.substring(8, 10);
        String formattedTime = now.substring(11, 13);
                + now.substring(14, 16);
        String fileName = "AutoSave_" + formattedDate + "_" + formattedTime;
        File fileToSave = new File(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileToSave.toString().replaceAll(".bin$", "") + ".bin"))) {
            out.writeObject(myBudget);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
