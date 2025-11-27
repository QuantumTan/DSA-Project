import java.util.ArrayList;

/**
 * Stores the result of solving the knapsack problem for a single group.
 */
public class GroupResult {
    private int groupIndex;
    private int itemsSelected;
    private int dynamicTimeUsed;
    private int maxValue;
    private ArrayList<Item> selectedItems;

    /**
     * Constructs a GroupResult with the specified values.
     * 
     * @param groupIndex The index of the group
     * @param itemsSelected Number of items selected in this group
     * @param dynamicTimeUsed Total dynamic time/weight used
     * @param maxValue Maximum value achieved for this group
     * @param selectedItems List of items selected in this group
     */
    public GroupResult(int groupIndex, int itemsSelected, int dynamicTimeUsed, 
                       int maxValue, ArrayList<Item> selectedItems) {
        this.groupIndex = groupIndex;
        this.itemsSelected = itemsSelected;
        this.dynamicTimeUsed = dynamicTimeUsed;
        this.maxValue = maxValue;
        this.selectedItems = selectedItems;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public int getItemsSelected() {
        return itemsSelected;
    }

    public int getDynamicTimeUsed() {
        return dynamicTimeUsed;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public ArrayList<Item> getSelectedItems() {
        return selectedItems;
    }
}
