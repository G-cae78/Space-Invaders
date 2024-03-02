import java.awt.*;

public class Sprite2D {

    //member data
    protected double x;
    protected double y;
    protected double xSpeed=0;
    protected Image myImage;

    protected Image myImage2;
    int winWidth;
    int framesDrawn=-0;
    protected boolean isAlive;

    //constructor
    public Sprite2D(Image i, Image j,int Width){

        // initialising fields
        myImage=i;
        winWidth=Width;
        myImage2=j;
        isAlive=true;
        //x=10;
        //y=10;
    }

    //setting position of objects
    public void setPosition(double xx, double yy){
        x= xx;
        y=yy;
    }

    // mutator method for xspeed
    public void setxSpeed(double dx){
        xSpeed=dx;
        x+=xSpeed;
    }

    //accessor method for xspeed
    public double getxSpeed(){

        return xSpeed;
    }

    //setter and getter methods for the variable y
    public boolean setY(double yyy){
        y=yyy;
        return true;
    }
    public double getY(){

        return y;
    }


    public int getWidth(){
        //getting image width dimensions
        return myImage.getWidth(null);
    }

    public int getHeight(){
        //getting image height dimensions
        return myImage.getHeight(null);
    }

    // setter and getter methods for the variable x

//    }
//
//    public void setX(double xxx){
//        x=xxx;
//    }


    public void dead(){
        // killing object so it won't be painted anymore
        isAlive=false;
    }

    public boolean isAlive(){
        // returning if object is alive or not
        return isAlive;
    }
    // paint method for objects


    public void paint(Graphics g){
        // checking if object is still alive then pianting
        if(isAlive) {
            g.drawImage(myImage, (int) x, (int) y, null);
        }
        //isAlive=true;
    }
}
