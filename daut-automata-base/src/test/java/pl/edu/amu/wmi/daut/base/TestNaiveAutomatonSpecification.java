package pl.edu.amu.wmi.daut.base;

import junit.framework.TestCase;
import java.util.List;

/**
 * Przykładowe testy przykładowej klasy NaiveAutomatonSpecification.
 */
public class TestNaiveAutomatonSpecification extends TestCase {

    /**
     * Prosta etykieta przejścia dla celów testowych.
     */
    private static class TestTransition extends TransitionLabel {
        /**
         * Konstruuje etykietę oznaczoną znakiem 'c'.
         */
        public TestTransition(char c) {
            ch_ = c;
        }

        public boolean canBeEpsilon() {
            return false;
        }

        public boolean canAcceptCharacter(char c) {
            return c == ch_;
        }

        public boolean isEmpty() {
            return false;
        }

        public char getChar() {
            return ch_;
        }

        protected TransitionLabel intersectWith(TransitionLabel label) {
            return label.canAcceptCharacter(ch_) ? this : new EmptyTransitionLabel();
        }

        private char ch_;
    }

    /**
     * Test prostego automatu o trzech stanach.
     */
    public final void testSimpleAutomaton() {
        NaiveAutomatonSpecification spec = new NaiveAutomatonSpecification();

        // budowanie

        State s0 = spec.addState();
        State s1 = spec.addState();
        spec.addTransition(s0, s1, new TestTransition('a'));
        State s2 = spec.addState();
        spec.addTransition(s0, s2, new TestTransition('b'));
        spec.addTransition(s1, s2, new TestTransition('c'));

        spec.markAsInitial(s0);
        spec.markAsFinal(s2);

        // testowanie

        State r0 = spec.getInitialState();

        List<OutgoingTransition> r0Outs = spec.allOutgoingTransitions(r0);

        // w ten sposób w JUnicie wyrażamy oczekiwanie, że liczba
        // przejść wychodzących z początkowego stanu powinna być równa 2
        assertEquals(r0Outs.size(), 2);
        assertFalse(spec.isFinal(r0));

        State r1;
        State r2;

        if (((TestTransition)r0Outs.get(0).getTransitionLabel()).getChar() == 'a') {
            r1 = r0Outs.get(0).getTargetState();
            r2 = r0Outs.get(1).getTargetState();
            assertEquals(((TestTransition)r0Outs.get(1).getTransitionLabel()).getChar(), 'b');
            assertTrue(((TestTransition)r0Outs.get(1).getTransitionLabel()).canAcceptCharacter('b'));
            assertFalse(((TestTransition)r0Outs.get(1).getTransitionLabel()).canAcceptCharacter('c'));
            assertFalse(((TestTransition)r0Outs.get(1).getTransitionLabel()).canBeEpsilon());
        }
        else {
            // kolejność może być odwrócona
            r1 = r0Outs.get(1).getTargetState();
            r2 = r0Outs.get(0).getTargetState();
            assertEquals(((TestTransition)r0Outs.get(0).getTransitionLabel()).getChar(), 'b');
        }

        assertFalse(spec.isFinal(r1));
        assertTrue(spec.isFinal(r2));
        assertSame(r0, spec.getInitialState());
        assertNotSame(r0, r1);
        assertNotSame(r0, r2);
        assertNotSame(r1, r2);

        List<State> states = spec.allStates();

        assertEquals(states.size(), 3);
    }

    /**
     * Prosty test wyznaczania przecięcia.
     */
    public final void testIntersections() {
        TestTransition tA1 = new TestTransition('a');
        TestTransition tA2 = new TestTransition('a');
        TestTransition tB = new TestTransition('b');
        EmptyTransitionLabel emptyTransition = new EmptyTransitionLabel();

        TransitionLabel intersectedA = tA1.intersect(tA2);
        assertFalse(intersectedA.isEmpty());
        assertTrue(intersectedA.canAcceptCharacter('a'));
        assertFalse(intersectedA.canAcceptCharacter('b'));

        assertTrue(tA1.intersect(tB).isEmpty());
        assertTrue(tB.intersect(tA1).isEmpty());
        assertTrue(emptyTransition.intersect(tA1).isEmpty());
        assertTrue(tA1.intersect(emptyTransition).isEmpty());
        assertTrue(emptyTransition.intersect(emptyTransition).isEmpty());
    }
}
