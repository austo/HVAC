package com.hvac;

public class EnvironmentControllerSpy implements EnvironmentController {

    private int minTemp, maxTemp;

    @Override

    public void tick() {

    }

    @Override
    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    @Override
    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }
}
