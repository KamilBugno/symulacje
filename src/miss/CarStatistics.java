package miss;

/**
 * Created by Kamil on 2017-04-29.
 */
public class CarStatistics {
    int numberOfCoveredCrossings;
    long timeOfSlowingDown;
    long timeOfSpeedingUp;
    long timeOfStopping;
    long timeOfDrivingWithoutSpeedChange;


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
