package wk.game.sweepmines;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game {
	int weight = 10;       //雷区横向个数
	int height = 10;     //雷区纵向个数
	int mineNumber = 10;    //地雷个数
	int findMineNumber = 0;     //已找到的地雷数 (右键标记无误        )
	int hasShowNumber = 0;           //已经翻开的方格数

	final int pToFrame = 10;     //面板到窗口的距离
	final int mineWeight = 16;   //地雷的大小

	Map map;
	Timer timer = new Timer(1000,new TimeListner());
	boolean isStart = true;

	JFrame frame = new JFrame("扫雷");
	MyInput input;
	JPanel p = new JPanel();

	JMenuBar menuBar = new JMenuBar();
	JMenu menu1 = new JMenu("游戏(G)");
	JMenu menu2 = new JMenu("帮助(H)");
	JMenuItem item1 = new JMenuItem("开局(N)",'N');
	JMenuItem item2 = new JMenuItem("初级(B)",'B');
	JMenuItem item3 = new JMenuItem("中级(I)",'I');
	JMenuItem item4 = new JMenuItem("高级(E)",'E');
	JMenuItem item5 = new JMenuItem("自定义(C)...",'C');
	JMenuItem item6 = new JMenuItem("扫雷英雄榜(T)...",'T');
	JMenuItem item7 = new JMenuItem("退出(X)",'X');
	JMenuItem item8 = new JMenuItem("版本信息(A)...",'A');

	JPanel p1 = new JPanel();
	JPanel p1_1 = new JPanel();                    //显示剩余地雷个数
	int lastMineNumber;
	JLabel lab1 = new JLabel();     //显示百位
	JLabel lab2 = new JLabel();   //显示十位
	JLabel lab3 = new JLabel();       //显示个位
	JPanel p1_2 = new JPanel();				//显示已用时间
	int time;
	JLabel lab4 = new JLabel();
	JLabel lab5 = new JLabel();
	JLabel lab6 = new JLabel();

	JButton reStart = new JButton();

	JPanel p2 = new JPanel();

	MyJButtonListenerSingle mbls = new MyJButtonListenerSingle();
	MyJButtonListenerDouble mbld = new MyJButtonListenerDouble();
	MyActionListener mil = new MyActionListener(this);
	
	ResourceUtil imageUtil = new ResourceUtil();



	Game() {
		this.setMenu();
		map = new Map(weight,height,mineNumber);
		p.setLayout(null);
		p.add(p1);
		p1.setLayout(null);
		p1.setBounds(0, 0, weight*mineWeight+pToFrame*2, mineWeight*2+20);

		p1_1.setBounds(pToFrame, pToFrame,3*mineWeight,mineWeight*2);
		p1_1.setBackground(Color.green);
		p1_1.setLayout(null);
		p1_1.add(lab1);
		lab1.setBounds(0, 0, 16, 32);
		p1_1.add(lab2);
		lab2.setBounds(16, 0, 16, 32);
		p1_1.add(lab3);
		lab3.setBounds(32, 0, 16, 32);
		lastMineNumber = mineNumber;
		lab1.setIcon(new ImageIcon(imageUtil.getImagePath("T" + lastMineNumber/100 + ".gif")));
		lab2.setIcon(new ImageIcon(imageUtil.getImagePath("T" + lastMineNumber%100/10 + ".gif")));
		lab3.setIcon(new ImageIcon(imageUtil.getImagePath("T" + lastMineNumber%10 + ".gif")));
		p1.add(p1_1);

		reStart.setBounds(pToFrame+((weight-6)/2+2)*mineWeight, pToFrame, 2*mineWeight, mineWeight*2);
		reStart.setIcon(new ImageIcon(imageUtil.getImagePath("laugh.gif")));
		reStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reStartGame();
			}
		});
		p1.add(reStart);

		p1_2.setBounds(pToFrame+(weight-3)*mineWeight, 10, 3*mineWeight, mineWeight*2);
		p1_2.setBackground(Color.green);
		p1_2.setLayout(null);
		p1_2.add(lab4);
		lab4.setBounds(0, 0, 16, 32);
		p1_2.add(lab5);
		lab5.setBounds(16, 0, 16, 32);
		p1_2.add(lab6);
		lab6.setBounds(32, 0, 16, 32);
		time = 0;
		lab4.setIcon(new ImageIcon(imageUtil.getImagePath("T" + time/100 + ".gif")));
		lab5.setIcon(new ImageIcon(imageUtil.getImagePath("T" + time%100/10 + ".gif")));
		lab6.setIcon(new ImageIcon(imageUtil.getImagePath("T" + time%10 + ".gif")));
		p1.add(p1_2);


		p.add(p2);
		for(int i=0; i!=map.mapNodes.size(); i++) {
			MyJButton b = (MyJButton)map.mapNodes.get(i);
			b.addMouseListener(mbls);
			b.addMouseListener(mbld);
			p2.add(b);
			b.setPreferredSize(new Dimension(mineWeight,mineWeight));
		}
		p2.setLayout(new FlowLayout((int) Component.LEFT_ALIGNMENT,0,0));
		p2.setBounds(pToFrame,mineWeight*2+20,weight*mineWeight,height*mineWeight);

		frame.setIconImage(new ImageIcon(imageUtil.getImagePath("icon.gif")).getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100,100,weight*mineWeight+pToFrame*2+8,height*mineWeight+pToFrame+mineWeight*2+80);
		frame.setContentPane(p);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.validate();

	}

	void setMenu() {
		frame.setJMenuBar(menuBar);
		menuBar.add(menu1);
		menu1.setMnemonic('G');
		menuBar.add(menu2);
		menu2.setMnemonic('H');

		item1.addActionListener(mil);
		item2.addActionListener(mil);
		item3.addActionListener(mil);
		item4.addActionListener(mil);
		item5.addActionListener(mil);
		item6.addActionListener(mil);
		item7.addActionListener(mil);
		item8.addActionListener(mil);

		menu1.add(item1);
		menu1.addSeparator();
		menu1.add(item2);
		menu1.add(item3);
		menu1.add(item4);
		menu1.add(item5);
		menu1.addSeparator();
		menu1.add(item6);
		menu1.addSeparator();
		menu1.add(item7);
		menu2.add(item8);
	}
	class MyActionListener implements ActionListener {
		Game g;
		MyActionListener(Game g) {
			this.g = g;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == item1) {
				reStartGame();
			} else if (e.getSource() == item2) {
				weight = 10;
				height = 10;
				mineNumber = 10;
				reStartGame();
			} else if (e.getSource() == item3) {
				weight = 16;
				height = 16;
				mineNumber = 40;
				reStartGame();
			} else if (e.getSource() == item4) {
				weight = 32;
				height = 16;
				mineNumber = 99;
				reStartGame();
			} else if (e.getSource() == item5) {
				input = new MyInput(g);
			} else if (e.getSource() == item6) {
				//英雄榜
			} else if (e.getSource() == item7) {
				System.exit(0);
			} else if (e.getSource() == item8) {
				new Introduction();
			}
		}
	}

	protected void reStartGame() {
		p2.removeAll();
		p2.validate();
		reStart.setIcon(new ImageIcon(imageUtil.getImagePath("laugh.gif")));
		map = new Map(weight,height,mineNumber);
		timer.stop();
		findMineNumber = 0;
		hasShowNumber = 0;
		isStart = true;
		lastMineNumber = mineNumber;
		time = 0;
		lab1.setIcon(new ImageIcon(imageUtil.getImagePath("T" + lastMineNumber/100 + ".gif")));
		lab2.setIcon(new ImageIcon(imageUtil.getImagePath("T" + lastMineNumber%100/10 + ".gif")));
		lab3.setIcon(new ImageIcon(imageUtil.getImagePath("T" + lastMineNumber%10 + ".gif")));
		lab4.setIcon(new ImageIcon(imageUtil.getImagePath("T" + time/100 + ".gif")));
		lab5.setIcon(new ImageIcon(imageUtil.getImagePath("T" + time%100/10 + ".gif")));
		lab6.setIcon(new ImageIcon(imageUtil.getImagePath("T" + time%10 + ".gif")));
		for(int i=0; i!=map.mapNodes.size(); i++) {
			MyJButton tempButton = (MyJButton)map.mapNodes.get(i);
			tempButton.setIcon(new ImageIcon(imageUtil.getImagePath("init.gif")));
			tempButton.addMouseListener(mbls);
			tempButton.addMouseListener(mbld);
			p2.add(tempButton);
			tempButton.setPreferredSize(new Dimension(mineWeight,mineWeight));
		}
		p1.setBounds(0, 0, weight*mineWeight+pToFrame*2, mineWeight*2+20);
		p1_1.setBounds(pToFrame, pToFrame,3*mineWeight,mineWeight*2);
		reStart.setBounds(pToFrame+((weight-6)/2+2)*mineWeight, pToFrame, 2*mineWeight, mineWeight*2);
		p1_2.setBounds(pToFrame+(weight-3)*mineWeight, 10, 3*mineWeight, mineWeight*2);
		p2.setSize(weight*mineWeight,height*mineWeight);
		p2.validate();

		frame.setSize(weight*mineWeight+pToFrame*2+8, height*mineWeight+pToFrame+mineWeight*2+80);
	}

	class TimeListner implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(time <= 999){
				time++;
				lab4.setIcon(new ImageIcon(imageUtil.getImagePath("T" + time/100 + ".gif")));
				lab5.setIcon(new ImageIcon(imageUtil.getImagePath("T" + time%100/10 + ".gif")));
				lab6.setIcon(new ImageIcon(imageUtil.getImagePath("T" + time%10 + ".gif")));
			}
		}

	}

	class MyJButtonListenerSingle extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			MyJButton b = (MyJButton)e.getSource();
			if(e.getModifiers() == InputEvent.BUTTON1_MASK && b.hasShow == false) {              	//左键单击
				if(isStart == true){
					timer.restart();
					isStart = false;
				}
				if(map.mineLocations.contains(new Integer(b.number))) {            //如果点中的是地雷
					timer.stop();
					b.setIcon(new ImageIcon(imageUtil.getImagePath("bang.gif")));
					b.nowIconName = "error.gif";
					reStart.setIcon(new ImageIcon(imageUtil.getImagePath("cry.gif")));
					for(int i=0; i!=map.mapNodes.size(); i++) {               	//所有按钮移除鼠标响应
						MyJButton temp = (MyJButton)map.mapNodes.get(i);
						if(map.mineLocations.contains(new Integer(temp.number)) && temp != b) {
							temp.setIcon(new ImageIcon(imageUtil.getImagePath(temp.iconName)));
						}
						if(temp.nowIconName.equals("guess.gif")				//已标记为雷
								&& !map.mineLocations.contains(new Integer(temp.number))) {          //但不是雷
							temp.setIcon(new ImageIcon(imageUtil.getImagePath("error.gif")));
							temp.nowIconName = "error.gif";
						}
						if(temp.getMouseListeners() != null) {
							temp.removeMouseListener(mbls);
							temp.removeMouseListener(mbld);
						}
					}
					JOptionPane.showMessageDialog(frame, "白痴！你输了(选错了)。");
				} else {                                                      	//点中的不是雷
					showWithoutMine(b);
				}
			} else if(e.getModifiers() == InputEvent.BUTTON3_MASK) {    				//单击右键
				if(b.getIcon().toString().endsWith("init.gif")) {
					if(map.mineLocations.contains(new Integer(b.number))) {
						findMineNumber++;
					}
					b.setIcon(new ImageIcon(imageUtil.getImagePath("guess.gif")));
					b.nowIconName = "guess.gif";
					if(lastMineNumber >= 1){
						lastMineNumber--;
					}
					b.hasShow = true;
				} else if(b.getIcon().toString().endsWith("guess.gif")) {
					if(map.mineLocations.contains(new Integer(b.number))) {
						findMineNumber--;
					}
					b.setIcon(new ImageIcon(imageUtil.getImagePath("doubt.gif")));
					b.nowIconName = "doubt.gif";
					lastMineNumber++;
					b.hasShow = false;
				} else if(b.getIcon().toString().endsWith("doubt.gif")) {
					b.setIcon(new ImageIcon(imageUtil.getImagePath("init.gif")));
					b.nowIconName = "init.gif";
					b.hasShow = false;
				}
				lab1.setIcon(new ImageIcon(imageUtil.getImagePath("T" + lastMineNumber/100 + ".gif")));
				lab2.setIcon(new ImageIcon(imageUtil.getImagePath("T" + lastMineNumber%100/10 + ".gif")));
				lab3.setIcon(new ImageIcon(imageUtil.getImagePath("T" + lastMineNumber%10 + ".gif")));
			}
			if(findMineNumber + hasShowNumber == weight*height && findMineNumber == mineNumber){
				timer.stop();
				for(int i=0; i!=map.mapNodes.size(); i++) {               	//所有按钮移除鼠标响应
					MyJButton temp = (MyJButton)map.mapNodes.get(i);
					if(temp.getMouseListeners() != null) {
						temp.removeMouseListener(mbls);
						temp.removeMouseListener(mbld);
					}
				}
				JOptionPane.showMessageDialog(frame, "牛B呀！哥们，你赢了。\n用时" + time + "秒");
			}
		}
	}

	void showWithoutMine(MyJButton b) {
		b.setIcon(new ImageIcon(imageUtil.getImagePath(b.iconName)));
		b.nowIconName = b.iconName;
		b.hasShow = true;
		hasShowNumber++;
		if(b.neighborMineNumber == 0){                       	 			//如果周围地雷非0
			for(int i=0; i!=b.neighbor.size(); i++ ) {
				MyJButton temp = (MyJButton)map.mapNodes.get(b.neighbor.get(i).intValue());
				if(temp.hasShow == false) {
					showWithoutMine(temp);
				}
			}
		}
	}

	class MyJButtonListenerDouble extends MouseAdapter {
		//boolean rightIsPress = false;
		@SuppressWarnings("static-access")
		@Override
		public void mousePressed(MouseEvent e) {
			MyJButton b = (MyJButton)e.getSource();
			String[] strArr = b.getIcon().toString().split("/");
			char c= strArr[strArr.length-1].charAt(0);
			if(c >= '0' && c <= '9'){
				//if(e.getModifiers() == InputEvent.BUTTON3_MASK) {
				//rightIsPress = true;
				//}
				//if(e.getModifiers() == InputEvent.BUTTON1_MASK && rightIsPress ) {
				if(e.getModifiersEx()==(InputEvent.BUTTON3_DOWN_MASK + InputEvent.BUTTON1_DOWN_MASK)) {
					for(int i=0; i!=b.neighbor.size(); i++) {
						if(map.mapNodes.get(b.neighbor.get(i)).hasShow == false) {
							map.mapNodes.get(b.neighbor.get(i)).setIcon(new ImageIcon(imageUtil.getImagePath("0.gif")));
						}
					}
				}
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			MyJButton b = (MyJButton)e.getSource();
			String[] strArr = b.getIcon().toString().split("/");
			char c= strArr[strArr.length-1].charAt(0);
			if(c >= '0' && c <= '9'){
				//if(e.getModifiers() == InputEvent.BUTTON3_MASK) {
				//rightIsPress = false;
				//}
				int guessMineNumber = 0;
				boolean isGuessAllRight = true;
				for(int i=0; i!=b.neighbor.size(); i++) {
					MyJButton temp = map.mapNodes.get(b.neighbor.get(i));
					if(temp.nowIconName.equals("guess.gif")) {
						guessMineNumber++;
						if(!map.mineLocations.contains(new Integer(temp.number))) {
							isGuessAllRight = false;
						}
					}
				}
				if(guessMineNumber == b.neighborMineNumber && isGuessAllRight == true) {//标记地雷数目正确位置正确
					for(int i=0; i!=b.neighbor.size(); i++) {
						MyJButton temp = map.mapNodes.get(b.neighbor.get(i));
						if(temp.hasShow == false) {
							temp.setIcon(new ImageIcon(imageUtil.getImagePath(temp.iconName)));
							showWithoutMine(temp);
							b.hasShow = true;
						}
					}
				} else if(guessMineNumber == b.neighborMineNumber && isGuessAllRight == false) {//标记地雷数目正确位置有错 输
					for (int i=0; i!=map.mapNodes.size(); i++) {
						MyJButton temp = map.mapNodes.get(i);
						if(b.neighbor.contains(new Integer(temp.number))     //temp是 b邻居
								&& temp.nowIconName.equals("guess.gif")				//已标记为雷
								&& !map.mineLocations.contains(new Integer(temp.number))) {    //但不是雷
							temp.setIcon(new ImageIcon(imageUtil.getImagePath("error.gif")));
							temp.nowIconName = "error.gif";
						} else if(b.neighbor.contains(new Integer(temp.number))     //temp是 b邻居
								&& temp.nowIconName.equals("init.gif")				//未标记为雷
								&& map.mineLocations.contains(new Integer(temp.number))) {   //但是雷
							temp.setIcon(new ImageIcon(imageUtil.getImagePath("bang.gif")));
							temp.nowIconName = "bang.gif";
						} else if(map.mineLocations.contains(new Integer(temp.number))) {
							temp.setIcon(new ImageIcon(imageUtil.getImagePath(temp.iconName)));
						}
						if(temp.getMouseListeners() != null) {
							temp.removeMouseListener(mbls);
							temp.removeMouseListener(mbld);
						}
					}
					timer.stop();
					JOptionPane.showMessageDialog(frame, "白痴！你输了(选错了)。");
				} else {
					for(int i=0; i!=b.neighbor.size(); i++) {
						MyJButton temp = map.mapNodes.get(b.neighbor.get(i));
						temp.setIcon(new ImageIcon(imageUtil.getImagePath(temp.nowIconName)));
					}
				}
			}          //if(c >= '0' && c <= '9')
			if(findMineNumber + hasShowNumber == weight*height && findMineNumber == mineNumber){
				timer.stop();
				for(int i=0; i!=map.mapNodes.size(); i++) {               	//所有按钮移除鼠标响应
					MyJButton temp = (MyJButton)map.mapNodes.get(i);
					if(temp.getMouseListeners() != null) {
						temp.removeMouseListener(mbls);
						temp.removeMouseListener(mbld);
					}
				}
				JOptionPane.showMessageDialog(frame, "牛B呀！哥们，你赢了。\n用时" + time + "秒");
			}
		}
	}

	public static void main(String[] args) {
		Game g = new Game();

	}
}

