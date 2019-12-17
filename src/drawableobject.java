import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

abstract class drawableObject extends JPanel{
    drawableObject(){

    }
    abstract void draw(Graphics g);
    abstract void clicked(int x,int y);
    abstract void mousedraged(int x,int y);
    abstract void keypressed(KeyEvent e);
    abstract void keyreleased(KeyEvent e);
    abstract int getwidth();
    abstract int getheight();
    abstract void setwidth(int width);
    abstract void setheight(int height);
    abstract int gety();
    abstract int getx();
    abstract void setX(int x);
    abstract void setY(int y);
    @Override
    public String toString() {
        return ("hello");
    }
}
