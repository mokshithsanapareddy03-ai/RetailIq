package retailiq.algorithms.stringalgo;

import java.util.ArrayList;
import java.util.List;

public class StringAlgorithms {

    // =====================================================
    // 1. NAIVE PATTERN MATCHING
    // =====================================================

    public static List<Integer> naiveSearch(String text, String pattern) {

        List<Integer> result = new ArrayList<>();

        if (pattern == null || pattern.length() == 0) {
            return result;
        }

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return result;
        }

        for (int i = 0; i <= n - m; i++) {

            int j = 0;

            while (j < m &&
                   text.charAt(i + j) == pattern.charAt(j)) {

                j++;
            }

            if (j == m) {
                result.add(i);
            }
        }

        return result;
    }


    // =====================================================
    // 2. KMP - KNUTH MORRIS PRATT
    // =====================================================

    public static int[] buildLPS(String pattern) {

        int[] lps = new int[pattern.length()];

        int length = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) == pattern.charAt(length)) {

                length++;
                lps[i] = length;
                i++;

            } else {

                if (length != 0) {

                    length = lps[length - 1];

                } else {

                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }


    public static List<Integer> kmpSearch(
            String text,
            String pattern) {

        List<Integer> result = new ArrayList<>();

        if (pattern == null || pattern.length() == 0) {
            return result;
        }

        int[] lps = buildLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;

                if (j == pattern.length()) {

                    result.add(i - j);

                    j = lps[j - 1];
                }

            } else {

                if (j != 0) {

                    j = lps[j - 1];

                } else {

                    i++;
                }
            }
        }

        return result;
    }


    // =====================================================
    // 3. Z-FUNCTION
    // =====================================================

    public static int[] zFunction(String s) {

        int n = s.length();

        int[] z = new int[n];

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {

            if (i <= right) {

                z[i] = Math.min(
                        right - i + 1,
                        z[i - left]
                );
            }

            while (i + z[i] < n &&
                   s.charAt(z[i]) == s.charAt(i + z[i])) {

                z[i]++;
            }

            if (i + z[i] - 1 > right) {

                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }


    public static List<Integer> zSearch(
            String text,
            String pattern) {

        List<Integer> result = new ArrayList<>();

        if (pattern == null || pattern.length() == 0) {
            return result;
        }

        String combined = pattern + "$" + text;

        int[] z = zFunction(combined);

        int patternLength = pattern.length();

        for (int i = patternLength + 1;
             i < combined.length();
             i++) {

            if (z[i] == patternLength) {

                result.add(
                        i - patternLength - 1
                );
            }
        }

        return result;
    }


    // =====================================================
    // 4. RABIN-KARP
    // =====================================================

    public static List<Integer> rabinKarp(
            String text,
            String pattern) {

        List<Integer> result = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        if (m == 0 || m > n) {
            return result;
        }

        final long BASE = 256;
        final long MOD = 1_000_000_007L;

        long patternHash = 0;
        long textHash = 0;

        long highPower = 1;

        for (int i = 0; i < m - 1; i++) {

            highPower =
                    (highPower * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {

            patternHash =
                    (patternHash * BASE
                            + pattern.charAt(i)) % MOD;

            textHash =
                    (textHash * BASE
                            + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {

            if (patternHash == textHash) {

                boolean match = true;

                for (int j = 0; j < m; j++) {

                    if (text.charAt(i + j)
                            != pattern.charAt(j)) {

                        match = false;
                        break;
                    }
                }

                if (match) {
                    result.add(i);
                }
            }

            if (i < n - m) {

                textHash =
                        (BASE *
                        (textHash
                        - text.charAt(i) * highPower)
                        + text.charAt(i + m)) % MOD;

                if (textHash < 0) {
                    textHash += MOD;
                }
            }
        }

        return result;
    }
}