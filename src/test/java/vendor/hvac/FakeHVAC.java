package vendor.hvac;

import org.junit.Test;

import static org.junit.Assert.*;

public class FakeHVAC implements HVAC {

    public boolean heatOn, coolOn, fanOn;

    private int temp;

    public FakeHVAC(int temp) {
        this.temp = temp;
    }

    public FakeHVAC() {
    }

    @Override
    public void heat(boolean on) {
        this.heatOn = on;
    }

    @Override
    public void cool(boolean on) {
        this.coolOn = on;
    }

    @Override
    public void fan(boolean on) {
        this.fanOn = on;
    }

    @Override
    public int temp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

}