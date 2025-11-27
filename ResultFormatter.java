import java.util.stream.Collectors;

/**
 * Formats solver results into a readable string output.
 */
public class ResultFormatter {
    
    /**
     * Formats the complete solver result into a formatted string.
     * Time Complexity: O(G * k) where G is groups and k is avg items per group
     * Space Complexity: O(G * k) for building the result string
     * 
     * @param result The solver result to format
     * @return Formatted string representation of the results
     */
    public static String format(SolverResult result) {
        StringBuilder sb = new StringBuilder();
        
        appendHeader(sb);
        appendOutputValue(sb, result.getMaxValue());
        appendGroupResults(sb, result);
        appendOptimalSelection(sb, result);
        appendExecutionTime(sb, result.getTotalTime());
        
        return sb.toString();
    }
    
    /**
     * Appends the header section.
     */
    private static void appendHeader(StringBuilder sb) {
        sb.append("══════════════════════════════════════════════════════════════\n");
        sb.append("                        OUTPUT RESULT                        \n");
        sb.append("══════════════════════════════════════════════════════════════\n\n");
    }
    
    /**
     * Appends the maximum output value.
     */
    private static void appendOutputValue(StringBuilder sb, int maxValue) {
        sb.append("OUTPUT:\n");
        sb.append(maxValue).append("\n\n");
    }
    
    /**
     * Appends the group results summary.
     */
    private static void appendGroupResults(StringBuilder sb, SolverResult result) {
        if (result.getGroupResults() == null || result.getGroupResults().isEmpty()) {
            return;
        }
        
        sb.append("Group Results:\n");
        for (GroupResult groupResult : result.getGroupResults()) {
            sb.append(String.format(
                "• Group %d: %d items, Dynamic Time used: %d, Max Value = %d\n",
                groupResult.getGroupIndex(),
                groupResult.getItemsSelected(),
                groupResult.getDynamicTimeUsed(),
                groupResult.getMaxValue()
            ));
        }
        sb.append("\n");
    }
    
    /**
     * Appends the optimal selection for each group.
     */
    private static void appendOptimalSelection(StringBuilder sb, SolverResult result) {
        if (result.getGroupResults() == null || result.getGroupResults().isEmpty()) {
            return;
        }
        
        sb.append("Optimal Selection:\n");
        for (GroupResult groupResult : result.getGroupResults()) {
            sb.append("Group ").append(groupResult.getGroupIndex()).append(": ");
            if (groupResult.getSelectedItems().isEmpty()) {
                sb.append("No items selected\n");
            } else {
                String selectionText = groupResult.getSelectedItems().stream()
                    .map(Item::toString)
                    .collect(Collectors.joining(" → "));
                sb.append(selectionText).append("\n");
            }
        }
        sb.append("\n");
    }
    
    /**
     * Appends the execution time.
     */
    private static void appendExecutionTime(StringBuilder sb, double totalTime) {
        sb.append("Execution Time Result:\n");
        sb.append(String.format("%.3f ms\n", totalTime));
    }
    
    /**
     * Generates initial/default result text.
     */
    public static String getInitialResultText() {
        return "══════════════════════════════════════════════════════════════\n" +
               "                        OUTPUT RESULT                        \n" +
               "══════════════════════════════════════════════════════════════\n\n" +
               "OUTPUT:\n" +
               "160\n\n" +
               "Group Results:\n" +
               "• Group 0: 2 items, Dynamic Time used: 6, Max Value = 25\n" +
               "• Group 1: 2 items, Dynamic Time used: 10, Max Value = 45\n" +
               "• Group 2: 1 items, Dynamic Time used: 6, Max Value = 30\n\n" +
               "Optimal Selection:\n" +
               "Group 0: (v=10, w=2) → (v=15, w=3)\n" +
               "Group 1: (v=20, w=4) → (v=25, w=5)\n" +
               "Group 2: (v=30, w=6)\n\n" +
               "Execution Time Result:\n" +
               "0.000 ms\n";
    }
}
