package com.kuifir.table.linked;

/**
 * 单链表
 *
 * index 从1开始
 * @param <E>
 */

public class DoublyLinkList<E> implements LinkedList<E> {
    int size = 0;
    Node<E> first;
    Node<E> last;

    public int getSize() {
        return size;
    }

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
    // 时间复杂度:O(n)
    public Node<E> findNode(E e) {
        if (e != null) {
            for (Node<E> p = first; p != null; p = p.next) {
                if (e.equals(p.item)) {
                   return p;
                }
            }
        } else {
            for (Node<E> p = first; p != null; p = p.next) {
                if (p.item == null) {
                    return p;
                }
            }
        }
        return null;
    }
    // 时间复杂度O(n)
    public boolean listInsert( int i, E e) {
        if(i<=0 || i > size+1){
            return false;
        }
        Node<E> tmp = first;
        size++;
        if (i == 1) {
            if(first == null){
                first = new Node<>(null ,e, null);
                last = first;
            }else {
                Node<E> newNode = new Node<>(null, e, first);
                first.pre = newNode;
                first = newNode;
            }
            return true;
        }
        for (int cur = 1; cur < i-1; cur++) {
            tmp = tmp.next;
        }
        Node<E> newNode = new Node<>(tmp, e, tmp.next);
        if(tmp.next ==null){
            last = newNode;
        }else {
            tmp.next.pre = newNode;
        }
        tmp.next = newNode;
        return true;
    }

    // 时间复杂度O(n)
    public boolean listDelete(int i) {
        checkElementIndex(i);
        size--;
        if(i == 1){
            Node<E> next = first.next;
            if(next == null){
                first = null;
                last = null;
                return true;
            }else {
                next.pre = null;
                first = next;
                return true;
            }
        }
        Node<E> tmp = first;
        for (int j = 1; j <i-1; j++) {
            tmp = tmp.next;
        }
        if(tmp.next.next == null){
            tmp.next= null;
            last = tmp;
            return true;
        }
        tmp.next.next.pre = tmp;
        tmp.next = tmp.next.next;
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
    public String toRevertString() {
        Node<E> tmp = last;
        int index = 1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        while (tmp!= null) {
            stringBuilder.append(tmp.item + ",");
            tmp = tmp.pre;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static class Node<E> {
        E item;
        Node<E> pre;
        Node<E> next;

        Node(Node<E> pre,E element, Node<E> next) {
            this.pre = pre;
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