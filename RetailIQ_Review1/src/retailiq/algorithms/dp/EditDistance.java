package retailiq.algorithms.dp;

public class EditDistance {


    // =====================================================
    // LEVENSHTEIN / WAGNER-FISCHER
    // =====================================================

    public static int levenshtein(
            String a,
            String b) {

        int m = a.length();
        int n = b.length();

        int[][] dp =
                new int[m + 1][n + 1];


        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }


        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }


        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                int cost =
                        a.charAt(i - 1)
                        == b.charAt(j - 1)
                        ? 0
                        : 1;


                dp[i][j] =
                        Math.min(

                            Math.min(
                                dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1
                            ),

                            dp[i - 1][j - 1]
                                    + cost
                        );
            }
        }


        return dp[m][n];
    }


    // =====================================================
    // DAMERAU-LEVENSHTEIN
    // Allows adjacent transposition
    // =====================================================

    public static int damerauLevenshtein(
            String a,
            String b) {

        int m = a.length();
        int n = b.length();

        int[][] dp =
                new int[m + 1][n + 1];


        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }


        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }


        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                int cost =
                        a.charAt(i - 1)
                        == b.charAt(j - 1)
                        ? 0
                        : 1;


                dp[i][j] =
                        Math.min(

                            Math.min(
                                dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1
                            ),

                            dp[i - 1][j - 1]
                                    + cost
                        );


                if (i > 1
                        && j > 1
                        && a.charAt(i - 1)
                        == b.charAt(j - 2)
                        && a.charAt(i - 2)
                        == b.charAt(j - 1)) {

                    dp[i][j] =
                            Math.min(
                                dp[i][j],
                                dp[i - 2][j - 2] + 1
                            );
                }
            }
        }


        return dp[m][n];
    }


    // =====================================================
    // WEIGHTED EDIT DISTANCE
    // Insert = 1
    // Delete = 1
    // Substitute = 2
    // =====================================================

    public static int weightedEditDistance(
            String a,
            String b) {

        int m = a.length();
        int n = b.length();

        int[][] dp =
                new int[m + 1][n + 1];


        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }


        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }


        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                int substitutionCost =
                        a.charAt(i - 1)
                        == b.charAt(j - 1)
                        ? 0
                        : 2;


                dp[i][j] =
                        Math.min(

                            Math.min(
                                dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1
                            ),

                            dp[i - 1][j - 1]
                                    + substitutionCost
                        );
            }
        }


        return dp[m][n];
    }
}