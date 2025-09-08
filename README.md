# Yahtzee Lite

[![CI](https://github.com/m98arcio/yahtzee-lite/actions/workflows/ci.yml/badge.svg)](https://github.com/<user>/<repo>/actions/workflows/ci.yml)

Progetto dimostrativo per Automated Software Delivery

## Struttura
```
it.yahtzee.lite
 ├─ app/        → ingresso applicazione (Main)
 ├─ core/       → logica del gioco (Dice, ScoreCard, YahtzeeGame)
 └─ ai/         → GA e strategie (GAEngine, Strategy, RandomStrategy, AlwaysKeepAllStrategy)
```

## Esecuzione
```bash
mvn clean package
mvn exec:java -Dexec.args="--seed=123 --pop=20 --gens=10 --games=10 --k=4 --elitism=2 --mutRate=0.2"
```

## Output & Logging
- Console: per generazione stampa `best` e `avg`.
- File CSV in `runs/`:
  - `fitness.csv` → `gen,best,avg`
  - `best_genomes.csv` → `gen,threshold`

## CI (GitHub Actions)
Workflow in `.github/workflows/ci.yml` (Java 21 + Maven).

## Docker
```bash
docker build -t yahtzee-lite .
docker run --rm yahtzee-lite --seed=123 --pop=20 --gens=10 --games=10 --k=4 --elitism=2 --mutRate=0.2
```
