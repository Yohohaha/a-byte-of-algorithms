package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * created at 2020/07/30 17:32:26
 *
 * @author Yohohaha
 */
public class 学点算法十四_将二叉树转为数组 {
    public static void main(String[] args) {
        BinaryTreeNode<Integer> root = new BinaryTreeNode<>(0);
        BinaryTreeNode<Integer> left = new BinaryTreeNode<>(root, 1);
        BinaryTreeNode<Integer> right = new BinaryTreeNode<>(root, 2);
        root.leftChild = left;
        root.rightChild = right;
        System.out.println(Arrays.toString(convertTree2Array(root)));
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
        Deque<BinaryTreeNode<T>> queue = new ArrayDeque<>();
        do {
            // 添加节点元素到列表中
            list.add(root.data);
            // 获取左子树节点
            BinaryTreeNode<T> leftChild = root.leftChild;
            if (leftChild != null) {
                queue.offer(leftChild);
            }
            // 获取右子树节点
            BinaryTreeNode<T> rightChild = root.rightChild;
            if (rightChild != null) {
                queue.offer(rightChild);
            }
            // 从队列中取出下一个节点
            root = queue.poll();
        } while (root != null);
        return (T[]) list.toArray();
    }
}
