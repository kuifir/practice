package com.kuifir.table.linked;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoublyLinkedTest {
    @BeforeAll
    public static void before() {

    }

    @Test
    public void testEmptyLinkedListGetOutIndex() {
        DoublyLinkList<Integer> list = new DoublyLinkList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.getElem(1));
    }

    @Test
    public void testLinkedListGet() {
        DoublyLinkList<Integer> list = new DoublyLinkList<>();
        list.listInsert(1, 2);
        assertEquals(2, list.getElem(1));
    }

    @Test
    public void testLinkedListGetEnd() {
        DoublyLinkList<Integer> list = new DoublyLinkList<>();
        IntStream.range(1, 10).boxed().forEach(i -> list.listInsert(1, i));
        System.out.println(list);
        System.out.println(list.toRevertString());
        assertEquals(1, list.getElem(9));
    }

    @Test
    public void testLinkedListPrint() {
        DoublyLinkList<Integer> list = new DoublyLinkList<>();
        IntStream.range(1, 10).boxed().forEach(i -> list.listInsert(1, i));
        System.out.println(list);
        System.out.println(list.toRevertString());
    }

    @Test
    public void testLinkedListDelete() {
        DoublyLinkList<Integer> list = new DoublyLinkList<>();
        IntStream.range(1, 10).boxed().forEach(i -> list.listInsert(1, i));
        list.listDelete(3);
        System.out.println(list);
        System.out.println(list.toRevertString());
    }

    @Test
    public void testLinkedListDeleteFirst() {
        DoublyLinkList<Integer> list = new DoublyLinkList<>();
        IntStream.range(1, 10).boxed().forEach(i -> list.listInsert(1, i));
        list.listDelete(1);
        System.out.println(list);
        System.out.println(list.toRevertString());
    }

    @Test
    public void testLinkedListDeleteEnd() {
        DoublyLinkList<Integer> list = new DoublyLinkList<>();
        IntStream.range(1, 10).boxed().forEach(i -> list.listInsert(1, i));
        list.listDelete(9);
        System.out.println(list);
        System.out.println(list.toRevertString());
    }

    @Test
    public void testLinkedListInsert() {
        DoublyLinkList<Integer> list = new DoublyLinkList<>();
        IntStream.range(1, 10).boxed().forEach(i -> list.listInsert(1, i));
        list.listInsert(3, 10);
        System.out.println(list);
        System.out.println(list.toRevertString());
    }

    @Test
    public void testLinkedListInsertEnd() {
        DoublyLinkList<Integer> list = new DoublyLinkList<>();
        IntStream.range(1, 10).boxed().forEach(i -> list.listInsert(1, i));
        list.listInsert(10, 0);
        System.out.println(list);
        System.out.println(list.toRevertString());
    }

}
