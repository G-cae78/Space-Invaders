import java.awt.Image;

public class PlayerBullet  extends Sprite2D{
    protected Image bulletImage;

    public PlayerBullet(Image i, int windowWidth){
        super(i,null,windowWidth);// calling superclass's constructor
        bulletImage=i;
    }

    public boolean move(){//int x1, int y1,int w1, int x2, int y2, int w2 ){
        //Moving bullet up by 10 pixels once fired
        return setY(y - 10);

        //if((x1<x2 && ))
    }

    public boolean collisonCheck(Alien alien){
        //Checking if bullet has hit alien
        if (((alien.x < x && alien.x + alien.getWidth() > x) || (x < alien.x && x + getWidth() > alien.x)) && ((alien.y < y && alien.y + alien.getHeight() > y || (y < alien.y && y + getHeight() > alien.y)))) {
            return true;
        }
        else{
                return false;
        }
    }

//    public int getImWidth(){
//        return bulletImage.getWidth(null);
//    }
//
//    public int getImHeight(){
//        return bulletImage.getWidth(null);
//    }

}
