package whack_a_mole;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

class MyButton extends Button {
	Image img;

	MyButton(Image img) {
		this.img = img;
	}

	public void setImage(Image img) {
		this.img = img;
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth() - 5, this.getHeight() - 5, this);
	}
}

class MyFrame05 extends JFrame implements ActionListener, Runnable {
	private MyButton bt[] = new MyButton[9];
	private Label time_lb = new Label("시간 : 10초");
	private Label score_lb = new Label("점수 : 0점");
	private Button start_bt = new Button("시작");
	private Panel center_p = new Panel();
	private Panel east_p = new Panel();

	private Image img1 = Toolkit.getDefaultToolkit().getImage("du1.jpg");
	private Image img2 = Toolkit.getDefaultToolkit().getImage("du2.jpg");

	private int ransu;
	private int co = 0;

	public void init() {
		add("Center", center_p);
		center_p.setLayout(new GridLayout(3, 3));

		for (int i = 0; i < bt.length; i++) {
			bt[i] = new MyButton(img1);
			center_p.add(bt[i]);
		}
		add("East", east_p);
		east_p.setLayout(new GridLayout(3, 1));
		east_p.add(time_lb);
		east_p.add(score_lb);
		east_p.add(start_bt);
	}

	public void start() {
		start_bt.addActionListener(this);
		for (int i = 0; i < bt.length; i++) {
			bt[i].addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start_bt) {
			start_bt.setEnabled(false);
			Thread th = new Thread(this);
			th.start();
			gameStart();
		} else {
			for (int i = 0; i < bt.length; i++) {
				if (e.getSource() == bt[i])
					gainScore(i);
			}
		}
	}

	public void gainScore(int i) {
		if (ransu == i) {
			co++;
			score_lb.setText("점수 : " + co);
			gameStart();
		}
	}

	public void gameStart() {
		int ran;
		do {
			ran = (int) (Math.random() * 9);
		} while (ransu==ran);
		bt[ransu].setImage(img1);  //두더지가 들어감
		bt[ransu].repaint();
		ransu=ran;
		bt[ransu].setImage(img2);
		bt[ransu].repaint();

	}

	public MyFrame05(String title) {
		super(title);
		this.init();
		this.start();
		this.setSize(400, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2) - this.getWidth() / 2;
		int ypos = (int) (screen.getHeight() - this.getHeight()) / 2;
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		setVisible(true);
	}

	@Override
	public void run() {
		int i = 10;
		while (i >= 0) {
			time_lb.setText("시간 : " + i);
			i--;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		start_bt.setEnabled(true);
		for (int j = 0; j < bt.length; j++) {
			bt[j].setEnabled(false);
		}
		bt[ransu].setImage(img1);
		bt[ransu].repaint();
	}
}

public class whack_a_mole {

	public static void main(String[] args) {
		MyFrame05 frame = new MyFrame05("frame예제");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
