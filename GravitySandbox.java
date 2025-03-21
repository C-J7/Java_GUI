import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList; 
public class GravitySandbox extends JFrame { 
    private ArrayList<SpaceObject> objects = new ArrayList<>(); 
    private boolean isPaused = false; 
    private DefaultListModel<String> objectListModel = new DefaultListModel<>(); 
    public GravitySandbox() { 
        setTitle("Gravity Sandbox"); 
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        //Canvas
 JPanel canvas = new JPanel() { 
    @Override protected void paintComponent(Graphics g) { 
        super.paintComponent(g); g.setColor(Color.BLACK); 
        g.fillRect(0, 0, getWidth(), getHeight()); 
        for (SpaceObject obj : objects) { g.setColor(obj.color); 
            g.fillOval((int)(obj.x - obj.radius), (int)(obj.y - obj.radius), (int)(2 * obj.radius), (int)(2 * obj.radius)); } } }; 
            canvas.setBackground(Color.BLACK); 
            canvas.addMouseListener(new MouseAdapter() { 
                @Override public void mouseClicked(MouseEvent e) { 
                    if (!isPaused) { 
                        SpaceObject newObj = new SpaceObject(e.getX(), e.getY(), 10 + Math.random() * 20, Math.random() * 2 - 1, Math.random() * 2 - 1, Color.YELLOW); 
                        objects.add(newObj); objectListModel.addElement("Object " + objects.size() + ": (" + newObj.x + ", " + newObj.y + ")"); } } });
                        add(canvas, BorderLayout.CENTER); 

  //Controls
  JPanel controls = new JPanel(); 
  JButton pauseButton = new JButton("Pause"); 
  pauseButton.addActionListener(e -> { 
    isPaused = !isPaused; pauseButton.setText(isPaused ? "Resume" : "Pause");
 });
  controls.add(pauseButton); add(controls, BorderLayout.SOUTH); 

  //Object List 
  JList<String> objectList = new JList<>(objectListModel); 
  objectList.setBackground(Color.DARK_GRAY); 
  objectList.setForeground(Color.WHITE); 
  add(new JScrollPane(objectList), BorderLayout.EAST); 

  //Simulation Timer
   Timer timer = new Timer(50, e -> { 
    if (!isPaused) { 
        for (int i = 0; i < objects.size(); i++) { 
            for (int j = i + 1; j < objects.size(); j++) { 
                SpaceObject o1 = objects.get(i), o2 = objects.get(j); 
                double dx = o2.x - o1.x, dy = o2.y - o1.y; 
                double dist = Math.sqrt(dx * dx + dy * dy); 
                if (dist < o1.radius + o2.radius) { 
                    o1.vx = -o1.vx; o1.vy = -o1.vy; o2.vx = -o2.vx; o2.vy = -o2.vy;
                 }
                  else { 
                    double force = 0.1 * o1.mass * o2.mass / (dist * dist); o1.vx += force * dx / dist; o1.vy += force * dy / dist; o2.vx -= force * dx / dist; o2.vy -= force * dy / dist; 
                }
             } 
             objects.get(i).update(getWidth(), getHeight()); 
            } 
            canvas.repaint(); 
        } 
    });
     timer.start();
     setVisible(true); 
    } 
    
    private class SpaceObject { 
        double x, y, vx, vy, mass, radius; 
        Color color; 
        SpaceObject(double x, double y, double radius, double vx, double vy, Color color) { 
            this.x = x; this.y = y; this.vx = vx; this.vy = vy; this.mass = radius; this.radius = radius; this.color = color; 
        } 
        void update(int width, int height) { 
            x += vx; y += vy;
             if (x < 0 || x > width) vx = -vx; 
             if (y < 0 || y > height) vy = -vy; } 
            } 
   public static void main(String[] args) { SwingUtilities.invokeLater(GravitySandbox::new); 
} 
}