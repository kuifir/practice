package com.kuifir.normal.innerClass.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于管理和触发事件的真正的控制框架。Event对象被保存在一个List<Event>类型的集合中。
 *
 * @author kuifir
 * @date 2023/5/7 21:43
 */
public class Controller {
    /**
     * 用List保存Event对象
     */
    private List<Event> eventList = new ArrayList<>();

    /**
     * 将一个Event添加到List的末尾
     *
     * @param event
     */
    public void addEvent(Event event) {
        eventList.add(event);
    }

    /**
     * 循环遍历eventList的一个副本，寻找准备就绪的(ready())、可以运行的Event对象。
     * 对找到的每一个符合要求的对象，它会使用该对象的toString()方法来大引起信息，
     * 然后调用其action()方法，最后将其从列表中移除、
     */
    public void run() {
        while (eventList.size() > 0){
            // 创建一个副本，这样在选择列表中的元素时就不会改动列表了
            for (Event e : new ArrayList<>(eventList)) {
                if (e.ready()){
                    System.out.println(e);
                    e.action();
                    eventList.remove(e);
                }
            }
        }
    }
}
