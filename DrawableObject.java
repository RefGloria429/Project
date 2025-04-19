import javafx.scene.paint.*;
import javafx.scene.canvas.*;

import javafx.event.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public abstract class DrawableObject
{
   
   //KeyListenerDown keys = new KeyListenerDown();
   //KeyListenerUp releasedKeys = new KeyListenerUp();
   
   public DrawableObject(float x, float y)
   {
      this.x = x;
      this.y = y;
   }

   //positions
   private float x;
   private float y;
   
   //takes the position of the player and calls draw me with appropriate positions
   public void draw(float playerx, float playery, GraphicsContext gc, boolean isPlayer)
   {
      //the 300,300 places the player at 300,300, if you want to change it you will have to modify it here
      
      if(isPlayer)
         drawMe(playerx,playery,gc);
      else
         drawMe(-playerx+300+x,-playery+300+y,gc);
   }
   
   //this method you implement for each object you want to draw. Act as if the thing you want to draw is at x,y.
   //NOTE: DO NOT CALL DRAWME YOURSELF. Let the the "draw" method do it for you. I take care of the math in that method for a reason.
   public abstract void drawMe(float x, float y, GraphicsContext gc);
   
   
   private float xForce = 0;
   private float yForce = 0; 
   
   
   public void changeYForce(String direction) //change the force in y directino based in each "tick"
   {
      if(direction.equals("add"))
      {
         yForce = yForce+.1f;
      }
      if(direction.equals("minus"))
      {
         yForce = yForce-.1f;
      }
      
      if(yForce<-5) //set parameters for force in y direction
      {
         yForce=-5;
      }
      else if(yForce>5)
      {
         yForce=5;
      }
      else
      {
      }
      
   }
   public void changeXForce(String direction) //change the force in x directino based in each "tick"
   {
      if(direction.equals("add"))
      {
         xForce = xForce+.1f;
      }
      if(direction.equals("minus"))
      {
         xForce = xForce-.1f;
      }
      
      if(xForce<-5) //set parameters for force in x direction
      {
         xForce=-5;
      }
      else if(xForce>5)
      {
         xForce=5;
      }
      else
      {
      }
   }
   
   public void cruiseY() //slows down player in the x direction if keys not pressed
   {
      if(yForce>0)
      {
         yForce = yForce-.025f;
      }
      else if(yForce<0)
      {
         yForce = yForce+.025f;
      }
      else if(yForce == 0)
      {
         yForce = 0;
      }
      else{
      }
      
   }
   
   public void cruiseX() //slows down player in the x direction if keys not pressed
   {
      if(xForce>0)
      {
         xForce = xForce-.025f;
      }
      else if(xForce<0)
      {
         xForce = xForce+.025f;
      }
      else if(xForce == 0)
      {
         xForce = 0;
      }
      else{
      }
      
   }
   public void stopX() //stop player if drifitng below .025 in x direction
   {
      if(xForce<.025 && xForce>-.025)
      {
         xForce = 0;
      }
   }
   public void stopY() //stop player if drifitng below .025 in y direction
   {
      if(yForce<.025 && yForce>-.025)
      {
         yForce = 0;
      }
   }
   
   public void act() //changes the x and y positions of the object
   {
      
      x = x+(xForce*10);
      y= y+(yForce*10);
      
   }
   
   public float getX(){return x;}
   public float getY(){return y;}
   public void setX(float x_){x = x_;}
   public void setY(float y_){y = y_;}
   
   private double distance = 0;
   public double getDistance() //calculatesplayer distance from the center/origin (300,300)
   {
      double calcX = (x-300)*(x-300);
      double calcY = (y-300)*(y-300);
      distance = Math.sqrt(calcX + calcY);
      return distance;   
   }
   
   public double distance(DrawableObject other)
   {
      return (Math.sqrt((other.x-x)*(other.x-x) +  (other.y-y)*(other.y-y)   ));
   }
}