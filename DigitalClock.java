import javax.swing.*;
import java.awt.*; 
import java.util.Calendar;



public class DigitalClock extends JFrame { 
    private JLabel timeLabel; 
    private boolean is24Hour = true; 
    private String theme = "Dark"; 
    private String alarmTime = ""; 
    public DigitalClock() { 

        setTitle("Digital Clock"); 
        setSize(400, 200); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLayout(new BorderLayout()); // Time Label

 timeLabel = new JLabel("", SwingConstants.CENTER); 
 timeLabel.setFont(new Font("SansSerif", Font.BOLD, 40)); 
 timeLabel.setForeground(Color.YELLOW); add(timeLabel, BorderLayout.CENTER); // Controls
  JPanel controls = new JPanel(); 
  JRadioButton hour24Button = new JRadioButton("24-hour", true);
   JRadioButton hour12Button = new JRadioButton("12-hour"); 
   ButtonGroup formatGroup = new ButtonGroup(); 
  formatGroup.add(hour24Button); 
  formatGroup.add(hour12Button); 
  hour24Button.addActionListener(e -> is24Hour = true); 
  hour12Button.addActionListener(e -> is24Hour = false); 
  JComboBox<String> themeCombo = new JComboBox<>(new String[]{"Dark", "Light"}); 
  themeCombo.addActionListener(e -> { theme = (String) themeCombo.getSelectedItem(); 
    updateTheme(); }); 
    JButton setAlarmButton = new JButton("Set Alarm"); 
    setAlarmButton.addActionListener(e -> { alarmTime = JOptionPane.showInputDialog("Set Alarm Time (HH:MM:SS)"); 
}); 

    controls.add(hour24Button);
    controls.add(hour12Button);
    controls.add(themeCombo); controls.add(setAlarmButton); 
    add(controls, BorderLayout.SOUTH); // Timer for Time Update
   Timer timer = new Timer(1000, e -> { updateTime(); checkAlarm(); 
}); 
timer.start(); updateTheme(); 
setVisible(true);
 } 
 private void updateTime() { 
    Calendar cal = Calendar.getInstance(); 
    int hour = cal.get(Calendar.HOUR_OF_DAY); 
    int minute = cal.get(Calendar.MINUTE); 
    int second = cal.get(Calendar.SECOND); 
    String timeStr; 
    if (is24Hour) { 
        timeStr = String.format("%02d:%02d:%02d", hour, minute, second);
     } 
     else { int hour12 = hour % 12 == 0 ? 12 : hour % 12; 
        timeStr = String.format("%02d:%02d:%02d %s", hour12, minute, second, hour < 12 ? "AM" : "PM");
     } 
     timeLabel.setText(timeStr); 
    } 
    private void checkAlarm() { 
        if (!alarmTime.isEmpty()) { 
            String currentTime = timeLabel.getText();
             if (currentTime.equals(alarmTime)) { 
                JOptionPane.showMessageDialog(this, "Alarm!"); 
                alarmTime = ""; } } } 
                private void updateTheme() { 
                    if (theme.equals("Dark")) { 
                        getContentPane().setBackground(Color.BLACK); 
                        timeLabel.setForeground(Color.YELLOW); } 
   else { getContentPane().setBackground(Color.WHITE);
     timeLabel.setForeground(Color.BLACK);
     } 
    } 
     public static void main(String[] args) { SwingUtilities.invokeLater(DigitalClock::new); } }