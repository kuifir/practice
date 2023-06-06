package com.kuifir.normal.reference.clone.deepCopy;

/**
 * 克隆组合对象
 *
 * @see OceanReading
 * @author kuifir
 * @date 2023/6/6 21:16
 */
public class TemperatureReading implements Cloneable{
    private long time;
    private double temperature;

    public TemperatureReading(double temperature) {
        this.time = System.currentTimeMillis();
        this.temperature = temperature;
    }

    @Override
    protected TemperatureReading clone() {
        try {
            return (TemperatureReading)super.clone();
        }catch (CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return String.valueOf(temperature);
    }
}
