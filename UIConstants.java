import java.awt.*;

/**
 * Defines all UI color constants and styling values for the application.
 */
public class UIConstants {
    // Professional Blue Color Scheme
    public static final Color DARK_NAVY = new Color(20, 30, 50);
    public static final Color MEDIUM_BLUE = new Color(40, 80, 140);
    public static final Color LIGHT_BLUE = new Color(70, 130, 200);
    public static final Color ACCENT_BLUE = new Color(100, 160, 230);
    public static final Color LIGHT_GRAY = new Color(230, 230, 230);
    public static final Color DARK_GRAY = new Color(45, 50, 65);
    public static final Color MEDIUM_GRAY = new Color(60, 65, 80);
    public static final Color RESULT_BG = new Color(35, 40, 55);
    
    // Group colors for table display
    public static final Color GROUP_0_COLOR = new Color(120, 180, 255);
    public static final Color GROUP_1_COLOR = new Color(150, 200, 255);
    public static final Color GROUP_2_COLOR = new Color(180, 220, 255);
    
    // Font configurations
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font TABLE_HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font RESULT_FONT = new Font("Consolas", Font.PLAIN, 13);
    public static final Font BORDER_TITLE_FONT = new Font("Segoe UI", Font.BOLD, 13);
    
    // Dimension constants
    public static final Dimension WINDOW_SIZE = new Dimension(1400, 900);
    public static final Dimension WINDOW_MIN_SIZE = new Dimension(1200, 800);
    public static final Dimension HEADER_SIZE = new Dimension(800, 80);
    public static final Dimension LEFT_PANEL_SIZE = new Dimension(450, 650);
    public static final Dimension PARAMETERS_PANEL_SIZE = new Dimension(420, 160);
    public static final Dimension CONTROL_PANEL_SIZE = new Dimension(420, 210);
    public static final Dimension BUTTON_SIZE = new Dimension(400, 42);
    public static final Dimension TEXTFIELD_SIZE = new Dimension(140, 32);
    public static final Dimension TABLE_SCROLL_SIZE = new Dimension(850, 600);
    public static final Dimension RESULT_PANEL_SIZE = new Dimension(1380, 220);
    
    // Other constants
    public static final int TABLE_ROW_HEIGHT = 30;
    public static final int BUTTON_BORDER_RADIUS = 15;
    public static final int DIVIDER_LOCATION = 460;
    
    private UIConstants() {
        // Prevent instantiation
    }
}
