# Yahtzee Lite

Progetto dimostrativo per l'esame di Automated Software Delivery.

## Struttura

- `app/` → ingresso applicazione (`Main`)
- `core/` → logica del gioco (`Dice`, `ScoreCard`, `YahtzeeGame`)
- `ai/` → algoritmo genetico e strategie

## Build & Run

```bash
mvn clean package
mvn exec:java -Dexec.args="--seed=123 --pop=20 --gens=10 --games=10"
```

## Docker

```bash
docker build -t yahtzee-lite .
docker run --rm yahtzee-lite --seed=123 --pop=20 --gens=10 --games=10
```
