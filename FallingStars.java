import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList; 
public class FallingStars extends JFrame { 
    private ArrayList<Point> stars = new ArrayList<>(); 
    private ArrayList<Burst> bursts = new ArrayList<>(); 
    private int starDensity = 20; 
    private Color burstColor = Color.YELLOW; 
    public FallingStars() { 
        setTitle("Falling Stars"); 
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sky Canvas 
JPanel sky = new JPanel() { 
    @Override protected void paintComponent(Graphics g) { 
        super.paintComponent(g); g.setColor(Color.BLACK); 
        g.fillRect(0, 0, getWidth(), getHeight()); 
        g.setColor(Color.WHITE); 

        for (Point star : stars) {
             g.drawLine(star.x, star.y, star.x, star.y + 5); 
            }
        for (Burst burst : bursts) { 
            g.setColor(burst.color); 
            g.fillOval(burst.x - burst.radius, burst.y - burst.radius, 2 * burst.radius, 2 * burst.radius);
         } 
        } 
    }; 
    sky.setBackground(Color.DARK_GRAY); 
    sky.addMouseListener(new MouseAdapter() { 
        @Override public void mouseClicked(MouseEvent e) 
        { 
            bursts.add(new Burst(e.getX(), e.getY(), 20, burstColor));
         }
         });
          add(sky, BorderLayout.CENTER); //Controls
JPanel controls = new JPanel();
JSlider densitySlider = new JSlider(10, 100, 20);
   densitySlider.addChangeListener(e -> { starDensity = densitySlider.getValue();
     updateStars();
     });
JComboBox<String> colorCombo = new JComboBox<>(new String[]{"Yellow", "Red", "Blue", "Green"});
colorCombo.addActionListener(e -> { String selected = (String) colorCombo.getSelectedItem();
     switch (selected)
      { 
        case "Red": burstColor = Color.RED;
         break;
        case "Blue": burstColor = Color.BLUE;
         break; 
        case "Green": burstColor = Color.GREEN;
         break; 
        default: burstColor = Color.YELLOW; 
    } 
}); 
controls.add(new JLabel("Star Density:"));
controls.add(densitySlider);
controls.add(new JLabel("Burst Color:"));
controls.add(colorCombo); add(controls, BorderLayout.SOUTH); //Animation Timer
Timer timer = new Timer(50, e -> { 
    for (Point star : stars) {
         star.y += 5;
          if (star.y > getHeight()) star.y = (int) (Math.random() * -400);
         } 
         bursts.removeIf(burst -> burst.update() < 0); sky.repaint();
         });
        timer.start(); 
        updateStars();
         setVisible(true); 
        }
private void updateStars() { 
    stars.clear();
     for (int i = 0; i < starDensity; i++) {
         stars.add(new Point((int) (Math.random() * 800), (int) (Math.random() * 600 - 600)));
         }
    } 
    private class Burst { int x, y, radius; Color color; 
        Burst(int x, int y, int radius, Color color) { 
            this.x = x; this.y = y; this.radius = radius; this.color = color; 
        }
int update() { 
    radius -= 1; return radius;
 }
 }
  public static void main(String[] args) { SwingUtilities.invokeLater(FallingStars::new); } }