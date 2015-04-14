import org.junit.Test;
import vendor.hvac.FakeHVAC;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnvironmentControllerImplTest {

    @Test
    public void ShouldDoNothingIfTemperatureIsOK() {
        EnvironmentControllerImpl controller = new EnvironmentControllerImpl(new FakeHVAC());
        assertTrue(controller.dormant());
    }

    @Test
    public void TurnHeatOnIfTemperatureIsBelow65() {
        FakeHVAC testHVAC = new FakeHVAC(63);
        EnvironmentControllerImpl controller = new EnvironmentControllerImpl(testHVAC);
        controller.tick();

        assertTrue(testHVAC.heatOn);
        assertFalse(testHVAC.coolOn);
        assertTrue(testHVAC.fanOn);

        assertTrue(controller.isHeaterOn());
        assertFalse(controller.isCoolerOn());
        assertTrue(controller.isFanOn());
    }

    @Test
    public void TurnCoolerOnIfTemperatureIsAbove75() {
        FakeHVAC testHVAC = new FakeHVAC(77);
        EnvironmentControllerImpl controller = new EnvironmentControllerImpl(testHVAC);
        controller.tick();

        assertFalse(testHVAC.heatOn);
        assertTrue(testHVAC.coolOn);
        assertTrue(testHVAC.fanOn);

        assertTrue(controller.isCoolerOn());
        assertFalse(controller.isHeaterOn());
        assertTrue(controller.isFanOn());
    }

    @Test
    public void IfCoolIsSwitchedOffHeatShouldWait3TicksBeforeTurningOn() {
        FakeHVAC testHVAC = new FakeHVAC(77);
        EnvironmentControllerImpl controller = new EnvironmentControllerImpl(testHVAC);
        controller.tick();

        assertFalse(testHVAC.heatOn);
        assertTrue(testHVAC.coolOn);
        assertTrue(testHVAC.fanOn);

        testHVAC.setTemp(63);

        for(int i = 0; i < 2; i++) {
            controller.tick();
            assertTrue(controller.dormant());
        }

        controller.tick();

        assertTrue(testHVAC.heatOn);
        assertFalse(testHVAC.coolOn);
        assertTrue(testHVAC.fanOn);
    }

    @Test
    public void IfHeatIsSwitchedOffCoolShouldWait5TicksBeforeTurningOn() {
        FakeHVAC testHVAC = new FakeHVAC();
        testHVAC.setTemp(64);
        EnvironmentControllerImpl controller = new EnvironmentControllerImpl(testHVAC);
        controller.tick();

        assertFalse(testHVAC.coolOn);
        assertTrue(testHVAC.heatOn);
        assertTrue(testHVAC.fanOn);

        testHVAC.setTemp(76);

        for(int i = 0; i < 4; i++) {
            controller.tick();
            assertTrue(controller.dormant());
        }

        controller.tick();

        assertTrue(testHVAC.coolOn);
        assertFalse(testHVAC.heatOn);
        assertTrue(testHVAC.fanOn);
    }

    @Test
    public void ShouldStayDormantIfTemperatureChangeIsWithinRange() {
        FakeHVAC testHVAC = new FakeHVAC();
        testHVAC.setTemp(64);
        EnvironmentControllerImpl controller = new EnvironmentControllerImpl(testHVAC);
        controller.tick();

        assertFalse(testHVAC.coolOn);
        assertTrue(testHVAC.heatOn);
        assertTrue(testHVAC.fanOn);

        testHVAC.setTemp(75);

        for(int i = 0; i < 4; i++) {
            controller.tick();
            assertTrue(controller.dormant());
        }

        controller.tick();

        assertTrue(controller.dormant());
    }

    @Test
    public void IfHeatIsSwitchedOffCoolShouldWait5TicksBeforeTurningOnWhenTempsAreNotDefaults() {
        final int minTemp = 60;
        final int maxTemp = 70;
        FakeHVAC testHVAC = new FakeHVAC();
        testHVAC.setTemp(minTemp - 1);
        EnvironmentControllerImpl controller = new EnvironmentControllerImpl(testHVAC);
        controller.minTemp = minTemp;
        controller.maxTemp = maxTemp;

        controller.tick();

        assertFalse(testHVAC.coolOn);
        assertTrue(testHVAC.heatOn);
        assertTrue(testHVAC.fanOn);

        testHVAC.setTemp(maxTemp + 1);

        for(int i = 0; i < 4; i++) {
            controller.tick();
            assertTrue(controller.dormant());
        }

        controller.tick();

        assertTrue(testHVAC.coolOn);
        assertFalse(testHVAC.heatOn);
        assertTrue(testHVAC.fanOn);
    }
}
