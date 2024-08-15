package com.kuifir.table.linked;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinkedTest {
    @BeforeAll
    public static void before(){

    }

    @Test
    public void testEmptyLinkedListGetOutIndex(){
        SingleChainLinkList<Integer> list = new SingleChainLinkList<>();
        assertThrows(IndexOutOfBoundsException.class,()->list.getElem(1));
    }
    @Test
    public void testLinkedListGet(){
        SingleChainLinkList<Integer> list = new SingleChainLinkList<>();
        list.listInsert(1,2);
        assertEquals(2,list.getElem(1));
    }
    @Test
    public void testLinkedListGetEnd(){
        SingleChainLinkList<Integer> list = new SingleChainLinkList<>();
        IntStream.range(1,10).boxed().forEach(i -> list.listInsert(1,i));
        System.out.println(list);
        assertEquals(1,list.getElem(9));
    }
    @Test
    public void testLinkedListPrint(){
        SingleChainLinkList<Integer> list = new SingleChainLinkList<>();
        IntStream.range(1,10).boxed().forEach(i -> list.listInsert(1,i));
        System.out.println(list);
    }
    @Test
    public void testLinkedListDelete(){
        SingleChainLinkList<Integer> list = new SingleChainLinkList<>();
        IntStream.range(1,10).boxed().forEach(i -> list.listInsert(1,i));
        list.listDelete(3);
        System.out.println(list);
    }
    @Test
    public void testLinkedListDeleteFirst(){
        SingleChainLinkList<Integer> list = new SingleChainLinkList<>();
        IntStream.range(1,10).boxed().forEach(i -> list.listInsert(1,i));
        list.listDelete(1);
        System.out.println(list);
    }
    @Test
    public void testLinkedListDeleteEnd(){
        SingleChainLinkList<Integer> list = new SingleChainLinkList<>();
        IntStream.range(1,10).boxed().forEach(i -> list.listInsert(1,i));
        list.listDelete(9);
        System.out.println(list);
    }
    @Test
    public void testLinkedListInsert(){
        SingleChainLinkList<Integer> list = new SingleChainLinkList<>();
        IntStream.range(1,10).boxed().forEach(i -> list.listInsert(1,i));
        list.listInsert(3,10);
        System.out.println(list);
    }
    @Test
    public void testLinkedListInsertEnd(){
        SingleChainLinkList<Integer> list = new SingleChainLinkList<>();
        IntStream.range(1,10).boxed().forEach(i -> list.listInsert(1,i));
        list.listInsert(10,0);
        System.out.println(list);
    }

}
