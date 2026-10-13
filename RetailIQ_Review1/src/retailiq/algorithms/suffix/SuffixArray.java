package retailiq.algorithms.suffix;

import java.util.Arrays;

public class SuffixArray {

    // =====================================================
    // SUFFIX ARRAY
    // Prefix-doubling implementation
    // =====================================================

    public static int[] build(String text) {

        int n = text.length();

        Integer[] suffixArray =
                new Integer[n];

        int[] rank = new int[n];


        for (int i = 0; i < n; i++) {

            suffixArray[i] = i;

            rank[i] = text.charAt(i);
        }


        for (int k = 1; k < n; k *= 2) {

            final int step = k;

            final int[] currentRank = rank;


            Arrays.sort(
                    suffixArray,
                    (a, b) -> {

                        if (currentRank[a]
                                != currentRank[b]) {

                            return Integer.compare(
                                    currentRank[a],
                                    currentRank[b]
                            );
                        }


                        int rankA =
                                a + step < n
                                ? currentRank[a + step]
                                : -1;

                        int rankB =
                                b + step < n
                                ? currentRank[b + step]
                                : -1;


                        return Integer.compare(
                                rankA,
                                rankB
                        );
                    }
            );


            int[] newRank =
                    new int[n];

            newRank[suffixArray[0]] = 0;


            for (int i = 1; i < n; i++) {

                int previous =
                        suffixArray[i - 1];

                int current =
                        suffixArray[i];


                int previousSecond =
                        previous + step < n
                        ? rank[previous + step]
                        : -1;

                int currentSecond =
                        current + step < n
                        ? rank[current + step]
                        : -1;


                if (rank[previous] != rank[current]
                        || previousSecond
                        != currentSecond) {

                    newRank[current] =
                            newRank[previous] + 1;

                } else {

                    newRank[current] =
                            newRank[previous];
                }
            }


            rank = newRank;


            if (rank[suffixArray[n - 1]]
                    == n - 1) {

                break;
            }
        }


        int[] result =
                new int[n];

        for (int i = 0; i < n; i++) {

            result[i] = suffixArray[i];
        }

        return result;
    }


    // =====================================================
    // KASAI LCP
    // =====================================================

    public static int[] kasaiLCP(
            String text,
            int[] suffixArray) {

        int n = text.length();

        int[] rank =
                new int[n];


        for (int i = 0; i < n; i++) {

            rank[suffixArray[i]] = i;
        }


        int[] lcp =
                new int[n];

        int h = 0;


        for (int i = 0; i < n; i++) {

            int position =
                    rank[i];


            if (position == 0) {
                continue;
            }


            int j =
                    suffixArray[position - 1];


            while (i + h < n
                    && j + h < n
                    && text.charAt(i + h)
                    == text.charAt(j + h)) {

                h++;
            }


            lcp[position] = h;


            if (h > 0) {
                h--;
            }
        }


        return lcp;
    }
}