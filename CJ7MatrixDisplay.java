import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class CJ7MatrixDisplay extends JFrame {
    private final String text = "C-J7";
    private int animationStyle = 0; //0: Glow, 1: Flicker, 2: Rotation
    private ArrayList<RainDrop> rainDrops = new ArrayList<>();
    private float glowAlpha = 1.0f;
    private boolean glowIncreasing = false;
    private Random random = new Random();
    private int[] wiggleOffsets = new int[text.length()]; //hover wiggle effect

    public CJ7MatrixDisplay() {
        setTitle("C-J7 Cyber Matrix Display");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Initialize Rain Drops
        for (int i = 0; i < 50; i++) {
            rainDrops.add(new RainDrop(random.nextInt(800), random.nextInt(600) - 600));
        }

        //Main Panel
        JPanel matrixPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                //Draw Background
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                //Draw Rain Drops
                g2d.setColor(new Color(0, 255, 0, 100));
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 14));
                for (RainDrop drop : rainDrops) {
                    g2d.drawString(String.valueOf((char) (random.nextInt(94) + 33)), drop.x, drop.y);
                }

                //Draw "C-J7" Text
                int fontSize = getWidth() / 7; //For Dynamic Font Sizing
                
                // g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Serif", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Dialog", Font.BOLD, fontSize));
                // g2d.setFont(new Font("DialogInput", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Serif", Font.BOLD, fontSize));
                // g2d.setFont(new Font("SansSerif", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Courier", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Courier New", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Georgia", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Comic Sans MS", Font.BOLD, fontSize));
                // g2d.setFont(new Font("Impact", Font.BOLD, fontSize));
                g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(text);
                int x = (getWidth() - textWidth) / 2;
                int y = getHeight() / 2;

                for (int i = 0; i < text.length(); i++) {
                    char c = text.charAt(i);
                    int charX = x + fm.stringWidth(text.substring(0, i)) + wiggleOffsets[i];

                    //Apply Animation Style
                    switch (animationStyle) {
                        case 0: //Glow
                            g2d.setColor(new Color(0, 255, 0, (int) (glowAlpha * 255)));
                            break;
                        case 1: //Flicker
                            g2d.setColor(random.nextBoolean() ? Color.GREEN : new Color(0, 255, 0, 50));
                            break;
                        case 2: //Rotation
                            g2d.setColor(Color.GREEN);
                            g2d.translate(charX + fm.charWidth(c) / 2, y + fm.getHeight() / 4);
                            g2d.rotate(Math.toRadians((System.currentTimeMillis() / 10) % 360));
                            g2d.translate(-(charX + fm.charWidth(c) / 2), -(y + fm.getHeight() / 4));
                            break;
                    }

                    g2d.drawString(String.valueOf(c), charX, y);
                    if (animationStyle == 2) g2d.setTransform(new java.awt.geom.AffineTransform()); //Reset transform
                }
            }
        };
        matrixPanel.setBackground(Color.BLACK);

        //Mouse Interaction for Wiggle Effect
        matrixPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                FontMetrics fm = matrixPanel.getFontMetrics(new Font("SansSerif", Font.BOLD, 100));
                int textWidth = fm.stringWidth(text);
                int x = (matrixPanel.getWidth() - textWidth) / 2;
                int y = matrixPanel.getHeight() / 2;

                for (int i = 0; i < text.length(); i++) {
                    int charX = x + fm.stringWidth(text.substring(0, i));
                    int charWidth = fm.charWidth(text.charAt(i));
                    if (e.getX() >= charX && e.getX() <= charX + charWidth &&
                        e.getY() >= y - fm.getHeight() && e.getY() <= y) {
                        wiggleOffsets[i] = random.nextInt(10) - 5;
                    } else {
                        wiggleOffsets[i] = 0;
                    }
                }
            }
        });

        //Click to Cycle Animation Style
        matrixPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                animationStyle = (animationStyle + 1) % 3;
            }
        });

        add(matrixPanel, BorderLayout.CENTER);

        //Animation Timer
        Timer timer = new Timer(50, e -> {
            //Update Rain Drops
            for (RainDrop drop : rainDrops) {
                drop.y += 5;
                if (drop.y > getHeight()) {
                    drop.y = random.nextInt(600) - 600;
                    drop.x = random.nextInt(800);
                }
            }

            //Update Glow Animation
            if (animationStyle == 0) {
                if (glowIncreasing) {
                    glowAlpha += 0.05f;
                    if (glowAlpha >= 1.0f) glowIncreasing = false;
                } else {
                    glowAlpha -= 0.05f;
                    if (glowAlpha <= 0.2f) glowIncreasing = true;
                }
            }

            matrixPanel.repaint();
        });
        timer.start();

        setVisible(true);
    }

    private class RainDrop {
        int x, y;
        RainDrop(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CJ7MatrixDisplay::new);
    }
}