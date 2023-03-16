import java.util.LinkedList;

public class Player
{
   public LinkedList<Piece> pieces;
   public boolean firstMove = true;
   public boolean canPlay = true;
   
   public Player(int color)
   {
      
      int[][][] shapes = Piece.getAllShapes();
      
      pieces = new LinkedList<Piece>();
      
      for (int i = 0; i < shapes.length; i++)
      {
         pieces.add(new Piece(shapes[i], color));
      }
   }
   
   public int getScore()
   {
      int total = 0;
      for (Piece bp : pieces)
      {
         total += bp.getPoints();
      }
      return total;
   }
}
