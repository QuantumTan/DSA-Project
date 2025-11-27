/**
 * Represents an item in the knapsack problem.
 * Each item has a value, base weight, and belongs to a specific group.
 */
public class Item {
    private int value;
    private int baseWeight;
    private int group;

    /**
     * Constructs an Item with the specified properties.
     * 
     * @param value The value of the item
     * @param baseWeight The base weight of the item
     * @param group The group index this item belongs to
     */
    public Item(int value, int baseWeight, int group) {
        this.value = value;
        this.baseWeight = baseWeight;
        this.group = group;
    }

    /**
     * Calculates the dynamic weight of the item based on its position.
     * Time Complexity: O(1) - Single arithmetic operation
     * Space Complexity: O(1) - No additional space used
     * 
     * @param k The position/step in the selection sequence
     * @param R The rate multiplier
     * @return The calculated dynamic weight
     */
    public int getDynamicWeight(int k, int R) {
        return baseWeight + (R * k);
    }

    public int getValue() {
        return value;
    }

    public int getBaseWeight() {
        return baseWeight;
    }

    public int getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return String.format("(v=%d, w=%d)", value, baseWeight);
    }
}
