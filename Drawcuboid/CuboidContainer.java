import java.awt.*;

// Incomplete CuboidContainer class for CE203 Assignment
// Date: 12/10/2021
// Author: F. Doctor

public class CuboidContainer implements Comparable<CuboidContainer>{

    int id = 000000;  // Cuboid id should be a six digit non-negative integer with no leading 0s
    int x = 25; // x pixel position of top left point of the first face of the cuboid (hardcoded not set by user)
    int y = 25; // y pixel position of top left point of the first face of the cuboid (hardcoded not set by user)
    int widthHelight; // Width and height of the cuboid where width = height (each face is a square)
    int length; // Length of cuboid in pixels
    Color colour;
    Point[] faceOnePoints; // Points array used to store vertices (points) of first face of the cuboid
    Point[] faceTwoPoints; // Points array used to store vertices (points) of second  face of the cuboid

    // constructor currently set the dimensions of the cuboid to be drawn. This should be modifed
    // to set the Id field and the colour. You might need to also include a handle to the top level
    // JFrame object see examples in Unit 2
    public CuboidContainer(int cWidthHeight, int cLength,Color colour,int id) {
        this.x = x;
        this.y = y;
        this.id=id;
        this.colour=colour;
        this.widthHelight = cWidthHeight;
        this.length = cLength;
        faceOnePoints = getCubeOnePoints();
        faceTwoPoints = getCubeTwoPoints();
    }

    // used to populate the points array with the vertices (corners/points) of first face of the cuboid
    private Point[] getCubeOnePoints() {
        Point[] points = new Point[4];
        points[0] = new Point(x, y);
        points[1] = new Point(x + widthHelight, y);
        points[2] = new Point(x + widthHelight, y + widthHelight);
        points[3] = new Point(x, y + widthHelight);
        return points;
    }

    // used to populate the points array with the vertices of the second face of the cuboid
    // using the example of the first face complete the remainder of the code to produce an
    // output cuboid as shown in the assignment script
    private Point[] getCubeTwoPoints() {
        int newX = x + length;
        int newY = y + length;
        Point[] points = new Point[4];
        points[0] = new Point(newX, newY);
        points[1] = new Point(newX + widthHelight, newY);
        points[2] = new Point(newX + widthHelight, newY + widthHelight);
        points[3] = new Point(newX, newY + widthHelight);
        return points;
    }

    // You will need to modify this method to set the colour of the Cuboid to be drawn
    // you will also need to complete the For loop to draw the lines connecting each face of the cuboid
    public void drawCuboid(Graphics g) {
        //----------FILL WITH COLOUR
        g.setColor(colour);
        g.drawRect(x, y, widthHelight, widthHelight);
        g.drawRect(x + length, y + length, widthHelight, widthHelight);
        for(int i=0;i<faceOnePoints.length;i++){
            g.drawLine(faceOnePoints[i].x,faceOnePoints[i].y,faceTwoPoints[i].x,faceTwoPoints[i].y);
        }
    }


    // gets a stored ID
    public int getID() {
        return id;
    }


    @Override
    // method used for comparing Cuboid Container objects based on stored ids, you need to complete the method
    public int compareTo(CuboidContainer o) {
        if (o.getID()>getID()){
            return -1;
        }
        else if(o.getID()<getID()){
            return 1;
        }
        return 0;
    }


    // outputs a string representation of the cuboid container object which can be used for testing
    public String toString()
    {
        return "Cuboid Data: Face Width/height: "+widthHelight+" Length: "+length+" Colour: "+colour;
    }
}
