# Bilancio Personale

Applicazione per la gestione di un bilancio personale, sviluppata come progetto universitario per l'esame di Programmazione ad Oggetti 2022/23.

## Funzionalità principali

- Aggiunta, modifica e rimozione delle voci del bilancio.
- Salvataggio automatico ogni 10 minuti, con possibilità di salvataggio manuale.
- Caricamento di un bilancio da file.
- Esportazione del bilancio in diversi formati (csv, excel, txt).
- Stampa del bilancio.
- Visualizzazione del bilancio attraverso un grafico a torta e una tabella.
- Filtro per selezionare un periodo giornaliero, settimanale, mensile, annuale o personalizzato.

## Tecnologie utilizzate

- Maven per la gestione delle librerie.
- Swing per l'interfaccia grafica.

## Come utilizzare

Il codice sorgente del progetto è disponibile su GitHub. Prima di eseguire l'applicazione, è necessario avere installato Java 14. Per compilare e avviare l'applicazione, seguire i seguenti passaggi:

1. Eseguire il comando `mvn clean install` per compilare il progetto e gestire le dipendenze tramite Maven.
2. Successivamente, eseguire il comando `mvn exec:java` per avviare l'applicazione.
