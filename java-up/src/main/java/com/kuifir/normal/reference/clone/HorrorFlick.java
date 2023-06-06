package com.kuifir.normal.reference.clone;

/**
 * 在继承层次结构中增加可克隆性并向下覆盖
 * 如果你创建了一个新的的类。则其基类默认是Object,因此默认是不具备可克隆性的。
 * 只要你不显示地增加可克隆性，它就不会具备该能力。
 * 但你可以在继承层次结构中的任意一层增加该能力，而且从该层开始向下的所有层次都会具备该能力.
 * 就像这样：{@link Person}{@link Hero}{@link Scientist}{@link MadScientist}
 * <p></p>
 * 在向继承层次结构中增加可克隆性之前，编译器会组织你进行克隆。
 * 在对Scientist加入可克隆性后，Scientist及它的所有后代就都是可克隆的了。
 * 注意Scientist 的clone()返回的是Scientist,而在克隆MadScientist时，他并未创建自己的克隆方法，
 * 而是使用了继承自Scientist的Clone()，因此需要转型，
 * @author kuifir
 * @date 2023/6/6 22:56
 */
public class HorrorFlick {
    public static void main(String[] args) {
        Person p = new Person();
        Hero h = new Hero();
        Scientist s = new Scientist();
        MadScientist m = new MadScientist();
        // 编译错误
        // p = (Person)p.clone();
        // h = (Hero) h.clone();
        Scientist clone = s.clone();
         m = (MadScientist) m.clone();

    }
}
class Person{}
class Hero extends Person{}
class Scientist extends Person implements Cloneable{
    @Override
    protected Scientist clone() {
        try {
            return (Scientist) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
class MadScientist extends Scientist{}