import javafx.scene.paint.*;
import javafx.scene.canvas.*;

//this is an example object
public class Player extends DrawableObject
{
	//takes in its position
   public Player(float x, float y)
   {
      super(x,y);
   }
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      /*gc.setFill(Color.RED);
      gc.fillOval(x-14,y-14,27,27);
      gc.setFill(Color.GRAY);
      gc.fillOval(x-13,y-13,25,25);*/
      
      //my oval
      gc.setFill(Color.BLACK);
      gc.fillOval(x - 15, y - 15, 28, 28);
      gc.setFill(Color.CYAN);
      gc.fillOval(x - 13, y - 13, 24, 24);
      gc.setFill(Color.RED);
      gc.fillOval(x - 10, y - 10, 17, 17);
      
   }
   
}
