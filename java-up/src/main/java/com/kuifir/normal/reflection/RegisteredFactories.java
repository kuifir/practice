package com.kuifir.normal.reflection;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 工厂方法(Factory Method)设计模式来 推迟 对象的创建，将其交给类自己去完成。
 * 工厂方法可以被多态的调用，来创建恰当类型的对象。
 * 实际上,{@link java.util.function.Supplier}通过他的{@code T get()}方法提供了一个工厂方法的原型。
 * {@code get()}方法可以通过协变返回类型为{@link java.util.function.Supplier}的不同子类返回对应的类型。
 *
 * @see java.util.function.Supplier
 * @author kuifir
 * @date 2023/5/17 22:49
 */
public class RegisteredFactories {
    public static void main(String[] args) {
        Stream.generate(new Part())
                .limit(10)
                .forEach(System.out::println);
    }
}
class Part implements Supplier<Part>{

    private static Random rand = new Random(47);
    static List<Supplier<? extends  Part>> prototypes = Arrays.asList(
            new FuelFilter(),
            new AirFilter(),
            new CabinAirFilter(),
            new OilFilter(),
            new FanBelt(),
            new PowerSteeringBelt(),
            new GeneratorBelt()
    );
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public Part get() {
        int n = rand.nextInt(prototypes.size());
        return prototypes.get(n).get();
    }
}
class Filter extends Part{}

class FuelFilter extends Filter{
    @Override
    public FuelFilter get() {
        return new FuelFilter();
    }
}
class AirFilter extends Filter{
    @Override
    public AirFilter get() {
        return new AirFilter();
    }
}
class CabinAirFilter extends Filter{
    @Override
    public CabinAirFilter get() {
        return new CabinAirFilter();
    }
}
class OilFilter extends Filter{
    @Override
    public OilFilter get() {
        return new OilFilter();
    }
}
class Belt extends Part{}

class FanBelt extends Belt{
    @Override
    public FanBelt get() {
        return new FanBelt();
    }
}
class GeneratorBelt extends Belt{
    @Override
    public GeneratorBelt get() {
        return new GeneratorBelt();
    }
}
class PowerSteeringBelt extends Belt{
    @Override
    public PowerSteeringBelt get() {
        return new PowerSteeringBelt();
    }
}