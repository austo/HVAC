import vendor.hvac.HVAC;

/*
 * This Java source file was auto generated by running 'gradle buildInit --type java-library'
 * by 'mconklin' at '4/13/15 3:29 PM' with Gradle 2.2
 *
 * @author mconklin, @date 4/13/15 3:29 PM
 */
public class EnvironmentControllerImpl implements EnvironmentController {

    private static final int MIN_TEMP = 65, MAX_TEMP = 75;

    private static int TICK_MULTIPLIER = 1;
    private static int COOLER_COUNTDOWN_START = 2;
    private static int HEAT_COUNTDOWN_START = 4;


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
        if (currentTemp < MIN_TEMP) {
            turnHeatOn();
        } else if (currentTemp > MAX_TEMP) {
            turnOnCooler();
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
}
