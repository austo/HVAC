import vendor.hvac.HVAC;

/*
 * This Java source file was auto generated by running 'gradle buildInit --type java-library'
 * by 'mconklin' at '4/13/15 3:29 PM' with Gradle 2.2
 *
 * @author mconklin, @date 4/13/15 3:29 PM
 */
public class EnvironmentControllerImpl implements EnvironmentController {

    private static int TICK_MULTIPLIER = 1;
    private static int COOLER_COUNTDOWN_START = 1;
    private static int HEAT_COUNTDOWN_START = 3;

    int minTemp = 65;
    int maxTemp = 75;

    private boolean fanOn;
    private boolean heaterOn;
    private boolean coolerOn;

    private int tickCounter;

    private HVAC hvac;

    public EnvironmentControllerImpl(HVAC hvac) {
        this.hvac = hvac;
    }

    @Override
    public void tick() {

        if (this.tickCounter > 0) {
            --tickCounter;
            return;
        }

        // invariant: tickCounter == 0
        int currentTemp = hvac.temp();
        if (currentTemp < minTemp) {
            turnHeatOn();
        } else if (currentTemp > maxTemp) {
            turnOnCooler();
        } else {
            makeDormant();
        }
    }

    private void turnHeatOn() {
        if (coolerOn) {
            switchCooler(false);
            return;
        }
        switchHeat(true);
    }

    private void turnOnCooler() {
        if (heaterOn) {
            switchHeat(false);
            return;
        }
        switchCooler(true);
    }

    private void switchHeat(boolean state) {
        if (!state) { // turning off
            tickCounter = HEAT_COUNTDOWN_START;
        }
        switchFan(state);
        hvac.heat(state);
        heaterOn = state;
    }

    private void switchCooler(boolean state) {
        if (!state) { // turning off
            tickCounter = COOLER_COUNTDOWN_START;
        }
        switchFan(state);
        hvac.cool(state);
        coolerOn = state;
    }

    private void switchFan(boolean state) {
        hvac.fan(state);
        fanOn = state;
    }

    public int getMinTemp() {
        return minTemp;
    }

    @Override
    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    @Override
    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public boolean isFanOn() {
        return fanOn;
    }

    public boolean isHeaterOn() {
        return heaterOn;
    }

    public boolean isCoolerOn() {
        return coolerOn;
    }

    public boolean dormant() {
        return !fanOn && !heaterOn && !coolerOn;
    }

    private void makeDormant() {
        this.fanOn = false;
        this.heaterOn = false;
        this.coolerOn = false;
    }
}
