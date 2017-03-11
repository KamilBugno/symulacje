import ec.util.MersenneTwisterFast;
import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;


public class Car implements Steppable
{
    MersenneTwisterFast random;
    double p;

    public Car(Cars cars){
        random = new MersenneTwisterFast();
        p = random.nextDouble();
        if(p>0.5){
            cars.yard.setObjectLocation(this, new Double2D(cars.yard.getWidth() * random.nextDouble(), cars.yard.getHeight() * 0.5));
        }else{
            cars.yard.setObjectLocation(this, new Double2D(cars.yard.getWidth() * random.nextDouble(), cars.yard.getHeight() * 0.5 - 1));
        }

    }

    public void step(SimState state){
        Cars cars = (Cars) state;
        Double2D me = cars.yard.getObjectLocation(this);
        MutableDouble2D sumForces = new MutableDouble2D();

        if(p>0.5){
            sumForces.x += 0.01;
        } else {
            sumForces.x -= 0.05;
        }


        sumForces.addIn(me);
        cars.yard.setObjectLocation(this, new Double2D(sumForces));
    }
}