package pl.edu.amu.wmi.daut.base;

abstract class DeterministicAutomatonSpecification extends AutomatonSpecification {
    /**
     * Zwraca stan, jaki zostanie osiągnięty przy przejściu ze stanu
     * from przez znak c. Zwraca null, jeśli nie istnieje przejście
     * ze stanu from przez znak c.
     */
    public abstract State targetState(State from, char c);
}


