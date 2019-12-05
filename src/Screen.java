import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Screen extends JPanel {
    private Graphics g;
    boolean[][] display;
    int[][]disp;
    private int width=640;
    private int height=640;
    private double x_center=0;
    private double y_center=0;
    private double x_rad=2;
    private int precision=5;
    BufferedImage pic;

    Screen(){
        Main.f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //System.out.println(e);
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    System.exit(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    x_rad*=.9;
                }
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    y_center+=.1*x_rad;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    x_center-=.1*x_rad;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    y_center-=.1*x_rad;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    x_center+=.1*x_rad;
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT ) {
                    precision+=1;
                    System.out.println("Hello");
                }
                if (e.getKeyCode() == KeyEvent.VK_ALT) {
                    Main.debug = !Main.debug;
                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    Main.framerate = 300;
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    Main.framerate = 60;
                }
            }
        });
    }
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
    public void paintComponent(Graphics g) {
        System.arraycopy(Main.frames, 0, Main.frames, 1, 59);
        Main.frames[0] = System.nanoTime();
        super.paintComponent(g);
        this.g = g;
        // Draw background
        g.setColor(Color.black);
        g.fillRect(0, 0, width,height);
        paintFullScreen();

    }
    private void paintFullScreen() {
        //Check for window being resized
        width = getWidth();
        height = getHeight();
        display= new boolean[width][height];
        disp= new int[width][height];
        Thread[] threads=new Thread[width];
        g.setColor(Color.BLUE);
        pic = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR );
        for (int x = 0; x < width; x++) {
            int finalX = x;

            threads[x]= new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
                public void run() {
                    try {
                        for (int y = 0; y < height; y++) {
                            Complex z=new Complex(0,0);
                            Complex c =new  Complex(2.0*x_rad* finalX /width-x_rad+x_center,2.0*x_rad*y/height-x_rad+y_center);
                            /*int i=0;
                            while(i<precision&&z.abs2()<4){
                                z=z.power(2).plus(c);
                                i++;
                            }
                            display[finalX][y]=(z.abs2()<2);
                            disp[finalX][y]=-1;
                            z=new Complex(0,0);
                            c =new  Complex(2.0*x_rad* finalX /width-x_rad+x_center,2.0*x_rad*y/height-x_rad+y_center);*/
                            for(int n=0; n<precision; n++) {
                                z=z.power(2).plus(c);
                                if(z.abs2()>2){
                                    //disp[finalX][y]=n;
                                    pic.setRGB(finalX,y, getShadingColor(n).getRGB());
                                    break;
                                }
                            }


                        }


                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            });
            threads[x].start();
            /*for (int y = 0; y < height; y++) {
                Complex z=new Complex(0,0);
                Complex c =new  Complex(2.0*x_rad*x/width-x_rad+x_center,2.0*x_rad*y/height-x_rad+y_center);
                int i=0;
                while(i<precision&&z.abs2()<50){
                    z=z.power(2).plus(c);
                    i++;
                }
                display[x][y]=(z.abs2()<2);

            }*/

        }
        for (int j = 0; j < width; j++) {
            try{
                threads[j].join();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        /*for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(display[x][y]){
                    g.fillRect(x, height-y, 1, 1);

                }
            }
        }*/
        /*for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(disp[x][y]!=-1){
                    g.setColor(getShadingColor(disp[x][y]));
                    g.fillRect(x, height-y, 1, 1);

                }
            }
        }*/
        //g.fillRect(10, 10, 10, 10);
        //g.setColor(Color.green);
        g.drawImage(pic,0,height,width,-height,null);
        g.drawString(String.valueOf(precision),10,20);
        /*try{
            File outputfile = new File("image.tiff");
            ImageIO.write(pic, "tiff", outputfile);
            System.exit(0);
        }
        catch (Exception e) {
            System.out.println(e);
        }*/




    }
    private Color getShadingColor(int b) {
        // hue, saturation, brightness
        return Color.getHSBColor( 90/(float)100, 100/(float)100,  (float)b / (float)precision );
    }
}
