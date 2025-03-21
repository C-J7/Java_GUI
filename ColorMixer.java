import javax.swing.*;
import javax.swing.event.ChangeListener;

import java.awt.*; 
 import java.util.ArrayList; public class ColorMixer extends JFrame { 
  private JLabel rgbLabel; 
  private ArrayList<Color> colorHistory = new ArrayList<>(); 
  private JPanel historyPanel; 
  public ColorMixer() { 
  setTitle("Color Mixer"); 
  setSize(600, 400); 
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  setLayout(new BorderLayout()); 
  
  //Color Preview
 JPanel colorPanel = new JPanel(); 
 colorPanel.setPreferredSize(new Dimension(200, 200));
  colorPanel.setBackground(Color.BLACK); 
  
  //RGB Label
  rgbLabel = new JLabel("RGB: 0, 0, 0", SwingConstants.CENTER); 
  rgbLabel.setForeground(Color.WHITE); 
  
  //History Panel
   historyPanel = new JPanel(); 
   historyPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
   historyPanel.setBackground(Color.darkGray); 
   
   
   //Sliders
    JSlider redSlider = new JSlider(0, 255, 0);
     JSlider greenSlider = new JSlider(0, 255, 0);
      JSlider blueSlider = new JSlider(0, 255, 0); 
      redSlider.setToolTipText("Adjust Red"); 
      greenSlider.setToolTipText("Adjust Green"); 
      blueSlider.setToolTipText("Adjust Blue"); 
      
      
      //Save Button
     JButton saveButton = new JButton("Save Color"); 
     saveButton.addActionListener(e -> { colorHistory.add(colorPanel.getBackground()); 
        updateHistory(); 
    }); 
    
    //Layout Setup
      JPanel controls = new JPanel(new GridBagLayout()); 
      GridBagConstraints c = new GridBagConstraints(); 
      c.fill = GridBagConstraints.HORIZONTAL; 
      c.insets = new Insets(5, 5, 5, 5); 
      c.gridx = 0; c.gridy = 0; controls.add(new JLabel("Red:"), c);
       c.gridx = 1; 
       controls.add(redSlider, c); 
       c.gridx = 0; 
       c.gridy = 1; 
       controls.add(new JLabel("Green:"), c); 
       c.gridx = 1; 
       controls.add(greenSlider, c); 
       c.gridx = 0; 
       c.gridy = 2; 
       controls.add(new JLabel("Blue:"), c); 
       c.gridx = 1; 
       controls.add(blueSlider, c); 
       c.gridx = 0; 
       c.gridy = 3; 
       c.gridwidth = 2; 
       controls.add(saveButton, c); 
       
       //Update Color on Slider Change 
      ChangeListener updateColor = e -> { 
        int r = redSlider.getValue(), g = greenSlider.getValue(), b = blueSlider.getValue(); 
        colorPanel.setBackground(new Color(r, g, b)); 
        rgbLabel.setText(String.format("RGB: %d, %d, %d", r, g, b));
     };
      redSlider.addChangeListener(updateColor); 
      greenSlider.addChangeListener(updateColor); 
      blueSlider.addChangeListener(updateColor); 
      
      
      
      //Main Layout 
      add(colorPanel, BorderLayout.CENTER); 
      add(controls, BorderLayout.WEST); 
      add(rgbLabel, BorderLayout.SOUTH); 
      add(new JScrollPane(historyPanel), BorderLayout.EAST); setVisible(true); 
    } 
    private void updateHistory() { 
        historyPanel.removeAll(); 
        for (Color color : colorHistory) { 
            JPanel colorBlock = new JPanel(); 
            colorBlock.setPreferredSize(new Dimension(30, 30)); 
            colorBlock.setBackground(color); 
            historyPanel.add(colorBlock); 
        } 
        historyPanel.revalidate(); historyPanel.repaint(); 
    } 
      public static void main(String[] args) { SwingUtilities.invokeLater(ColorMixer::new); } }