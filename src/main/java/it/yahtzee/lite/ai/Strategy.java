package it.yahtzee.lite.ai;

/** Strategia per decidere quali dadi tenere durante i rilanci. */
public interface Strategy {
    /**
     * @param dice valori attuali dei 5 dadi
     * @param rollsLeft rilanci rimasti (2,1,0)
     * @return array di 5 boolean: true=tieni il dado, false=rilancia
     */
    boolean[] chooseKeep(int[] dice, int rollsLeft);
}
