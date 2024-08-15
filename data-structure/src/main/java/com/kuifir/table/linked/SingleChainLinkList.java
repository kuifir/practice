package com.kuifir.table.linked;

/**
 * 单链表
 * index 从1开始
 *
 * @param <E>
 */

public class SingleChainLinkList<E> implements LinkedList<E> {
    int size = 0;
    Node<E> first;
    private boolean isElementIndex(int index) {
        return index > 0 && index <= size;
    }
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }
    // 时间复杂都O(n)
    E getElem(int i) {
        checkElementIndex(i);
        int j = 1;
        Node<E> tmp = first;
        while (j < i) {
            tmp = tmp.next;
            j++;
        }
        return tmp.item;
    }

    // 时间复杂度:O(n)
    public int locateItem(E e) {
        int index = 1;
        if (e != null) {
            for (Node<E> p = first; p != null; p = p.next) {
                if (e.equals(p.item)) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<E> p = first; p != null; p = p.next) {
                if (p.item == null) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    // 时间复杂度O(n)
    public boolean listInsert( int i, E e) {
        if(i<0 || i > size+1){
            return false;
        }
        Node<E> tmp = first;
        size++;
        if (i == 1) {
            first = new Node<>(e, tmp);
            return true;
        }
        for (int cur = 1; cur < i-1; cur++) {
            tmp = tmp.next;
        }
        tmp.next = new Node<>(e, tmp.next);
        return true;
    }

    // 时间复杂度O(n)
    public boolean listDelete(int i) {
        checkElementIndex(i);
        size--;
        if(i == 1){
            first = first.next;
            return true;
        }
        Node<E> tmp = first;
        for (int j = 1; j <i-1; j++) {
            tmp = tmp.next;
        }
        tmp.next=tmp.next.next;
        return true;
    }

    @Override
    public String toString() {
        Node<E> tmp = first;
        int index = 1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        while (tmp!= null) {
            stringBuilder.append(tmp.item + ",");
            tmp = tmp.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static class Node<E> {
        E item;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E item) {
            this.item = item;
        }
    }

}