import javax.swing.*;
import java.awt.*;



public class ContainerPanel extends JPanel{

    CuboidContainer cuboid;   // Cuboid container object instance

    public ContainerPanel(CuboidContainer cuboidContainer) {

        cuboid = cuboidContainer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        cuboid.drawCuboid(g);
    }
}
