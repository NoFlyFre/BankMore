package com.noflyfre.bankmore;

import java.time.LocalDate;

/**
 * Classe astratta che definisce la Voce del bilancio.
 * Definisce la voce del bilancio, in particolare la data e la descrizione.
 */
public abstract class VoceBilancio {
    private LocalDate data;
    private String descrizione;

    public VoceBilancio(LocalDate data, String descrizione) {
        this.data = data;
        this.descrizione = descrizione;
    }

    public VoceBilancio(LocalDate data) {
        this.data = data;
        this.descrizione = " *no_description* ";
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public abstract double getAmount();

    public abstract void setAmount(double amount);

}
