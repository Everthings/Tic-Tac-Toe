
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Board extends JPanel{
	
	Icon X = new ImageIcon("/Users/XuMan/Documents/workspace/Tic Tac Toe/images/Tic Tac Toe X.png");
	Icon O = new ImageIcon("/Users/XuMan/Documents/workspace/Tic Tac Toe/images/Tic Tac Toe O.png");
	Icon empty = new ImageIcon("");
	
	JLabel Win = new JLabel("You Won");
	JLabel Lose = new JLabel("You Lost");
	JLabel Tie = new JLabel("You Tied");
	
	JButton One; 
	JButton Two; 
	JButton Three; 
	JButton Four; 
	JButton Five; 
	JButton Six; 
	JButton Seven; 
	JButton Eight;  
	JButton Nine; 
	
	final int OPEN_SPACE = 0;
	final int X_SPACE = 1;
	final int O_SPACE = 2;
	
	final int size = 500;
	int bx, by;
	int[] Moves;
	
	boolean playerHasPlayed = false;
	boolean cpuHasPlayed = true;
	
	JButton[][] BArray= {
	{One = new JButton(),      //| One  | Two   | Three |
	Two = new JButton(),      //| Four | Five  | Six   |
	Three = new JButton()},  //| Seven| Eight | Nine  |
	{Four = new JButton(),    
	Five = new JButton(),	   
	Six = new JButton()},
	{Seven = new JButton(),
	Eight = new JButton(),
	Nine = new JButton()}
	};
	
	int[][] BoardLayout = {
	{OPEN_SPACE, OPEN_SPACE, OPEN_SPACE},
	{OPEN_SPACE, OPEN_SPACE, OPEN_SPACE},
	{OPEN_SPACE, OPEN_SPACE, OPEN_SPACE}
	};
	
	public Board(){

		this.setLayout(new GridLayout(3, 3));
			
		for(int i = 0; i <= 2; i++){
			for(int j = 0; j <= 2; j++){
				this.add(BArray[i][j]);
				ButtonSetup(i, j);
			
			}
		}
	}
	
	
	public void resetBoard(){
		for(int i = 0; i <= 2; i++){
			for(int j = 0; j <= 2; j++){
				BArray[i][j].setIcon(empty);
				BoardLayout[i][j] = OPEN_SPACE;
			}
		}	
	}
	
	@Override
    public Dimension getPreferredSize(){
        return new Dimension(size, size);
    }
	
	public int getButtonLength(){
		final int BUTTON_WIDTH = size/3 - 15;
		return BUTTON_WIDTH;
	}
	
	
	public Dimension getButtonSize(){
		return new Dimension(getButtonLength(), getButtonLength());
	}
	
public void ButtonSetup(final int a, final int b){
		
		BArray[a][b].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				playerMove(a, b);
			}
		});
	}
	
	public void playerMove(int a, int b){
		
		if(BoardLayout[a][b] == OPEN_SPACE){
			BArray[a][b].setIcon(X);
			BoardLayout[a][b] = X_SPACE;
			cpuHasPlayed = false;
		}
		
		repaint();
		for(int q = 0; q <= 2; q++){
			System.out.print(BoardLayout[q][0] + " ");
			System.out.print(BoardLayout[q][1] + " ");
			System.out.println(BoardLayout[q][2] + " ");
		}
		System.out.println("-----------------------");
		
		if(checkWin()){	
			int response = JOptionPane.showConfirmDialog(null, "Congratulations, You Won! Play Again?", "Game Over",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (response == JOptionPane.NO_OPTION) {
		      System.exit(0);
		    } else if (response == JOptionPane.YES_OPTION) {
		      resetBoard();  
		      repaint();
		    } else if (response == JOptionPane.CLOSED_OPTION) {
		      System.exit(0);
		    }
		}else if(checkFull()){
			int response = JOptionPane.showConfirmDialog(null, "You Tied =( Do you want to retry?", "Game Over",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (response == JOptionPane.NO_OPTION) {
		      System.exit(0);
		    } else if (response == JOptionPane.YES_OPTION) {
		      resetBoard();
		      repaint();
		    } else if (response == JOptionPane.CLOSED_OPTION) {
		      System.exit(0);
		   }
		}
		
		cpuMove(a, b);
	}
	
	public void cpuMove(int a, int b){
		
		
		while(cpuHasPlayed == false){
			Moves = findMove();
			if(BoardLayout[Moves[0]][Moves[1]] == OPEN_SPACE){
				BArray[Moves[0]][Moves[1]].setIcon(O);
				BoardLayout[Moves[0]][Moves[1]] = O_SPACE;
			//	playerHasPlayed = false;
				cpuHasPlayed = true;
			}
		}	
		
		repaint();
		for(int q = 0; q <= 2; q++){
			System.out.print(BoardLayout[q][0] + " ");
			System.out.print(BoardLayout[q][1] + " ");
			System.out.println(BoardLayout[q][2] + " ");
		}
		System.out.println("-----------------------");
		
		if(checkWin()){	
			int response = JOptionPane.showConfirmDialog(null, "You Lost... Try Again?", "Game Over",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (response == JOptionPane.NO_OPTION) {
		      System.exit(0);
		    } else if (response == JOptionPane.YES_OPTION) {
		      resetBoard();  
		      repaint();
		    } else if (response == JOptionPane.CLOSED_OPTION) {
		      System.exit(0);
		    }
		}else if(checkFull()){
			int response = JOptionPane.showConfirmDialog(null, "You Tied =( Do you want to retry?", "Game Over",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (response == JOptionPane.NO_OPTION) {
		      System.exit(0);
		    } else if (response == JOptionPane.YES_OPTION) {
		      resetBoard();
		      repaint();
		    } else if (response == JOptionPane.CLOSED_OPTION) {
		      System.exit(0);
		   }
		}
	}
		
	public boolean checkFull(){
		for(int o = 0; o <= 2; o++){
			for(int p = 0; p <= 2; p++){
				if(BoardLayout[o][p] == OPEN_SPACE)
					return false;
			}
		}
		
		return true;
	}
	
	public boolean checkWin(){
		
		for(int i = 0; i <= 2 ;i++){
			if(BoardLayout[i][0] == BoardLayout[i][1] && BoardLayout[i][1] == BoardLayout[i][2]){
				if(BoardLayout[i][0] == X_SPACE || BoardLayout[i][0] == O_SPACE)
					return true;
			}
			if(BoardLayout[0][i] == BoardLayout[1][i] && BoardLayout[1][i] == BoardLayout[2][i]){
				if(BoardLayout[0][i] == X_SPACE || BoardLayout[0][i] == O_SPACE)
					return true;
			}
		
			if(BoardLayout[0][0] == BoardLayout[1][1] && BoardLayout[0][0] == BoardLayout[2][2]){
				if(BoardLayout[0][0] == X_SPACE || BoardLayout[0][0] == O_SPACE)
					return true;
			}
		
			if(BoardLayout[2][0] == BoardLayout[1][1] && BoardLayout[2][0] == BoardLayout[0][2]){
				if(BoardLayout[2][0] == X_SPACE || BoardLayout[2][0] == O_SPACE)
					return true;
			}
		}
		
		return false;
	}
	
	public int[] findMove(){
		int[] b = {-1, -1};
		boolean solved = false;
		
		for(int i = 0; i <= 2; i++){
			if(BoardLayout[i][0] == BoardLayout[i][1]){
				if(BoardLayout[i][0] == O_SPACE){
					if(BoardLayout[i][2] == OPEN_SPACE){
						solved = true;
						b[0] = i;
						b[1] = 2;
						break;
					}
				}
			}
			
			if(BoardLayout[i][0] == BoardLayout[i][2]){
				if(BoardLayout[i][0] == O_SPACE){
					if(BoardLayout[i][1] == OPEN_SPACE){
						solved = true;
						b[0] = i;
						b[1] = 1;
						break;
					}
				}
			}
			
			if(BoardLayout[i][1] == BoardLayout[i][2]){
				if(BoardLayout[i][1] == O_SPACE){
					if(BoardLayout[i][0] == OPEN_SPACE){
						solved = true;
						b[0] = i;
						b[1] = 0;
						break;
					}
				}
			}
			
			if(BoardLayout[0][i] == BoardLayout[1][i]){
				if(BoardLayout[0][i] == O_SPACE){
					if(BoardLayout[2][i] == OPEN_SPACE){
						solved = true;
						b[0] = 2;
						b[1] = i;
						break;
					}
				}
			}
			
			if(BoardLayout[0][i] == BoardLayout[2][i]){
				if(BoardLayout[0][i] == O_SPACE){
					if(BoardLayout[1][i] == OPEN_SPACE){
						solved = true;
						b[0] = 1;
						b[1] = i;
						break;
					}
				}
			}
			
			if(BoardLayout[1][i] == BoardLayout[2][i]){
				if(BoardLayout[1][i] == O_SPACE){
					if(BoardLayout[0][i] == OPEN_SPACE){
						solved = true;
						b[0] = 0;
						b[1] = i;
						break;
					}	
				}
			}
		}
		
		if(!solved){
			for(int i = 0; i <= 2; i++){
				if(BoardLayout[i][0] == BoardLayout[i][1]){
					if(BoardLayout[i][0] == X_SPACE){
						if(BoardLayout[i][2] == OPEN_SPACE){
							solved = true;
							b[0] = i;
							b[1] = 2;
							break;
						}
					}
				}
				
				if(BoardLayout[i][0] == BoardLayout[i][2]){
					if(BoardLayout[i][0] == X_SPACE){
						if(BoardLayout[i][1] == OPEN_SPACE){
							solved = true;
							b[0] = i;
							b[1] = 1;
							break;
						}
					}
				}
				
				if(BoardLayout[i][1] == BoardLayout[i][2]){
					if(BoardLayout[i][1] == X_SPACE){
						if(BoardLayout[i][0] == OPEN_SPACE){
							solved = true;
							b[0] = i;
							b[1] = 0;
							break;
						}
					}
				}
				
				if(BoardLayout[0][i] == BoardLayout[1][i]){
					if(BoardLayout[0][i] == X_SPACE){
						if(BoardLayout[2][i] == OPEN_SPACE){
							solved = true;
							b[0] = 2;
							b[1] = i;
							break;
						}
					}
				}
				
				if(BoardLayout[0][i] == BoardLayout[2][i]){
					if(BoardLayout[0][i] == X_SPACE){
						if(BoardLayout[1][i] == OPEN_SPACE){
							solved = true;
							b[0] = 1;
							b[1] = i;
							break;
						}
					}
				}
				
				if(BoardLayout[1][i] == BoardLayout[2][i]){
					if(BoardLayout[1][i] == X_SPACE){
						if(BoardLayout[0][i] == OPEN_SPACE){
							solved = true;
							b[0] = 0;
							b[1] = i;
							break;
						}	
					}
				}
			}
		}
		
		if(!solved){
			if(BoardLayout[0][0] == BoardLayout[1][1]){
				if(BoardLayout[0][0] == X_SPACE){
					if(BoardLayout[2][2] == OPEN_SPACE){
						b[0] = 2;
						b[1] = 2;
					}
				}
			}
			
			if(BoardLayout[0][0] == BoardLayout[2][2]){
				if(BoardLayout[0][0] == X_SPACE){
					if(BoardLayout[1][1] == OPEN_SPACE){
						b[0] = 1;
						b[1] = 1;
					}
				}
			}
			
			if(BoardLayout[1][1] == BoardLayout[2][2]){
				if(BoardLayout[1][1] == X_SPACE){
					if(BoardLayout[0][0] == OPEN_SPACE){
						b[0] = 0;
						b[1] = 0;
					}
				}
			}
			
			if(BoardLayout[0][2] == BoardLayout[2][0]){
				if(BoardLayout[0][2] == X_SPACE){
					if(BoardLayout[1][1] == OPEN_SPACE){
						b[0] = 1;
						b[1] = 1;
					}
				}
			}
			
			if(BoardLayout[0][2] == BoardLayout[1][1]){
				if(BoardLayout[0][2] == X_SPACE){
					if(BoardLayout[2][0] == OPEN_SPACE){
						b[0] = 2;
						b[1] = 0;
					}
				}
			}
			
			if(BoardLayout[2][0] == BoardLayout[1][1]){
				if(BoardLayout[2][0] == X_SPACE){
					if(BoardLayout[0][2] == OPEN_SPACE){
						b[0] = 0;
						b[1] = 2;
					}
				}
			}
				
			if(BoardLayout[0][0] == BoardLayout[1][1]){
				if(BoardLayout[0][0] == O_SPACE){
					if(BoardLayout[2][2] == OPEN_SPACE){
						b[0] = 2;
						b[1] = 2;
					}
				}
			}
			
			if(BoardLayout[0][0] == BoardLayout[2][2]){
				if(BoardLayout[0][0] == O_SPACE){
					if(BoardLayout[1][1] == OPEN_SPACE){
						b[0] = 1;
						b[1] = 1;
					}
				}
			}
			
			if(BoardLayout[1][1] == BoardLayout[2][2]){
				if(BoardLayout[1][1] == O_SPACE){
					if(BoardLayout[0][0] == OPEN_SPACE){
						b[0] = 0;
						b[1] = 0;
					}
				}
			}
			
			if(BoardLayout[0][2] == BoardLayout[2][0]){
				if(BoardLayout[0][2] == O_SPACE){
					if(BoardLayout[1][1] == OPEN_SPACE){
						b[0] = 1;
						b[1] = 1;
					}
				}
			}
			
			if(BoardLayout[0][2] == BoardLayout[1][1]){
				if(BoardLayout[0][2] == O_SPACE){
					if(BoardLayout[2][0] == OPEN_SPACE){
						b[0] = 2;
						b[1] = 0;
					}
				}
			}
			
			if(BoardLayout[2][0] == BoardLayout[1][1]){
				if(BoardLayout[2][0] == O_SPACE){
					if(BoardLayout[0][2] == OPEN_SPACE){
						b[0] = 0;
						b[1] = 2;
					}
				}
			}
		}

		if(b[0] == -1){
			
			Random rand = new Random();
			b[0] = rand.nextInt(3);
			b[1] = rand.nextInt(3);
			int r = rand.nextInt(4);
			
			
			if(BoardLayout[0][0] == OPEN_SPACE || BoardLayout[2][2] == OPEN_SPACE || BoardLayout[0][2] == OPEN_SPACE || BoardLayout[2][0] == OPEN_SPACE){
				System.out.println("called");
				switch(r){
					case 0: 
						if(BoardLayout[0][0] == OPEN_SPACE){
							b[0] = 0;
							b[1] = 0;
							break;
						}
					case 1: 
						if(BoardLayout[2][2] == OPEN_SPACE){
							b[0] = 2;
							b[1] = 2;
							break;
						}	
					case 2: 
						if(BoardLayout[2][0] == OPEN_SPACE){
							b[0] = 2;
							b[1] = 0;
							break;
						}		
					case 3: 
						if(BoardLayout[0][2] == OPEN_SPACE){
							b[0] = 0;
							b[1] = 2;
							break;
					}	
				}
			}
			
			if(BoardLayout[1][1] == OPEN_SPACE){
				b[0] = 1;
				b[1] = 1;
			}
			
			
		}
		
		return b;
	}
	
	
	public static void main(String args[]){
		JFrame GameBoard = new JFrame("Tic Tac Toe");
		Board GamePanel = new Board();
		GameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameBoard.pack();
        GameBoard.setResizable(false);
        GameBoard.setLocationByPlatform(true);
        GameBoard.setSize(500, 500);
        GameBoard.add(GamePanel);
        GameBoard.setVisible(true);
	}
}

