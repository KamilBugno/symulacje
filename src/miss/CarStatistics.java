package miss;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil on 2017-04-29.
 */
public class CarStatistics {
    private List<Integer> list;
    int numberOfCoveredCrossings;
    long timeOfSlowingDown;
    long timeOfSpeedingUp;
    long timeOfStopping;
    long timeOfDrivingWithoutSpeedChange;

    CarStatistics(){
        list = new ArrayList<>();
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public void addTimeOfDrivingWithoutSpeedChange(long timeOfDrivingWithoutSpeedChange) {
        this.timeOfDrivingWithoutSpeedChange += timeOfDrivingWithoutSpeedChange;
    }


    public void addTimeOfSlowingDown(long timeOfSlowingDown) {
        this.timeOfSlowingDown += timeOfSlowingDown;
    }

    public void addCrossings() {
        this.numberOfCoveredCrossings++;
    }

    public void addTimeOfSpeedingUp(long timeOfSpeedingUp) {
        this.timeOfSpeedingUp += timeOfSpeedingUp;
    }

    public void addTimeOfStopping(long timeOfStopping) {
        this.timeOfStopping += timeOfStopping;
    }

    @Override
    public String toString() {
        int divide = 1000000;
        return "Time in milliseconds: CarStatistics{" +
                "numberOfCoveredCrossings=" + numberOfCoveredCrossings +
                ", timeOfSlowingDown=" + timeOfSlowingDown/divide +
                ", timeOfSpeedingUp=" + timeOfSpeedingUp/divide +
                ", timeOfStopping=" + timeOfStopping/divide +
                ", timeOfDrivingWithoutSpeedChange=" + timeOfDrivingWithoutSpeedChange/divide +
                '}';
    }
}
