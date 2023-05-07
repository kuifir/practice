package com.kuifir.normal.innerClass.controller;

/**
 * 这里生成了控制系统的一个具体应用，所有代码在一个类中。
 * 内部类允许我们为每类事件封装不同功能
 *
 * @author kuifir
 * @date 2023/5/7 22:17
 */
public class GreenhouseControls extends Controller {
    private boolean light = false;

    public class LightOn extends Event {
        public LightOn(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            // 将硬件控制代码放在这里
            // 实际上打开灯
            light = true;
        }

        @Override
        public String toString() {
            return "Light is On";
        }
    }

    public class LightOff extends Event {

        public LightOff(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            // 将硬件控制代码放在这里
            // 实际上关上灯
            light = false;
        }

        @Override
        public String toString() {
            return "Light is Off";
        }
    }

    private boolean water = false;

    public class WaterOn extends Event {
        public WaterOn(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            // 将硬件控制代码放在这里
            water = true;
        }

        @Override
        public String toString() {
            return "Greenhouse water is on";
        }
    }

    public class WaterOff extends Event {
        public WaterOff(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            // 将硬件控制代码放在这里
            water = false;
        }

        @Override
        public String toString() {
            return "Greenhouse water is off";
        }
    }

    private String thermostat = "Day";

    public class ThermostatNight extends Event {
        public ThermostatNight(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            //
            thermostat = "Night";
        }

        @Override
        public String toString() {
            return "Thermostat on night setting";
        }

    }

    public class ThermostatDay extends Event {
        public ThermostatDay(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            //
            thermostat = "Day";
        }

        @Override
        public String toString() {
            return "Thermostat on day setting";
        }
    }

    /**
     * action()的一个例子，向事件列表中插入一个新的相同事件：
     * <p>
     * Bell控制响铃，然后向事件列表中加入一个新的Bell对象，这样之后就可以再次响铃了。
     * 可以注意到，内部类和多重继承非常像：
     * - Bell和Restart 拥有Event的所有方法，而且看上去也拥有外围类GreenhouseControls的所有方法
     */
    public class Bell extends Event {
        public Bell(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            addEvent(new Bell(delayTime.toMillis()));
        }

        @Override
        public String toString() {
            return "Bing!";
        }

    }

    /**
     * Restart中有一个Event 对象数组，它会将其添加到控制器中。
     * 因为Restart也是一个Event对象，所以同样可以挂在Restart.action()中添加一个Restart对象，这样系统就可以定时重启了
     */
    public class Restart extends Event {
        private Event[] eventsList;

        public Restart(long delayTime, Event[] eventsList) {
            super(delayTime);
            this.eventsList = eventsList;
            for (Event event : eventsList) {
                addEvent(event);
            }
        }

        @Override
        public void action() {
            for (Event event : eventsList) {
                // 重新运行每个事件
                event.start();
                addEvent(event);
            }
            // 重新运行该事件
            start();
            addEvent(this);
        }

        @Override
        public String toString() {
            return "Restart system";
        }

    }

    public static class Terminate extends Event {
        public Terminate(long delayTime) {
            super(delayTime);
        }

        @Override
        public void action() {
            System.exit(0);
        }

        @Override
        public String toString() {
            return "Terminating";
        }
    }
}
