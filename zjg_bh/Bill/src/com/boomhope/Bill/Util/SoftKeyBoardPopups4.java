package com.boomhope.Bill.Util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import sun.swing.SwingUtilities2;

import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.sun.glass.events.KeyEvent;

public class SoftKeyBoardPopups4 extends JPopupMenu {
	/**
	 * 
	 */
	static Logger logger = Logger.getLogger(SoftKeyBoardPopups4.class);
	private static final long serialVersionUID = 1L;
	private static Color transparentColor = new Color(255, 255, 255, 0);
	// 键盘大小设置
	private static Dimension popupSize = new Dimension(730, 260);
	private static Color backColor = new Color(23, 127, 194);
	private static Random random = new Random();
	public static SoftKeyBoardPanel accGetKeyBoredPanel;
	private static Robot robot;
	public SoftKeyBoardPopups4(JTextField textField) {
		logger.info("进入金额键盘类");
		accGetKeyBoredPanel = new SoftKeyBoardPanel(textField, this);
		accGetKeyBoredPanel.setBackground(Color.WHITE);
		accGetKeyBoredPanel.setBorder(BorderFactory.createEmptyBorder());
		add(accGetKeyBoredPanel);
		accGetKeyBoredPanel.setLayout(new MigLayout("", "[]", "[]"));
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));// 空边框
		setOpaque(false);// 透明
	}

	public static void gc() {
		popupSize = null;
		backColor = null;
		random = null;
		System.gc();
	}

	public static void resetValue() {
		popupSize = new Dimension(380, 110);
		backColor = new Color(23, 127, 194);
		random = new Random();
	}

	public SoftKeyBoardPanel getSoftKeyBoardPanel() {
		return accGetKeyBoredPanel;
	}

	// 软键盘面板
	public static class SoftKeyBoardPanel extends JPanel implements
			ActionListener {
		JTextField passwordField;

		JPopupMenu popupMenu;
		RowPanel[] rows;
		KeyStatus status = KeyStatus.normal;
		Paint[] paints = new Paint[] { new Color(70, 67, 114),
				new Color(62, 192, 238), new Color(138, 180, 231) };

		public SoftKeyBoardPanel(JTextField textField, JPopupMenu popupMenu) {
			this.passwordField = textField;
			this.popupMenu = popupMenu;
			initSoftKeyBoardPanel();
		}
		
		
		// 初始化
		private void initSoftKeyBoardPanel() {
			setLayout(null);
			setBackground(backColor);

			JPanel proxyPanel = new JPanel(new GridLayout(4, 1, 0, 0));// 4行一列，0水平间隙，1垂直间隙
			proxyPanel.setBackground(backColor);
			proxyPanel.setLocation(3, 4);
			proxyPanel.setSize(popupSize.width - 6, popupSize.height - 7);
			add(proxyPanel);

			rows = new RowPanel[] { new RowPanel(RowType.first),
					new RowPanel(RowType.second), new RowPanel(RowType.third),
					new RowPanel(RowType.fourth) };
			for (int i = 0; i < rows.length; i++) {
				proxyPanel.add(rows[i]);
			}
			
		}

		// 重写paint绘制想要的效果
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;
			ImageTool.setAntiAliasing(g2d);// 抗锯齿
			ImageTool.drawRoundRect(g2d, getWidth(), getHeight(), 0, null,
					paints);// 绘制软键盘成圆角和多层颜色边框
		}

		// 处理所有软键盘点击事件
		@Override
		public void actionPerformed(ActionEvent e) {
			KeyLable keyLable = (KeyLable) e.getSource();
			if (keyLable.isBackSpace()) {
				clickBackSpace();
			} else if (keyLable.isCommKey()) {
				String key;
				if (status == KeyStatus.shift
						|| status == KeyStatus.shiftAndCapsLock) {
					key = keyLable.getCenterKey();
				} else if (status == KeyStatus.normal
						|| status == KeyStatus.capsLock) {
					key = keyLable.getLowerLeftKey() == null ? keyLable
							.getCenterKey() : keyLable.getLowerLeftKey();
				} else {
					key = "";
				}
				JTextField a = clickCommKey(key);
				String cs = a.getText();
				char[] password1 = cs.toCharArray();
				String psw = new String(password1);
				scanBill1(a);

			}
		}

		/***
		 * 返回值
		 * 
		 * @return
		 */
		private JTextField scanBill1(JTextField a) {

			return passwordField;
		}

		// 通知KeyLabel更新状态
		public void notifyKeyLabel() {
			for (RowPanel rowPanel : rows) {
				for (KeyLable keyLable : rowPanel.getKeys()) {
					keyLable.setStatus(status);
				}
			}
		}

		// 重置键盘， 清除按压状态，并将键盘恢复至初始状态
		public JTextField reset() {
			for (RowPanel rowPanel : rows) {
				for (KeyLable keyLable : rowPanel.getKeys()) {
					keyLable.reset();
				}
			}
			status = KeyStatus.normal;
			return passwordField;
		}

		// 点击了删除键， 删除一个字符
		public JTextField clickBackSpace() {
			try{
				logger.info("点击删除");
				robot=new Robot();
				robot.keyPress(KeyEvent.VK_BACKSPACE);
    			robot.keyRelease(KeyEvent.VK_BACKSPACE);
			}catch(AWTException e){
				logger.error("点击删除异常"+e);
			}
				
			return passwordField;
		}

		

		// 点击了普通的键，添加一个字符
		public JTextField clickCommKey(String key) {
			String text=passwordField.getText();
			text=StringUtils.full2Half(text);
			passwordField.setText(text);
			if("0".equals(text)&&key.matches("^[0-9]*[0-9][0-9]*$")){
				return passwordField;
			}
			if(text.contains(".")){
				if(text.startsWith(".")&&text.length()<3){
					Dispatch.call(BaseLoginFrame.dispath1,"InterFaceKeyEvent",key,"");
					logger.info("点击"+key);
					return passwordField;
				}else if(text.startsWith(".")&&text.length()>=3){
					return passwordField;
				}
				if(".".equals(key)){
					return passwordField;
				}
				String[] split = text.split("\\.");
				if(split.length==2){
					if(split[0].matches("^[0-9]*[0-9][0-9]*$") && (split[1].matches("^[0-9]*[0-9][0-9]*$"))){//整数、小数都为数字
						
					}else{
						Dispatch.call(BaseLoginFrame.dispath1,"InterFaceKeyEvent",key,"");
						logger.info("点击"+key);
						return passwordField;
					}
					if(split[1].length()==1){
						Dispatch.call(BaseLoginFrame.dispath1,"InterFaceKeyEvent",key,"");
						logger.info("点击"+key);
						return passwordField;
					}else if(split[1].length()>=2){
						return passwordField;
					}
				}else if(split.length<2){
				
					Dispatch.call(BaseLoginFrame.dispath1,"InterFaceKeyEvent",key,"");
					logger.info("点击"+key);
					return passwordField;
					
				}
			}else{
				if("".equals(text)&&".".equals(key)){
						return passwordField;
					}
				if(text.contains("。")){
					text=text.replaceAll("。", ".");
					passwordField.setText(text);
					if(".".equals(key)){
						return passwordField;
					}
					Dispatch.call(BaseLoginFrame.dispath1,"InterFaceKeyEvent",key,"");
					logger.info("点击"+key);
					return passwordField;
				}
				Dispatch.call(BaseLoginFrame.dispath1,"InterFaceKeyEvent",key,"");
				logger.info("点击"+key);
				return passwordField;
			}
			return passwordField;
		}

		public RowPanel[] getRows() {
			return rows;
		}

		public class RowPanel extends JPanel {
			RowType rowType;
			KeyLable[] keys;

			public RowPanel(RowType rowType) {
				this.rowType = rowType;
				initRowPanel();
			}

			private void initRowPanel() {
				setOpaque(true);
				setLayout(new FlowLayout(FlowLayout.CENTER, 1, 0));// 水平间隙1，垂直间隙0
				setBackground(backColor);
				if (rowType == RowType.first) {

					KeyLable key1 = new KeyLable("1", SoftKeyBoardPanel.this);
					KeyLable key2 = new KeyLable("2", SoftKeyBoardPanel.this);
					KeyLable key3 = new KeyLable("3", SoftKeyBoardPanel.this);

					keys = new KeyLable[] { key1, key2, key3 };
					ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(
							keys.length);

					for (KeyLable key : keys) {
						keylist.add(key);
					}

					for (KeyLable key : keylist) {
						this.add(key);
					}

				} else if (rowType == RowType.second) {

					KeyLable key1 = new KeyLable("4", SoftKeyBoardPanel.this);
					KeyLable key2 = new KeyLable("5", SoftKeyBoardPanel.this);
					KeyLable key3 = new KeyLable("6", SoftKeyBoardPanel.this);
					keys = new KeyLable[] { key1, key2, key3 };
					ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(
							keys.length);
					for (KeyLable key : keys) {
						keylist.add(key);
					}
					for (KeyLable key : keylist) {
						this.add(key);
					}
				} else if (rowType == RowType.third) {

					KeyLable key1 = new KeyLable("7", SoftKeyBoardPanel.this);
					KeyLable key2 = new KeyLable("8", SoftKeyBoardPanel.this);
					KeyLable key3 = new KeyLable("9", SoftKeyBoardPanel.this);

					keys = new KeyLable[] { key1, key2, key3 };
					ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(
							keys.length);
					for (KeyLable key : keys) {
						keylist.add(key);
					}
					for (KeyLable key : keylist) {
						this.add(key);
					}

				} else if (rowType == RowType.fourth) {
					//功能键删除
					KeyLable key1 = new KeyLable("删除", true,SoftKeyBoardPanel.this);
					KeyLable key2 = new KeyLable("0", SoftKeyBoardPanel.this);
					// 功能键确认
					KeyLable key3 = new KeyLable(".", SoftKeyBoardPanel.this);

					keys = new KeyLable[] { key1, key2, key3 };
					ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(
							keys.length);
					for (KeyLable key : keys) {
						keylist.add(key);
					}
					for (KeyLable key : keylist) {
						this.add(key);
					}
				}
			}

			public KeyLable[] getKeys() {
				return keys;
			}
		}

	}

	// 键标签
	public static class KeyLable extends JLabel {

		// 用String而不是char考虑有功能键显示的是文字，不想再多一个字段了
		String centerKey;
		String lowerLeftKey;
		boolean isBackSpace;
		boolean isPressed;

		KeyStatus status = KeyStatus.normal;
		// 按钮大小设置
		Dimension size = new Dimension(87, 65);
		Color keyBorderColor = new Color(54, 112, 184);
		Color keyBorderFocusColor = new Color(64, 194, 241);
		Color keyBackColor = new Color(253, 255, 255);
		Color keyBackFocusColor = new Color(28, 159, 228);
		// 按钮字体大小 小写
		Font boldFont = new Font("微软雅黑", Font.PLAIN, 30);
		Color boldColor = new Color(0, 0, 57);
		// 按钮字体大小 大写
		Font plainFont = new Font("微软雅黑", Font.PLAIN, 30);
		Color plainColor = new Color(156, 157, 197);

		public KeyLable(String centerKey, ActionListener action) {
			this(centerKey, null, action);
		}

		
		public KeyLable(String centerKey, String lowerLeftKey,
				ActionListener action) {
			this(centerKey, lowerLeftKey, false, action);
		}

		public KeyLable(String centerKey, boolean isFunctionKey,
				ActionListener action) {
			this(centerKey, null, isFunctionKey, action);
		}

		public KeyLable(String centerKey, String lowerLeftKey,
				boolean isFunctionKey, final ActionListener action) {
			this.centerKey = centerKey;
			this.lowerLeftKey = lowerLeftKey;
			if (isFunctionKey) {// 这个变量主要是提高效率
				if (centerKey.indexOf("删") >= 0 || centerKey.indexOf("除") >= 0) {
					isBackSpace = true;
				}
			}

			setOpaque(true);// 不透明
			setBackground(keyBackColor);
			setPreferredSize(size);
			setBorder(BorderFactory.createLineBorder(keyBorderColor));
			setFont(boldFont);
			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					KeyLable.this.setBackground(keyBackFocusColor);// 鼠标悬浮时的背景色
				}

				public void mouseExited(MouseEvent e) {
					if (!isPressed) {
						KeyLable.this.setBackground(keyBackColor);
					}
				}

				public void mouseClicked(MouseEvent e) {
					// 创建一个ActionEvent将KeyLable对象作为Source
					action.actionPerformed(new ActionEvent(KeyLable.this,
							ActionEvent.ACTION_PERFORMED, e.getID() + ""));
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);// 完成背景色的绘制

			Graphics2D g2d = (Graphics2D) g;
			ImageTool.setAntiAliasing(g2d);// 抗锯齿
			Container parent = getParent();
			ImageTool.clearAngle(g2d, parent != null ? parent.getBackground()
					: this.getBackground(), this.getWidth(), this.getHeight(),
					4);// 清除角落变圆角

			if (getMousePosition() != null) {// 如果鼠标正在这个键的范围内，绘制圆角边框
				g2d.setPaint(keyBorderFocusColor);
				// 字体外框
				g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 4, 4);
			}

			if (status == KeyStatus.normal || status == KeyStatus.capsLock) {
				if (lowerLeftKey == null) {
					g2d.setFont(boldFont);
					g2d.setPaint(boldColor);
					// 设置字体大小
					SwingUtilities2.drawStringUnderlineCharAt(this, g2d,
							centerKey, -1, isCommKey() ? 40 : 15, 50);

				} else {
					g2d.setFont(plainFont);
					g2d.setPaint(plainColor);
					SwingUtilities2.drawStringUnderlineCharAt(this, g2d,
							centerKey, -1, isCommKey() ? 25 : 4, 20);

					g2d.setFont(boldFont);
					g2d.setPaint(boldColor);
					SwingUtilities2.drawStringUnderlineCharAt(this, g2d,
							lowerLeftKey, -1, isCommKey() ? 12 : 4, 35);
				}
			} else if (status == KeyStatus.shift
					|| status == KeyStatus.shiftAndCapsLock) {
				if (lowerLeftKey == null) {
					g2d.setFont(boldFont);
					g2d.setPaint(boldColor);
					SwingUtilities2.drawStringUnderlineCharAt(this, g2d,
							centerKey, -1, isCommKey() ? 18 : 4, 30);

				} else {
					g2d.setFont(boldFont);
					g2d.setPaint(boldColor);
					SwingUtilities2.drawStringUnderlineCharAt(this, g2d,
							centerKey, -1, isCommKey() ? 25 : 4, 20);

					g2d.setFont(plainFont);
					g2d.setPaint(plainColor);
					SwingUtilities2.drawStringUnderlineCharAt(this, g2d,
							lowerLeftKey, -1, isCommKey() ? 12 : 4, 35);
				}
			}
		}

		public String getCenterKey() {
			return centerKey;
		}

		public String getLowerLeftKey() {
			return lowerLeftKey;
		}

		public boolean isBackSpace() {
			return isBackSpace;
		}
		

		public void setPressed(boolean isPressed) {
			this.isPressed = isPressed;
		}

		public boolean isPressed() {
			return isPressed;
		}

		public boolean isCommKey() {
			return !isBackSpace  ;
		}

		

		// 重置
		public void reset() {
			this.isPressed = false;
			if (isCommKey()) {
				if (lowerLeftKey == null) {
					centerKey = centerKey.toLowerCase();
				}
			}
			status = KeyStatus.normal;
			repaint();
		}

		// 设置状态
		public void setStatus(KeyStatus status) {
			if (isCommKey() && this.status != status) {
				if (status == KeyStatus.shift || status == KeyStatus.capsLock) {
					if (lowerLeftKey == null) {
						if (Character.isUpperCase(centerKey.charAt(0))) {
							centerKey = centerKey.toLowerCase();
						} else {
							centerKey = centerKey.toUpperCase();
						}
					}
				} else if (status == KeyStatus.normal
						|| status == KeyStatus.shiftAndCapsLock) {
					if (lowerLeftKey == null) {
						centerKey = centerKey.toLowerCase();
					}
				}
				this.status = status;
				repaint();
			}
		}
	}

	public static enum RowType {
		first, second, third, fourth
	}

	public static enum KeyStatus {
		normal, shift, capsLock, shiftAndCapsLock
	}
}


	

