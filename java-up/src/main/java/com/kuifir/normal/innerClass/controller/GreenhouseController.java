package com.kuifir.normal.innerClass.controller;

/**
 * 是这样配置系统的：
 * 创建一个{@link GreenhouseControls}对象，然后加入各种不同的Event对象。
 * 这是命令(Command)设计模式的一个例子-eventList中的每个对象都是被封装为对象的请求
 * @see GreenhouseControls
 * @author kuifir
 * @date 2023/5/7 22:48
 */
public class GreenhouseController {
    public static void main(String[] args) {
        GreenhouseControls greenhouseControls = new GreenhouseControls();
        // 也可以是从文本中解析配置信息，而不是使用代码
        greenhouseControls.addEvent(greenhouseControls.new Bell(900));
        Event[] eventList = {
                greenhouseControls.new ThermostatNight(0),
                greenhouseControls.new LightOn(200),
                greenhouseControls.new LightOff(400),
                greenhouseControls.new WaterOn(600),
                greenhouseControls.new WaterOff(800),
                greenhouseControls.new ThermostatDay(1400)
        };
        greenhouseControls.addEvent(greenhouseControls.new Restart(2000,eventList));
        greenhouseControls.addEvent(new GreenhouseControls.Terminate(5000));
        greenhouseControls.run();
    }
}
