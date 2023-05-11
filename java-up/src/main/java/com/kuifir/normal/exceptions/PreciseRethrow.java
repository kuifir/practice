package com.kuifir.normal.exceptions;

/**
 * 基类
 */
class BaseException extends Exception{}

/**
 * 衍生异常
 */
class DerivedException extends BaseException{}
/**
 * java7 之前，如果捕获了一个异常，那么只能抛出这样类型的异常。
 * 这导致代码中出现不精确的问题，java7已经修复了。
 * 在java7之前以下代码无法通过编译。
 *
 * @author kuifir
 * @date 2023/5/11 22:30
 */
public class PreciseRethrow {
    void catcher() throws DerivedException{
        try {
            throw new DerivedException();
        }catch (BaseException e){
            throw e;
        }
    }
}
