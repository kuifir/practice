package com.kuifir.create;

/**
 * @author kuifir
 * @date 2023/10/12 22:10
 */
public class Book {
    //方式一：声明时进行初始化
    private String name = " book ";

    //方式二：构造器语句块
    public Book(String bookName) {
        name = bookName;
    }

    //方式三：初始化语句块
    {
        name = " Java Book";
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print() {
        System.out.println(name);
    }
    public Book EscapeExample() {
        return new Book("Java Book");
    }
    public String EscapeExample2() {
        Book book = new Book("Java Book");
        return book.name;
    }
    public static void main(String[] args) {
        Book book1 = new Book();
        String a = "";
        for (int i = 0; i < 10000000; i++) {
            Book book = book1.EscapeExample();
            a = book.name+i;
//            a = book1.EscapeExample2()+i;
        }
    }
}
