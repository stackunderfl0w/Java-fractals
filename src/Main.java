import javax.swing.*;

public class Main {
    private static long[] last_frame = new long[60];
    private static long last_fps_update;
    public static JFrame f = new JFrame();
    public static Screen screen;
    public static int framerate=60;
    private static long next_frame;
    public static boolean debug;
    public static long[]frames = new long[60];

    public static void main(String[] args) {
        f.setTitle("Stackunderfl0w java fractals");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen = new Screen();
        f.add(screen);
        f.pack();
        f.setResizable(true);
        f.setVisible(true);
        f.setFocusable(true);
        f.requestFocusInWindow();
        f.requestFocus();
        while(true){
            if (System.nanoTime() >next_frame){
                double fps =round(1000000000.0/(System.nanoTime()-frames[59])*60,2);
                //long starttime=System.nanoTime();
                if (System.nanoTime()-last_fps_update>1000000000/2) {
                    last_fps_update=System.nanoTime();
                    f.setTitle("Stackunderfl0w java fraactals (fps, " + fps + ", " + round(100 * fps / 60, 1) + "%)");
                    f.setTitle(fps+"");
                }
                System.arraycopy(last_frame, 0, last_frame, 1, 59);
                last_frame[0] = System.nanoTime();
                f.repaint();
                next_frame = last_frame[0]+1000000000/framerate;
            }
            if(next_frame-System.nanoTime()>1100000){
                sleep(1);
            }
        }
    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    private static void sleep(long x){
        try {
            Thread.sleep(x);
        }
        catch(Exception e) {
            // this part is executed when an exception (in this example InterruptedException) occurs
        }
    }
}
