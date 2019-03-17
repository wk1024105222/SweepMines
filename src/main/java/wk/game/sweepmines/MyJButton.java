package wk.game.sweepmines;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class MyJButton extends JButton {
	int number;
	String iconName;
	String nowIconName;
	ArrayList<Integer> neighbor = new ArrayList<Integer>();
	int neighborMineNumber ;
	boolean hasShow = false;
	
	MyJButton(int number) {
		this.number = number;		
		neighbor = null;
		this.neighborMineNumber = 100;
	}
	
	MyJButton(int number, int[] array) {
		this.number = number;		
		for(int i=0; i!=array.length; i++) {
			neighbor.add(new Integer(array[i]));
		}
	}
	
	void setIconName(int n) {
		this.neighborMineNumber = n;
		this.iconName = n + ".gif";
	}
	
	void changeIcon() {
		this.setIcon(new ImageIcon("iconName"));
	}
}
