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
        
        // Initialize DP tables
        // Time Complexity: O(m * T) for initialization
        // Space Complexity: O(m * T) for DP table and selection tracking
        int[][] dp = new int[m + 1][T + 1];
        @SuppressWarnings("unchecked")
        ArrayList<Item>[][] selection = new ArrayList[m + 1][T + 1];

        for (int i = 0; i <= m; i++) {
            for (int t = 0; t <= T; t++) {
                dp[i][t] = -1;
                selection[i][t] = new ArrayList<>();
            }
        }
        dp[0][0] = 0;

        // Main DP loop: consider each item and each possible count
        // Time Complexity: O(m * T * k) where k is average selection size for copying
        // Space Complexity: O(m * T * k) for storing all possible selections at each state
        for (int i = 0; i < m; i++) {
            Item currentItem = groupItems.get(i);
            
            for (int count = 0; count <= T; count++) {
                if (dp[i][count] >= 0) {
                    // Option 1: Skip current item
                    if (dp[i][count] > dp[i + 1][count]) {
                        dp[i + 1][count] = dp[i][count];
                        selection[i + 1][count] = new ArrayList<>(selection[i][count]);
                    }

                    // Option 2: Take current item (time increases by 1 per item)
                    int newCount = count + 1;
                    if (newCount <= T) {
                        int newValue = dp[i][count] + currentItem.getValue();
                        if (newValue > dp[i + 1][newCount]) {
                            dp[i + 1][newCount] = newValue;
                            selection[i + 1][newCount] = new ArrayList<>(selection[i][count]);
                            selection[i + 1][newCount].add(currentItem);
                        }
                    }
                }
            }
        }

        // Find the best selection from all possible counts
        // Time Complexity: O(T * k) where k is selection size (for copying and calculating)
        // Space Complexity: O(k) for storing the best selection
        int maxGroupValue = 0;
        int bestTimeUsed = Integer.MAX_VALUE;
        ArrayList<Item> bestSelection = new ArrayList<>();
        
        for (int count = 0; count <= T; count++) {
            if (dp[m][count] > maxGroupValue) {
                maxGroupValue = dp[m][count];
                bestSelection = new ArrayList<>(selection[m][count]);
                bestTimeUsed = calculateDynamicTime(bestSelection);
            } else if (dp[m][count] == maxGroupValue && dp[m][count] >= 0) {
                ArrayList<Item> candidateSelection = selection[m][count];
                int candidateTime = calculateDynamicTime(candidateSelection);
                if (candidateTime < bestTimeUsed) {
                    bestTimeUsed = candidateTime;
                    bestSelection = new ArrayList<>(candidateSelection);
                }
            }
        }

        return new GroupResult(
            groupIndex,
            bestSelection.size(),
            bestTimeUsed,
            maxGroupValue,
            new ArrayList<>(bestSelection)
        );
    }

    /**
     * Calculates the total dynamic time/weight for a selection of items.
     * Time Complexity: O(k) where k is the number of items in selection
     * Space Complexity: O(1) - Only uses constant extra space
     * 
     * @param selection List of selected items
     * @return Total dynamic time/weight
     */
    private int calculateDynamicTime(ArrayList<Item> selection) {
        int total = 0;
        int step = 0;
        for (Item item : selection) {
            total += item.getDynamicWeight(step, R);
            step++;
        }
        return total;
    }
}
