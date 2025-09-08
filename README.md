# Yahtzee Lite

Progetto dimostrativo per Automated Software Delivery — progressione a commit.

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
mvn exec:java -Dexec.args="--seed=123 --pop=20 --gens=10 --games=10"
```

### Parametri
- `--seed`  : long per RNG (se assente, viene generato casualmente)
- `--pop`   : dimensione popolazione
- `--gens`  : numero generazioni
- `--games` : partite per valutazione fitness (default robusto: 10)

## Output & Logging
- Console: per generazione stampa `best` e `avg`.
- File CSV in `runs/`:
  - `fitness.csv` con colonne `gen,best,avg`
  - `fitness_details.csv` con `gen,index,fitness` per individuo

## CI (GitHub Actions)
Il workflow è in `.github/workflows/ci.yml` e lancia `mvn clean test` ad ogni push/PR su `main`.

## Docker
Build & run:
```bash
docker build -t yahtzee-lite .
docker run --rm yahtzee-lite --seed=123 --pop=20 --gens=10 --games=10
```
Oppure:
```bash
docker compose up --build
```

## Note
- Il GA usa torneo **k=4** ed **elitismo=2**.
- La mutazione del `threshold` è limitata a `[1..6]` (bugfix Step 11).
