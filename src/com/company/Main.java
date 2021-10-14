package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Main {

    public static void main(String[] args) {
	JFrame frame = new JFrame("HelloJava4");
    frame.add( new HelloComponent4("Hello, Java!"));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300,300);
    frame.setVisible(true);
    }

    static class HelloComponent4 extends JComponent implements MouseMotionListener, ActionListener, Runnable{
        String theMessage;
        int messageX = 125, messageY = 95; // Координаты сообщения
        JButton theButton;
        int colorIndex; // Текущий индекс переменной someColors
        static Color[] someColor = {Color.black, Color.red, Color.green, Color.blue, Color.magenta};
        boolean blinkState;

        public HelloComponent4(String message){
            theMessage = message;
            theButton = new JButton("Change Color");
            setLayout(new FlowLayout());
            add(theButton);
            theButton.addActionListener(this);
            addMouseMotionListener(this);
            Thread t = new Thread(this);
            t.start();
        }

        public void paintComponent(Graphics g){
            g.setColor(blinkState ? getBackground() : currentColor());
            g.drawString(theMessage, messageX, messageY);
        }

        public void mouseDragged(MouseEvent e){
            messageX = e.getX();
            messageY = e.getY();
            repaint();
        }

        public void mouseMoved(MouseEvent e){}

        public void actionPerformed(ActionEvent e){
            if(e.getSource() == theButton){
                changeColor();
            }
        }

        synchronized private void changeColor(){
            if(++colorIndex >= someColor.length){
                colorIndex = 0;
            }
            setForeground(currentColor());
            repaint();
        }

        synchronized private Color currentColor(){
            return someColor[colorIndex];
        }

        public void run(){
            try {
                while(true){
                    blinkState = !blinkState; //переключить blinkState.
                    repaint();
                    Thread.sleep(300);
                }
            }catch (InterruptedException ie){}
        }
    }
}
