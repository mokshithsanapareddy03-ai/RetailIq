package retailiq.algorithms.suffix;

import java.util.HashMap;
import java.util.Map;

public class SuffixAutomaton {

    private static class State {

        int length;

        int link = -1;

        Map<Character, Integer> next =
                new HashMap<>();
    }

    private State[] states;

    private int size;

    private int last;


    public SuffixAutomaton(String text) {

        states =
                new State[2 * text.length() + 1];

        for (int i = 0; i < states.length; i++) {
            states[i] = new State();
        }

        size = 1;

        last = 0;


        for (char ch : text.toCharArray()) {

            extend(ch);
        }
    }


    private void extend(char ch) {

        int current = size++;

        states[current].length =
                states[last].length + 1;


        int p = last;


        while (p != -1 &&
               !states[p].next.containsKey(ch)) {

            states[p].next.put(ch, current);

            p = states[p].link;
        }


        if (p == -1) {

            states[current].link = 0;

        } else {

            int q =
                    states[p].next.get(ch);


            if (states[p].length + 1
                    == states[q].length) {

                states[current].link = q;

            } else {

                int clone = size++;

                states[clone].length =
                        states[p].length + 1;

                states[clone].next =
                        new HashMap<>(
                                states[q].next
                        );

                states[clone].link =
                        states[q].link;


                while (p != -1
                        && states[p].next.get(ch)
                        == q) {

                    states[p].next.put(
                            ch,
                            clone
                    );

                    p = states[p].link;
                }


                states[q].link = clone;

                states[current].link = clone;
            }
        }


        last = current;
    }


    // Check whether a substring exists
    public boolean contains(String pattern) {

        int current = 0;

        for (char ch :
                pattern.toCharArray()) {

            if (!states[current].next
                    .containsKey(ch)) {

                return false;
            }

            current =
                    states[current].next.get(ch);
        }

        return true;
    }
}