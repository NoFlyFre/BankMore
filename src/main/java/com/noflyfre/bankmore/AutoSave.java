package com.noflyfre.bankmore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("checkstyle:magicnumber")
public class AutoSave {
    private Bilancio myBudget;

    public AutoSave(Bilancio myBudget) {
        this.myBudget = myBudget;
    }

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
        timer.scheduleAtFixedRate(task, 10 * 60 * 1000, 10 * 60 * 1000);
    }

    private void saveBudget() {
        String now = LocalDateTime.now().toString();
        String formattedTime = now.substring(0, 4) + now.substring(5, 7) + now.substring(8, 10) + now.substring(11, 13)
                + now.substring(14, 16);
        String fileName = "AutoSave" + formattedTime;
        File fileToSave = new File(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileToSave.toString().replaceAll(".bin$", "") + ".bin"))) {
            out.writeObject(myBudget);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
