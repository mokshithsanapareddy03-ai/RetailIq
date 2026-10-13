package retailiq.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retailiq.algorithms.dp.BitmaskDP;
import retailiq.algorithms.dp.EditDistance;
import retailiq.algorithms.dp.MatrixChainMultiplication;
import retailiq.algorithms.stringalgo.AhoCorasick;
import retailiq.algorithms.stringalgo.StringAlgorithms;
import retailiq.algorithms.stringalgo.Trie;
import retailiq.algorithms.suffix.SuffixArray;
import retailiq.algorithms.suffix.SuffixAutomaton;

public class AlgorithmTests {

    private static void check(
            boolean condition,
            String testName) {

        if (!condition) {

            throw new AssertionError(
                    "FAILED: " + testName
            );
        }

        System.out.println(
                "PASS: " + testName
        );
    }


    public static void main(String[] args) {

        System.out.println(
                "========================================"
        );

        System.out.println(
                "       RETAILIQ ALGORITHM TESTS"
        );

        System.out.println(
                "========================================"
        );


        // =============================================
        // NAIVE
        // =============================================

        List<Integer> naive =
                StringAlgorithms.naiveSearch(
                        "banana",
                        "ana"
                );


        check(
                naive.equals(
                        Arrays.asList(1, 3)
                ),
                "Naive Pattern Matching"
        );


        // =============================================
        // KMP
        // =============================================

        List<Integer> kmp =
                StringAlgorithms.kmpSearch(
                        "banana",
                        "ana"
                );


        check(
                kmp.equals(
                        Arrays.asList(1, 3)
                ),
                "KMP"
        );


        // =============================================
        // Z
        // =============================================

        List<Integer> z =
                StringAlgorithms.zSearch(
                        "banana",
                        "ana"
                );


        check(
                z.equals(
                        Arrays.asList(1, 3)
                ),
                "Z-Function"
        );


        // =============================================
        // RABIN-KARP
        // =============================================

        List<Integer> rk =
                StringAlgorithms.rabinKarp(
                        "banana",
                        "ana"
                );


        check(
                rk.equals(
                        Arrays.asList(1, 3)
                ),
                "Rabin-Karp"
        );


        // =============================================
        // TRIE
        // =============================================

        Trie trie = new Trie();

        trie.insert("iphone");

        trie.insert("ipad");


        check(
                trie.search("iphone"),
                "Trie Exact Search"
        );


        check(
                trie.startsWith("ip").size() == 2,
                "Trie Prefix Search"
        );


        // =============================================
        // AHO-CORASICK
        // =============================================

        AhoCorasick ac =
                new AhoCorasick();


        ac.addPattern("he");

        ac.addPattern("she");

        ac.addPattern("his");

        ac.addPattern("hers");


        ac.build();


        Map<String, List<Integer>> matches =
                ac.search("ushers");


        check(
                matches.containsKey("she"),
                "Aho-Corasick"
        );


        // =============================================
        // SUFFIX ARRAY
        // =============================================

        int[] suffixArray =
                SuffixArray.build("banana");


        int[] expected = {
                5, 3, 1, 0, 4, 2
        };


        check(
                Arrays.equals(
                        suffixArray,
                        expected
                ),
                "Suffix Array"
        );


        // =============================================
        // KASAI
        // =============================================

        int[] lcp =
                SuffixArray.kasaiLCP(
                        "banana",
                        suffixArray
                );


        check(
                lcp[2] == 3,
                "Kasai LCP"
        );


        // =============================================
        // SUFFIX AUTOMATON
        // =============================================

        SuffixAutomaton sam =
                new SuffixAutomaton(
                        "banana"
                );


        check(
                sam.contains("ana"),
                "Suffix Automaton"
        );


        // =============================================
        // LEVENSHTEIN
        // =============================================

        check(
                EditDistance.levenshtein(
                        "kitten",
                        "sitting"
                ) == 3,
                "Levenshtein"
        );


        // =============================================
        // DAMERAU
        // =============================================

        check(
                EditDistance.damerauLevenshtein(
                        "ca",
                        "ac"
                ) == 1,
                "Damerau-Levenshtein"
        );


        // =============================================
        // WEIGHTED EDIT
        // =============================================

        check(
                EditDistance.weightedEditDistance(
                        "abc",
                        "adc"
                ) == 2,
                "Weighted Edit Distance"
        );


        // =============================================
        // BITMASK DP
        // =============================================

        BitmaskDP.Result result =
                BitmaskDP.maximize(
                        new double[]{
                                10,
                                20,
                                15
                        },

                        new double[]{
                                5,
                                8,
                                7
                        },

                        35
                );


        check(
                result.getBestScore() == 15,
                "Bitmask DP"
        );


        // =============================================
        // MATRIX CHAIN
        // =============================================

        check(
                MatrixChainMultiplication
                        .minMultiplications(
                                new int[]{
                                        10,
                                        30,
                                        5,
                                        60
                                }
                        ) == 4500,

                "Matrix Chain Multiplication"
        );


        System.out.println();
        System.out.println(
                "========================================"
        );

        System.out.println(
                "       ALL TESTS PASSED"
        );

        System.out.println(
                "========================================"
        );
    }
}