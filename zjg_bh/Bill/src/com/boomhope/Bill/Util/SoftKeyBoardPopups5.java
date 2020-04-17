package com.boomhope.Bill.Util;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import sun.swing.SwingUtilities2;

import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.sun.glass.events.KeyEvent;
 //不加密全键盘 
//软键盘弹出菜单  
public class SoftKeyBoardPopups5 extends JPopupMenu {  
    private static final long serialVersionUID = 1L; 
    static Logger logger = Logger.getLogger(SoftKeyBoardPopups5.class);
	static Robot robot;
	static int length;
    public static void main(String[] args) {  
  
        final JFrame frame = new JFrame();  
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); 
        //关闭窗口后台关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setSize(500, 500);  
        //窗口显示在中央
        frame.setLocationRelativeTo(null);  
        //输入框
        final JPanel passwordPanel = new JPanel(new BorderLayout());  
        passwordPanel.setBackground(Color.WHITE);  
        passwordPanel.setPreferredSize(new Dimension(202, 30));  
        passwordPanel.setLayout(new BorderLayout());  
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));  
  
        frame.add(passwordPanel);  
  
        final JPasswordField password = new JPasswordField();  
        password.setSelectedTextColor(Color.BLACK);// 颜色障眼法，产生不能去选中密码框的任何东西的幻觉，其实已经选中了但你不知道  
        password.setSelectionColor(Color.WHITE);// 颜色障眼法，产生不能去选中密码框的任何东西的幻觉，其实已经选中了但你不知道  
        password.setForeground(Color.BLACK);  
        password.setFont(password.getFont().deriveFont(22f));  
        // password.setEchoChar('●');  
        password.setBorder(new EmptyBorder(5, 3, 0, 3));// 左间隙  
        passwordPanel.add(password, BorderLayout.CENTER);  
  
        final SoftKeyBoardPopups keyPopup = new SoftKeyBoardPopups(password,5);  
        final JLabel keyBoard = new JLabel("软件盘");  
        keyBoard.setOpaque(true);  
        keyBoard.setBackground(Color.WHITE);  
        keyBoard.setBorder(new EmptyBorder(0, 0, 0, 0));  
        keyBoard.setToolTipText("打开软键盘");  
        keyBoard.setPreferredSize(new Dimension(42, 23));  
        keyBoard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  
  
        keyBoard.addMouseListener(new MouseAdapter() {  
            public void mouseClicked(MouseEvent e) {  
                if (!keyPopup.isVisible()) {  
                    keyPopup.show(passwordPanel, 0, passwordPanel.getPreferredSize().height);  
                    keyPopup.getSoftKeyBoardPanel().reset();  
                    keyPopup.repaint();
             
                }  
            }  
        });  
        passwordPanel.add(keyBoard, BorderLayout.EAST);  
  
        frame.setVisible(true);
       
    }  
  
    private static Color transparentColor = new Color(255, 255, 255, 0);  
    // private static Dimension popupSize = new Dimension(360, 110);//QQ软键盘大小  
    //键盘大小设置
    private static Dimension popupSize = new Dimension(730, 220);  
    private static Color backColor = new Color(23, 127, 194);  
    private static Random random = new Random();  
  
    protected SoftKeyBoardPanel softKeyBoardPanel;  
  
    public SoftKeyBoardPopups5(JTextField textField,int lengths) {  
    	logger.info("中文键盘类");
        softKeyBoardPanel = new SoftKeyBoardPanel(textField, this);  
        softKeyBoardPanel.setPreferredSize(popupSize);  
        softKeyBoardPanel.setBorder(BorderFactory.createEmptyBorder());  
        length = lengths;
        add(softKeyBoardPanel);  
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));// 空边框  
        setOpaque(false);// 透明  
    }  
    public void close(){
    	softKeyBoardPanel.removeAll();
    	softKeyBoardPanel.setVisible(false);
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
        return softKeyBoardPanel;  
    }  
  
    // 软键盘面板  
    public static class SoftKeyBoardPanel extends JPanel implements ActionListener {  
//    	private JTextField textField;
    	JTextField passwordField;  
		JPopupMenu popupMenu;  
        RowPanel[] rows;  
        KeyStatus status = KeyStatus.normal;  
        Paint[] paints = new Paint[] { new Color(70, 67, 114), new Color(62, 192, 238), new Color(138, 180, 231) };  
  
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
  
            rows = new RowPanel[] { new RowPanel(RowType.first), new RowPanel(RowType.second), new RowPanel(RowType.third), new RowPanel(RowType.fourth) };  
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
            ImageTool.drawRoundRect(g2d, getWidth(), getHeight(), 0, null, paints);// 绘制软键盘成圆角和多层颜色边框  
        }  
  
        // 处理所有软键盘点击事件  
        @Override  
        public void actionPerformed(ActionEvent e) {  
            KeyLable keyLable = (KeyLable) e.getSource();  
            if (keyLable.isShift() || keyLable.isCapsLock()) {  
                boolean pressed = keyLable.isPressed();  
                if (keyLable.isShift()) {  
                    clickShift();  
                } else if (keyLable.isCapsLock()) {  
                	clickCapsLock();  
                } 
                pressed = !pressed;  
                keyLable.setPressed(pressed);  
                notifyKeyLabel();  
            }else if(keyLable.isJia()){
            	clickJia();
            }else if (keyLable.isEsc()) {
				clickEsc();
			} else if(keyLable.isLeft()){
            	clickLeft();
            }else if(keyLable.isUsRight()){
            	clickRight();
            }else if(keyLable.isL()){
            	clickL();
            }else if(keyLable.isJian()){
            	clickJian();
            }else if (keyLable.isBackSpace()) {  
                clickBackSpace();  
            } else if (keyLable.isCommKey()) {  
                String key;  
                if (status == KeyStatus.shift || status == KeyStatus.shiftAndCapsLock) {  
                    key = keyLable.getCenterKey();  
                } else if (status == KeyStatus.normal || status == KeyStatus.capsLock) {  
                	key = keyLable.getLowerLeftKey() == null ? keyLable.getCenterKey() : keyLable.getLowerLeftKey();  
                } else if(status == KeyStatus.capsLock){
                	key=keyLable.getCenterKey().toUpperCase();
                }else {  
                    key = "";  
                }  
                JTextField a= clickCommKey(key,length);
               String cs = a.getText();
               char[] password1=cs.toCharArray();
               String psw = new String(password1);
               System.out.println("66"+psw);
               scanBill1(a);
           
            }
        }  
        //点击加号
        public void clickJia() {
        	try {
        		logger.info("点击加号");
				robot=new Robot();
				robot.keyPress(KeyEvent.VK_EQUALS);
				robot.keyRelease(KeyEvent.VK_EQUALS);
			} catch (AWTException e) {
				logger.error("点击加号异常"+e);
			}
			
		}
        //点击左键头
        public void clickLeft(){
        	try {
        		logger.info("点击左箭头");
				robot=new Robot();
				robot.keyPress(KeyEvent.VK_LEFT);
				robot.keyRelease(KeyEvent.VK_LEFT);
			} catch (AWTException e) {
				logger.error("点击左箭头异常"+e);
				
			}
        }
        //点击右键头
        public void clickRight(){
        	try {
        		logger.info("点击右箭头");
				robot=new Robot();
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
			} catch (AWTException e) {
				logger.error("点击右箭头异常"+e);
				
			}
        }
		//点击-号
    	public void clickJian() {
			try {
				logger.info("点击减号");
				robot=new Robot();
				robot.keyPress(KeyEvent.VK_MINUS);
				robot.keyRelease(KeyEvent.VK_MINUS);
			} catch (AWTException e) {
				logger.error("点击减号异常"+e);
			}
			
		}
    	//点击l
    	public void clickL(){
    		try {
    			logger.info("点击字母l");
				robot=new Robot();
				robot.keyPress(KeyEvent.VK_L);
	    		robot.keyRelease(KeyEvent.VK_L);
			} catch (AWTException e) {
				logger.error("点击字母l异常"+e);
			}
    		
    	}
		/***
    	 * 返回值
    	 * @return 
    	 */
    	private JTextField scanBill1(JTextField a){
    		
    		return passwordField;
    	}
//         通知KeyLabel更新状态  
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
        
        // 更改状态  
        public void clickShift() {  
          try {
        	  logger.info("点击切换");
			robot=new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SPACE);
			robot.keyRelease(KeyEvent.VK_SPACE);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
			logger.error("点击切换异常"+e);
		}
        }  
        
        // 更改状态  
        public void clickCapsLock() { 
        	logger.info("点击大写");
        	 if (status == KeyStatus.capsLock) {  
                 status = KeyStatus.normal;  
             } else if (status == KeyStatus.normal) {  
                 status = KeyStatus.capsLock;  
             } else {  
                 status = KeyStatus.normal;  
             }  
        	
        	
        }  
        
        // 点击了删除键， 删除一个字符  
        public JTextField clickBackSpace() {  
        	try {
        		logger.info("点击删除");
				robot=new Robot();
				String ch = passwordField.getText();
        		char[] password=ch.toCharArray();
        		if (password != null && password.length > 0) {  
        			char[] copyOf = Arrays.copyOf(password, password.length - 1);  
        			System.out.println("已删除的字符：" + password[password.length - 1]);  
        			System.out.println("删除后的的密码：" + new String(copyOf)); 
        			robot.keyPress(KeyEvent.VK_BACKSPACE);
        			robot.keyRelease(KeyEvent.VK_BACKSPACE);
        			String cs = passwordField.getText();
        			char[] password1=cs.toCharArray();
        			String psw = new String(password1);
        			System.out.println("password1：" + psw);
        		}  
			} catch (AWTException e) {
				logger.info("点击删除异常"+e);
			}
        	
        	return passwordField;  
        }  
     // 点击了退出键，将输入的值写入，收起键盘
		public void clickEsc() {
			try {
				logger.info("点击退出");
				Robot robot=new Robot();
				robot.keyPress(KeyEvent.VK_ESCAPE);
				robot.keyRelease(KeyEvent.VK_ESCAPE);
				
			} catch (AWTException e) {
				logger.error("点击退出异常"+e);
			}
			
			
		}
        // 点击了普通的键，添加一个字符  
        public JTextField clickCommKey(String key,int length) { 
        	if(status == KeyStatus.capsLock){
            	String s=passwordField.getText();
            	if(s!=null&&s.length()==length){
            		return passwordField;
            	}
                char[] password=s.toCharArray();
                String string = (password == null ? "" : new String(password));  
                passwordField.setText(string + key);  
                return passwordField;
        	}
        	String s=passwordField.getText();
        	if(s!=null&&s.length()==length){
        		return passwordField;
        	}
        	if("(".equals(key)){
        		String text=passwordField.getText() ;
        		text=text+"(";
        		passwordField.setText(text);
        		return passwordField;
        	}
        	if(")".equals(key)){
        		String text=passwordField.getText() ;
        		text=text+")";
        		passwordField.setText(text);
        		return passwordField;
        	}
        	
            Dispatch.call(BaseLoginFrame.dispath1,"InterFaceKeyEvent",key,"");
            logger.info("点击"+key);
			return passwordField;  
        }  
  
        public RowPanel[] getRows() {  
            return rows;  
        }  
  
        // 创建关闭图标  
        public Image createCloseImage(Color fontColor, boolean isFocus) {  
            int width = 12;  
            BufferedImage bi = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);  
            Graphics2D g2d = bi.createGraphics();  
  
            ImageTool.setAntiAliasing(g2d);  
  
            // 画背景  
            g2d.setPaint(transparentColor);  
            g2d.fillRect(0, 0, width, width);  
  
            int[] xpoints_1 = { 2, 4, 8, 10 };  
            int[] ypoints_1 = { 2, 2, 10, 10 };  
            int npoints_1 = 4;  
            Polygon p_left = new Polygon(xpoints_1, ypoints_1, npoints_1);// 左上角到右下角图标  
  
            int[] xpoints_2 = xpoints_1;  
            int[] ypoints_2 = { 10, 10, 2, 2 };  
            int npoints_2 = 4;  
            Polygon p_right = new Polygon(xpoints_2, ypoints_2, npoints_2);// 右上角到左下角图标  
  
            if (isFocus) {  
                g2d.setPaint(new GradientPaint(0, 0, fontColor, 0, width, new Color(fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue(), 50)));  
            } else {  
                g2d.setPaint(fontColor);  
            }  
            // 画关闭图标("x")  
            g2d.fillPolygon(p_left);  
            g2d.fillPolygon(p_right);  
  
            return bi;  
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
  
//                    KeyLable key1 = new KeyLable("!", "1", SoftKeyBoardPanel.this);  
//                    KeyLable key2 = new KeyLable("@", "2", SoftKeyBoardPanel.this);  
//                    KeyLable key3 = new KeyLable("#", "3", SoftKeyBoardPanel.this);  
//                    KeyLable key4 = new KeyLable("$", "4", SoftKeyBoardPanel.this);  
//                    KeyLable key5 = new KeyLable("%", "5", SoftKeyBoardPanel.this);  
//                    KeyLable key6 = new KeyLable("^", "6", SoftKeyBoardPanel.this);  
//                    KeyLable key7 = new KeyLable("&", "7", SoftKeyBoardPanel.this);  
//                    KeyLable key8 = new KeyLable("*", "8", SoftKeyBoardPanel.this);  
//                    KeyLable key9 = new KeyLable("(", "9", SoftKeyBoardPanel.this);  
//                    KeyLable key10 = new KeyLable(")", "0", SoftKeyBoardPanel.this);  
//                    KeyLable key11 = new KeyLable("~", "`", SoftKeyBoardPanel.this);// 这个键的位置随机  
//                    KeyLable key12 = new KeyLable("BackSpace", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最右  
//                    //BackSpace按钮设置大小
//                    key12.setPreferredSize(new Dimension(140, 50));  
//  
//                    keys = new KeyLable[] { key4, key5, key6, key7, key8, key9, key10, key11, key1, key2, key3, key12 };  
//                    ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
//  
//                    for (KeyLable key : keys) {  
//                        if (key != key11) {// key11排除在外  
//                            keylist.add(key);  
//                        }  
//                    }  
//  
//                    int randomIndex = random.nextInt(keys.length - 1);// 排除最后一个留给key12的位置  
//                    keylist.add(randomIndex, key11);  
//  
//                    for (KeyLable key : keylist) {  
//                        this.add(key);  
//                    }  
  
                    KeyLable key1 = new KeyLable( "1", SoftKeyBoardPanel.this);  
                    KeyLable key2 = new KeyLable( "2", SoftKeyBoardPanel.this);  
                    KeyLable key3 = new KeyLable( "3", SoftKeyBoardPanel.this);  
                    KeyLable key4 = new KeyLable( "4", SoftKeyBoardPanel.this);  
                    KeyLable key5 = new KeyLable( "5", SoftKeyBoardPanel.this);  
                    KeyLable key6 = new KeyLable( "6", SoftKeyBoardPanel.this);  
                    KeyLable key7 = new KeyLable( "7", SoftKeyBoardPanel.this);  
                    KeyLable key8 = new KeyLable( "8", SoftKeyBoardPanel.this);  
                    KeyLable key9 = new KeyLable( "9", SoftKeyBoardPanel.this);  
                    KeyLable key10 = new KeyLable( "0", SoftKeyBoardPanel.this);  
                    KeyLable key11 = new KeyLable(" 退   出",true, SoftKeyBoardPanel.this);// 这个键的位置随机  
                    key11.setPreferredSize(new Dimension(100, 50));
                    //BackSpace按钮设置大小
  
//                    keys = new KeyLable[] { key4, key5, key6, key7, key8, key9, key10, key11, key1, key2, key3, key12 };  
//                    ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
  
                    keys = new KeyLable[] { key11, key1, key2, key3, key4, key5, key6, key7, key8, key9, key10 };  
                    ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length); 
                    
                    for (KeyLable key : keys) {  
//                        if (key != key11) {// key11排除在外  
                            keylist.add(key);  
//                        }  
                    }  
  
//                    int randomIndex = random.nextInt(keys.length - 1);// 排除最后一个留给key12的位置  
//                    keylist.add(randomIndex, key11);  
  
                    for (KeyLable key : keylist) {  
                        this.add(key);  
                    }
                    // 关闭Label  
                    final Image defaImage = createCloseImage(new Color(138, 180, 231), false);  
                    final Image focusImage = createCloseImage(new Color(30, 90, 150), true);  
                    final JLabel closeLabel = new JLabel(new ImageIcon(defaImage)) {  
                        JToolTip toolTip;  
  
                        protected void paintComponent(Graphics g) {  
                            super.paintComponent(g);  
                            ImageIcon icon = (ImageIcon) getIcon();  
                            if (icon != null) {  
                                g.drawImage(icon.getImage(), 0, 0, 12, 12, 0, 0, 12, 12, null);  
                            }  
                        }  
  
                        public JToolTip createToolTip() {  
                            JToolTip toolTip = new JToolTip();  
                            Color color = new Color(118, 118, 118);  
  
                            toolTip.setComponent(this);  
                            toolTip.setTipText(this.getToolTipText());  
                            toolTip.setBackground(Color.WHITE);  
                            toolTip.setForeground(color);  
                            toolTip.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));  
  
                            Border outside = BorderFactory.createLineBorder(color);  
                            Border inside = BorderFactory.createEmptyBorder(2, 3, 2, 3);  
                            CompoundBorder border = BorderFactory.createCompoundBorder(outside, inside);  
                            toolTip.setBorder(border);  
                            return toolTip;  
                        }  
                    };  
                    MouseAdapter mouseAdapter = new MouseAdapter() {  
                        public void mouseClicked(MouseEvent e) {  
                            popupMenu.setVisible(false);  
                        }  
  
                        public void mouseEntered(MouseEvent e) {  
                            closeLabel.setIcon(new ImageIcon(focusImage));  
                        }  
  
                        public void mouseExited(MouseEvent e) {  
                            closeLabel.setIcon(new ImageIcon(defaImage));  
                        }  
                    };  
                    closeLabel.setToolTipText("关闭");  
                    closeLabel.addMouseListener(mouseAdapter);  
                    closeLabel.setPreferredSize(new Dimension(12, 12));  
                    add(closeLabel);  
                } else if (rowType == RowType.second) {  
                    // key1至key10是一个闭环顺序，通过随机数决定在哪个位置切断这个环从而使其变成单链  
//                    KeyLable key1 = new KeyLable("+", "=", SoftKeyBoardPanel.this);  
//                    KeyLable key2 = new KeyLable("|", "\\", SoftKeyBoardPanel.this);  
//                    KeyLable key3 = new KeyLable("{", "[", SoftKeyBoardPanel.this);  
//                    KeyLable key4 = new KeyLable("}", "]", SoftKeyBoardPanel.this);  
//                    KeyLable key5 = new KeyLable(":", ";", SoftKeyBoardPanel.this);  
//                    KeyLable key6 = new KeyLable("\"", "'", SoftKeyBoardPanel.this);  
//                    KeyLable key7 = new KeyLable("<", ",", SoftKeyBoardPanel.this);  
//                    KeyLable key8 = new KeyLable(">", ".", SoftKeyBoardPanel.this);  
//                    KeyLable key9 = new KeyLable("?", "/", SoftKeyBoardPanel.this);  
//                    KeyLable key10 = new KeyLable("_", "-", SoftKeyBoardPanel.this);  
//                    KeyLable key11 = new KeyLable("Shift", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最左  
//                    //Shift按钮设置大小
//                    key11.setPreferredSize(new Dimension(70, 50));  
//                    KeyLable key12 = new KeyLable("CapsLock", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最右  
//                    //CapsLock按钮设置大小
//                    key12.setPreferredSize(new Dimension(130, 50));  
//  
//                    keys = new KeyLable[] { key11, key2, key3, key4, key5, key6, key7, key8, key9, key10, key1, key12 };  
//                    ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
//                    int randomIndex = random.nextInt(keys.length - 2) + 1;// 随机切入点，排除key11和key12的位置  
//  
//                    keylist.add(key11);  
//                    for (int i = randomIndex; i < keys.length - 1; i++) {  
//                        keylist.add(keys[i]);  
//                    }  
//                    for (int i = 1; i < randomIndex; i++) {  
//                        keylist.add(keys[i]);  
//                    }  
//                    keylist.add(key12);  
//  
//                    for (KeyLable key : keylist) {  
//                        this.add(key);  
//                    } 
                    KeyLable key1 = new KeyLable("q", SoftKeyBoardPanel.this);  
                    KeyLable key2 = new KeyLable("w", SoftKeyBoardPanel.this);  
                    KeyLable key3 = new KeyLable("e", SoftKeyBoardPanel.this);  
                    KeyLable key4 = new KeyLable("r", SoftKeyBoardPanel.this);  
                    KeyLable key5 = new KeyLable("t", SoftKeyBoardPanel.this);  
                    KeyLable key6 = new KeyLable("y", SoftKeyBoardPanel.this);  
                    KeyLable key7 = new KeyLable("u", SoftKeyBoardPanel.this);  
                    KeyLable key8 = new KeyLable("i", SoftKeyBoardPanel.this);  
                    KeyLable key9 = new KeyLable("o", SoftKeyBoardPanel.this);  
                    KeyLable key10 = new KeyLable("p", SoftKeyBoardPanel.this);                 
                    KeyLable key11 = new KeyLable("  +",true, SoftKeyBoardPanel.this);   
                    KeyLable key12 = new KeyLable("  -", true, SoftKeyBoardPanel.this);  
//                    KeyLable key13 = new KeyLable("|", "\\", SoftKeyBoardPanel.this);
                    key11.setPreferredSize(new Dimension(50, 48));
                    key12.setPreferredSize(new Dimension(50, 48));
                 keys = new KeyLable[] { key1, key2, key3, key4, key5, key6, key7, key8, key9, key10, key11, key12 };  
                  ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
                    for (KeyLable key : keys) {  
                          keylist.add(key);  
                  }  
                  for (KeyLable key : keylist) {  
                      this.add(key);  
                  } 
                } else if (rowType == RowType.third) {  
                    // key1至key13是一个闭环顺序，通过随机数决定在哪个位置切断这个环从而使其变成单链  
//                    KeyLable key1 = new KeyLable("c", SoftKeyBoardPanel.this);  
//                    KeyLable key2 = new KeyLable("d", SoftKeyBoardPanel.this);  
//                    KeyLable key3 = new KeyLable("e", SoftKeyBoardPanel.this);  
//                    KeyLable key4 = new KeyLable("f", SoftKeyBoardPanel.this);  
//                    KeyLable key5 = new KeyLable("g", SoftKeyBoardPanel.this);  
//                    KeyLable key6 = new KeyLable("h", SoftKeyBoardPanel.this);  
//                    KeyLable key7 = new KeyLable("i", SoftKeyBoardPanel.this);  
//                    KeyLable key8 = new KeyLable("j", SoftKeyBoardPanel.this);  
//                    KeyLable key9 = new KeyLable("k", SoftKeyBoardPanel.this);  
//                    KeyLable key10 = new KeyLable("l", SoftKeyBoardPanel.this);  
//                    KeyLable key11 = new KeyLable("m", SoftKeyBoardPanel.this);  
//                    KeyLable key12 = new KeyLable("a", SoftKeyBoardPanel.this);  
//                    KeyLable key13 = new KeyLable("b", SoftKeyBoardPanel.this);  
//  
//                    keys = new KeyLable[] { key1, key2, key3, key4, key5, key6, key7, key8, key9, key10, key11, key12, key13 };  
//                    ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
//                    int randomIndex = random.nextInt(keys.length);// 随机切入点  
//  
//                    for (int i = randomIndex; i < keys.length; i++) {  
//                        keylist.add(keys[i]);  
//                    }  
//                    for (int i = 0; i < randomIndex; i++) {  
//                        keylist.add(keys[i]);  
//                    }  
//  
//                    for (KeyLable key : keylist) {  
//                        this.add(key);  
//                    } 
                	 KeyLable key10 = new KeyLable("大 写 ",true, SoftKeyBoardPanel.this);  
                	 KeyLable key1 = new KeyLable("a", SoftKeyBoardPanel.this);  
                     KeyLable key2 = new KeyLable("s", SoftKeyBoardPanel.this);  
                     KeyLable key3 = new KeyLable("d", SoftKeyBoardPanel.this);  
                     KeyLable key4 = new KeyLable("f", SoftKeyBoardPanel.this);  
                     KeyLable key5 = new KeyLable("g", SoftKeyBoardPanel.this);  
                     KeyLable key6 = new KeyLable("h", SoftKeyBoardPanel.this);  
                     KeyLable key7 = new KeyLable("j", SoftKeyBoardPanel.this);  
                     KeyLable key8 = new KeyLable("k", SoftKeyBoardPanel.this);  
                     KeyLable key9 = new KeyLable("l",true, SoftKeyBoardPanel.this);
                     KeyLable key12 = new KeyLable(" 删  除", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最右  
//                     KeyLable key10 = new KeyLable("l", SoftKeyBoardPanel.this);  
//                     KeyLable key11 = new KeyLable(":", ";", SoftKeyBoardPanel.this);  
//                     KeyLable key12 = new KeyLable("\"", "'", SoftKeyBoardPanel.this);  
                     
//                     KeyLable key13 = new KeyLable("CapsLock", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最右  
                     //CapsLock按钮设置大小
                     key12.setPreferredSize(new Dimension(80, 50));
                     key10.setPreferredSize(new Dimension(80, 50));
                     keys = new KeyLable[] { key10,key1, key2, key3, key4, key5, key6, key7, key8, key9,key12 };  
                     ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
                       for (KeyLable key : keys) {  
                             keylist.add(key);  
                     }  
                     for (KeyLable key : keylist) {  
                         this.add(key);  
                     } 
                	
                } else if (rowType == RowType.fourth) {  
                    // key1至key13是一个闭环顺序，通过随机数决定在哪个位置切断这个环从而使其变成单链  
//                    KeyLable key1 = new KeyLable("n", SoftKeyBoardPanel.this);  
//                    KeyLable key2 = new KeyLable("o", SoftKeyBoardPanel.this);  
//                    KeyLable key3 = new KeyLable("p", SoftKeyBoardPanel.this);  
//                    KeyLable key4 = new KeyLable("q", SoftKeyBoardPanel.this);  
//                    KeyLable key5 = new KeyLable("r", SoftKeyBoardPanel.this);  
//                    KeyLable key6 = new KeyLable("s", SoftKeyBoardPanel.this);  
//                    KeyLable key7 = new KeyLable("t", SoftKeyBoardPanel.this);  
//                    KeyLable key8 = new KeyLable("u", SoftKeyBoardPanel.this);  
//                    KeyLable key9 = new KeyLable("v", SoftKeyBoardPanel.this);  
//                    KeyLable key10 = new KeyLable("w", SoftKeyBoardPanel.this);  
//                    KeyLable key11 = new KeyLable("x", SoftKeyBoardPanel.this);  
//                    KeyLable key12 = new KeyLable("y", SoftKeyBoardPanel.this);  
//                    KeyLable key13 = new KeyLable("z", SoftKeyBoardPanel.this);  
//  
//                    keys = new KeyLable[] { key1, key2, key3, key4, key5, key6, key7, key8, key9, key10, key11, key12, key13 };  
//                    ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
//                    int randomIndex = random.nextInt(keys.length);// 随机切入点  
//  
//                    for (int i = randomIndex; i < keys.length; i++) {  
//                        keylist.add(keys[i]);  
//                    }  
//                    for (int i = 0; i < randomIndex; i++) {  
//                        keylist.add(keys[i]);  
//                    }  
//  
//                    for (KeyLable key : keylist) {  
//                        this.add(key);  
//                    } 
                    KeyLable key1 = new KeyLable("z", SoftKeyBoardPanel.this);  
                    KeyLable key2 = new KeyLable("x", SoftKeyBoardPanel.this);  
                    KeyLable key3 = new KeyLable("c", SoftKeyBoardPanel.this);  
                    KeyLable key4 = new KeyLable("v", SoftKeyBoardPanel.this);  
                    KeyLable key5 = new KeyLable("b", SoftKeyBoardPanel.this);  
                    KeyLable key6 = new KeyLable("n", SoftKeyBoardPanel.this);  
                    KeyLable key7 = new KeyLable("m", SoftKeyBoardPanel.this);  
                    KeyLable key8 = new KeyLable(" ←",true, SoftKeyBoardPanel.this);  
                    KeyLable key9 = new KeyLable(" →", true,SoftKeyBoardPanel.this);  
                    KeyLable key10 = new KeyLable("(", SoftKeyBoardPanel.this);  
                    KeyLable key12 = new KeyLable(")", SoftKeyBoardPanel.this);  
                    
//                    KeyLable key11 = new KeyLable("x", SoftKeyBoardPanel.this);  
//                    KeyLable key12 = new KeyLable("y", SoftKeyBoardPanel.this);  
//                    KeyLable key13 = new KeyLable("z", SoftKeyBoardPanel.this);

                    KeyLable key11 = new KeyLable("切 换", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最左  
                    //Shift按钮设置大小
                    key11.setPreferredSize(new Dimension(60, 50));  

                  
                    keys = new KeyLable[] {key11, key1, key2, key3, key4, key5, key6, key7, key8, key9, key10,key12 };  
                    ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
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
        boolean isCapsLock;  
        boolean isShift;  
        boolean isPressed;  
        boolean isJia;
        boolean isJian;
        boolean isL;
        boolean isEsc;
        boolean isCaps;
        public boolean isCaps() {
			return isCaps;
		}

		public boolean isEsc() {
			return isEsc;
		}

		public boolean isLeft() {
			return isLeft;
		}

		public boolean isUsRight() {
			return usRight;
		}
		boolean isLeft;
        boolean usRight;
		public boolean isL() {
			return isL;
		}
		KeyStatus status = KeyStatus.normal; 
        //按钮大小设置
        Dimension size = new Dimension(48, 48);  
        Color keyBorderColor = new Color(54, 112, 184);  
        Color keyBorderFocusColor = new Color(64, 194, 241);  
        Color keyBackColor = new Color(253, 255, 255);  
        Color keyBackFocusColor = new Color(28, 159, 228); 
        //按钮字体大小 小写
        Font boldFont = new Font("微软雅黑", Font.PLAIN, 24);  
        Color boldColor = new Color(0, 0, 57); 
        //按钮字体大小 大写
        Font plainFont = new Font("微软雅黑", Font.PLAIN, 20);  
        Color plainColor = new Color(156, 157, 197);  
  
        public KeyLable(String centerKey, ActionListener action) {  
            this(centerKey, null, action);  
        }  
  
        public KeyLable(String centerKey, String lowerLeftKey, ActionListener action) {  
            this(centerKey, lowerLeftKey, false, action);  
        }  
  
        public KeyLable(String centerKey, boolean isFunctionKey, ActionListener action) {  
            this(centerKey, null, isFunctionKey, action);  
        }  
  
        public KeyLable(String centerKey, String lowerLeftKey, boolean isFunctionKey, final ActionListener action) {  
            this.centerKey = centerKey;  
            this.lowerLeftKey = lowerLeftKey;  
            if (isFunctionKey) {// 这个变量主要是提高效率  
                if (centerKey.indexOf("切") >= 0|| centerKey.indexOf("换") >= 0) {  
                    isShift = true;  
                } else if (centerKey.indexOf("删") >= 0 || centerKey.indexOf("除") >= 0) {  
                    isBackSpace = true;  
                } else if(centerKey.indexOf("+")>=0){
                	isJia=true;
                }else if(centerKey.indexOf("-")>=0){
                	isJian=true;
                }else if(centerKey.indexOf("l")>=0){
                	isL=true;
                }else if(centerKey.indexOf("←")>=0){
                	isLeft=true;
                }else if(centerKey.indexOf("→")>=0){
                	usRight=true;
                }else if(centerKey.indexOf("退") >= 0 || centerKey.indexOf("出") >= 0){
                	isEsc=true;
                }else if(centerKey.indexOf("大") >= 0 || centerKey.indexOf("写") >= 0){
                	isCapsLock=true;
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
                	if ((!KeyLable.this.isCaps ) || (!isPressed)) {  
                        KeyLable.this.setBackground(keyBackColor);  
                    }  
                    
                }  
  
                public void mouseClicked(MouseEvent e) {  
                    // 创建一个ActionEvent将KeyLable对象作为Source  
                    action.actionPerformed(new ActionEvent(KeyLable.this, ActionEvent.ACTION_PERFORMED, e.getID() + ""));  
                }  
            });  
        }  
  
        @Override  
        protected void paintComponent(Graphics g) {  
            super.paintComponent(g);// 完成背景色的绘制  
  
            Graphics2D g2d = (Graphics2D) g;  
            ImageTool.setAntiAliasing(g2d);// 抗锯齿  
            Container parent = getParent();  
            ImageTool.clearAngle(g2d, parent != null ? parent.getBackground() : this.getBackground(), this.getWidth(), this.getHeight(), 4);// 清除角落变圆角  
  
            if (getMousePosition() != null) {// 如果鼠标正在这个键的范围内，绘制圆角边框  
                g2d.setPaint(keyBorderFocusColor);
                //字体外框
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 4, 4);  
            }  
  
            if (status == KeyStatus.normal || status == KeyStatus.capsLock) {  
                if (lowerLeftKey == null) {  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
                    // g2d.drawString(centerKey, isCommKey() ? 8 : 4, 17);  
                  //设置字体大小
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, centerKey, -1, isCommKey() ? 18 : 4, 30);  
  
                } else {  
                    g2d.setFont(plainFont);  
                    g2d.setPaint(plainColor);  
                    // g2d.drawString(centerKey, 12, 15);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, centerKey, -1, isCommKey() ? 25 : 4, 20);  
  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
                    // g2d.drawString(lowerLeftKey, 3, 20);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, lowerLeftKey, -1, isCommKey() ? 12 : 4, 35);  
                }  
            } else if (status == KeyStatus.shift || status == KeyStatus.shiftAndCapsLock) {  
                if (lowerLeftKey == null) {  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
                    // g2d.drawString(centerKey, isCommKey() ? 8 : 4, 17);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, centerKey, -1, isCommKey() ? 18 : 4, 30);  
  
                } else {  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
                    // g2d.drawString(centerKey, 10, 15);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, centerKey, -1, isCommKey() ? 25 : 4, 20);  
  
                    g2d.setFont(plainFont);  
                    g2d.setPaint(plainColor);  
                    // g2d.drawString(lowerLeftKey, 3, 20);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, lowerLeftKey, -1, isCommKey() ? 12 : 4, 35);  
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
  
        public boolean isCapsLock() {  
            return isCapsLock;  
        }  
  
        public boolean isShift() {  
            return isShift;  
        }  
  
        public void setPressed(boolean isPressed) {  
            this.isPressed = isPressed;  
        }  
  
        public boolean isPressed() {  
            return isPressed;  
        }  
  
        public boolean isCommKey() {  
            return !isBackSpace && !isCapsLock && !isShift&&!isJia&&!isJian&&!isL&&!isLeft&&!usRight&&!isEsc&&!isCaps;  
        }  
        public boolean isJia() {
			return isJia;
		}

		public boolean isJian() {
			return isJian;
		}
        // 重置  
        public void reset() {  
            this.isPressed = false;  
            if (isShift || isCapsLock) {  
                KeyLable.this.setBackground(keyBackColor);  
            } else if (isCommKey()) {  
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
                } else if (status == KeyStatus.normal || status == KeyStatus.shiftAndCapsLock) {  
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
  
 
