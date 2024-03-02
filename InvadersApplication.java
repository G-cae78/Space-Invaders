import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

public class InvadersApplication extends JFrame implements Runnable, KeyListener {
    //initilaising fields
    private static final Dimension WindowSize= new Dimension(800,600);
    private BufferStrategy strategy;
    private static final int NUMALIENS = 30;
    private Alien[] aliensArray= new Alien[NUMALIENS];
    private Spaceship PlayerShip;
    //private PlayerBullet bullet;
    private Image alienImage;
    private Image alienImage2;
    private Image playerImage;

    private Image bulletImage;
    private static String workingDirectory;
//    private int finXPos;
//    private int finYPos;
    private boolean alienMoving;

    private ArrayList bulletsList= new ArrayList();

    private boolean hasCollided;

    private int score;
    private int bestScore;

    private boolean inProgress;
    private boolean inMenu;

    private boolean gameOver;
    private int deaths;
    private boolean allAliensDead;

    public InvadersApplication() {
        score=0;
        bestScore=0;
        gameOver=false;
        deaths=1;
        this.setTitle("Assignment 4: Space Invaders uniform movement of aliens");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Centering the window on the screen
        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = screensize.width / 2 - WindowSize.width / 2;
        int y = screensize.height / 2 - WindowSize.height / 2;
        setBounds(x, y, WindowSize.width, WindowSize.height);
        setVisible(true);

        //creating buffer and implementing it into our program
        createBufferStrategy(2);
        strategy=getBufferStrategy();

        //Loading images for the alien and the spaceship
        ImageIcon alienIcon= new ImageIcon("/Users/georgea.e/Documents/Second Year 2/Next Gen game dev/Assignment 4/alien_ship_1.png");
        alienImage= alienIcon.getImage();
        ImageIcon alienIcon2= new ImageIcon("/Users/georgea.e/Documents/Second Year 2/Next Gen game dev/Assignment 4/alien_ship_2.png");
        alienImage2= alienIcon2.getImage();

        //Initialising playerBullet and setting position to wherever player ship is
        ImageIcon playerBulletIcon= new ImageIcon("/Users/georgea.e/Documents/Second Year 2/Next Gen game dev/Assignment 4/bullet.png");
        bulletImage= playerBulletIcon.getImage();
        //bullet = new PlayerBullet(bulletImage, WindowSize.width);


        //Initialising Alien objects in grid position
        for (int i = 0; i < NUMALIENS; i++) {
            aliensArray[i] = new Alien(alienImage,alienImage2, WindowSize.width);
            int row = i/6;
            int column= i%6;
            int xPos= column *50+10;
            int yPos= row*50+80;
            aliensArray[i].setPosition(xPos,yPos);
        }


        //initialising Spaceship object
        ImageIcon playerIcon= new ImageIcon("/Users/georgea.e/Documents/Second Year 2/Next Gen game dev/Assignment 4/player_ship.png");
        playerImage= playerIcon.getImage();
        //Spaceship player = new Spaceship(playerImage,WindowSize.width);
        PlayerShip = new Spaceship(playerImage, WindowSize.width);
        PlayerShip.setPosition(WindowSize.width/2 - playerImage.getWidth(null), WindowSize.height - playerImage.getHeight(null) - 35);

        //initialisng and starting thread
        Thread t = new Thread(this);
        t.start();

        //Adding KeyListener to JFrame
        addKeyListener(this);
        setFocusable(true);

        inProgress=false;
        inMenu=true;

    }

    public void run(){

        //Thread start message
        System.out.println("Thread Started");

        while(true){
            repaint();
            if(inProgress) {
                //calling method to move objects
                moveObjects();
               // boolean shipAlienCollison=false;
                for(Alien alien:aliensArray){
                    if(alien.isAlive()) {
                        if (PlayerShip.collisonCheck(alien)) { // checking for collsion between aliens and space ship
                            gameOver = true;
                            break;// breaking out of for loop if there has been a collsion
                        }
                    }
                }
                //Ending game when aliens collide with space ship
                if(gameOver){
                    //inMenu=true;
                    inMenu=true;
                    inProgress=false;
                }

                //method to start new game if all aliens have been killed
                startNewGame();


                // redraw background after each image movement
                repaint();
                try {
                    //Setting thread timer
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //System.out.println("Thread finished");

    }

    public void moveObjects(){
        boolean aliensMoving = false;
        //moving around the space invader objects in the array
        for (Alien aliens : aliensArray) {

            //if aliens are still moving horizontally and have not hit a wall keep moving else break out of for loop
            if (aliens.move()) {
                aliensMoving = true;
            } else {
                aliensMoving = false;
                break;
            }
        }

        //reversing direction of alien movements after they hit an edge
        if (!aliensMoving) {
            for (Alien aliens : aliensArray) {
                aliens.reverseDirection();
            }
        }

//            if(!aliensMoving){
//                for(Alien alien: aliensArray){
//                    alien.hitWall();
//                }
//            }

//            for(Alien aliens: aliensArray){
//                if(!aliensMoving) {
//                    aliens.hitWall();
//                }
//            }


        // moving around the player ship
        PlayerShip.move();

        // looping through bulletList
        for (Object obj : bulletsList) {
            if (obj instanceof PlayerBullet) {// checking if the object in the list is a PlayerBullet object
                PlayerBullet bullet = (PlayerBullet) obj; // casting object to PlayerBullet
                bullet.move();// moving bullet

                //while bullet is moving check for collision with aliens
                for (Alien aliens : aliensArray) {
                    if (aliens.isAlive() && bullet.isAlive()) {// only check for collision if both the bullet and alien are alive
                        if (bullet.collisonCheck(aliens)) {
                            hasCollided = true;
                        }
                        if (hasCollided) {
                            aliens.dead();//killing alien after collision
                            bullet.dead();// making bullet disappear after collision
                            score+=50;// increasing score
                            hasCollided = false;// resetting hasCollided
                            //break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        //Checking what key was pressed and adjusting direction of movement accordingly
        if(inMenu && !inProgress){// waiting for user to press a button to begin game in menu
            if(e.getKeyCode()!= KeyEvent.VK_UNDEFINED){
                inProgress=true;// starting game
                inMenu=false;
                repaint();
                //run();
            }
        }
//        else if(gameOver && !inProgress){
//            if(e.getKeyCode()!= KeyEvent.VK_UNDEFINED){
//                inProgress=true;
//                gameOver=false;
//                repaint();
//                //run();
//            }
//        }
        else {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                PlayerShip.setxSpeed(-5);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                PlayerShip.setxSpeed(5);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                shootBullet();// calling shoot bullet if space bar is pressed
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()== KeyEvent.VK_RIGHT || e.getKeyCode()== KeyEvent.VK_LEFT){
            PlayerShip.setxSpeed(0);
        }

    }
    @Override
    public void keyTyped(KeyEvent e){

        //Printing out what key was typed
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            System.out.println("Right Key Typed");
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            System.out.println("Left Key Typed");
        }
    }

    @Override
    public void setFocusable(boolean b ){

        super.setFocusable(b);
    }

    public void shootBullet(){
        //initialising bullet and setting position
        PlayerBullet bullet= new PlayerBullet(bulletImage, WindowSize.width);
        bullet.setPosition(PlayerShip.x+54/2, PlayerShip.y+10);
        bulletsList.add(bullet);// adding bullet object to array list
        //bullet.move();
    }
    //Application's paint method
    public void paint(Graphics g){
        // Setting Background color of application
        g= strategy.getDrawGraphics();// redirecting drawing calls to offscreen buffer
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        // displaying menu option when game hasn't started
        if(inMenu && !inProgress){
            // setting what to be displayed in menu state
            g.setColor(Color.WHITE);

            g.setFont(new Font("Arial", Font.BOLD,50));
           String menuMessage= "GAME OVER";
            int x= ((g.getFontMetrics().stringWidth(menuMessage)/2)+50);
            int y= WindowSize.height/4;
            g.drawString(menuMessage,x,y);

            g.setFont(new Font("Arial", Font.BOLD,20));
            String menuMessage1= "Press Any Key to Start";
            int x1= ((g.getFontMetrics().stringWidth(menuMessage)/2)+150);
            int y1= WindowSize.height-400;
            g.drawString(menuMessage1,x1,y1);

            g.setFont(new Font("Arial", Font.BOLD,18));
            String menuMessage2= "[Arrow Keys to move, Space to Fire]";
            int x2= ((g.getFontMetrics().stringWidth(menuMessage)/2)+150);
            int y2= WindowSize.height/2;
            g.drawString(menuMessage2,x2,y2);
        }
//        else if(gameOver && !inProgress){
//            g.setColor(Color.WHITE);
//
//            g.setFont(new Font("Arial", Font.BOLD,50));
//            String menuMessage= "GAME OVER";
//            int x= ((g.getFontMetrics().stringWidth(menuMessage)/2)+50);
//            int y= WindowSize.height/4;
//            g.drawString(menuMessage,x,y);
//
//            g.setFont(new Font("Arial", Font.BOLD,20));
//            String menuMessage1= "Press Any Key to Start Again";
//            int x1= ((g.getFontMetrics().stringWidth(menuMessage)/2)+150);
//            int y1= WindowSize.height/3;
//            g.drawString(menuMessage1,x1,y1);
//
//            g.setFont(new Font("Arial", Font.BOLD,18));
//            String menuMessage2= "[Arrow Keys to move, Space to Fire]";
//            int x2= ((g.getFontMetrics().stringWidth(menuMessage)/2)+150);
//            int y2= WindowSize.height/2;
//            g.drawString(menuMessage2,x2,y2);
//        }

        else {

            // setting what to be displayed in Progress mode
            // painting alien images
            for (Alien alien : aliensArray) {
                if (alien.isAlive()) {
                    alien.paint(g);
                }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD,24));
            String scoreMessage= "Score: "+getScore()+"       Best: "+getBestScore();
            int x3= (g.getFontMetrics().stringWidth(scoreMessage)/2);
            int y3= WindowSize.height-550;
            g.drawString(scoreMessage,x3,y3);

            // painting out player ship image
            PlayerShip.paint(g);

            // painting out player bullet image
//        for(Object obj: bulletsList) {
//            if (obj instanceof PlayerBullet) {
//                PlayerBullet bullet = (PlayerBullet) obj;
//                if(bullet.isAlive()) {
//                    bullet.paint(g);
//                }
//            }
//        }
            Iterator iterator = bulletsList.iterator();
            while (iterator.hasNext()) {
                PlayerBullet bullet = (PlayerBullet) iterator.next();
                bullet.paint(g);
            }
            //bullet.paint(g);
        }

        //showing image stored in buffer
        strategy.show();
    }

    public int getScore(){
        // getting score and upddating best score
        if(score>bestScore){
            bestScore=score;
        }
        return score;
    }

    public int getBestScore(){
        return bestScore;// returning value for bestScore
    }

    public void startNewGame(){
        // starting new game after all aliens are killed

        allAliensDead=true;
        for(Alien alien:aliensArray){
            if(alien.isAlive()){ // checking if all aliens have been killed off
                allAliensDead=false;
                break;
            }
        }
        if(allAliensDead) {
            score=0;
            deaths++; // increasing deaths
            for (int i = 0; i < NUMALIENS; i++) {
                // re- instantiating alien grid
                aliensArray[i] = new Alien(alienImage, alienImage2, WindowSize.width);
                int row = i / 6;
                int column = i % 6;
                int xPos = column * 50 + 10;
                int yPos = row * 50 + 80;
                //setting positions of new alien grid
                aliensArray[i].setPosition(xPos, yPos);
                //increasing movement speed based on the number of deaths that have occured
                aliensArray[i].setxSpeed(aliensArray[i].getxSpeed() * deaths);
            }
        }
    }

    //application entry point
    public static void main(String[] args){
        //Printing the working directory to screen
        workingDirectory = System.getProperty("user.dir");
        System.out.println("Working Directory= "+workingDirectory);
        //Creating and instance of the application class
        InvadersApplication trial = new InvadersApplication();

    }

}
