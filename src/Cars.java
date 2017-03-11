import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;
public class Cars extends SimState
{
    public Continuous2D yard = new Continuous2D(1.0,100,100);
    public int numCars = 10;

    public Cars(long seed)
    {
        super(seed);
    }
    public void start()
    {
        super.start();

        yard.clear();

        for(int i = 0; i < numCars; i++)
        {
            Car car = new Car(this);
            schedule.scheduleRepeating(car);
        }
        
    }

}
