import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class ContainerFrame extends JFrame{

    JTextField widthHeightField,lengthField,colourField,idField,searchField,writeField;
    JLabel widthHeightLabel,lengthLabel,colourLabel,idLabel,searchLabel;
    JButton addButton,searchButton;
    ArrayList<CuboidContainer> cuboidList;

    //Find the cuboid in the list
    private CuboidContainer findCuboid(int id)
    {
        for (int i=0;i<cuboidList.size();i++){
            if(cuboidList.get(i).getID()==id){
                return cuboidList.get(i);
            }
        }
        return null;
    }
    public void createComponents() {
        cuboidList = new ArrayList<CuboidContainer>();
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.gray);
        add(controlPanel,BorderLayout.NORTH);
        addButton = new JButton("Add");
        widthHeightLabel = new JLabel("Width/Height:");
        lengthLabel = new JLabel("Length:");
        colourLabel = new JLabel("Colour:");
        idLabel = new JLabel("ID:");
        widthHeightField = new JTextField();
        widthHeightField.setColumns(6);
        lengthField = new JTextField();
        lengthField.setColumns(6);
        colourField = new JTextField();
        colourField.setColumns(8);
        idField = new JTextField();
        idField.setColumns(6);
        //--------------------------------------
        controlPanel.add(widthHeightLabel);
        controlPanel.add(widthHeightField);
        controlPanel.add(lengthLabel);
        controlPanel.add(lengthField);
        controlPanel.add(colourLabel);
        controlPanel.add(colourField);
        controlPanel.add(idLabel);
        controlPanel.add(idField);
        controlPanel.add(addButton);
        //------------------------------------------
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.darkGray);
        add(searchPanel,BorderLayout.LINE_END);
        searchButton = new JButton("Search");
        searchField = new JTextField();
        searchField.setColumns(6);
        searchLabel = new JLabel("ID:");
        searchLabel.setForeground(Color.green);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);



        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int id = Integer.parseInt(searchField.getText());
                    CuboidContainer cuboidContainer = findCuboid(id);
                    if(cuboidContainer!=null){
                        JPanel panel = new ContainerPanel(cuboidContainer);
                        add(panel, BorderLayout.CENTER);
                        revalidate();
                        System.out.println("Cuboid found with id '"+id+"'.");
                    }
                    else{
                        System.out.println("Cuboid with id "+id+" could not found.");
                    }
                }
                catch (InputMismatchException ex){
                    System.out.println("Invalid cuboid ID.");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int widthHeight,length,id;
                    String colour = colourField.getText();
                    widthHeight = Integer.parseInt(widthHeightField.getText());
                    length = Integer.parseInt(lengthField.getText());
                    id = Integer.parseInt(idField.getText());
                    Color color;
                    //check user color input
                    try {
                        Field field = Class.forName("java.awt.Color").getField(colour.toLowerCase());
                        color = (Color)field.get(null);
                    } catch (Exception ex) {
                        color = null;
                    }
                    //check id
                    if(findCuboid(id)==null&&(id>=100000&&id<=999999)&&color!=null){
                        cuboidList.add(new CuboidContainer(widthHeight,length,color,id));
                        Collections.sort(cuboidList);
                        System.out.println("Cuboid "+id+" has been added to the list");
                        DisplayAllIds();
                        Display();
                    }
                    else{
                        System.out.println("The Cuboid ‘"+idField.getText()+"’ was not added to the list as a valid id or color was not provided");
                    }
                }
                catch (InputMismatchException ex){
                    System.out.println("The Cuboid ‘"+idField.getText()+"’ was not added to the list as a valid id was not provided");
                }
            }
        });

        setSize(900, 900);
        setVisible(true);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );	// Close action.
    }


    //Display ıd in the screen
    private void DisplayAllIds() {
        for (CuboidContainer cuboidContainer : cuboidList){
            System.out.println(cuboidContainer.getID());
        }
    }

    //Display toStrin() in the screen
    private void Display() {
        for (CuboidContainer cuboidContainer : cuboidList){
            System.out.println(cuboidContainer.toString());
        }
    }

    public static void main(String[] args) {
        ContainerFrame cFrame = new ContainerFrame();
        cFrame.createComponents();
        cFrame.setSize(1200,800);
    }

}
