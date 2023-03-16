import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

import java.awt.Point;

public class Board implements java.io.Serializable
{
   public static final int NONE = 0;
   public static final int BLUE = 1;
   public static final int RED = 2;
   public static final int YELLOW = 3;
   public static final int GREEN = 4;
   public static final int BOARD_SIZE = 20;
   public static final int DEFAULT_RESOLUTION = 400;
   
   public static final Color BOARD_COLOR = Color.BLACK;
   public static final Color GRID_LINE_COLOR = Color.WHITE;
   
   public static final String OFF_BOARD_ERROR = "Please place entire piece on the board.";
   public static final String ADJACENCY_ERROR = "Please do not place piece adjacent to another one.";
   public static final String OVERLAP_ERROR = "Please do not overlap pieces.";
   public static final String START_ERROR = "Please start at respective board corner.";
   public static final String CORNER_ERROR = "Please connect pieces by piece corner.";
   
   private int[][] grid;
   private int[][] overlay;
   
   public Board()
   {
      grid = new int[BOARD_SIZE][BOARD_SIZE];
      overlay = new int[BOARD_SIZE][BOARD_SIZE];
      reset(grid);
      reset(overlay);
    }
    
   public boolean isValidMove(Piece bp, int xOffset, int yOffset, boolean firstMove) throws IllegalMoveException
   {
      boolean corner = false;
      for (int x = 0; x < Piece.SHAPE_SIZE; x++)
      {
         for (int y = 0; y < Piece.SHAPE_SIZE; y++)
         {
            int value = bp.getValue(x, y);
            boolean inBounds = isInBounds(x + xOffset, y + yOffset);
            
            if (inBounds)
            {
               int gridValue = grid[x + xOffset][y + yOffset];
               if (gridValue != NONE)
               {
                  if (value == Piece.PIECE) throw new IllegalMoveException(OVERLAP_ERROR);
                  if (gridValue == bp.getColor())
                  {
                     if (value == Piece.ADJACENT) throw new IllegalMoveException(ADJACENCY_ERROR);
                     if (value == Piece.CORNER) corner = true;
                  }
               }
               else
               {
                  if (firstMove && value == Piece.PIECE && new Point(x + xOffset, y + yOffset).equals(getCorner(bp.getColor())))
                     corner = true;
               }
            }
            else
            {
               if (value == Piece.PIECE) throw new IllegalMoveException(OFF_BOARD_ERROR);
            }
         }
      }
      if (!corner) throw new IllegalMoveException(firstMove ? START_ERROR : CORNER_ERROR);

      //The move is valid

      return true;
   }
   
   public boolean isValidMove(Piece bp, int xOffset, int yOffset) throws IllegalMoveException
   {
      return isValidMove(bp, xOffset, yOffset, false);
   }

   public void placePiece(Piece bp, int xOff, int yOff, boolean firstMove) throws IllegalMoveException
   {
      isValidMove(bp, xOff, yOff, firstMove);
      
      for (int x = 0; x < Piece.SHAPE_SIZE; x++)
      {
         for (int y = 0; y < Piece.SHAPE_SIZE; y++)
         {
            if (bp.getValue(x, y) == Piece.PIECE) grid[x + xOff][y + yOff] = bp.getColor();
         }
      }
   }
   
   public void placePiece(Piece bp, int xOff, int yOff) throws IllegalMoveException
   {
      placePiece(bp, xOff, yOff, false);
   }
   
   public Point getCoordinates(Point pixel, int res)
   {
      return new Point(pixel.x / (res / BOARD_SIZE), pixel.y / (res / BOARD_SIZE));
   }
   
   public void overlay(Piece bp, int xOff, int yOff)
   {
      reset(overlay);
      
      for (int x = 0; x < Piece.SHAPE_SIZE; x++)
      {
         for (int y = 0; y < Piece.SHAPE_SIZE; y++)
         {
            if (isInBounds(x + xOff - Piece.SHAPE_SIZE / 2, y + yOff - Piece.SHAPE_SIZE / 2) && bp.getValue(x, y) == Piece.PIECE)
            {
               overlay[x + xOff - Piece.SHAPE_SIZE / 2][y + yOff - Piece.SHAPE_SIZE / 2] = bp.getColor();
            }
         }
      }
   }
   
   public BufferedImage render()
   {
      return render(DEFAULT_RESOLUTION);
   }
   
   public BufferedImage render(int size)
   {
      BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
      int cellSize = size / (BOARD_SIZE);
      Graphics2D g = (Graphics2D) image.getGraphics();
      
      for (int x = 0; x < BOARD_SIZE; x++)
      {
         for (int y = 0; y < BOARD_SIZE; y++)
         {
            g.setColor(getColor(grid[x][y]));
            if (overlay[x][y] != NONE)
            {
               g.setColor(blend(g.getColor(), getColor(overlay[x][y]), 0.1f));
            }
            g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            g.setColor(GRID_LINE_COLOR);
            g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            
            if (grid[x][y] == NONE)
            {
               boolean corner = false;
               Point p = new Point(x, y);
               if (getCorner(BLUE).equals(p))
               {
                  g.setColor(getColor(BLUE));
                  corner = true;
               }
               else if (getCorner(RED).equals(p))
               {
                  g.setColor(getColor(RED));
                  corner = true;
               }
               else if (getCorner(GREEN).equals(p))
               {
                  g.setColor(getColor(GREEN));
                  corner = true;
               }
               else if (getCorner(YELLOW).equals(p))
               {
                  g.setColor(getColor(YELLOW));
                  corner = true;
               }
               if (corner)
               {
                  g.fillOval(x * cellSize + cellSize / 2 - cellSize / 6, y * cellSize + cellSize / 2 - cellSize / 6, cellSize / 3, cellSize / 3);
               }
            }
         }
      }
      return image;
   }
   
   private Color blend(Color c1, Color c2, float balance)
   {
      int r = (int)(c1.getRed() * balance + c2.getRed() * (1 - balance));
      int g = (int)(c1.getGreen() * balance + c2.getGreen() * (1 - balance));
      int b = (int)(c1.getBlue() * balance + c2.getBlue() * (1 - balance));
      return new Color(r, g, b);
   }
   
   public void resetOverlay()
   {
      reset(overlay);
   }
   
   private void reset(int[][] array)
   {
      for (int x = 0; x < BOARD_SIZE; x++)
         for (int y = 0; y < BOARD_SIZE; y++)
            array[x][y] = NONE;
   }
   
   private boolean isInBounds(int x, int y)
   {
      return (x >= 0 && y >= 0 && x < BOARD_SIZE && y < BOARD_SIZE);
   }
   
   private Point getCorner(int color)
   {
      switch (color)
      {
         case BLUE: return new Point(0, 0);
         case GREEN: return new Point(0, BOARD_SIZE - 1);
         case YELLOW: return new Point(BOARD_SIZE - 1, BOARD_SIZE - 1);
         case RED: return new Point(BOARD_SIZE - 1, 0);
         default: throw new IllegalArgumentException();
      }
   }
   
   public static Color getColor(int color)
   {
      switch (color)
      {
         case BLUE: {if(Options.getColorDef() == true) {
        	 			return Color.magenta;
         				}
         			}
         			return Color.BLUE;
         case RED: {if(Options.getColorDef() == true) {
	 					return Color.cyan;
						}
         		   }
         		   return Color.RED;
         case YELLOW: {if(Options.getColorDef() == true) {
	 					return Color.orange;
						}
					  }
         			return Color.YELLOW;
         case GREEN: {if(Options.getColorDef() == true) {
	 					return Color.lightGray;
						}
         			 }
         			return Color.GREEN;
         default: return BOARD_COLOR;
      }
   }
   
   public static String getColorName(int color)
   {
      switch (color)
      {
         case BLUE: return "Blue";
         case RED: return "Red";
         case YELLOW: return "Yellow";
         case GREEN: return "Green";
         default: return "Unknown";
      }
   }
   
   public static class IllegalMoveException extends Exception
   {
      public IllegalMoveException()
      {
         super();
      }
      
      public IllegalMoveException(String message)
      {
         super(message);
      }
   }
}
