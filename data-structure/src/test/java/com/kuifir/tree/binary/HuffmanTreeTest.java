package com.kuifir.tree.binary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanTreeTest {
    HuffmanTree<Integer> huffmanTree;

    @BeforeEach
    void beforeEach() throws Exception {
        int[] weights = {5,29,7,8,14,23,3,11};
        huffmanTree = new HuffmanTree<>(weights.length);
        huffmanTree.init(weights);
    }
    @Test
    void init() {
        Arrays.stream(huffmanTree.huffmanTreeNodes).forEach(System.out::println);
    }
}