package com.boomhope.Bill.Util;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class SignatureUtil extends JPanel implements Runnable, ActionListener,
		MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private JButton clear, drawdddd;// 清除,开始画和重放和颜色的按钮
	Color choosedColor = Color.BLACK;// 选中的颜色
	JDialog dialog = null;
	private LinkedList<MyShape> save;// 保存起来的形状

	private int flag = 0;// 定义一个标志,用它来传递当前是在干什么
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	private final int DRAW = 1;// 1代表当前正在画图
	private final int REPLAY = 2;// 2代表当前正在重放

	private LinkedList<Pen> ps;// 保存点

	private volatile boolean go;

	/** Creates a new instance of Test */
	public SignatureUtil() {
		setBounds(0, 51, 484, 255);
		setBackground(Color.WHITE);
		setLayout(null);
		initOther();
		initWindow();
	}

	private void initOther() {
		clear = new JButton("清除");
		clear.setBounds(176, 5, 57, 23);
		drawdddd = new JButton("开始画");
		drawdddd.setBounds(238, 5, 69, 23);

		clear.addActionListener(this);
		drawdddd.addActionListener(this);

		save = new LinkedList<MyShape>();
		ps = new LinkedList<Pen>();
		clear.setEnabled(false);
	}

	public LinkedList<MyShape> getSave() {
		return save;
	}

	public void setSave(LinkedList<MyShape> save) {
		this.save = save;
	}

	private void initWindow() {
//		JFrame jf = new JFrame("画板 Dreamm ");
//		JPanel down = new JPanel();
//		down.setBounds(0, 379, 484, 33);
//		down.setLayout(null);
//		down.add(clear);
//		down.add(drawdddd);
//		jf.getContentPane().setLayout(null);
		this.setBorder(BorderFactory.createTitledBorder(""));
//		jf.getContentPane().add(this);
//		jf.getContentPane().add(down);
//		jf.setSize(500, 450);
//		jf.setLocationRelativeTo(null);
//		jf.setVisible(true);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		flag = DRAW;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (flag == DRAW) {
			for (MyShape my : save) {
				my.drawMe(g);
			}
			Point previous = null;
			for (Pen pp : ps) {
				Point p = pp.getP();
				if (previous != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
							BasicStroke.JOIN_BEVEL, 2));
					g.setColor(pp.getColor());
					g.drawLine(previous.x, previous.y, p.x, p.y);
				}
				previous = p;
			}
		} else if (flag == REPLAY) {
			try {
				for (MyShape my : save) {
					if (my.hasNext()) {
						my.drawNext(g);
						return;
					} else {
						my.drawMe(g);
					}
				}

				for (MyShape my : save) {
					my.reset();
				}
				go = false;
				drawdddd.setEnabled(true);
			} catch (Exception exe) {
				exe.printStackTrace();
			}
		}
	}

	public void run() {
		while (go) {
			try {
				Thread.sleep(20);
				repaint();
			} catch (Exception exe) {
				exe.printStackTrace();
			}
		}
		flag = DRAW;
		repaint();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == clear) {
			save.clear();
			repaint();
		} else if (source == drawdddd) {
			flag = DRAW;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			drawdddd.setEnabled(false);
			clear.setEnabled(true);
		} 
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		Pen pp = new Pen(p, choosedColor);
		ps.offer(pp);
		repaint();
	}

	public void mouseReleased(MouseEvent e) {
		Pen pp = new Pen(e.getPoint(), choosedColor);
		save.add(new MyLine(ps));
		ps = new LinkedList<Pen>();
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		Pen pp = new Pen(e.getPoint(), choosedColor);
		ps.offer(pp);
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
	}

	public static void main(String[] args) {
		new SignatureUtil();
	}
}

// 定义了一个接口,它规定了重绘自己的方法
interface MyShape {
	void drawMe(Graphics g);// 一下子画出自己

	boolean hasNext();// 是否还有下一个状态

	void drawNext(Graphics g);// 画出自己下一个样子

	void reset();// 重置一下
}

class Pen {
	private Point p;
	private Color color;

	Pen(Point p, Color color) {
		this.p = p;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}

}

class MyLine implements MyShape {

	private LinkedList<Pen> ll;// 用来保存点
	private Iterator<Pen> it;// 一个迭代器
	private LinkedList<Pen> over;// 保存已经画过的点

	public MyLine(LinkedList<Pen> ll) {
		this.ll = ll;
		it = ll.iterator();
		over = new LinkedList<Pen>();
	}

	public void drawMe(Graphics g) {
		Point previous = ll.getFirst().getP();
		for (Pen pp : ll) {
			Point p = pp.getP();
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,3));
			g.setColor(pp.getColor());
			g.drawLine(previous.x, previous.y, p.x, p.y);
			previous = p;
		}
	}

	public boolean hasNext() {
		return it.hasNext();
	}

	public void drawNext(Graphics g) {
		over.add(it.next());
		Point previous = over.getFirst().getP();
		for (Pen pp : over) {
			Point p = pp.getP();
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL, 2));
			g.setColor(pp.getColor());
			g.drawLine(previous.x, previous.y, p.x, p.y);
			previous = p;
		}
	}

	public void reset() {
		it = ll.iterator();
		over.clear();
	}
}