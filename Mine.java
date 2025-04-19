import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import java.util.*;

//this is an example object
public class Mine extends DrawableObject
{
	//takes in its position
   public Mine(float x, float y)
   {
      super(x,y);
   }
   
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      Random rand = new Random();
      int colorRand = rand.nextInt(256); //set random number for color
      
      gc.setFill(Color.GRAY); //gray background
      gc.fillOval(x - 15, y - 15, 28, 28);
      //cycle through random shades of red/white
      gc.setFill(new Color(1.0,colorRand/255.0,colorRand/255.0, 1.0));
      gc.fillOval(x - 10, y - 10, 17, 17);
         
   }
}