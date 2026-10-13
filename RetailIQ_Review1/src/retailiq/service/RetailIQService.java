package retailiq.service;

import java.util.ArrayList;
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
import retailiq.model.Product;

public class RetailIQService {

    private final List<Product> products;

    private final Trie trie;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public RetailIQService(List<Product> products) {

        this.products = products;

        this.trie = new Trie();

        // Insert all existing product names and word tokens into Trie
        for (Product product : products) {
            indexProductInTrie(product);
        }
    }

    private void indexProductInTrie(Product product) {
        if (product.getName() == null) return;
        trie.insert(product.getName());
        String[] words = product.getName().split("\\s+");
        for (String word : words) {
            if (word.length() > 2) {
                trie.insert(word);
            }
        }
    }

    // =====================================================
    // GET PRODUCTS
    // =====================================================

    public List<Product> getProducts() {

        return products;
    }

    // =====================================================
    // ADD PRODUCT
    // =====================================================

    public void addProduct(Product product) {

        products.add(product);

        indexProductInTrie(product);
    }

    // =====================================================
    // PRODUCT SEARCH
    // Naive / KMP / Z / Rabin-Karp
    // =====================================================

    public void productSearch(
            String query,
            int algorithm) {

        System.out.println(
                "\n===== PRODUCT SEARCH ====="
        );

        if (query == null ||
                query.trim().isEmpty()) {

            System.out.println(
                    "Search query cannot be empty."
            );

            return;
        }

        query =
                query.toLowerCase();

        boolean found = false;

        for (Product product : products) {

            String text =
                    (
                            product.getName()
                            + " "
                            + product.getCategory()
                            + " "
                            + product.getDescription()
                    ).toLowerCase();

            List<Integer> positions =
                    new ArrayList<>();

            switch (algorithm) {

                case 1:

                    positions =
                            StringAlgorithms.naiveSearch(
                                    text,
                                    query
                            );

                    break;

                case 2:

                    positions =
                            StringAlgorithms.kmpSearch(
                                    text,
                                    query
                            );

                    break;

                case 3:

                    positions =
                            StringAlgorithms.zSearch(
                                    text,
                                    query
                            );

                    break;

                case 4:

                    positions =
                            StringAlgorithms.rabinKarp(
                                    text,
                                    query
                            );

                    break;

                default:

                    System.out.println(
                            "Invalid algorithm."
                    );

                    return;
            }

            if (!positions.isEmpty()) {

                found = true;

                System.out.println(
                        "\nFOUND → "
                        + product
                );

                System.out.println(
                        "Match positions: "
                        + positions
                );
            }
        }

        if (!found) {

            System.out.println(
                    "\nNo matching products found."
            );
        }
    }

    // =====================================================
    // TRIE AUTOCOMPLETE
    // =====================================================

    public void productAutocomplete(
            String prefix) {

        System.out.println(
                "\n===== PRODUCT AUTOCOMPLETE ====="
        );

        if (prefix == null ||
                prefix.trim().isEmpty()) {

            System.out.println(
                    "Prefix cannot be empty."
            );

            return;
        }

        prefix =
                prefix.toLowerCase();

        List<String> suggestions =
                trie.startsWith(prefix);

        if (suggestions.isEmpty()) {

            System.out.println(
                    "No suggestions found."
            );

        } else {

            System.out.println(
                    "Suggestions:"
            );

            for (String suggestion :
                    suggestions) {

                System.out.println(
                        " → " + suggestion
                );
            }
        }
    }



    // =====================================================
    // SUFFIX ARRAY + KASAI
    // =====================================================

    public void productDescriptionIndexing(
            String text) {

        System.out.println(
                "\n===== SUFFIX ARRAY + KASAI ====="
        );

        if (text == null ||
                text.trim().isEmpty()) {

            System.out.println(
                    "Text cannot be empty."
            );

            return;
        }

        int[] suffixArray =
                SuffixArray.build(text);

        int[] lcp =
                SuffixArray.kasaiLCP(
                        text,
                        suffixArray
                );

        System.out.println(
                "Text: " + text
        );

        System.out.println(
                "Suffix Array: "
                + Arrays.toString(
                        suffixArray
                )
        );

        System.out.println(
                "LCP Array: "
                + Arrays.toString(lcp)
        );
    }

    // =====================================================
    // SUFFIX AUTOMATON
    // =====================================================

    public void substringSearch(
            String text,
            String pattern) {

        System.out.println(
                "\n===== SUFFIX AUTOMATON ====="
        );

        if (text == null ||
                text.isEmpty()) {

            System.out.println(
                    "Text cannot be empty."
            );

            return;
        }

        if (pattern == null ||
                pattern.isEmpty()) {

            System.out.println(
                    "Pattern cannot be empty."
            );

            return;
        }

        SuffixAutomaton sam =
                new SuffixAutomaton(text);

        System.out.println(
                "Text: " + text
        );

        System.out.println(
                "Pattern: " + pattern
        );

        System.out.println(
                "Contains: "
                + sam.contains(pattern)
        );
    }

    // =====================================================
    // EDIT DISTANCE
    // =====================================================

    public void queryCorrection(
            String customerQuery) {

        System.out.println(
                "\n===== CUSTOMER QUERY CORRECTION ====="
        );

        if (customerQuery == null ||
                customerQuery.trim().isEmpty()) {

            System.out.println(
                    "Customer query cannot be empty."
            );

            return;
        }

        customerQuery =
                customerQuery.toLowerCase().trim();

        int bestDistance =
                Integer.MAX_VALUE;

        Product bestProduct =
                null;

        String bestMatchedTerm =
                "";

        for (Product product : products) {

            String productName =
                    product.getName()
                            .toLowerCase();

            String[] tokens =
                    productName.split("\\s+");

            for (String token : tokens) {

                int tokenDist =
                        EditDistance.levenshtein(
                                customerQuery,
                                token
                        );

                if (tokenDist < bestDistance) {

                    bestDistance = tokenDist;

                    bestProduct = product;

                    bestMatchedTerm = token;
                }
            }

            int fullDistance =
                    EditDistance.levenshtein(
                            customerQuery,
                            productName
                    );

            if (fullDistance < bestDistance) {

                bestDistance = fullDistance;

                bestProduct = product;

                bestMatchedTerm = productName;
            }
        }

        if (bestProduct == null) {

            System.out.println(
                    "No products available."
            );

            return;
        }

        System.out.println(
                "Customer Query: "
                + customerQuery
        );

        System.out.println(
                "Closest Product Match: "
                + bestProduct
        );

        System.out.println(
                "Matched Product Term: \""
                + bestMatchedTerm
                + "\""
        );

        System.out.println(
                "Levenshtein Distance: "
                + bestDistance
        );

        System.out.println(
                "Damerau-Levenshtein Distance: "
                + EditDistance.damerauLevenshtein(
                        customerQuery,
                        bestMatchedTerm
                )
        );

        System.out.println(
                "Weighted Edit Distance: "
                + EditDistance.weightedEditDistance(
                        customerQuery,
                        bestMatchedTerm
                )
        );
    }

    // =====================================================
    // BITMASK DP
    // =====================================================

    public void productSelection(
            double budget) {

        System.out.println(
                "\n===== BITMASK DP ====="
        );

        if (products.isEmpty()) {

            System.out.println(
                    "No products available."
            );

            return;
        }

        if (budget <= 0) {

            System.out.println(
                    "Budget must be greater than 0."
            );

            return;
        }

        /*
         * Bitmask DP uses an integer mask.
         * Therefore we restrict the number
         * of products to 20.
         */

        int n =
                Math.min(
                        products.size(),
                        20
                );

        double[] prices =
                new double[n];

        double[] scores =
                new double[n];

        for (int i = 0; i < n; i++) {

            Product product =
                    products.get(i);

            prices[i] =
                    product.getPrice();

            /*
             * Demonstration relevance score.
             *
             * This can later be replaced by
             * rating/popularity/sales score.
             */

            scores[i] =
                    100.0 /
                    (1.0 + prices[i] / 10000.0);
        }

        BitmaskDP.Result result =
                BitmaskDP.maximize(
                        prices,
                        scores,
                        budget
                );

        System.out.println(
                "Budget: ₹" + budget
        );

        System.out.println(
                "Best Score: "
                + result.getBestScore()
        );

        System.out.println(
                "Total Cost: ₹"
                + result.getTotalCost()
        );

        System.out.println(
                "Selected Products:"
        );

        int mask =
                result.getMask();

        boolean selected =
                false;

        for (int i = 0; i < n; i++) {

            if ((mask & (1 << i)) != 0) {

                selected = true;

                System.out.println(
                        " → "
                        + products.get(i)
                );
            }
        }

        if (!selected) {

            System.out.println(
                    "No products fit within budget."
            );
        }
    }

    // =====================================================
    // MATRIX CHAIN DP
    // =====================================================

    public void optimizeProcessing(
            int[] dimensions) {

        System.out.println(
                "\n===== MATRIX CHAIN DP ====="
        );

        if (dimensions == null ||
                dimensions.length < 2) {

            System.out.println(
                    "At least two dimensions are required."
            );

            return;
        }

        long result =
                MatrixChainMultiplication
                        .minMultiplications(
                                dimensions
                        );

        System.out.println(
                "Dimensions: "
                + Arrays.toString(
                        dimensions
                )
        );

        System.out.println(
                "Minimum Multiplications: "
                + result
        );
    }
}