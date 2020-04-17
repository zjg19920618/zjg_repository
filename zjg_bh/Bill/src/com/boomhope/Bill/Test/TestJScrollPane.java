package com.boomhope.Bill.Test;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TestJScrollPane extends JFrame  {
 public TestJScrollPane() {
   super("TestJScrollPane");
   getContentPane().setLayout(null);
   this.setBounds(200, 200, 520, 341);
   JPanel panel = new JPanel();
   panel.setPreferredSize(new Dimension(550,100));//主要是这句代码，设置panel的首选大小，同时保证宽高大于JScrollPane的宽高，这样下面的JScrollPane才会出现滚动条
   panel.setLayout(null);
   
   JButton btnNewButton = new JButton("New button");
   btnNewButton.setBounds(149, 5, 93, 23);
   panel.add(btnNewButton);
   JButton button1  = new JButton("1");  
   button1.setBounds(250, 54, 39, 23);
   panel.add(button1);
   JButton button2  = new JButton("2");  
   button2.setBounds(291, 5, 39, 23);
   panel.add(button2);
   JButton button3  = new JButton("3");  
   button3.setBounds(335, 5, 39, 23);
   panel.add(button3);
   JButton button4  = new JButton("4");  
   button4.setBounds(383, 54, 39, 23);
   panel.add(button4);
   JButton button5  = new JButton("5");  
   button5.setBounds(423, 5, 39, 23);
   panel.add(button5);
   JButton button6  = new JButton("6");  
   button6.setBounds(467, 5, 39, 23);
   panel.add(button6);
   JButton button7  = new JButton("7");  
   button7.setBounds(511, 5, 57, 23);
   panel.add(button7);
   JScrollPane scrollPane = new JScrollPane(panel);
//   scrollPane.setBorder(null);
   scrollPane.setBounds(0, 77, 504, 127);
   this.getContentPane().add(scrollPane);
   this.setVisible(true);
   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }


 public static void main(String[] args) {
    new TestJScrollPane();

  }


};