package retailiq.algorithms.dp;

public class BitmaskDP {

    public static class Result {

        private double bestScore;

        private double totalCost;

        private int mask;


        public Result(
                double bestScore,
                double totalCost,
                int mask) {

            this.bestScore = bestScore;
            this.totalCost = totalCost;
            this.mask = mask;
        }


        public double getBestScore() {
            return bestScore;
        }


        public double getTotalCost() {
            return totalCost;
        }


        public int getMask() {
            return mask;
        }
    }


    // =====================================================
    // PRODUCT SELECTION USING BITMASK
    // =====================================================

    public static Result maximize(
            double[] prices,
            double[] scores,
            double budget) {

        int n = prices.length;

        double bestScore = 0;

        double bestCost = 0;

        int bestMask = 0;


        for (int mask = 0;
             mask < (1 << n);
             mask++) {

            double cost = 0;

            double score = 0;


            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {

                    cost += prices[i];

                    score += scores[i];
                }
            }


            if (cost <= budget
                    && score > bestScore) {

                bestScore = score;

                bestCost = cost;

                bestMask = mask;
            }
        }


        return new Result(
                bestScore,
                bestCost,
                bestMask
        );
    }
}