import java.util.ArrayList;

/**
 * Solves the knapsack problem using dynamic programming.
 * Handles multiple groups of items with dynamic weights.
 */
public class KnapsackSolver {
    private int G; // Number of groups
    private int T; // Time limit
    private int R; // Rate multiplier
    private ArrayList<Item> items;

    /**
     * Constructs a KnapsackSolver with the specified parameters.
     * 
     * @param G Number of groups
     * @param T Time limit
     * @param R Rate multiplier for dynamic weights
     * @param items List of all items to consider
     */
    public KnapsackSolver(int G, int T, int R, ArrayList<Item> items) {
        this.G = G;
        this.T = T;
        this.R = R;
        this.items = items;
    }

    /**
     * Solves the knapsack problem for all groups.
     * Overall Time Complexity: O(G * m * T * k) where:
     *   - G is the number of groups
     *   - m is the average items per group
     *   - T is the time limit
     *   - k is the average selection size
     * Overall Space Complexity: O(G * m * T * k)
     * 
     * @return SolverResult containing the maximum value and detailed group results
     */
    public SolverResult solve() {
        long totalStartTime = System.nanoTime();
        
        int maxOverall = 0;
        ArrayList<GroupResult> groupResults = new ArrayList<>();
        
        // Process each group independently
        // Time Complexity: O(G * N) where G is number of groups, N is total items
        // Space Complexity: O(N) for storing group-specific items
        for (int group = 0; group < G; group++) {
            ArrayList<Item> groupItems = getItemsForGroup(group);

            if (groupItems.isEmpty()) {
                continue;
            }

            GroupResult groupResult = solveForGroup(group, groupItems);
            groupResults.add(groupResult);
            maxOverall = Math.max(maxOverall, groupResult.getMaxValue());
        }

        long totalEndTime = System.nanoTime();
        double totalTime = (totalEndTime - totalStartTime) / 1_000_000.0; // Convert to ms

        return new SolverResult(maxOverall, totalTime, groupResults);
    }

    /**
     * Filters items belonging to a specific group.
     * Time Complexity: O(N) where N is total items
     * Space Complexity: O(m) where m is items in the group
     * 
     * @param group The group index to filter by
     * @return List of items belonging to the specified group
     */
    private ArrayList<Item> getItemsForGroup(int group) {
        ArrayList<Item> groupItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getGroup() == group) {
                groupItems.add(item);
            }
        }
        return groupItems;
    }

    /**
     * Solves the knapsack problem for a single group using dynamic programming.
     * Time Complexity: O(m * T * k) where m is items, T is time limit, k is selection size
     * Space Complexity: O(m * T * k) for DP table and selection tracking
     * 
     * @param groupIndex The index of the group being solved
     * @param groupItems List of items in this group
     * @return GroupResult containing the optimal solution for this group
     */
    private GroupResult solveForGroup(int groupIndex, ArrayList<Item> groupItems) {
        int m = groupItems.size();

        // DP over (k items picked, total time t used) -> max value
        // dp[k][t] = max value using exactly k items and total dynamic time t (<= T)
        int[][] dp = new int[T + 1][T + 1];
        @SuppressWarnings("unchecked")
        ArrayList<Item>[][] sel = new ArrayList[T + 1][T + 1];
        for (int k = 0; k <= T; k++) {
            for (int t = 0; t <= T; t++) {
                dp[k][t] = -1;
                sel[k][t] = new ArrayList<>();
            }
        }
        dp[0][0] = 0;

        // Process each item once (0/1), updating states in a new table each time
        for (int i = 0; i < m; i++) {
            Item it = groupItems.get(i);

            int[][] next = new int[T + 1][T + 1];
            @SuppressWarnings("unchecked")
            ArrayList<Item>[][] nsel = new ArrayList[T + 1][T + 1];
            for (int k = 0; k <= T; k++) {
                for (int t = 0; t <= T; t++) {
                    next[k][t] = dp[k][t];
                    nsel[k][t] = sel[k][t]; // safe to reference; we copy only on changes
                }
            }

            for (int k = 0; k <= T - 1; k++) {
                int cost = it.getBaseWeight() + R * k; // dynamic cost at position k
                if (cost > T) continue;
                for (int t = 0; t + cost <= T; t++) {
                    if (dp[k][t] < 0) continue;
                    int nk = k + 1;
                    int nt = t + cost;
                    int nv = dp[k][t] + it.getValue();
                    if (nv > next[nk][nt]) {
                        next[nk][nt] = nv;
                        ArrayList<Item> picked = new ArrayList<>(sel[k][t]);
                        picked.add(it);
                        nsel[nk][nt] = picked;
                    }
                }
            }

            dp = next;
            sel = nsel;
        }

        // Find best over all (k, t <= T)
        int bestVal = 0;
        int bestK = 0;
        int bestT = 0;
        ArrayList<Item> bestSel = new ArrayList<>();
        for (int k = 0; k <= T; k++) {
            for (int t = 0; t <= T; t++) {
                if (dp[k][t] > bestVal) {
                    bestVal = dp[k][t];
                    bestK = k;
                    bestT = t;
                    bestSel = sel[k][t];
                }
            }
        }

        return new GroupResult(
            groupIndex,
            bestK,
            bestT,
            bestVal,
            new ArrayList<>(bestSel)
        );
    }

    // Dynamic time for a selection of k items is captured directly by t in DP state.
}
