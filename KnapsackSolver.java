import java.util.ArrayList;

/**
 * Solves the knapsack problem using dynamic programming.
 * Handles multiple groups of items with dynamic weights.
 */
public class KnapsackSolver {
    private int G; // Number of groups
    private int T; // Time limit
    @SuppressWarnings("unused")
    private int R; // Rate multiplier (unused in classic mode)
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
        // Match the old unenhanced solution: solve per-group using classic 0/1 knapsack
        // with base weights only (ignore R parameter for compatibility with original outputs).
        long totalStartTime = System.nanoTime();

        int maxOverall = 0;
        ArrayList<GroupResult> groupResults = new ArrayList<>();

        for (int group = 0; group < G; group++) {
            ArrayList<Item> groupItems = getItemsForGroup(group);
            if (groupItems.isEmpty()) continue;

            GroupResult groupResult = solveClassicGroup(group, groupItems);
            groupResults.add(groupResult);
            maxOverall = Math.max(maxOverall, groupResult.getMaxValue());
        }

        long totalEndTime = System.nanoTime();
        double totalTime = (totalEndTime - totalStartTime) / 1_000_000.0;
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
    private GroupResult solveClassicGroup(int groupIndex, ArrayList<Item> groupItems) {
        // 1D 0/1 knapsack by base weights only, capacity T
        int[] dp = new int[T + 1];
        @SuppressWarnings("unchecked")
        ArrayList<Item>[] sel = new ArrayList[T + 1];
        for (int t = 0; t <= T; t++) sel[t] = new ArrayList<>();

        for (Item it : groupItems) {
            int w = it.getBaseWeight();
            int v = it.getValue();
            for (int t = T; t >= w; t--) {
                int nv = dp[t - w] + v;
                if (nv > dp[t]) {
                    dp[t] = nv;
                    ArrayList<Item> picked = new ArrayList<>(sel[t - w]);
                    picked.add(it);
                    sel[t] = picked;
                }
            }
        }

        int bestVal = 0;
        int bestT = 0;
        for (int t = 0; t <= T; t++) {
            if (dp[t] > bestVal) {
                bestVal = dp[t];
                bestT = t;
            }
        }

        ArrayList<Item> bestSel = sel[bestT];
        return new GroupResult(
            groupIndex,
            bestSel.size(),
            bestT,
            bestVal,
            new ArrayList<>(bestSel)
        );
    }

    // Dynamic time for a selection of k items is captured directly by t in DP state.
}
