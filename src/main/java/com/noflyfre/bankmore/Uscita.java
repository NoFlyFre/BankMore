package com.noflyfre.bankmore;

import java.time.LocalDate;

/**
 * Classe che definisce l'oggetto Uscita.
 * Definisce la voce Uscita, che estende la classe astratta VoceBilancio.
 */
public class Uscita extends VoceBilancio {

    private double amount;

    public Uscita(LocalDate data, String descrizione, double amount) {
        super(data, descrizione);
        this.amount = Math.abs(amount);
    }

    public Uscita(LocalDate data, double amount) {
        super(data);
        this.amount = Math.abs(amount);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
