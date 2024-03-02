import java.awt.Image;
public class Spaceship extends Sprite2D{
    public Spaceship(Image i, int windowWidth){
        super(i,null,windowWidth);
    }
    public void move() {
        // apply current movement
        x+=xSpeed;

        // stop movement at screen edge?
        if (x<=0) {
            x=0;
            xSpeed=0;
        }
        else if (x>=winWidth-myImage.getWidth(null)) {
            x=winWidth-myImage.getWidth(null);
            xSpeed=0;
        }
    }
    public boolean collisonCheck(Alien alien){
        // checking if the alien object has collided with our spaceShip and returning boolean
        if (((alien.x < x && alien.x + alien.getWidth() > x) || (x < alien.x && x + getWidth() > alien.x)) && ((alien.y < y && alien.y + alien.getHeight() > y || (y < alien.y && y + getHeight() > alien.y)))) {
            return true;
        }
        else{
            return false;
        }
    }

}
