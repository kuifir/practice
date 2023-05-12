package com.kuifir.normal.exceptions;

/**
 * 异常的约束
 * 再重写一个方法时，只能抛出该方法的基类版本中说明的异常。
 * 这意味着：能够配合基类工作的代码，可以自动地配合从这个基类派生而来的任何类的对象工作，异常也不例外
 *
 * @author kuifir
 * @date 2023/5/12 22:44
 * @see Inning
 */
public class StoryMyInning extends Inning implements Storm {
    /**
     * 可以为构造器添加新异常，但是必须处理基类构造器的异常
     *
     * @throws RainedOutException
     * @throws BaseballException
     */
    StoryMyInning() throws RainedOutException, BaseballException {
    }

    /**
     * 普通方法必须遵守基类方法的约定
     * <pre>{@code
     * void walk() throws PopFoulException{} // 编译错误
     * }
     * </pre>
     */
    @Override
    public void walk() {
        super.walk();
    }

    /**
     * 对于基类中存在的方法，接口不能增加其异常
     * - public void event() throws RainedOutException{}
     * 即使基类版本会抛出异常，我们也可以选择不抛出任何异常
     */
    @Override
    public void event() {

    }

    /**
     * 重写的方法，可以抛出其基类版本所说明的异常的子类
     *
     * @throws PopFoulException
     */
    @Override
    public void atBat() throws PopFoulException {

    }

    /**
     * 对于基类中不存在的方法，接口中可以自行声明
     *
     * @throws RainedOutException
     */
    @Override
    public void rainHard() throws RainedOutException {

    }

    public static void main(String[] args) {
        try {
            StoryMyInning storyMyInning = new StoryMyInning();
        } catch (PopFoulException e) {
            System.out.println("Pop foul");
        } catch (RainedOutException e) {
            System.out.println("RainedOutException");
        } catch (BaseballException e) {
            System.out.println("Generic baseball exception");
        }
        // 派生版本，没有抛出Strike
        try {
            // 如果向上转型，会发生什么？
            Inning i = new StoryMyInning();
            i.atBat();
            // 必须捕捉来自该方法的积累版本的异常
        } catch (RainedOutException e) {
            throw new RuntimeException(e);
        } catch (BaseballException e) {
            throw new RuntimeException(e);
        }
    }
}

class BaseballException extends Exception {
}

class FoulException extends BaseballException {
}

class StrikeException extends BaseballException {
}

abstract class Inning {
    Inning() throws BaseballException {
    }

    public void event() throws BaseballException {
        // 实际上不是必须抛出异常
    }

    public abstract void atBat() throws StrikeException, FoulException;

    /**
     * 没有抛出检查型异常
     */
    public void walk() {
    }
}

class StormException extends Exception {
}

class RainedOutException extends StormException {
}

class PopFoulException extends FoulException {
}

interface Storm {
    void event() throws RainedOutException;

    void rainHard() throws RainedOutException;
}