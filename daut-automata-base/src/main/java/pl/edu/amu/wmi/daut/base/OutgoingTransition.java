package pl.edu.amu.wmi.daut.base;

/**
 * Klasa reprezentująca przejście wychodzące, tzn. etykietę przejścia plus
 * stan docelowy.
 *
 * Stan źródłowy nie jest tu uwzględniany.
 */
class OutgoingTransition {
    public OutgoingTransition(TransitionLabel aTransitionLabel, State aTargetState) {
        targetState = aTargetState;
        transitionLabel = aTransitionLabel;
    }

    public State getTargetState() {
        return targetState;
    }

    public TransitionLabel getTransitionLabel() {
        return transitionLabel;
    }

    private State targetState;
    private TransitionLabel transitionLabel;
}
