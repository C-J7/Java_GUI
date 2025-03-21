import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class PortfolioGUI extends JFrame {
    private final int WIDTH = 900;
    private final int HEIGHT = 700;
    private final Color BACKGROUND_COLOR = new Color(20, 20, 20);
    private final Color ACCENT_COLOR = new Color(255, 215, 0);
    private final Color TEXT_COLOR = new Color(240, 240, 240);
    
    private JLabel bioLabel;
    private final String bioText = "Providing functional solutions with Java, ReactJs, NextJs, TypeScript, JavaScript, Python, PostgreSQL, MySQL, NestJs, and Github Workflows.";
    private int bioIndex = 0;
    
    public PortfolioGUI() {
        setTitle("THE ART OF CODE");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0)); //padding

        JLabel titleLabel = new JLabel("THE ART OF CODE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 70));
        titleLabel.setForeground(ACCENT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Center Content Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Quote Section
        JLabel quoteLabel = new JLabel("\"Art is the lie that enables us to realize the truth.\" - Pablo Picasso", SwingConstants.CENTER);
        quoteLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        quoteLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 50, 10); // Adjust padding to raise the section
        centerPanel.add(quoteLabel, gbc);
        
        // Bio Section with Typewriter Effect
        bioLabel = new JLabel("", SwingConstants.CENTER);
        bioLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bioLabel.setForeground(ACCENT_COLOR);
        gbc.gridy = 1;
        centerPanel.add(bioLabel, gbc);
        
        add(centerPanel, BorderLayout.CENTER);

        // Footer Contact Info
        JLabel contactLabel = new JLabel("Email: cjtrgs8@gmail.com | GitHub: github.com/C-J7 | LinkedIn: linkedin.com/Bamgbose-Christian", SwingConstants.CENTER);
        contactLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        contactLabel.setForeground(TEXT_COLOR);
        add(contactLabel, BorderLayout.SOUTH);

        // Typewriter Effect for Bio
        Timer bioTimer = new Timer();
        bioTimer.schedule(new TimerTask() {
            @Override
            public void run() {
            if (bioIndex < bioText.length()) {
                String currentText = bioLabel.getText();
                if (currentText.length() > 0 && currentText.charAt(currentText.length() - 1) == '-') {
                bioLabel.setText(currentText.substring(0, currentText.length() - 1) + bioText.charAt(bioIndex));
                } else {
                bioLabel.setText(currentText + bioText.charAt(bioIndex));
                }
                bioIndex++;
                if (bioIndex % 50 == 0) { 
                bioLabel.setText(bioLabel.getText() + "\n");
                }
            } else {
                bioTimer.cancel();
            }
            }
        }, 0, 50);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PortfolioGUI::new);
    }
}
