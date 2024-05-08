package src.utilities;
import javax.swing.*;
import java.awt.*;

public class JStartScreenFrame extends JFrame {
    public Component comp;
    public JStartScreenFrame(Component comp, String title) {
      super(title);
      this.comp = comp;
      getContentPane().add(BorderLayout.CENTER, comp);
      setPreferredSize(new Dimension(850, 500));
      setLocationRelativeTo(null);
      pack();
      this.setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      repaint();
      setResizable(false);
    }
}
