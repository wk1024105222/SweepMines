package wk.game.sweepmines;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MyInput {
	JFrame frame = new JFrame("自定义雷区");
	int height;
	int weight;
	int mineNumber;

	JLabel label1 = new JLabel("高度：");
	JLabel label2 = new JLabel("宽度：");
	JLabel label3 = new JLabel("雷数：");
	JPanel p = (JPanel)frame. getContentPane();

	JTextField inputHeight = new JTextField(5);
	JTextField inputWeight = new JTextField(5);
	JTextField inputMineNumber = new JTextField(5);

	JButton OK = new JButton("确定");
	JButton Cencle = new JButton("取消");

	MyInput(final Game g) {
		this.height = g.height;
		this.weight =g.weight;
		this.mineNumber = g.mineNumber;

		p.setLayout(null);

		p.add(label1);
		p.add(inputHeight);
		label1.setBounds(20, 20, 40, 25);
		inputHeight.setBounds(70, 20, 50, 25);
		inputHeight.setText(height+"");

		p.add(label2);
		p.add(inputWeight);
		label2.setBounds(20, 55, 40, 25);
		inputWeight.setBounds(70, 55, 50, 25);
		inputWeight.setText(weight+"");

		p.add(label3);
		p.add(inputMineNumber);
		label3.setBounds(20, 90, 40, 25);
		inputMineNumber.setBounds(70, 90, 50, 25);
		inputMineNumber.setText(mineNumber+"");

		p.add(OK);
		OK.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				try {
					height = Integer.parseInt(inputHeight.getText());
					weight = Integer.parseInt(inputWeight.getText());
					mineNumber = Integer.parseInt(inputMineNumber.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frame, "请填写正整数！","提示",JOptionPane.WARNING_MESSAGE);
				}
				if(weight < 0 || height < 0 || mineNumber < 0){
					JOptionPane.showMessageDialog(frame, "请填写正整数！");
				} else if(weight % 2 != 0 || weight < 8) {
					JOptionPane.showMessageDialog(frame, "请确保宽度为偶数且大于8（布局美观）！");
				} else if(mineNumber < 0 || mineNumber > weight * height) {
					JOptionPane.showMessageDialog(frame, "请确保地雷数目合理！");
				} else {
					g.weight = weight;
					g.height = height;
					g.mineNumber = mineNumber;
					g.reStartGame();
					frame.setVisible(false);
				}
			}
		});
		OK.setBounds(140, 30, 60, 30);
		p.add(Cencle);
		Cencle.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		Cencle.setBounds(140, 75, 60, 30);
		frame.setResizable(false);
		frame.setBounds(500, 100, 230, 165);
		frame.validate();
		frame.setVisible(true);
	}
}

