package com.kuifir.algorithm.sorting.radix;

import java.util.Arrays;

public class RadixSort {
    public static void main(String[] args) {
        int[] l = {278, 109, 63, 930, 589, 184, 505, 269, 8, 83};
        Node[] radix = new Node[10];
        Node[] tail = new Node[10];
        Node node = new Node();
        Node head = node;
        for (int i : l) {
            Node tmp = new Node();
            tmp.data = i;
            tmp.pre = node;
            node.next = tmp;
            node = tmp;
        }
        radixSort(head, radix, tail, 3);
        System.out.println(Arrays.toString(l));
    }

    public static void radixSort(Node l, Node[] radix, Node[] tail, int keys) {
        for (int i = 1, j = 1; j <= keys; i *= 10, j++) {
            distribute(l, radix, tail, i);
            collect(l, radix, tail);
        }
    }

    public static void distribute(Node l, Node[] radix, Node[] tail, int keys) {
        Arrays.fill(radix, null);
        Arrays.fill(tail, null);
        Node tmp = l.next;
        while (tmp != null) {
            int index = tmp.data / keys > 0 ? tmp.data / keys % 10 : 0;
            if (radix[index] != null) {
                tail[index].next = new Node(radix[index], tmp.data, null);
                tail[index] = radix[index].next;
            } else {
                radix[index] = new Node(null, tmp.data, null);
                tail[index] = radix[index];
            }
            tmp = tmp.next;
        }
        System.out.println(Arrays.toString(radix));
    }

    public static void collect(Node l, Node[] radix, Node[] tail) {
        int i = 0;
        for (; radix[i] == null; i++) ;
        l.next = radix[i];
        radix[i].pre = l;
        int j = i;
        while (j < radix.length - 1) {
            if (radix[j + 1] != null) {
                tail[i].next = radix[j + 1];
                radix[j + 1].pre = tail[i];
                i = j + 1;
            }
            j++;
        }

    }

    static class Node {
        Node pre;
        int data;
        Node next;

        public Node() {
        }

        public Node(Node pre, int data, Node next) {
            this.pre = pre;
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return data + (next == null ? "" : "," + next);
        }
    }


}
