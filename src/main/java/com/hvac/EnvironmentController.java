package com.hvac;

public interface EnvironmentController {
    void tick();
    void setMinTemp(int minTemp);
    void setMaxTemp(int maxTemp);
}
