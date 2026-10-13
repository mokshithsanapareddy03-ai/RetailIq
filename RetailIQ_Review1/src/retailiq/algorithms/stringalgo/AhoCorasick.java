package retailiq.algorithms.stringalgo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class AhoCorasick {

    private static class Node {

        Map<Character, Node> next = new LinkedHashMap<>();

        Node failure;

        List<String> output = new ArrayList<>();
    }

    private Node root = new Node();


    // Add keyword
    public void addPattern(String pattern) {

        Node current = root;

        pattern = pattern.toLowerCase();

        for (char ch : pattern.toCharArray()) {

            current.next.putIfAbsent(
                    ch,
                    new Node()
            );

            current = current.next.get(ch);
        }

        current.output.add(pattern);
    }


    // Build failure links
    public void build() {

        Queue<Node> queue =
                new ArrayDeque<>();

        root.failure = root;

        for (Node child : root.next.values()) {

            child.failure = root;

            queue.add(child);
        }


        while (!queue.isEmpty()) {

            Node current = queue.poll();

            for (Map.Entry<Character, Node> entry
                    : current.next.entrySet()) {

                char ch = entry.getKey();

                Node child = entry.getValue();

                Node failureNode =
                        current.failure;


                while (failureNode != root &&
                       !failureNode.next.containsKey(ch)) {

                    failureNode =
                            failureNode.failure;
                }


                if (failureNode.next.containsKey(ch)
                        && failureNode.next.get(ch) != child) {

                    child.failure =
                            failureNode.next.get(ch);

                } else {

                    child.failure = root;
                }


                child.output.addAll(
                        child.failure.output
                );

                queue.add(child);
            }
        }
    }


    // Search multiple patterns
    public Map<String, List<Integer>> search(
            String text) {

        Map<String, List<Integer>> result =
                new LinkedHashMap<>();

        Node current = root;

        text = text.toLowerCase();


        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);


            while (current != root &&
                   !current.next.containsKey(ch)) {

                current = current.failure;
            }


            if (current.next.containsKey(ch)) {

                current = current.next.get(ch);

            } else {

                current = root;
            }


            for (String pattern
                    : current.output) {

                int start =
                        i - pattern.length() + 1;

                result.computeIfAbsent(
                        pattern,
                        k -> new ArrayList<>()
                ).add(start);
            }
        }

        return result;
    }
}