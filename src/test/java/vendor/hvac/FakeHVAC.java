package vendor.hvac;

import org.junit.Test;

import static org.junit.Assert.*;

public class FakeHVAC implements HVAC {

    public int heatCount, coolCount, fanCount;

    private int temp;

    public FakeHVAC(int temp) {
        this.temp = temp;
    }

    public FakeHVAC() {
    }

    @Override
    public void heat(boolean on) {
        this.heatCount++;
    }

    @Override
    public void cool(boolean on) {
        this.coolCount++;
    }

    @Override
    public void fan(boolean on) {
        this.fanCount++;

    }

    @Override
    public int temp() {
        return temp;
    }
}