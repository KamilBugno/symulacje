package miss;
import sim.portrayal.continuous.*;
import sim.engine.*;
import sim.display.*;
import sim.portrayal.simple.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import sim.portrayal.*;

public class CarsWithUI extends GUIState{

	public Display2D display;
    public JFrame displayFrame;
    ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
    public static void main(String[] args)
    {
        CarsWithUI vid = new CarsWithUI();
        Console c = new Console(vid);
        c.setVisible(true);
    }
    public CarsWithUI() { super(new Cars( System.currentTimeMillis())); }
    public CarsWithUI(SimState state) { super(state); }
    public static String getName() { return "Cars"; }
	    
    public void start()
    {
        super.start();
        setupPortrayals();
    }
    public void load(SimState state)
    {
        super.load(state);
        setupPortrayals();
    }
    public void setupPortrayals()
    {
        Image i = new ImageIcon(getClass().getResource("/miss/crossV2.jpg")).getImage();
        BufferedImage b = display.getGraphicsConfiguration().createCompatibleImage(i.getWidth(null), i.getHeight(null));
        Graphics g = b.getGraphics();
        g.drawImage(i,0,0,i.getWidth(null),i.getHeight(null),null);
        g.dispose();
        display.setBackdrop(new TexturePaint(b, new Rectangle(0,0,i.getWidth(null),i.getHeight(null))));

        Cars cars = (Cars) state;
        yardPortrayal.setField( cars.getYard() );
        yardPortrayal.setPortrayalForRemainder(new OvalPortrayal2D()
        {
            public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
            {
                paint = new Color(180);
                super.draw(object, graphics, info);
            }
        });
        for(Car car : cars.getCarOutRoad()){
            yardPortrayal.setPortrayalForObject(car, new OvalPortrayal2D()
            {
                public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
                {
                    paint = new Color(0xFCFF17);
                    super.draw(object, graphics, info);
                }
            });
        }


        display.reset();


        display.repaint();
    }
    public void init(Controller c)
    {
        super.init(c);
        display = new Display2D(600,600,this);
        display.setClipping(false);
        displayFrame = display.createFrame();
        displayFrame.setTitle("Traffic");
        c.registerFrame(displayFrame);
        displayFrame.setVisible(true);
        display.attach( yardPortrayal, "Yard" );
    }

    public void quit()
    {
        super.quit();
        if (displayFrame!=null) displayFrame.dispose();
        displayFrame = null;
        display = null;
    }
}