package wk.game.sweepmines;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Map {
	int b;                          //主人地址  和邻居相对
	int w;
	int h;
	int mineNumber;
	ResourceUtil imageUtil = new ResourceUtil();

	ArrayList<Integer> mineLocations = new ArrayList<Integer>();
	ArrayList<MyJButton> mapNodes = new ArrayList<MyJButton>();

	Map(int weight,int height, int mineNumber) {
		w = weight;
		h = height;
		this.mineNumber = mineNumber;
		int area = w*h;

		do {
			int i = rand(area);
			if(!mineLocations.contains(new Integer(i))) {
				mineLocations.add(new Integer(i));
				//System.out.print(i+"  ");
			}
		} while(mineLocations.size() < mineNumber);
		for(int i=0; i!=area; i++) {
			MyJButton mb;
			int b = i;
			int[] N = {b+1,b-1,b+w,b+w-1,b+w+1};
			int[] EN = {b-1,b+w,b+w-1};
			int[] E = {b-w-1,b-w,b-1,b+w-1,b+w};
			int[] ES = {b-w-1,b-w,b-1};
			int[] S = {b-w-1,b-w,b-w+1,b-1,b+1};
			int[] WS = {b-w,b-w+1,b+1};
			int[] W = {b-w,b-w+1,b+1,b+w,b+w+1};
			int[] WN = {b+1,b+w,b+w+1};
			int[] CEN = {b-w-1,b-w,b-w+1,b-1,b+1,b+w-1,b+w,b+w+1};

			if(!this.mineLocations.contains(new Integer(i))) {       //此格不是地雷
				if((i > 0) && (i < w-1)) {
					mb = new MyJButton(i,N);
				} else if(i == w - 1) {
					mb = new MyJButton(i,EN);
				} else if((i % w == w - 1) && (i / w > 0) && (i / w < h - 1)) {
					mb = new MyJButton(i,E);
				} else if(i == area - 1) {
					mb = new MyJButton(i,ES);
				} else if((i > area - w) && (i < area)) {
					mb = new MyJButton(i,S);
				} else if(i == area - w) {
					mb = new MyJButton(i,WS);
				} else if((i % w == 0) && (i / w > 0) && (i / w < h - 1)) {
					mb = new MyJButton(i,W);
				} else if(i == 0) {
					mb = new MyJButton(i,WN);
				} else {
					mb = new MyJButton(i,CEN);
				}

				ArrayList<Integer> tempArray = new ArrayList<Integer>(this.mineLocations);  //临时使用的arrayList 计算交集个数用
				tempArray.retainAll(mb.neighbor);
				int neighborMineNumber = tempArray.size();
				mb.setIconName(neighborMineNumber);

			} else {                                                    //此格是地雷
				mb = new MyJButton(i);
				mb.iconName = "mine.gif";
			}
			mb.setIcon(new ImageIcon(imageUtil.getImagePath("init.gif")));
			mb.nowIconName = "init.gif";
			mapNodes.add(mb);
		}
	}

	int rand(int n) {
		int a = (int)(Math.random() * n);
		return a;
	}

	ArrayList<Integer> clone(ArrayList<Integer> tempArray,ArrayList<Integer> mineLocations2) {
		for(int i=0; i!=mineLocations2.size(); i++) {
			tempArray.add(mineLocations2.get(i));
		}
		return tempArray;
	}
}
