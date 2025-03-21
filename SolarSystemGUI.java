import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class SolarSystemGUI extends JFrame {
    private SolarSystemPanel solarSystemPanel;
    private InfoPanel infoPanel;
    private Timer slideTimer;
    private boolean infoPanelVisible = false;

    public SolarSystemGUI() {
        setTitle("Solar System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        solarSystemPanel = new SolarSystemPanel(this::showInfoPanel);
        solarSystemPanel.setBounds(0, 0, 800, 600);
        add(solarSystemPanel);

        infoPanel = new InfoPanel(this::hideInfoPanel);
        infoPanel.setBounds(800, 0, 300, 600);
        add(infoPanel);

        slideTimer = new Timer(10, e -> animatePanel());
        setVisible(true);
    }

    private void showInfoPanel(String planet) {
        infoPanel.setPlanet(planet);
        if (!infoPanelVisible) {
            infoPanelVisible = true;
            slideTimer.start();
        }
    }

    private void hideInfoPanel() {
        if (infoPanelVisible) {
            infoPanelVisible = false;
            slideTimer.start();
        }
    }

    private void animatePanel() {
        int targetX = infoPanelVisible ? 500 : 800;
        int step = infoPanelVisible ? -10 : 10;
        int currentX = infoPanel.getX();
        if ((infoPanelVisible && currentX > targetX) || (!infoPanelVisible && currentX < targetX)) {
            infoPanel.setLocation(currentX + step, 0);
        } else {
            slideTimer.stop();
        }
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SolarSystemGUI::new);
    }
}

class SolarSystemPanel extends JPanel {
    private final String[] planets = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
    private final Color[] colors = {Color.GRAY, new Color(204, 153, 0), new Color(0, 102, 204),
            new Color(153, 0, 0), new Color(204, 102, 0), new Color(255, 204, 102), new Color(0, 204, 204), new Color(0, 51, 153)};
    private final int[] radii = {5, 8, 8, 6, 15, 12, 10, 10};
    private final int[] distances = {50, 70, 90, 110, 150, 180, 210, 240};
    private final int[][] planetPositions = new int[planets.length][2];
    private double[] angles = new double[planets.length];

    public SolarSystemPanel(java.util.function.Consumer<String> onPlanetClick) {
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < planets.length; i++) {
                    int dx = e.getX() - planetPositions[i][0];
                    int dy = e.getY() - planetPositions[i][1];
                    if (dx * dx + dy * dy <= radii[i] * radii[i]) {
                        onPlanetClick.accept(planets[i]);
                        break;
                    }
                }
            }
        });
        
        // Slow rotation speed
        Timer rotationTimer = new Timer(200, e -> {
            for (int i = 0; i < angles.length; i++) {
                angles[i] += 0.001 * (i + 1);
            }
            repaint();
        });
        rotationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 50; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            g2d.fillRect(x, y, 1, 1);
        }

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        g2d.setColor(new Color(255, 204, 0));
        g2d.fillOval(centerX - 20, centerY - 20, 40, 40);

        for (int i = 0; i < planets.length; i++) {
            int x = (int) (centerX + distances[i] * Math.cos(angles[i]));
            int y = (int) (centerY + distances[i] * Math.sin(angles[i]));
            planetPositions[i][0] = x;
            planetPositions[i][1] = y;

            g2d.setColor(Color.GRAY.darker());
            g2d.drawOval(centerX - distances[i], centerY - distances[i], 2 * distances[i], 2 * distances[i]);
            g2d.setColor(colors[i]);
            g2d.fillOval(x - radii[i], y - radii[i], 2 * radii[i], 2 * radii[i]);
        }
    }
}

class InfoPanel extends JPanel {
    private final JLabel nameLabel;
    private final JTextArea infoArea;
    private static final Map<String, String> PLANET_FACTS = new HashMap<>();

    static {
        PLANET_FACTS.put("Mercury", """
            "Teacher's Pet." \n
            Mercury is the closest planet to the Sun \nand the smallest in the Solar System.
            - Diameter: 4,879 km
            - Orbit: 88 days
            - Moons: 0
            - Temperature: -173°C to 427°C
            """);
        PLANET_FACTS.put("Venus", """
            "Hot stuff." \n
            Venus is the hottest planet with a thick, toxic atmosphere.
            - Diameter: 12,104 km
            - Orbit: 225 days
            - Moons: 0
            - Temperature: 462°C
            """);
        PLANET_FACTS.put("Earth", """
            "Home sweet home." \n
            Earth is the only known planet with life, \nfeaturing diverse ecosystems.
            - Diameter: 12,742 km
            - Orbit: 365 days
            - Moons: 1
            - Temperature: -88°C to 58°C
            """);
        PLANET_FACTS.put("Mars", """
            "The Red Planet." \n
            Mars, known as the Red Planet, has the tallest volcano and deepest canyon.
            - Diameter: 6,779 km
            - Orbit: 687 days
            - Moons: 2
            - Temperature: -125°C to 20°C
            """);
        PLANET_FACTS.put("Jupiter", """
            "The big guy." \n
            Jupiter is the largest planet, famous for its Great Red Spot storm.
            - Diameter: 139,820 km
            - Orbit: 12 years
            - Moons: 79
            - Temperature: -145°C
            """);
        PLANET_FACTS.put("Saturn", """
            "Has a nice ring to it." \n
            Saturn is renowned for its stunning ring system made of ice and rock.
            - Diameter: 116,460 km
            - Orbit: 29 years
            - Moons: 82
            - Temperature: -178°C
            """);
        PLANET_FACTS.put("Uranus", """
            "..."\n
            Uranus rotates on its side and has a blue-green color due to methane.
            - Diameter: 50,724 km
            - Orbit: 84 years
            - Moons: 27
            - Temperature: -224°C
            """);
        PLANET_FACTS.put("Neptune", """
            "Just a chill guy:)". \n
            Neptune is the farthest planet from the Sun, \nknown for its strong winds.
            - Diameter: 49,244 km
            - Orbit: 165 years
            - Moons: 14
            - Temperature: -214°C
            """);
    }

    public InfoPanel(Runnable onClose) {
        setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 30, 30));
        
        nameLabel = new JLabel("", SwingConstants.CENTER);
        nameLabel.setForeground(Color.YELLOW);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(nameLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> onClose.run());
        topPanel.add(closeButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(30, 30, 30));
        infoArea.setForeground(Color.WHITE);
        infoArea.setFont(new Font("Serif", Font.PLAIN, 14));
        add(new JScrollPane(infoArea), BorderLayout.CENTER);
    }

    public void setPlanet(String planet) {
        nameLabel.setText(planet);
        infoArea.setText(PLANET_FACTS.getOrDefault(planet, "No data available"));
    }
}
