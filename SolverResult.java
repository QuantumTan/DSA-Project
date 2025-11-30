import java.util.ArrayList;

/**
 * Represents the complete result of solving the knapsack problem across all groups.
 */
public class SolverResult {
    private int maxValue;
    private double totalTime; // in milliseconds
    private ArrayList<GroupResult> groupResults;

    /**
     * Constructs a SolverResult with the specified values.
     * 
     * @param maxValue The maximum value achieved across all groups
     * @param totalTime The total execution time in milliseconds
     * @param groupResults List of results for each group
     */
    public SolverResult(int maxValue, double totalTime, ArrayList<GroupResult> groupResults) {
        this.maxValue = maxValue;
        this.totalTime = totalTime;
        this.groupResults = groupResults;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public ArrayList<GroupResult> getGroupResults() {
        return groupResults;
    }
}
