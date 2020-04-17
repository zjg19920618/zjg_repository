package com.boomhope.Bill.Util;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import sun.swing.SwingUtilities2;

public class GuaLostAccKeyBoardPop extends JPopupMenu{
    private static final long serialVersionUID = 1L;  
    
    public static void main(String[] args) {  
  
        final JFrame frame = new JFrame();  
        frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); 
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
  
        frame.getContentPane().add(passwordPanel);  
  
        final JPasswordField password = new JPasswordField();  
        password.setSelectedTextColor(Color.BLACK);// 颜色障眼法，产生不能去选中密码框的任何东西的幻觉，其实已经选中了但你不知道  
        password.setSelectionColor(Color.WHITE);// 颜色障眼法，产生不能去选中密码框的任何东西的幻觉，其实已经选中了但你不知道  
        password.setForeground(Color.BLACK);  
        password.setFont(password.getFont().deriveFont(22f));  
        // password.setEchoChar('●');  
        password.setBorder(new EmptyBorder(5, 3, 0, 3));// 左间隙  
        passwordPanel.add(password, BorderLayout.CENTER);  
  
        final GuaLostAccKeyBoardPop keyPopup = new GuaLostAccKeyBoardPop(password,5);  
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
                    keyPopup.getSoftKeyBoardPanel2().reset();  
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
    private static int length;
  
    protected SoftKeyBoardPanel2 softKeyBoardPanel2;  
  
    public GuaLostAccKeyBoardPop(JTextField textField,int lengths) {  
        softKeyBoardPanel2 = new SoftKeyBoardPanel2(textField, this);  
        softKeyBoardPanel2.setBackground(Color.WHITE);
        softKeyBoardPanel2.setPreferredSize(new Dimension(350, 260));  
        softKeyBoardPanel2.setBorder(BorderFactory.createEmptyBorder());  
        length = lengths;
        add(softKeyBoardPanel2);  
        softKeyBoardPanel2.setLayout(new MigLayout("", "[]", "[]"));
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
  
    public SoftKeyBoardPanel2 getSoftKeyBoardPanel2() {  
        return softKeyBoardPanel2;  
    }  
  
    // 软键盘面板  
    public static class SoftKeyBoardPanel2 extends JPanel implements ActionListener {  
//    	private JTextField textField;
    	JTextField passwordField;  


		JPopupMenu popupMenu;  
        RowPanel[] rows;  
        KeyStatus status = KeyStatus.normal;  
        Paint[] paints = new Paint[] { new Color(70, 67, 114), new Color(62, 192, 238), new Color(138, 180, 231) };  
  
        public SoftKeyBoardPanel2(JTextField textField, JPopupMenu popupMenu) {  
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
            } else if (keyLable.isBackSpace()) {  
                clickBackSpace();  
            } else if (keyLable.isCommKey()) {  
                String key;  
                if (status == KeyStatus.shift || status == KeyStatus.shiftAndCapsLock) {  
                    key = keyLable.getCenterKey();  
                } else if (status == KeyStatus.normal || status == KeyStatus.capsLock) {  
                    key = keyLable.getLowerLeftKey() == null ? keyLable.getCenterKey() : keyLable.getLowerLeftKey();  
                } else {  
                    key = "";  
                }  
                JTextField a= clickCommKey(key,length);
               String cs = a.getText();
               char[] password1=cs.toCharArray();
               String psw = new String(password1);
           System.out.println("66"+psw);
           scanBill1(a);
           
            } 
//            return passwordField;
        }  
    	/***
    	 * 返回值
    	 * @return 
    	 */
    	private JTextField scanBill1(JTextField a){
    		
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
  
        // 更改状态  
        public void clickShift() {  
            if (status == KeyStatus.capsLock) {  
                status = KeyStatus.shiftAndCapsLock;  
            } else if (status == KeyStatus.shiftAndCapsLock) {  
                status = KeyStatus.capsLock;  
            } else if (status == KeyStatus.shift) {  
                status = KeyStatus.normal;  
            } else if (status == KeyStatus.normal) {  
                status = KeyStatus.shift;  
            } else {  
                status = KeyStatus.normal;  
            }  
        }  
  
        // 更改状态  
        public void clickCapsLock() {  
            if (status == KeyStatus.capsLock) {  
                status = KeyStatus.normal;  
            } else if (status == KeyStatus.shiftAndCapsLock) {  
                status = KeyStatus.shift;  
            } else if (status == KeyStatus.shift) {  
                status = KeyStatus.shiftAndCapsLock;  
            } else if (status == KeyStatus.normal) {  
                status = KeyStatus.capsLock;  
            } else {  
                status = KeyStatus.normal;  
            }  
        }  
  
        // 点击了删除键， 删除一个字符  
        public JTextField clickBackSpace() {  
            String ch = passwordField.getText();
         
            		char[] password=ch.toCharArray();
            if (password != null && password.length > 0) {  
                char[] copyOf = Arrays.copyOf(password, password.length - 1);  
                passwordField.setText(new String(copyOf));  
                System.out.println("已删除的字符：" + password[password.length - 1]);  
                System.out.println("删除后的的密码：" + new String(copyOf)); 
                
                String cs = passwordField.getText();
                char[] password1=cs.toCharArray();
                String psw = new String(password1);
                System.out.println("password1：" + psw);
            }  return passwordField;  
        }  
  
        // 点击了普通的键，添加一个字符  
        public JTextField clickCommKey(String key,int length) {  
            if (key != null) {  
                if (key.length() > 1) {// 可有可无的检查  
                    key = key.substring(0, key.length() - 1);  
                }  
                String ch = passwordField.getText(); 
                if(ch!=null&&ch.length()==length){
                	return passwordField;
                }
                char[] password=ch.toCharArray();
                String string = (password == null ? "" : new String(password));  
                passwordField.setText(string + key);  
                System.out.println("新添加的字符：" + key);  
                System.out.println("添加后的密码：" + string + key);
                String cs = passwordField.getText();
                char[] password1=ch.toCharArray();
                String psw = new String(password1);
                System.out.println("passwordField：" + psw);
            }
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
  
                    KeyLable key1 = new KeyLable("1", SoftKeyBoardPanel2.this);  
                    KeyLable key2 = new KeyLable("2", SoftKeyBoardPanel2.this);  
                    KeyLable key3 = new KeyLable("3", SoftKeyBoardPanel2.this);  
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
  
//                    keys = new KeyLable[] { key4, key5, key6, key7, key8, key9, key10, key11, key1, key2, key3, key12 };  
//                    ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
  
                    keys = new KeyLable[] { key1, key2, key3 };  
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
//                    final Image defaImage = createCloseImage(new Color(138, 180, 231), false);  
//                    final Image focusImage = createCloseImage(new Color(30, 90, 150), true);  
//                    final JLabel closeLabel = new JLabel(new ImageIcon(defaImage)) {  
//                        JToolTip toolTip;  
//  
//                        protected void paintComponent(Graphics g) {  
//                            super.paintComponent(g);  
//                            ImageIcon icon = (ImageIcon) getIcon();  
//                            if (icon != null) {  
//                                g.drawImage(icon.getImage(), 0, 0, 12, 12, 0, 0, 12, 12, null);  
//                            }  
//                        }  
//  
//                        public JToolTip createToolTip() {  
//                            JToolTip toolTip = new JToolTip();  
//                            Color color = new Color(118, 118, 118);  
//  
//                            toolTip.setComponent(this);  
//                            toolTip.setTipText(this.getToolTipText());  
//                            toolTip.setBackground(Color.WHITE);  
//                            toolTip.setForeground(color);  
//                            toolTip.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));  
//  
//                            Border outside = BorderFactory.createLineBorder(color);  
//                            Border inside = BorderFactory.createEmptyBorder(2, 3, 2, 3);  
//                            CompoundBorder border = BorderFactory.createCompoundBorder(outside, inside);  
//                            toolTip.setBorder(border);  
//                            return toolTip;  
//                        }  
//                    };  
//                    MouseAdapter mouseAdapter = new MouseAdapter() {  
//                        public void mouseClicked(MouseEvent e) {  
//                            popupMenu.setVisible(false);  
//                        }  
//  
//                        public void mouseEntered(MouseEvent e) {  
//                            closeLabel.setIcon(new ImageIcon(focusImage));  
//                        }  
//  
//                        public void mouseExited(MouseEvent e) {  
//                            closeLabel.setIcon(new ImageIcon(defaImage));  
//                        }  
//                    };  
//                    closeLabel.setToolTipText("关闭");  
//                    closeLabel.addMouseListener(mouseAdapter);  
//                    closeLabel.setPreferredSize(new Dimension(12, 12));  
//                    add(closeLabel);  
                } else if (rowType == RowType.second) {  
                    // key1至key10是一个闭环顺序，通过随机数决定在哪个位置切断这个环从而使其变成单链  
//                  KeyLable key1 = new KeyLable("+", "=", SoftKeyBoardPanel.this);  
//                  KeyLable key2 = new KeyLable("|", "\\", SoftKeyBoardPanel.this);  
//                  KeyLable key3 = new KeyLable("{", "[", SoftKeyBoardPanel.this);  
//                  KeyLable key4 = new KeyLable("}", "]", SoftKeyBoardPanel.this);  
//                  KeyLable key5 = new KeyLable(":", ";", SoftKeyBoardPanel.this);  
//                  KeyLable key6 = new KeyLable("\"", "'", SoftKeyBoardPanel.this);  
//                  KeyLable key7 = new KeyLable("<", ",", SoftKeyBoardPanel.this);  
//                  KeyLable key8 = new KeyLable(">", ".", SoftKeyBoardPanel.this);  
//                  KeyLable key9 = new KeyLable("?", "/", SoftKeyBoardPanel.this);  
//                  KeyLable key10 = new KeyLable("_", "-", SoftKeyBoardPanel.this);  
//                  KeyLable key11 = new KeyLable("Shift", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最左  
//                  //Shift按钮设置大小
//                  key11.setPreferredSize(new Dimension(70, 50));  
//                  KeyLable key12 = new KeyLable("CapsLock", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最右  
//                  //CapsLock按钮设置大小
//                  key12.setPreferredSize(new Dimension(130, 50));  

//                  keys = new KeyLable[] { key11, key2, key3, key4, key5, key6, key7, key8, key9, key10, key1, key12 };  
//                  ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
//                  int randomIndex = random.nextInt(keys.length - 2) + 1;// 随机切入点，排除key11和key12的位置  

//                  keylist.add(key11);  
//                  for (int i = randomIndex; i < keys.length - 1; i++) {  
//                      keylist.add(keys[i]);  
//                  }  
//                  for (int i = 1; i < randomIndex; i++) {  
//                      keylist.add(keys[i]);  
//                  }  
//                  keylist.add(key12);  
//
//                  for (KeyLable key : keylist) {  
//                      this.add(key);  
//                  } 
                  // key1至key10是一个闭环顺序，通过随机数决定在哪个位置切断这个环从而使其变成单链  
                  KeyLable key1 = new KeyLable("4", SoftKeyBoardPanel2.this);  
                  KeyLable key2 = new KeyLable("5", SoftKeyBoardPanel2.this);  
                  KeyLable key3 = new KeyLable("6", SoftKeyBoardPanel2.this);  
//                  KeyLable key4 = new KeyLable("r", SoftKeyBoardPanel.this);  
//                  KeyLable key5 = new KeyLable("t", SoftKeyBoardPanel.this);  
//                  KeyLable key6 = new KeyLable("y", SoftKeyBoardPanel.this);  
//                  KeyLable key7 = new KeyLable("u", SoftKeyBoardPanel.this);  
//                  KeyLable key8 = new KeyLable("i", SoftKeyBoardPanel.this);  
//                  KeyLable key9 = new KeyLable("o", SoftKeyBoardPanel.this);  
//                  KeyLable key10 = new KeyLable("p", SoftKeyBoardPanel.this);                 
//                  KeyLable key11 = new KeyLable("{", "[", SoftKeyBoardPanel.this);   
//                  KeyLable key12 = new KeyLable("}", "]", SoftKeyBoardPanel.this);  
//                  KeyLable key13 = new KeyLable("|", "\\", SoftKeyBoardPanel.this);
                  
               keys = new KeyLable[] { key1, key2, key3 };  
                ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
                  for (KeyLable key : keys) {  
                        keylist.add(key);  
                }  
                for (KeyLable key : keylist) {  
                    this.add(key);  
                }  
              } else if (rowType == RowType.third) {  
                  // key1至key13是一个闭环顺序，通过随机数决定在哪个位置切断这个环从而使其变成单链  
//                KeyLable key1 = new KeyLable("c", SoftKeyBoardPanel.this);  
//                KeyLable key2 = new KeyLable("d", SoftKeyBoardPanel.this);  
//                KeyLable key3 = new KeyLable("e", SoftKeyBoardPanel.this);  
//                KeyLable key4 = new KeyLable("f", SoftKeyBoardPanel.this);  
//                KeyLable key5 = new KeyLable("g", SoftKeyBoardPanel.this);  
//                KeyLable key6 = new KeyLable("h", SoftKeyBoardPanel.this);  
//                KeyLable key7 = new KeyLable("i", SoftKeyBoardPanel.this);  
//                KeyLable key8 = new KeyLable("j", SoftKeyBoardPanel.this);  
//                KeyLable key9 = new KeyLable("k", SoftKeyBoardPanel.this);  
//                KeyLable key10 = new KeyLable("l", SoftKeyBoardPanel.this);  
//                KeyLable key11 = new KeyLable("m", SoftKeyBoardPanel.this);  
//                KeyLable key12 = new KeyLable("a", SoftKeyBoardPanel.this);  
//                KeyLable key13 = new KeyLable("b", SoftKeyBoardPanel.this);  
//
//                keys = new KeyLable[] { key1, key2, key3, key4, key5, key6, key7, key8, key9, key10, key11, key12, key13 };  
//                ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
//                int randomIndex = random.nextInt(keys.length);// 随机切入点  
//
//                for (int i = randomIndex; i < keys.length; i++) {  
//                    keylist.add(keys[i]);  
//                }  
//                for (int i = 0; i < randomIndex; i++) {  
//                    keylist.add(keys[i]);  
//                }  
//
//                for (KeyLable key : keylist) {  
//                    this.add(key);  
//                } 
                KeyLable key1 = new KeyLable("7", SoftKeyBoardPanel2.this);  
                KeyLable key2 = new KeyLable("8", SoftKeyBoardPanel2.this);  
                KeyLable key3 = new KeyLable("9", SoftKeyBoardPanel2.this);  
//                KeyLable key4 = new KeyLable("f", SoftKeyBoardPanel.this);  
//                KeyLable key5 = new KeyLable("g", SoftKeyBoardPanel.this);  
//                KeyLable key6 = new KeyLable("h", SoftKeyBoardPanel.this);  
//                KeyLable key7 = new KeyLable("j", SoftKeyBoardPanel.this);  
//                KeyLable key8 = new KeyLable("k", SoftKeyBoardPanel.this);  
//                KeyLable key9 = new KeyLable("l", SoftKeyBoardPanel.this);  
////                KeyLable key10 = new KeyLable("l", SoftKeyBoardPanel.this);  
//                KeyLable key11 = new KeyLable(":", ";", SoftKeyBoardPanel.this);  
//                KeyLable key12 = new KeyLable("\"", "'", SoftKeyBoardPanel.this);  
                
//                KeyLable key13 = new KeyLable("CapsLock", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最右  
//                //CapsLock按钮设置大小
//                key13.setPreferredSize(new Dimension(120, 50));
                
                keys = new KeyLable[] { key1, key2, key3 };  
                ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
                  for (KeyLable key : keys) {  
                        keylist.add(key);  
                }  
                for (KeyLable key : keylist) {  
                    this.add(key);  
                } 
                
            } else if (rowType == RowType.fourth) {  
                // key1至key13是一个闭环顺序，通过随机数决定在哪个位置切断这个环从而使其变成单链  
//              KeyLable key1 = new KeyLable("n", SoftKeyBoardPanel.this);  
//              KeyLable key2 = new KeyLable("o", SoftKeyBoardPanel.this);  
//              KeyLable key3 = new KeyLable("p", SoftKeyBoardPanel.this);  
//              KeyLable key4 = new KeyLable("q", SoftKeyBoardPanel.this);  
//              KeyLable key5 = new KeyLable("r", SoftKeyBoardPanel.this);  
//              KeyLable key6 = new KeyLable("s", SoftKeyBoardPanel.this);  
//              KeyLable key7 = new KeyLable("t", SoftKeyBoardPanel.this);  
//              KeyLable key8 = new KeyLable("u", SoftKeyBoardPanel.this);  
//              KeyLable key9 = new KeyLable("v", SoftKeyBoardPanel.this);  
//              KeyLable key10 = new KeyLable("w", SoftKeyBoardPanel.this);  
//              KeyLable key11 = new KeyLable("x", SoftKeyBoardPanel.this);  
//              KeyLable key12 = new KeyLable("y", SoftKeyBoardPanel.this);  
//              KeyLable key13 = new KeyLable("z", SoftKeyBoardPanel.this);  
//
//              keys = new KeyLable[] { key1, key2, key3, key4, key5, key6, key7, key8, key9, key10, key11, key12, key13 };  
//              ArrayList<KeyLable> keylist = new ArrayList<KeyLable>(keys.length);  
//              int randomIndex = random.nextInt(keys.length);// 随机切入点  
//
//              for (int i = randomIndex; i < keys.length; i++) {  
//                  keylist.add(keys[i]);  
//              }  
//              for (int i = 0; i < randomIndex; i++) {  
//                  keylist.add(keys[i]);  
//              }  
//
//              for (KeyLable key : keylist) {  
//                  this.add(key);  
//              } 
              KeyLable key1 = new KeyLable("", SoftKeyBoardPanel2.this);  
              KeyLable key2 = new KeyLable("0", SoftKeyBoardPanel2.this);  
//              KeyLable key3 = new KeyLable("删除", SoftKeyBoardPanel.this);  
              KeyLable key3 = new KeyLable("删除", true, SoftKeyBoardPanel2.this);// 功能键，位置固定在最右  
//              KeyLable key4 = new KeyLable("v", SoftKeyBoardPanel.this);  
//              KeyLable key5 = new KeyLable("b", SoftKeyBoardPanel.this);  
//              KeyLable key6 = new KeyLable("n", SoftKeyBoardPanel.this);  
//              KeyLable key7 = new KeyLable("m", SoftKeyBoardPanel.this);  
//              KeyLable key8 = new KeyLable("<", ",", SoftKeyBoardPanel.this);  
//              KeyLable key9 = new KeyLable(">", ".", SoftKeyBoardPanel.this);  
//              KeyLable key10 = new KeyLable("?", "/", SoftKeyBoardPanel.this);  
//              KeyLable key11 = new KeyLable("x", SoftKeyBoardPanel.this);  
//              KeyLable key12 = new KeyLable("y", SoftKeyBoardPanel.this);  
//              KeyLable key13 = new KeyLable("z", SoftKeyBoardPanel.this);

//              KeyLable key11 = new KeyLable("Shift", true, SoftKeyBoardPanel.this);// 功能键，位置固定在最左  
//              //Shift按钮设置大小
//              key11.setPreferredSize(new Dimension(120, 50));  

            
              keys = new KeyLable[] { key1, key2, key3 };  
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
        KeyStatus status = KeyStatus.normal; 
        //按钮大小设置
        Dimension size = new Dimension(109, 75); 
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
                if (centerKey.indexOf("Shift") >= 0) {  
                    isShift = true;  
                } else if (centerKey.indexOf("删") >= 0 || centerKey.indexOf("除") >= 0) {
                    isBackSpace = true;  
                } else if (centerKey.indexOf("Caps") >= 0 || centerKey.indexOf("Lock") >= 0) {  
                    isCapsLock = true;  
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
                    // 如果不是Shift和CapsLock键则还原背景色，或者是Shift和CapsLock键但是不是按压状态也要还原背景色  
                    if ((!KeyLable.this.isShift && !KeyLable.this.isCapsLock) || (!isPressed)) {  
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
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, centerKey, -1, isCommKey() ? 40 : 15, 50);
  
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
            return !isBackSpace && !isCapsLock && !isShift;  
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
