package controllers;
import java.awt.event.MouseEvent;


import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import model.*;
import boundary.*;

public class TileDragController implements MouseMotionListener {

	JPanel panel;
	JLayeredPane layeredPane;
	JLabel[] arrows;
	int current_check = 0;
	Model model;
	Application app;
	
	public TileDragController(JPanel p, JLayeredPane lp, JLabel[] a, Application app, Model model) {
		this.panel = p;
		this.layeredPane = lp;
		this.arrows = a;
		this.model = model;
		this.app = app;
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		
		int x_rel = e.getX();
		int y_rel = e.getY();
		int i = this.getTile(x_rel, y_rel);	
		
		//valid placement needs extensive testing
		if (i != 0 && i != current_check){ 
			JToggleButton selectedButton = (JToggleButton) panel.getComponent(i-1);
			selectedButton.setEnabled(false);
			
			// the tile to be added to selected tiles once the mouse touches this tile
			Tile newTile = new Tile(this.xPos(i), this.yPos(i));
			
			// the tile is selected to create a word to be removed later 
			this.model.currentLevel.getBoard().selectTile(newTile);
			
			if (current_check != 0){
				System.out.println("hi");
				activateLabel(i -1, current_check);
			}
			current_check = i;			
		}	
		
	}
	
	private void activateLabel(int i, int c){
		
		int[] neighbors = {c-7, c-5, c+5, c+7, c-6, c-1, c+1, c+6};
		int connect_type = 0;
		
		for (int j = 0; j < 8; j++){
			if (i + 1 == neighbors[j]){
				connect_type = j;
				//break;
			}
		}

		JLabel label;
		
		switch (connect_type) {
		case 0:  //top-left arrow
			label = arrows[31 + (2 * (i % 6)) + (11 *  (i / 6))]; 
    		layeredPane.setLayer(label, 2);
    		label.setIcon(new ImageIcon(TileDragController.class.getResource("/resources/up_left.png")));
        break;
        case 1:  //top-right arrow
        	label = arrows[29 + (2 * (i % 6)) + (11 *  (i / 6))]; 
    		layeredPane.setLayer(label, 2);
    		label.setIcon(new ImageIcon(TileDragController.class.getResource("/resources/up_right.png")));
        break;
        case 2:  //bottom-left arrow
        	label = arrows[20 + (2 * (i % 6)) + (11 *  (i / 6))]; 
    		layeredPane.setLayer(label, 2);
    		label.setIcon(new ImageIcon(TileDragController.class.getResource("/resources/down_left.png")));
        break;
        case 3:  //bottom-right arrow
        	label = arrows[18 + (2 * (i % 6)) + (11 *  (i / 6))]; 
    		layeredPane.setLayer(label, 2);
    		label.setIcon(new ImageIcon(TileDragController.class.getResource("/resources/down_right.png")));
        break;
        case 4:  
        	label = arrows[30 + (2 * (i % 6)) + (11 *  (i / 6))]; 
    		layeredPane.setLayer(label, 2);
    		label.setIcon(new ImageIcon(TileDragController.class.getResource("/resources/up.png")));
        break;
        case 5:  
        	label = arrows[i - (i/6)];
    		layeredPane.setLayer(label, 2);
    		label.setIcon(new ImageIcon(TileDragController.class.getResource("/resources/left.png")));
        break;
        case 6:  
        	label = arrows[i-1 - (i/6)];
    		layeredPane.setLayer(label, 2);
    		label.setIcon(new ImageIcon(TileDragController.class.getResource("/resources/right.png")));
        break;
        case 7:  
        	label = arrows[19 + (2 * (i % 6)) + (11 *  (i / 6))]; 
    		layeredPane.setLayer(label, 2);
    		label.setIcon(new ImageIcon(TileDragController.class.getResource("/resources/down.png")));
        break;
		} 
        
	}
	
	//WARNING: MAY PRODUCE DEFECTS
	private boolean isValidPlacement(int i, int c) {
		
		int[] neighbors = {c-7, c-5, c+5, c+7, c-6, c-1, c+1, c+6};
		
		if (c == 0){
			return true;
		}
		for (int j = 0; j < 8; j++){
			if (i == neighbors[j]){
				System.out.println("true");
				return true;
			}
		}
		
		return false;
	}

	private int getTile(int x, int y){
		for (int i = 0; i < 36; i++){
			int x_len = 100 + 100*(i % 6);
			int y_len = 100 + 100*(i / 6);
			
			if (x < (x_len + 80) && x > (x_len+20) && y < (y_len + 80) && y > (y_len + 20)){
				return i + 1;
			}
		}		
		return 0;
	}
	
	// The method to calculate the x position of the tile on the board(6x6 board),
	// given the index of the button on the panel(panel is assumed to be an array of buttons)
	private int xPos(int index) {
		// The remainder 
		int rem = index%6;
		// The quotient
		int quot = index/6;
		// Check if remainder is 0, then decrement the quotient part
		if (rem == 0) {
			quot = quot - 1;
		}
		return quot;
	}
	
	// The method to calculate the y position of the tile on the board(6x6 board),
	// given the index of the button on the panel(panel is assumed to be an array of buttons)
	private int yPos(int index) {
		// The remainder 
		int rem = index%6;
		// Check if remainder is 0, then set the remainder to 6
		if (rem == 0) {
			rem = 6;
		}
		return rem;
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
