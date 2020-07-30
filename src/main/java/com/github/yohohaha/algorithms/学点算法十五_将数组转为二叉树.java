package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * created at 2020/07/30 16:36:26
 *
 * @author Yohohaha
 */
public class 学点算法十五_将数组转为二叉树 {
    public static void main(String[] args) {

    }

    /**
     * 将数组转为二叉树
     * @param arr 数组
     * @param <T> 数组类型
     * @return 二叉树根节点
     */
    public static <T extends Comparable<T>> BinaryTreeNode<T> convertArray2Tree(T[] arr) {
        // 如果数组为null或者长度为0则返回null
        if (arr == null || arr.length == 0) {
            return null;
        }
        int len = arr.length;
        BinaryTreeNode<T> root = new BinaryTreeNode<>(arr[0]);
        BinaryTreeNode<T> realRoot = root;
        Deque<BinaryTreeNode<T>> queue = new ArrayDeque<>(len);
        int rightNodeIdx = 2;
        // 无剩余节点，退出
        do {
            int leftNodeIdx = rightNodeIdx - 1;
            if (rightNodeIdx < len) {
                // 左右子孩子索引都合法
                // 获取左孩子
                T leftNodeData = arr[leftNodeIdx];
                if (leftNodeData != null) {
                    // 有左孩子
                    // 生成左孩子
                    BinaryTreeNode<T> leftChild = new BinaryTreeNode<>(root, leftNodeData);
                    // 将左孩子接入根节点
                    root.leftChild = leftChild;
                    // 左孩子入队
                    queue.offer(leftChild);
                }
                // 获取右孩子
                T rightNodeData = arr[leftNodeIdx];
                if (rightNodeData != null) {
                    // 有右孩子
                    // 生成右孩子
                    BinaryTreeNode<T> rightChild = new BinaryTreeNode<>(root, rightNodeData);
                    // 将右孩子接入根节点
                    root.rightChild = rightChild;
                    // 右孩子入队
                    queue.offer(rightChild);
                }
            } else if (leftNodeIdx < len) {
                // 左孩子索引合法，右孩子索引非法
                // 获取左孩子
                T leftNodeData = arr[leftNodeIdx];
                if (leftNodeData != null) {
                    // 有左孩子
                    // 生成左孩子
                    BinaryTreeNode<T> leftChild = new BinaryTreeNode<>(root, leftNodeData);
                    // 将左孩子接入根节点
                    root.leftChild = leftChild;
                    // 左孩子入队
                    queue.offer(leftChild);
                }
            }
            // 左右孩子索引都非法的时候无需做任何事情
            // 节点出队
            root = queue.poll();
        } while (root != null);
        return realRoot;
    }
}
