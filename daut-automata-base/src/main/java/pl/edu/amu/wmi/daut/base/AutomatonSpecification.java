package pl.edu.amu.wmi.daut.base;

import java.util.List;
/**
 * Klasa abstrakcyjna reprezentująca specyfikację (opis) automatu
 * (jakie są stany, przejścia, który stan jest stanem początkowym,
 * które stany są stanami akceptującymi).
 *
 * Uwaga: klasa ta nie reprezentuje działającego automatu (nie ma tu funkcji
 * odpowiadających na pytanie, czy automat akceptuje napis, czy nie),
 * tylko "zawartość" automatu.
 */
abstract class AutomatonSpecification {

    // metody "budujące" automat

    /**
     * Dodaje nowy stan do automatu.
     *
     * Zwraca dodany stan.
     */
    public abstract State addState();

    /**
     * Dodaje przejście od stanu 'from' do stanu 'to' etykietowane etykietą transitionLabel.
     */
    public abstract void addTransition(State from, State to, TransitionLabel transitionLabel);

    /**
     * Oznacza stan jako początkowy.
     */
    public abstract void markAsInitial(State state);

    /**
     * Oznacza stan jako końcowy (akceptujący).
     */
    public abstract void markAsFinal(State state);

    // metody zwracające informacje o automacie

    /**
     * Zwraca listę wszystkich stanów.
     *
     * Stany niekoniecznie muszą być zwrócone w identycznej
     * kolejności, jak były dodane.
     */
    public abstract List<State> allStates();

    /**
     * Zwraca listę wszystkich przejść wychodzących ze stanu 'from'.
     *
     * Przejścia niekoniecznie muszą być zwrócone w identycznej
     * kolejności, jak były dodane.
     */
    public abstract List<OutgoingTransition> allOutgoingTransitions(State from);

    /**
     * Zwraca stan początkowy.
     */
    public abstract State getInitialState();

    /**
     * Zwraca true, wgdy stan jest stanem końcowym.
     */
    public abstract boolean isFinal(State state);
    
    public boolean isFull(String alphabet) {
        for (State state : allStates()) {
            for (OutgoingTransition transition : allOutgoingTransitions(state)) {
                for (int i = 0; i<=alphabet.length(); i++) {
                    if (!transition.getTransitionLabel().canAcceptCharacter(alphabet.charAt(i)))
                    {
                        return false;
                    }
                }
            }
        }
        return true; 
        
    }
};
