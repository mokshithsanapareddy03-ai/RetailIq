package retailiq.algorithms.stringalgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {

    private static class Node {

        Map<Character, Node> children =
                new HashMap<>();

        boolean endOfWord;
    }

    private Node root = new Node();


    // Insert product name
    public void insert(String word) {

        Node current = root;

        word = word.toLowerCase();

        for (char ch : word.toCharArray()) {

            current.children.putIfAbsent(
                    ch,
                    new Node()
            );

            current = current.children.get(ch);
        }

        current.endOfWord = true;
    }


    // Search complete word
    public boolean search(String word) {

        Node node = findNode(word);

        return node != null && node.endOfWord;
    }


    // Product autocomplete
    public List<String> startsWith(String prefix) {

        List<String> result =
                new ArrayList<>();

        Node node = findNode(prefix);

        if (node == null) {
            return result;
        }

        collectWords(
                node,
                prefix.toLowerCase(),
                result
        );

        return result;
    }


    private Node findNode(String word) {

        Node current = root;

        word = word.toLowerCase();

        for (char ch : word.toCharArray()) {

            if (!current.children.containsKey(ch)) {
                return null;
            }

            current = current.children.get(ch);
        }

        return current;
    }


    private void collectWords(
            Node node,
            String word,
            List<String> result) {

        if (node.endOfWord) {
            result.add(word);
        }

        for (Map.Entry<Character, Node> entry
                : node.children.entrySet()) {

            collectWords(
                    entry.getValue(),
                    word + entry.getKey(),
                    result
            );
        }
    }
}