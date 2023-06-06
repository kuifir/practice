package com.kuifir.normal.reference.clone.deepCopy;

/**
 * @author kuifir
 * @date 2023/6/6 21:44
 */
public class DeepCopyTest {
    public static void main(String[] args) {
        OceanReading reading = new OceanReading(33.9, 100.5);
        // 进行克隆
        OceanReading clone = reading.clone();
        TemperatureReading tr = clone.getTemperature();
        tr.setTemperature(tr.getTemperature()+1);
        clone.setTemperature(tr);
        DepthReading dr = clone.getDepth();
        dr.setDepth(dr.getDepth()+1);
        clone.setDepth(dr);
        System.out.println(reading.toString());
        System.out.println(clone.toString());
    }
}
