# Yahtzee Lite

[![CI](https://github.com/m98arcio/yahtzee-lite/actions/workflows/ci.yml/badge.svg)](https://github.com/<user>/<repo>/actions/workflows/ci.yml)

Progetto di Automated Software Delivery

## Struttura
```
it.yahtzee.lite
 ├─ app/        → ingresso applicazione (Main)
 ├─ core/       → logica del gioco (Dice, ScoreCard, YahtzeeGame)
 └─ ai/         → GA e strategie (GAEngine, Strategy, RandomStrategy, AlwaysKeepAllStrategy)
```

## Esecuzione
Esegui il comando run dal main

## Output & Logging
	•	Console: per ogni generazione stampa best e avg.
	•	File CSV generati in runs/:
	•	fitness.csv → gen,best,avg
	•	best_genomes.csv → gen,threshold

## CI (GitHub Actions)
Workflow in `.github/workflows/ci.yml` (Java 21 + Maven).
Ogni push su main esegue build e test.

## Docker
```bash
docker build -t yahtzee-lite .
docker compose up --build
docker run --rm yahtzee-lite --seed=123 --pop=20 --gens=10 --games=10 --k=4 --elitism=2 --mutRate=0.2
```
