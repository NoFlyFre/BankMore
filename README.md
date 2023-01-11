# Bilancio Personale

Applicazione per gestire un bilancio personale, sviluppata per il progetto universitario per l'esame di Programmazione ad Oggetti 2022/23.

## Funzionalità principali
- Aggiunta, modifica e rimozione di voci dal bilancio
- Salvataggio manuale ed automatico ogni 10 minuti
- Caricamento di un bilancio da file
- Esportazione in diversi formati (csv, excel, txt)
- Stampa del bilancio
- Visualizzazione del bilancio in un grafico a torta ed in una tabella
- Filtro per periodo giornaliero, settimanale, mensile, annuale oppure con date a piacere

## Tecnologie utilizzate
- [Maven](https://maven.apache.org/) per la gestione delle librerie
- [Swing](https://docs.oracle.com/en/java/javase/14/docs/api/javax/swing/package-summary.html) per l'interfaccia grafica

## Come utilizzare
Il codice sorgente del progetto è disponibile su [GitHub](https://github.com/NoFlyFre/BankMore). Per eseguirlo è necessario avere Java 14 installato.
Per compilare e lanciare l'applicazione:
`mvn clean install`
`mvn exec:java`
