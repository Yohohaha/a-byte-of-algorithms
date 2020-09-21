package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * created at 2020/07/30 17:32:26
 *
 * @author Yohohaha
 */
public class 学点算法十二_将二叉树转为数组 {

    @Test
    public void testConvertTree2Array() {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(0);
        BinaryTreeNode<Integer> left = new BinaryTreeNode<>(root, 1);
        BinaryTreeNode<Integer> right = new BinaryTreeNode<>(root, 2);
        left.leftChild = new BinaryTreeNode<>(left, 3);
        right.rightChild = new BinaryTreeNode<>(right, 4);
        root.leftChild = left;
        root.rightChild = right;
        Integer[] arr = convertTree2Array(root);
        // 因为数组后面的null不影响数据，为了方便测试，将数组后面的null值都去掉
        int idx = arr.length - 1;
        while (arr[idx] == null) {idx--;}
        Integer[] nullFilteredArr = Arrays.copyOf(arr, idx + 1);
        assertArrayEquals(new Integer[]{0, 1, 2, 3, null, null, 4}, nullFilteredArr);
    }

    /**
     * 将二叉树转为数组（二叉树层次遍历）
     * @param root 二叉树根节点
     * @param <T> 二叉树元素类型
     * @return 数组
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T[] convertTree2Array(BinaryTreeNode<T> root) {
        if (root == null) {
            // 如果根节点为null，则返回null
            return null;
        }
        // 由于不知道数组长度，先将元素存入list中
        List<T> list = new ArrayList<>();
        BinaryTreeNode<T> currentNode = root;
        Deque<BinaryTreeNode<T>> queue = new ArrayDeque<>();
        // 创建一个特殊对象来存储null
        BinaryTreeNode<T> NULL = new BinaryTreeNode<>(null);
        do {
            // 添加节点元素到列表中
            list.add(currentNode.data);
            if (currentNode != NULL) {
                // 获取左子树节点
                BinaryTreeNode<T> leftChild = currentNode.leftChild;
                if (leftChild == null) {
                    queue.offer(NULL);
                } else {
                    queue.offer(leftChild);
                }
                // 获取右子树节点
                BinaryTreeNode<T> rightChild = currentNode.rightChild;
                if (rightChild == null) {
                    queue.offer(NULL);
                } else {
                    queue.offer(rightChild);
                }
            }
            // 从队列中取出下一个节点
            currentNode = queue.poll();
        } while (currentNode != null);
        return list.toArray((T[]) Array.newInstance(root.data.getClass(), 0));
    }
}
