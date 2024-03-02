import java.awt.*;

public class Alien extends Sprite2D{

    // boolean to store if wall has been hit
    protected boolean hitwall;


    public Alien(Image i, Image j, int windowWidth){
        super(i, j,windowWidth);
        //initialPos();
        //declaring speed to be 4
        xSpeed=4;
    }
    public boolean move(){
        //boolean isMoving;
        // starting alien movement
        setxSpeed(xSpeed);

        //checking if aliens are still within window bounds
        if(x<=0 || x >=winWidth - (myImage.getWidth(null))) {
            hitwall=true;
            return false;
        }
        else{
            hitwall=false;
            //reverseDirection();
            return true;
        }
    }

    @Override
    public int getWidth() {
        // returning width of image based on what image is being shown currently
        //return super.getWidth();
        if(framesDrawn%100<50){
            return myImage.getWidth(null);
        }

        else{
            return myImage2.getWidth(null);
        }
    }

    @Override
    public int getHeight() {
        //returning height of image being shown currently
        //return super.getWidth();
        //Switching image every 50 frames
        if(framesDrawn%100<50){
            return myImage.getHeight(null);
        }

        else{
            return myImage2.getHeight(null);
        }
    }

    public void reverseDirection() {
        setxSpeed(-getxSpeed()); // reversing direction of alien movement
        setY(getY()+10); // moving down aliens by 10 each time they hit a wall
    }
//    public boolean hitWall(){
//
//        return hitwall;
//    }


    @Override
    public void paint(Graphics g) {
        //checking if alien is alive and then painting the alien based on what frame we are on
        if (isAlive) {
            framesDrawn++;
            if (framesDrawn % 100 < 50) {
                g.drawImage(myImage, (int) x, (int) y, null);
            } else {
                g.drawImage(myImage2, (int) x, (int) y, null);
            }
        }
        //isAlive=true;
    }



}
