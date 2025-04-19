import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;

import java.io.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;

import javafx.event.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;



public class Main extends Application
{
   //FlowPane fp;
   StackPane fp;
   Canvas theCanvas = new Canvas(600,600);
   
   Player thePlayer = new Player(300,300); //create the plyer
   
   KeyListenerDown keys = new KeyListenerDown();
   KeyListenerUp releasedKeys = new KeyListenerUp();
   
   //varables to keep track of current distance and the highest score
   private double currentScore;
   private double highScore; 
   
   Label score;
   Label highestScore;
   VBox scores = new VBox();
   AnimationTimer ta;
   public void start(Stage stage)
   {
      try
      {
         Scanner scan = new Scanner(new File("HighScore.txt"));
         highScore = scan.nextDouble();
      }
      catch(FileNotFoundException fnfe)
      {
         System.out.println("File does not exist!");
      }
      
      //fp = new FlowPane();
      fp = new StackPane();
      fp.getChildren().add(theCanvas);
      gc = theCanvas.getGraphicsContext2D();
      drawBackground(300,300,gc);
      
      currentScore = thePlayer.getDistance();
      score = new Label("Score is "+currentScore);   //creat labes to display scores
      //Label score;
      highestScore = new Label("High Score is "+highScore);
      score.setTextFill(Color.WHITE);
      highestScore.setTextFill(Color.WHITE);
      scores.getChildren().add(score);
      scores.getChildren().add(highestScore);
      fp.getChildren().add(scores);
      
      
      Scene scene = new Scene(fp, 600, 600);
      scene.setOnKeyPressed(keys);
      scene.setOnKeyReleased(releasedKeys); 
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      //AnimationTimer ta;
      ta = new AnimationHandler();
      ta.start();  //start animation

   }
   
   GraphicsContext gc;
   
   
   
   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();
   //this piece of code doesn't need to be modified
   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
	  //re-scale player position to make the background move slower. 
      playerx*=.1;   
      playery*=.1;
   
	//figuring out the tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      
      int xi = (int) x;
      int yi = (int) y;
      
	  //draw a certain amount of the tiled images
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
      
	  //below repeats with an overlay image
      playerx*=2f;
      playery*=2f;
   
      x = (playerx) / 400;
      y = (playery) / 400;
      
      xi = (int) x;
      yi = (int) y;
      
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   
   
   
   
   

   private int timer = 0;//timer to count every second which in this case is my "tick"
   private boolean up = false;
   private boolean down = false;
   private boolean left = false;
   private boolean right = false;
   
   public class KeyListenerDown implements EventHandler<KeyEvent> //added key listener to know whe key is pressed
   {
      public void handle(KeyEvent event)  //determine if pllayer is presing WASD
      {
         if (event.getCode() == KeyCode.W) 
         {
            up = true;
         }
         
         if (event.getCode() == KeyCode.A) 
         {
            left = true;
         }
         
         if (event.getCode() == KeyCode.S) 
         {
            down = true;
         }
         
         if (event.getCode() == KeyCode.D) 
         {
            right = true;
         }
      }
   }
   
   public class KeyListenerUp implements EventHandler<KeyEvent> //added key listener to know whe key is released
   {
      public void handle(KeyEvent event)
      {
         if (event.getCode() == KeyCode.W) 
         {
            up = false;
         }
         
         if (event.getCode() == KeyCode.A) 
         {
            left = false;
         }
         
         if (event.getCode() == KeyCode.S) 
         {
            down = false;
         }
         
         if (event.getCode() == KeyCode.D) 
         {
            right = false;
         }
         
      }
   }
   
   //create arraylist to keep track of miens
   private ArrayList<Mine> mineTracker = new ArrayList<Mine>();  //mineTracker.remove(index);
   Random rand = new Random(); //create Random to be used later in Mine generation
   
   int gridX = 3;
   int gridY = 3;
   int N = 4;
   int counter = 0;
   boolean runner = true;
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         
        score.setText("Score is "+currentScore);
        highestScore.setText("High Score is "+highScore);
        
         gc.clearRect(0,0,600,600);
         
         //USE THIS CALL ONCE YOU HAVE A PLAYER
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc); 


	      //example calls of draw - this should be the player's call for draw
         thePlayer.draw(300,300,gc,true); //all other objects will use false in the parameter.
         thePlayer.act();
         
         //example call of a draw where m is a non-player object. Note that you are passing the player's position in and not m's position.
         //m.draw(thePlayer.getX(),thePlayer.getY(),gc,false);
         
         timer++;
         if(timer == 1000000000) //keep track of ticks here, ticks are the parameter needed for act method
         {
            
            timer=0;
         }
         
         if(up == true)  //change the "force" based on key inputs using booleans, player cruises to a stop when keys are released
         {
            thePlayer.changeYForce("minus");
         }
         else
         {
            thePlayer.cruiseY();
            thePlayer.stopY();
         }
         if(down == true)
         {
            thePlayer.changeYForce("add");
         }
         else
         {
            thePlayer.cruiseY();
            thePlayer.stopY();
         }
         if(left == true)
         {
            thePlayer.changeXForce("minus");
         }
         else
         {
            thePlayer.cruiseX();
            thePlayer.stopX();
         }
         if(right == true)
         {
            thePlayer.changeXForce("add");
         }
         else
         {
            thePlayer.cruiseX();
            thePlayer.stopX();
         }
         
         
         currentScore = thePlayer.getDistance(); //recalulate the score and set new highest score
         if(highScore<currentScore)
         {
            highScore=currentScore;
         }
         
         //implement the grid based on players position
         int cgridx = ((int)thePlayer.getX())/100;
         int cgridy = ((int)thePlayer.getY())/100;
         System.out.println("Player is in grid cell: " + cgridx + ", " + cgridy);
         
         //draw mines
   
        int nextGridX = 0;
        int nextGridY = 0; // predict player direction to determine where mines will spawn
        if(up == true)
        {
            nextGridY=-4;
        }
        if(down == true)
        {
            nextGridY=4;
        }
        if(right == true)
        {
            nextGridX=4;
        }
        if(left == true)
        {
            nextGridX=-4;
        }
         
         if(Math.abs(cgridx) > Math.abs(gridX)+4 || Math.abs(cgridy) > Math.abs(gridY)+4)
         {
            if (Math.random() < 0.3)  
            {
                 int worldX = (cgridx+nextGridX) * 100; //originally cgridx and cgridy, test with gridX and gridY
                 int worldY = (cgridy+nextGridY) * 100;
                 
                 float mineX = worldX + (rand.nextInt(200) - 100);
                 float mineY = worldY + (rand.nextInt(200)-100); //generate random quoordinates for each mine
                 
                 double spawnDistance = Math.sqrt((thePlayer.getX()-mineX)*(thePlayer.getX()-mineX) +  (thePlayer.getY()-mineY)*(thePlayer.getY()-mineY)); //calculate anticipated spawinging distance from player
                
                double originDistance = Math.sqrt((mineX-300)*(mineX-300) +  (mineY-300)*(mineY-300));
                double maxMines = originDistance/1000;  //calculate the max num of mines per grid based on distance from origin
                
                if(spawnDistance > 100) // prevent mine from spawning on top of player
                {
                 Mine m = new Mine(mineX, mineY);
                 mineTracker.add(m); //create each mine and add to arraylist
                }
                 
                 gridX = cgridx;
                 gridY = cgridy;
             }
         }
       
               
             
          
         for(int i=0; i<mineTracker.size(); i++)
         {
            mineTracker.get(i).draw(thePlayer.getX(),thePlayer.getY(),gc,false);
         }
         
         //remove mine if 800 away from player
         for(int i=0; i<mineTracker.size(); i++)
         {
            if(thePlayer.distance(mineTracker.get(i)) > 800)
            {
               mineTracker.remove(i);
            }
         }
         
         //check if player is in mine hit distance
         
         for(int i=0; i<mineTracker.size(); i++)
         {
            if(thePlayer.distance(mineTracker.get(i)) < 20) //if player in mine hitbox(20 units) then player cannot move, end animation
            {
               gc.clearRect(0,0,600,600);
         
         
               drawBackground(thePlayer.getX(),thePlayer.getY(),gc); //clear player and mines
               ta.stop();//end animation
            }
         }
         System.out.println(currentScore);
         System.out.println(highScore);
        
         //make file to keep track of high score
         try
         {
            FileOutputStream fos = new FileOutputStream("HighScore.txt", false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(highScore);
            pw.close();
            
         }
         catch(FileNotFoundException fnfe)
         {
            System.out.println("File does not exist!");
         }
         
         
         
      }
   }

   public static void main(String[] args)
   {
      launch(args);
   }
}

