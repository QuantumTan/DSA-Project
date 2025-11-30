import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Main GUI application for the Knapsack Problem Solver.
 * Provides an interactive interface for adding items and solving the knapsack problem.
 */
public class KnapsackGUI extends JFrame {
    // Input fields
    private JTextField tfG, tfT, tfR;
    
    // Table components
    private JTable itemsTable;
    private DefaultTableModel tableModel;
    
    // Result display
    private JTextArea resultArea;
    
    // Control buttons
    private JButton btnAddItem, btnSolve, btnClear, btnReset;
    
    // Data storage
    private ArrayList<Item> items;

    /**
     * Constructs the main GUI window.
     */
    public KnapsackGUI() {
        // Initialize with expected capacity to reduce reallocations
        items = new ArrayList<>(60); // Expected sample data size
        initializeGUI();
    }

    /**
     * Initializes all GUI components and layout.
     * Time Complexity: O(1) - Fixed GUI initialization
     * Space Complexity: O(1) - Fixed number of components
     */
    private void initializeGUI() {
        setTitle("Knapsack Problem - Grouped Items with Dynamic Weights");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(UIConstants.DARK_NAVY);

        // Header Panel
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Main Content Panel with split pane
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setLeftComponent(createLeftPanel());
        mainSplitPane.setRightComponent(createTablePanel());
        mainSplitPane.setDividerLocation(UIConstants.DIVIDER_LOCATION);
        mainSplitPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(mainSplitPane, BorderLayout.CENTER);

        // Result Panel
        add(createResultPanel(), BorderLayout.SOUTH);

        setSize(UIConstants.WINDOW_SIZE);
        setMinimumSize(UIConstants.WINDOW_MIN_SIZE);
        setLocationRelativeTo(null);
    }

    /**
     * Creates the header panel with title and subtitle.
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UIConstants.DARK_NAVY);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        panel.setPreferredSize(UIConstants.HEADER_SIZE);

        JLabel titleLabel = new JLabel("KNAPSACK PROBLEM SOLVER", JLabel.CENTER);
        titleLabel.setForeground(UIConstants.LIGHT_BLUE);
        titleLabel.setFont(UIConstants.TITLE_FONT);

        JLabel subtitleLabel = new JLabel(
            "Grouped Items with Time-Limited Capacities & Dynamic Weights", 
            JLabel.CENTER
        );
        subtitleLabel.setForeground(UIConstants.LIGHT_GRAY);
        subtitleLabel.setFont(UIConstants.SUBTITLE_FONT);

        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(subtitleLabel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the left panel containing parameters and controls.
     */
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(UIConstants.DARK_NAVY);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(UIConstants.LEFT_PANEL_SIZE);

        JPanel topSection = new JPanel(new BorderLayout(10, 10));
        topSection.setBackground(UIConstants.DARK_NAVY);
        topSection.add(createParametersPanel(), BorderLayout.NORTH);
        topSection.add(createControlPanel(), BorderLayout.CENTER);
        
        panel.add(topSection, BorderLayout.NORTH);
        panel.add(Box.createVerticalGlue(), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates the parameters input panel (G, T, R).
     */
    private JPanel createParametersPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIConstants.MEDIUM_BLUE, 1), 
            "Parameters", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP,
            UIConstants.BORDER_TITLE_FONT,
            UIConstants.LIGHT_BLUE
        ));
        panel.setBackground(UIConstants.DARK_NAVY);
        panel.setPreferredSize(UIConstants.PARAMETERS_PANEL_SIZE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 8, 4, 8);

        // Groups (G)
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.4;
        panel.add(createStyledLabel("Groups (G):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        tfG = createStyledTextField("2");
        panel.add(tfG, gbc);

        // Time Limit (T)
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.4;
        panel.add(createStyledLabel("Time Limit (T):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        tfT = createStyledTextField("100");
        panel.add(tfT, gbc);

        // Rate (R)
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.4;
        panel.add(createStyledLabel("Rate (R):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.6;
        tfR = createStyledTextField("0");
        panel.add(tfR, gbc);

        return panel;
    }

    /**
     * Creates the control panel with action buttons.
     */
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIConstants.MEDIUM_BLUE, 1), 
            "Controls", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP,
            UIConstants.BORDER_TITLE_FONT,
            UIConstants.LIGHT_BLUE
        ));
        panel.setBackground(UIConstants.DARK_NAVY);
        panel.setPreferredSize(UIConstants.CONTROL_PANEL_SIZE);
        panel.setMaximumSize(UIConstants.CONTROL_PANEL_SIZE);

        // Create and configure buttons
        btnAddItem = createStyledButton("Add Item", 400);
        btnSolve = createStyledButton("Solve", 400);
        btnClear = createStyledButton("Clear", 400);
        btnReset = createStyledButton("Reset", 400);

        // Attach action listeners
        btnAddItem.addActionListener(new AddItemListener());
        btnSolve.addActionListener(new SolveListener());
        btnClear.addActionListener(new ClearListener());
        btnReset.addActionListener(new ResetListener());

        panel.add(btnAddItem);
        panel.add(btnSolve);
        panel.add(btnClear);
        panel.add(btnReset);

        return panel;
    }

    /**
     * Creates the table panel for displaying items.
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIConstants.MEDIUM_BLUE, 1), 
            "Items Database", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP,
            UIConstants.BORDER_TITLE_FONT,
            UIConstants.LIGHT_BLUE
        ));
        panel.setBackground(UIConstants.DARK_NAVY);

        String[] columns = {"ID", "Value", "Weight", "Group", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemsTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(
                javax.swing.table.TableCellRenderer renderer, 
                int row, int column
            ) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(true);
                }
                
                // Alternating row colors
                c.setBackground(row % 2 == 0 ? UIConstants.DARK_GRAY : UIConstants.MEDIUM_GRAY);
                
                // Color code based on column content
                c.setForeground(getColorForCell(row, column));
                
                return c;
            }
        };

        configureTable();
        
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.getViewport().setBackground(UIConstants.DARK_GRAY);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.MEDIUM_BLUE, 1));
        scrollPane.setPreferredSize(UIConstants.TABLE_SCROLL_SIZE);

        panel.add(scrollPane, BorderLayout.CENTER);

        addSampleData();
        return panel;
    }

    /**
     * Configures table styling and behavior.
     */
    private void configureTable() {
        itemsTable.setBackground(UIConstants.DARK_GRAY);
        itemsTable.setForeground(UIConstants.LIGHT_GRAY);
        itemsTable.setGridColor(UIConstants.MEDIUM_BLUE);
        itemsTable.setSelectionBackground(UIConstants.LIGHT_BLUE);
        itemsTable.setSelectionForeground(UIConstants.DARK_NAVY);
        itemsTable.setFont(UIConstants.TABLE_FONT);
        itemsTable.setRowHeight(UIConstants.TABLE_ROW_HEIGHT);
        itemsTable.getTableHeader().setReorderingAllowed(false);
        itemsTable.getTableHeader().setResizingAllowed(false);

        JTableHeader header = itemsTable.getTableHeader();
        header.setBackground(UIConstants.DARK_NAVY);
        header.setForeground(UIConstants.LIGHT_BLUE);
        header.setFont(UIConstants.TABLE_HEADER_FONT);
        header.setBorder(BorderFactory.createLineBorder(UIConstants.MEDIUM_BLUE, 1));
    }

    /**
     * Determines the color for a specific table cell based on its content.
     */
    private Color getColorForCell(int row, int column) {
        String value = itemsTable.getValueAt(row, column).toString();
        
        switch (column) {
            case 1: // Value column
                try {
                    int val = Integer.parseInt(value);
                    if (val >= 100) return UIConstants.ACCENT_BLUE;
                    if (val >= 80) return UIConstants.LIGHT_BLUE;
                } catch (NumberFormatException e) {
                    // Fall through to default
                }
                break;
                
            case 3: // Group column
                switch (value) {
                    case "0": return UIConstants.GROUP_0_COLOR;
                    case "1": return UIConstants.GROUP_1_COLOR;
                    case "2": return UIConstants.GROUP_2_COLOR;
                }
                break;
                
            case 4: // Status column
                if ("Available".equals(value)) {
                    return UIConstants.LIGHT_BLUE;
                }
                break;
        }
        
        return UIConstants.LIGHT_GRAY;
    }

    /**
     * Creates the result panel for displaying solver output.
     */
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(UIConstants.MEDIUM_BLUE, 1), 
            "Solution Results", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP,
            UIConstants.BORDER_TITLE_FONT,
            UIConstants.LIGHT_BLUE
        ));
        panel.setBackground(UIConstants.DARK_NAVY);
        panel.setPreferredSize(UIConstants.RESULT_PANEL_SIZE);

        resultArea = new JTextArea(16, 110);
        resultArea.setEditable(false);
        resultArea.setFont(UIConstants.RESULT_FONT);
        resultArea.setBackground(UIConstants.RESULT_BG);
        resultArea.setForeground(UIConstants.LIGHT_GRAY);
        resultArea.setCaretColor(UIConstants.LIGHT_BLUE);
        resultArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText(ResultFormatter.getInitialResultText());

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.getViewport().setBackground(UIConstants.RESULT_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.MEDIUM_BLUE, 1));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates a styled label with consistent formatting.
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIConstants.LIGHT_GRAY);
        label.setFont(UIConstants.LABEL_FONT);
        return label;
    }

    /**
     * Creates a styled text field with consistent formatting.
     */
    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text);
        field.setBackground(UIConstants.DARK_GRAY);
        field.setForeground(UIConstants.LIGHT_GRAY);
        field.setCaretColor(UIConstants.LIGHT_BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.MEDIUM_BLUE, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        field.setFont(UIConstants.LABEL_FONT);
        field.setPreferredSize(UIConstants.TEXTFIELD_SIZE);
        return field;
    }

    /**
     * Creates a styled button with hover effects and rounded corners.
     */
    private JButton createStyledButton(String text, int width) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                   RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 
                               UIConstants.BUTTON_BORDER_RADIUS, 
                               UIConstants.BUTTON_BORDER_RADIUS);
                
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                
                g2.dispose();
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                   RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConstants.MEDIUM_BLUE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 
                               UIConstants.BUTTON_BORDER_RADIUS, 
                               UIConstants.BUTTON_BORDER_RADIUS);
                g2.dispose();
            }
        };
        
        button.setBackground(UIConstants.LIGHT_BLUE);
        button.setForeground(UIConstants.DARK_NAVY);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setPreferredSize(new Dimension(width, UIConstants.BUTTON_SIZE.height));
        
        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(UIConstants.ACCENT_BLUE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(UIConstants.LIGHT_BLUE);
            }
        });
        
        return button;
    }

    /**
     * Adds sample data to the table for demonstration.
     * Time Complexity: O(1) - Fixed number of operations (60 items)
     * Space Complexity: O(1) - Constant space for fixed sample data
     */
    private void addSampleData() {
        // Group 0 - 20 items
        addItemToTable(60, 10, 0, "Available");
        addItemToTable(100, 20, 0, "Available");
        addItemToTable(120, 30, 0, "Available");
        addItemToTable(80, 15, 0, "Available");
        addItemToTable(90, 25, 0, "Available");
        addItemToTable(110, 35, 0, "Available");
        addItemToTable(70, 12, 0, "Available");
        addItemToTable(95, 22, 0, "Available");
        addItemToTable(105, 28, 0, "Available");
        addItemToTable(85, 18, 0, "Available");
        addItemToTable(75, 14, 0, "Available");
        addItemToTable(115, 32, 0, "Available");
        addItemToTable(65, 11, 0, "Available");
        addItemToTable(125, 38, 0, "Available");
        addItemToTable(88, 19, 0, "Available");
        addItemToTable(92, 21, 0, "Available");
        addItemToTable(78, 16, 0, "Available");
        addItemToTable(102, 26, 0, "Available");
        addItemToTable(108, 29, 0, "Available");
        addItemToTable(82, 17, 0, "Available");
        
        // Group 1 - 20 items
        addItemToTable(130, 40, 1, "Available");
        addItemToTable(140, 45, 1, "Available");
        addItemToTable(150, 50, 1, "Available");
        addItemToTable(135, 42, 1, "Available");
        addItemToTable(145, 47, 1, "Available");
        addItemToTable(155, 52, 1, "Available");
        addItemToTable(132, 41, 1, "Available");
        addItemToTable(142, 46, 1, "Available");
        addItemToTable(152, 51, 1, "Available");
        addItemToTable(138, 44, 1, "Available");
        addItemToTable(148, 48, 1, "Available");
        addItemToTable(158, 53, 1, "Available");
        addItemToTable(133, 43, 1, "Available");
        addItemToTable(143, 49, 1, "Available");
        addItemToTable(160, 55, 1, "Available");
        addItemToTable(136, 39, 1, "Available");
        addItemToTable(146, 46, 1, "Available");
        addItemToTable(156, 54, 1, "Available");
        addItemToTable(139, 42, 1, "Available");
        addItemToTable(149, 47, 1, "Available");
        
        // Group 2 - 20 items (only if G >= 3)
        addItemToTable(170, 60, 2, "Available");
        addItemToTable(180, 65, 2, "Available");
        addItemToTable(190, 70, 2, "Available");
        addItemToTable(175, 62, 2, "Available");
        addItemToTable(185, 67, 2, "Available");
        addItemToTable(195, 72, 2, "Available");
        addItemToTable(172, 61, 2, "Available");
        addItemToTable(182, 66, 2, "Available");
        addItemToTable(192, 71, 2, "Available");
        addItemToTable(178, 64, 2, "Available");
        addItemToTable(188, 68, 2, "Available");
        addItemToTable(198, 73, 2, "Available");
        addItemToTable(173, 63, 2, "Available");
        addItemToTable(183, 69, 2, "Available");
        addItemToTable(200, 75, 2, "Available");
        addItemToTable(176, 59, 2, "Available");
        addItemToTable(186, 66, 2, "Available");
        addItemToTable(196, 74, 2, "Available");
        addItemToTable(179, 62, 2, "Available");
        addItemToTable(189, 67, 2, "Available");
    }

    /**
     * Adds an item to the table and items list.
     * Time Complexity: O(1) - Constant time for array creation and additions
     * Space Complexity: O(1) - Single row array and one Item object
     */
    private void addItemToTable(int value, int weight, int group, String status) {
        String[] row = {
            String.valueOf(items.size() + 1),
            String.valueOf(value),
            String.valueOf(weight),
            String.valueOf(group),
            status
        };
        tableModel.addRow(row);
        items.add(new Item(value, weight, group));
    }

    // ==================== Action Listeners ====================

    /**
     * Listener for the Add Item button.
     */
    private class AddItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showAddItemDialog();
        }
    }

    /**
     * Listener for the Solve button.
     */
    private class SolveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            solveProblem();
        }
    }

    /**
     * Listener for the Clear button.
     */
    private class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearItems();
        }
    }

    /**
     * Listener for the Reset button.
     */
    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetAll();
        }
    }

    // ==================== Action Methods ====================

    /**
     * Shows a dialog for adding a new item.
     * Validates all inputs to prevent non-numeric and negative values.
     */
    private void showAddItemDialog() {
        try {
            JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
            panel.setBackground(UIConstants.DARK_NAVY);
            
            JTextField valueField = createStyledTextField("");
            JTextField weightField = createStyledTextField("");
            JTextField groupField = createStyledTextField("0");
            
            panel.add(createStyledLabel("Value:"));
            panel.add(valueField);
            panel.add(createStyledLabel("Weight:"));
            panel.add(weightField);
            panel.add(createStyledLabel("Group:"));
            panel.add(groupField);
            
            int result = JOptionPane.showConfirmDialog(
                this, panel, "Add New Item", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );
            
            if (result == JOptionPane.OK_OPTION) {
                // Validate G parameter first
                int G = validateAndParseInt(tfG.getText(), "Groups (G)", 1, 1000);
                
                // Validate item inputs with proper ranges
                int value = validateAndParseInt(valueField.getText(), "Value", 1, 1000000);
                int weight = validateAndParseInt(weightField.getText(), "Weight", 1, 1000000);
                int group = validateAndParseInt(groupField.getText(), "Group", 0, G - 1);
                
                addItemToTable(value, weight, group, "Available");
                showMessage("Item added successfully!", "Success");
            }
        } catch (IllegalArgumentException ex) {
            showMessage(ex.getMessage(), "Validation Error");
        }
    }

    /**
     * Solves the knapsack problem using the current parameters and items.
     * Validates all inputs and optimizes memory before solving.
     */
    private void solveProblem() {
        try {
            // Validate parameters with proper ranges
            int G = validateAndParseInt(tfG.getText(), "Groups (G)", 1, 1000);
            int T = validateAndParseInt(tfT.getText(), "Time Limit (T)", 1, 10000);
            int R = validateAndParseInt(tfR.getText(), "Rate (R)", 0, 1000);

            if (items.isEmpty()) {
                showMessage("Please add some items first!", "Error");
                return;
            }

            // Optimize memory before solving
            optimizeMemory();

            resultArea.setText("Solving knapsack problem... Please wait...\n");
            
            // Run solver in a separate thread to keep UI responsive
            new Thread(() -> {
                try {
                    KnapsackSolver solver = new KnapsackSolver(G, T, R, items);
                    SolverResult result = solver.solve();

                    SwingUtilities.invokeLater(() -> displayResults(result));
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> 
                        showMessage("Error during calculation: " + ex.getMessage(), "Error")
                    );
                }
            }).start();

        } catch (IllegalArgumentException ex) {
            showMessage(ex.getMessage(), "Validation Error");
        }
    }

    /**
     * Displays the solver results in the result area.
     */
    private void displayResults(SolverResult result) {
        resultArea.setText(ResultFormatter.format(result));
        showMessage(
            String.format(
                "Problem solved successfully!\nMaximum value: %d\nSolving time: %.3f ms", 
                result.getMaxValue(), 
                result.getTotalTime()
            ), 
            "Solution Complete"
        );
    }

    /**
     * Clears all items from the table.
     * Time Complexity: O(N) where N is the number of items (clearing operations)
     * Space Complexity: O(1) - No additional space allocated
     */
    private void clearItems() {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Clear all items from the table?", 
            "Clear Items", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setRowCount(0);
            items.clear();
            items.trimToSize(); // Optimize memory after clearing
            resultArea.setText(ResultFormatter.getInitialResultText());
            showMessage("All items cleared!", "Success");
        }
    }

    /**
     * Resets all parameters and clears all data.
     * Optimizes memory after reset.
     */
    private void resetAll() {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Reset all parameters and clear all data?", 
            "Reset All", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            tfG.setText("2");
            tfT.setText("100");
            tfR.setText("0");
            tableModel.setRowCount(0);
            items.clear();
            items.trimToSize(); // Optimize memory after clearing
            resultArea.setText(ResultFormatter.getInitialResultText());
            showMessage("All data reset to defaults!", "Success");
        }
    }

    /**
     * Shows a message dialog to the user.
     */
    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, 
                                     JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Validates and parses an integer from a text field with range checking.
     * Time Complexity: O(n) where n is the string length
     * Space Complexity: O(1)
     * 
     * @param text The string to parse
     * @param fieldName Name of the field for error messages
     * @param min Minimum allowed value (inclusive)
     * @param max Maximum allowed value (inclusive)
     * @return Parsed integer value
     * @throws IllegalArgumentException if validation fails
     */
    private int validateAndParseInt(String text, String fieldName, int min, int max) 
            throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        
        String trimmed = text.trim();
        
        // Check for non-numeric characters
        if (!trimmed.matches("-?\\d+")) {
            throw new IllegalArgumentException(
                fieldName + " must be a valid integer. Got: '" + trimmed + "'"
            );
        }
        
        try {
            int value = Integer.parseInt(trimmed);
            
            // Range validation
            if (value < min || value > max) {
                throw new IllegalArgumentException(
                    String.format("%s must be between %d and %d. Got: %d", 
                                fieldName, min, max, value)
                );
            }
            
            return value;
        } catch (NumberFormatException e) {
            // Handle overflow/underflow
            throw new IllegalArgumentException(
                fieldName + " is out of valid integer range. Got: '" + trimmed + "'"
            );
        }
    }

    /**
     * Optimizes memory usage by trimming ArrayList capacity to match size.
     * Time Complexity: O(n) - creates new array and copies elements
     * Space Complexity: O(n) - temporarily doubles memory during trimming
     */
    private void optimizeMemory() {
        if (items != null) {
            // Trim backing array capacity to match current size.
            items.trimToSize();
        }
    }

    // ==================== Main Method ====================

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                UIManager.put("OptionPane.background", UIConstants.DARK_NAVY);
                UIManager.put("Panel.background", UIConstants.DARK_NAVY);
                UIManager.put("OptionPane.messageForeground", UIConstants.LIGHT_GRAY);
                UIManager.put("OptionPane.messageFont", UIConstants.LABEL_FONT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new KnapsackGUI().setVisible(true);
        });
    }
}
