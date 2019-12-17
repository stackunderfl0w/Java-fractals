import java.awt.*;
import java.awt.event.KeyEvent;

public class drawableslider extends uielement{
    private int width;
    private int height;
    private int x_location;
    private int y_location;
    private boolean clicked;
    private double position=1;
    private boolean drawbounds;
    private int xofset;
    private int yofset;
    drawableslider(int x, int y,int w, int h,boolean bounds){
        x_location=x;
        y_location=y;
        width=w;
        height=h;
        drawbounds=bounds;
    }
    drawableslider(int x, int y,int w, int h,int xoff,int yoff,boolean bounds){
        x_location=x;
        y_location=y;
        width=w;
        height=h;
        drawbounds=bounds;
        xofset=xoff;
        yofset=yoff;
    }
    double getposition(){
        return position;
    }
    void updateposition(int x, int y, int w, int h){
        x_location=x;
        y_location=y;
    }
    @Override
    void draw(Graphics g) {
        g.setColor(Color.gray);
        g.drawRect(x_location,y_location,width,height);
        g.drawLine(x_location+10,y_location+height/2,x_location+width-10,y_location+height/2);
        g.fillOval(x_location+(int)((width-20)*position),y_location+height/2-10,20,20);
    }
    void clicked(int x, int y) {
        if(10<x&&x<width-10){
            clicked=true;
        }
        System.out.println(x);
    }
    @Override
    void mousedraged(int x, int y) {
        position=(x-10.0)/(width-20);
        if(position<0){position=0;}
        if (position>1){position=1;}

    }

    @Override
    void keypressed(KeyEvent e) {

    }

    @Override
    void keyreleased(KeyEvent e) {

    }

    @Override
    int getwidth() {
        return width;
    }

    @Override
    int getheight() {
        return height;
    }

    @Override
    void setwidth(int width) {
        this.width=width;
    }

    @Override
    void setheight(int height) {
        this.height=height;
    }

    @Override
    int gety() {
        return y_location;
    }

    @Override
    int getx() {
        return x_location;
    }

    @Override
    void setX(int x) {
        x_location=x;
    }

    @Override
    void setY(int y) {
        y_location=y;
    }
}
