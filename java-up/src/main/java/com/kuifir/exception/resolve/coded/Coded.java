package com.kuifir.exception.resolve.coded;

/**
 * 解决java异常处理性能消耗过多，尝试重回错误码，设计既能返回值又能返回错误码的数据结构
 * <p>
 * 缺陷：生成一个 Coded 的实例，需要遵守两条纪律。
 * 第一条纪律是错误码的数值必须一致，0 代表没有错误，如果是其他的值表示出现了错误；
 *
 * @param returned
 * @param errorCode
 * @param <T>
 * @<<code> public static Coded<Digest> of(String algorithm) {
 * return switch (algorithm) {
 * // INCORRECT: set both error code and value.
 * case "SHA-256" -> new Coded(sha256, -1);
 * case "SHA-512" -> new Coded(sha512, 0);
 * default -> new Coded(sha256, -1);
 * };
 * }
 * </code>
 * <p>
 * 第二条纪律是不能同时设置返回值和错误码。违反了任何一条纪律，都会出现不可预测的错误。
 * @<<code> Coded<Digest> coded = Digest.of("SHA-256");
 * // INCORRECT: use returned value before checking error code.
 * coded.returned().digest("Hello, world!".getBytes());
 * </code>
 */
public record Coded<T>(T returned, int errorCode) {

}
