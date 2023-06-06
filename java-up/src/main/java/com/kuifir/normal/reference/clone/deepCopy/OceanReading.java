package com.kuifir.normal.reference.clone.deepCopy;

/**
 * 克隆组合对象
 * OceanReading是由DepthReading和TemperatureReading对象组合而成的，
 * 因此要实现深拷贝，它的clone()就必须克隆OceanReading内部的所有引用。
 * 要完成这项任务，super.clone()的结果必须转型为OceanReading对象（这样才可以访问depth和temperature的引用）
 * @see DepthReading
 * @see TemperatureReading
 * @author kuifir
 * @date 2023/6/6 21:36
 */
public class OceanReading implements Cloneable{
    private DepthReading depth;
    private TemperatureReading temperature;

    public OceanReading(double tdata, double ddata) {
        temperature = new TemperatureReading(tdata);
        depth = new DepthReading(ddata);
    }

    @Override
    protected OceanReading clone() {
        OceanReading or = null;
        try {
            or = (OceanReading) super.clone();
        }catch (CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
        // 必须克隆引用
        or.depth = or.depth.clone();
        or.temperature = or.temperature.clone();
        return or;
    }

    public DepthReading getDepth() {
        return depth;
    }

    public void setDepth(DepthReading depth) {
        this.depth = depth;
    }

    public TemperatureReading getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureReading temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "temperature: " + temperature +",depth: " + depth;
    }
}
