package com.noflyfre.bankmore;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe che definisce l'oggetto Entrata. Definisce la voce Entrata, che estende la classe astratta VoceBilancio.
 */
public class Entrata extends VoceBilancio implements Serializable {
    private double amount;

    public Entrata(LocalDate data, String descrizione, double amount) {
        super(data, descrizione);
        this.amount = amount;
    }

    public Entrata(LocalDate data, double amount) {
        super(data);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
