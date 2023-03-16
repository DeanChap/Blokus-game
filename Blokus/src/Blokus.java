import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.*;
import java.net.URL;

public class Blokus extends JFrame implements java.io.Serializable
{
   public static final int NUM_PLAYERS = 4;
   private Board board;
  
   private Player[] players;
   private int turn = -1;
   
   private int pieceIndex;
   private Point selected;
   
   private JPanel topPanel, mainPanel, sidePanel, piecesPanel, boardPanel, bottomPanel;
   private JLabel label, message;
   private ImageIcon boardImage;
   private JButton giveUp, options;
   
   public Blokus()
   {
      super("Blokus");
      board = new Board();
      players = new Player[NUM_PLAYERS];
      players[0] = new Player(Board.BLUE);
      players[1] = new Player(Board.RED);
      players[2] = new Player(Board.YELLOW);
      players[3] = new Player(Board.GREEN);
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      initializeGUI();
      startNewTurn();
   }
   
   private void initializeGUI()
   {
      class BoardClickListener implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener
      {
         public void mouseClicked(MouseEvent e)
         {
            if (e.getButton() == MouseEvent.BUTTON3)
            {
               flipPiece();
            }
            else
            {
               try
               {
                  board.placePiece(players[turn].pieces.get(
                     pieceIndex), selected.x - Piece.SHAPE_SIZE / 2, 
                     selected.y - Piece.SHAPE_SIZE / 2, players[turn].firstMove);
                  drawBoard();
                  players[turn].pieces.remove(pieceIndex);
                  players[turn].firstMove = false;
                  players[turn].canPlay = players[turn].pieces.size() != 0;
                  startNewTurn();
               }
               catch (Board.IllegalMoveException ex)
               {
                  displayMessage(ex.getMessage(), "Illegal Move!");
               }
            }
         }
         
         public void mousePressed(MouseEvent e)
         {
         }
         
         public void mouseReleased(MouseEvent e)
         {  
         }
         
         public void mouseEntered(MouseEvent e)
         {
         }
         
         public void mouseExited(MouseEvent e)
         {
            selected = null;
            board.resetOverlay();
            drawBoard();
         }
         
         public void mouseDragged(MouseEvent e)
         {
         
         }
         
         public void mouseMoved(MouseEvent e)
         {
            Point p = board.getCoordinates(e.getPoint(), Board.DEFAULT_RESOLUTION);
            if (!p.equals(selected))
            {
               selected = p;
               board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
               drawBoard();
            }
         }
         
         public void mouseWheelMoved(MouseWheelEvent e)
         {
         }

		@Override
		public void keyTyped(KeyEvent e) {			
		}

		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP : 
				flipPiece();
				break;
			case KeyEvent.VK_DOWN : 
				flipPiece();
				break;
			case KeyEvent.VK_LEFT :
				rotateClockwise();
				break;
			case KeyEvent.VK_RIGHT : 
	            rotateCounterClockwise();
	            break;
			case KeyEvent.VK_KP_UP : 
				flipPiece();
				break;
			case KeyEvent.VK_KP_DOWN : 
				flipPiece();
				break;
			case KeyEvent.VK_KP_LEFT :
				rotateClockwise();
				break;
			case KeyEvent.VK_KP_RIGHT : 
	            rotateCounterClockwise();
	            break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {			
		}
      }
      
      class SurrenderListener implements ActionListener
      {
         public void actionPerformed(ActionEvent event)
         {
            players[turn].canPlay = false;
            startNewTurn();
         }
      }
      
      class OptionsListener implements ActionListener
      {
          public void actionPerformed(ActionEvent event)
          {
        	  new Options();
          }
       }
      
      topPanel = new JPanel();
      topPanel.setLayout(new BorderLayout());
	  URL pic = Blokus.class.getResource("Data/Blokus.png");
	  ImageIcon picIcon = new ImageIcon(pic);
	  JLabel background = new JLabel(picIcon);
	  topPanel.setBackground(Color.BLACK);
	  topPanel.add(background, BorderLayout.CENTER);

      mainPanel = new JPanel();
      mainPanel.setLayout(new BorderLayout());
      piecesPanel = new JPanel();
      piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.PAGE_AXIS));
      
      JScrollPane jsp = new JScrollPane(piecesPanel);
      jsp.getVerticalScrollBar().setUnitIncrement(Piece.DEFAULT_RESOLUTION / 3);
      //ugly
      jsp.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION - 80, Board.DEFAULT_RESOLUTION - 30));

            
      sidePanel = new JPanel();
      sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
      giveUp = new JButton("Give Up...");
      giveUp.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION, 30));
      giveUp.addActionListener(new SurrenderListener());
      options = new JButton("Options");
      options.addActionListener(new OptionsListener());
      sidePanel.add(jsp);
      sidePanel.add(giveUp);
      sidePanel.add(options);
      
      boardPanel = new JPanel();
      boardImage = new ImageIcon(board.render());
      label = new JLabel(boardImage);
      BoardClickListener bcl = new BoardClickListener();
      label.addMouseListener(bcl);
      label.addMouseMotionListener(bcl);
      label.addMouseWheelListener(bcl);
      label.setFocusable(true);
      label.requestFocus();
      label.addKeyListener(bcl);
      boardPanel.add(label);
            
      bottomPanel = new JPanel();
      message = new JLabel();
      bottomPanel.add(message);
      
      mainPanel.add(topPanel, BorderLayout.NORTH);
      mainPanel.add(sidePanel, BorderLayout.EAST);
      mainPanel.add(boardPanel, BorderLayout.CENTER);
      mainPanel.add(bottomPanel, BorderLayout.SOUTH);
      
      getContentPane().add(mainPanel);
      pack();
      setLocationRelativeTo(null);
      setVisible(true);
      setResizable(false);
   }
   
   private void rotateClockwise()
   {
      players[turn].pieces.get(pieceIndex).rotateClockwise();
      board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
      drawBoard();
   }
   
   private void rotateCounterClockwise()
   {
      players[turn].pieces.get(pieceIndex).rotateCounterClockwise();
      board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
      drawBoard();
   }
   
   private void flipPiece()
   {
      players[turn].pieces.get(pieceIndex).flipOver();
      board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
      drawBoard();
   }
   
   private void drawBoard()
   {
      boardImage.setImage(board.render());
      label.repaint();
   }
   
   public void setBoard(Board board) {
		this.board = board;
	}
   
   public void setPlayers(Player[] players) {
		this.players = players;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

   private void drawBorder()
   {
      JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
      piece.setBorder(BorderFactory.createLineBorder(Color.BLACK));
   }
   
   private void clearBorder()
   {
      JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
      piece.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
   }
   
   private void displayMessage(String message, String title)
   {
      JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
   }
   
   private class PieceLabelClickListener implements MouseListener
   {
      public void mouseClicked(MouseEvent e)
      {
         
         BlokusPieceLabel bp = (BlokusPieceLabel) e.getComponent();
         clearBorder();
         pieceIndex = bp.pieceIndex;
         drawBorder();
      }
      
      public void mousePressed(MouseEvent e)
      {
         
      }
      
      public void mouseReleased(MouseEvent e)
      {
         
      }
      
      public void mouseEntered(MouseEvent e)
      {
         
      }
      
      public void mouseExited(MouseEvent e)
      {
         
      }
   }
   
   private void startNewTurn()
   {
      turn++;
      turn %= NUM_PLAYERS;
      message.setText("Use left and right arrow keys to rotate and up and down to flip");
      
      if (isGameOver())
      {
         gameOver();
      }
      
      if (!players[turn].canPlay)
      {
         startNewTurn();
         return;
      }
      
      piecesPanel.removeAll();
      
      for (int i = 0; i < players[turn].pieces.size(); i++)
      {
         BlokusPieceLabel pieceLabel = 
            new BlokusPieceLabel(i, players[turn].pieces.get(i), Piece.DEFAULT_RESOLUTION);
         pieceLabel.addMouseListener(new PieceLabelClickListener());
         pieceLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
         piecesPanel.add(pieceLabel);
      }
      
      pieceIndex = 0;
      drawBorder();
      piecesPanel.repaint();
      
      pack();
   }
   
   private boolean isGameOver()
   {
      for (int i = 0; i < NUM_PLAYERS; i++)
      {
         if (players[i].canPlay) return false;
      }
      return true;
   }
   
   private void gameOver()
   {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < NUM_PLAYERS; i++)
      {
         sb.append(Board.getColorName(getPlayerColor(i)));
         sb.append(": ");
         sb.append(players[i].getScore());
         sb.append("\n");
      }
      JOptionPane.showMessageDialog(this, sb.toString(), "Game Over", JOptionPane.INFORMATION_MESSAGE );
      System.exit(0);
   }
   
   private int getPlayerColor(int index)
   {
      switch (index)
      {
         case 0: return Board.BLUE;
         case 1: return Board.RED;
         case 2: return Board.YELLOW;
         case 3: return Board.GREEN;
         default: return 0;
      }
   }
   
   
   public static class BlokusPieceLabel extends JLabel
   {  
      public int pieceIndex;
      
      public BlokusPieceLabel(int pieceIndex, Piece bp, int size)
      {
         super(new ImageIcon(bp.render(size)));
         this.pieceIndex = pieceIndex;
      }
   }
   
   public void save() {
       try {
           FileOutputStream boardfileOut = new FileOutputStream("board.ser");
           ObjectOutputStream out = new ObjectOutputStream(boardfileOut);
           out.writeObject(this.board);
           out.close();
           boardfileOut.close();

           FileOutputStream p1fileout = new FileOutputStream("players.ser");
           ObjectOutputStream p1 = new ObjectOutputStream(p1fileout);
           p1.writeObject(this.players);
           p1.close();
           p1fileout.close();
           
           FileOutputStream turnFile = new FileOutputStream("turn.ser");
           ObjectOutputStream turn = new ObjectOutputStream(turnFile);
           turn.writeObject(this.turn);
           turn.close();
           turnFile.close(); 
 	  } catch (Exception e) {
 		
 		e.printStackTrace();
 	  }
 	    
   }
   public static void load() {
 	  try {
 	         FileInputStream fileIn = new FileInputStream("board.ser");
 	         ObjectInputStream in = new ObjectInputStream(fileIn);
 	         Board e = (Board) in.readObject();
 	         in.close();
 	         fileIn.close(); 
 	         setBoard(e);
 	         drawBoard();

 	         FileInputStream a = new FileInputStream("players.ser");
 	         ObjectInputStream b = new ObjectInputStream(a);
 	         Player [] p1 = (Player []) b.readObject();
 	         setPlayers(p1);
 	         b.close();
 	         a.close(); 
 	         
 	         FileInputStream c = new FileInputStream("turn.ser");
 	         ObjectInputStream d = new ObjectInputStream(c);
 	         int turn = (int) d.readObject();
 	         setTurn(turn);
 	         c.close();
 	         d.close(); 
 	         drawBoard(); 
 	         
 	         piecesPanel.removeAll();
 	         
 	         for (int i = 0; i < players[turn].pieces.size(); i++)
 	         {
 	            PieceLabel pLa = new PieceLabel(i, players[turn].pieces.get(i), Piece.defaultSize);
 	            pLa.addMouseListener(new PieceListener());
 	            pLa.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
 	            piecePanel.add(pLa);
 	         }
 	         pieceIndex = 0;
 	         drawBorder();
 	         piecePanel.repaint();	         
 	             	         
 	         pack();
 	         
 	  }
 	  
 	  catch(IOException i) {
 	         i.printStackTrace();
 	         return;
 	  } catch (ClassNotFoundException e1) {
 		
			e1.printStackTrace();
			return;
		}
   }
   
  // public static void main(String[] args)
 //  {
   //   Blokus bf = new Blokus();
 //  }
}
