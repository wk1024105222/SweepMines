package wk.game.sweepmines;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Introduction extends JDialog {
	Container container = this.getContentPane();

	JTabbedPane jTabledPane = new JTabbedPane();
	JPanel childJPanel1 = new JPanel();
	JPanel childJPanel2 = new JPanel();

	JTextArea area1 = new JTextArea(5,20);
	JTextArea area2 = new JTextArea(5,20);

	Introduction() {
		this.setTitle("扫雷");
		this.setBounds(200, 200, 300, 200);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		String s1 = new String("1：实现了Windows扫雷的所与功能（包括双键试探功能），" +
				"对雷区的大小没有限制，但当游戏窗口面积大于显示器屏幕时，" +
				"显示效果有些问题。\n" +
				"2：双键试探功能实现效果较差，有时左键单击也会出现试探的功能。\n" +
				"3：整个程序没有使用线程实现，算法有待优化。");
		String s2 = new String("扫雷V1.0\n\n制作人：王凯\n\nQQ：1024105222" +
				"\n\n邮箱：wangkai_1437@sina.com\n\n哈尔滨理工大学\n2010.3.21");

		area1.setText(s1);
		area1.setSize(300, 200);
		area1.setEditable(false);
		area1.setLineWrap(true);
		area1.setFont(new Font("微软雅黑",Font.PLAIN,20));
		area1.setForeground(Color.blue);

		area2.setText(s2);
		area2.setSize(300, 200);
		area2.setEditable(false);
		area2.setLineWrap(true);
		area2.setFont(new Font("微软雅黑",Font.PLAIN,20));
		area2.setForeground(Color.blue);

		childJPanel1.add(area1);
		childJPanel2.add(area2);

		jTabledPane.setSize(300, 300);
		jTabledPane.addTab("游戏说明", null, childJPanel1);
		jTabledPane.addTab("版权声明", null, childJPanel2);

		container.add(jTabledPane);
		this.pack();
		this.setVisible(true);

	}
}
