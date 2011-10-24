package pl.edu.amu.wmi.daut.base;

import java.util.List;
import java.util.LinkedList;

/**
 * Nieefektywne, przykładowe ukonkretnienie klasy abstrakcyjnej
 * AutomatonSpecification.
 *
 * Wszystkie przejścia są przechowywane na jednej liście.
 */
class NaiveAutomatonSpecification extends AutomatonSpecification {

    /**
     * Stan to po prostu pusta klasa. Liczy się tylko tożsamość instancji.
     *
     * Dlaczego ta klasa jest zadeklarowana jako statyczna?
     * Odpowiedź na stronie: http://download.oracle.com/javase/tutorial/java/javaOO/nested.html
     */
    static class NaiveState implements State {
        /**
         * Konstruuje stan.
         */
        public NaiveState() {
        }
    }

    /**
     * Pomocnicza klasa reprezentująca przejście.
     */
    private static class NaiveTransition {
        /**
         * Konstruuje przejście.
         */
        public NaiveTransition(NaiveState aFrom, NaiveState aTo, TransitionLabel aTransitionLabel) {
            from = aFrom;
            to = aTo;
            transitionLabel = aTransitionLabel;
        }

        /**
         * Zwraca stan źródłowy.
         */
        public NaiveState getSourceState() {
            return from;
        }

        /**
         * Zwraca stan docelowy.
         */
        public NaiveState getTargetState() {
            return to;
        }

        /**
         * Zwraca etykietę przejścia.
         */
        public TransitionLabel getTransitionLabel() {
            return transitionLabel;
        }

        private NaiveState from;
        private NaiveState to;
        private TransitionLabel transitionLabel;
    }

    public NaiveState addState() {
        NaiveState newState = new NaiveState();
        allStates.add(newState);
        return newState;
    }

    public void addTransition(State from, State to, TransitionLabel transitionLabel) {
        transitions.add(new NaiveTransition((NaiveState) from, (NaiveState) to, transitionLabel));
    }

    public void markAsInitial(State state) {
        initialState = (NaiveState) state;
    }

    public void markAsFinal(State state) {
        finalStates.add((NaiveState) state);
    }

    public List<State> allStates() {
        return allStates;
    }

    public List<OutgoingTransition> allOutgoingTransitions(State from) {
        LinkedList<OutgoingTransition> returnedList = new LinkedList<OutgoingTransition>();

        for (NaiveTransition transition : transitions) {
            if (transition.getSourceState() == from)
                returnedList.add(convertNaiveTransitionToOutgoingTransition(transition));
        }

        return returnedList;
    }

    public State getInitialState() {
        return initialState;
    }

    public boolean isFinal(State state) {
        for (NaiveState someState : finalStates) {
            if (someState == state)
                return true;
        }

        return false;
    }

    private OutgoingTransition convertNaiveTransitionToOutgoingTransition(
        NaiveTransition transition) {

        return new OutgoingTransition(
            transition.getTransitionLabel(),
            transition.getTargetState());
    }

    private LinkedList<State> allStates = new LinkedList<State>();
    private LinkedList<NaiveTransition> transitions = new LinkedList<NaiveTransition>();
    private NaiveState initialState;
    private LinkedList<NaiveState> finalStates = new LinkedList<NaiveState>();
}
