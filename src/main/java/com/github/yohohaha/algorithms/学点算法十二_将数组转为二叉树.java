package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static com.github.yohohaha.algorithms.学点算法十一_将二叉树转为数组.convertTree2Array;
import static org.junit.Assert.assertArrayEquals;

/**
 * created at 2020/07/30 16:36:26
 *
 * @author Yohohaha
 */
public class 学点算法十二_将数组转为二叉树 {

    @Test
    public void testConvertArray2Tree() {
        BinaryTreeNode<Integer> root = convertArray2Tree(new Integer[]{1, null, 2, 4, 3, 3, 2, 2, 2, 1, null, 3, 5, null, 7, null, 8, null});
        // 使用convertTree2Array方法帮助验证（该方法在学点算法十一验证过）
        Integer[] arr = convertTree2Array(root);
        // 因为数组后面的null不影响数据，为了方便测试，将数组后面的null值都去掉
        int idx = arr.length - 1;
        while (arr[idx] == null) {idx--;}
        Integer[] nullFilteredArr = Arrays.copyOf(arr, idx + 1);
        assertArrayEquals(new Integer[]{1, null, 2, 4, 3, 3, 2, 2, 2, 1, null, 3, 5, null, 7, null, 8}, nullFilteredArr);
    }

    /**
     * 将数组转为二叉树
     *
     * @param arr 数组
     * @param <T> 数组类型
     *
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
        // 数组中表示右孩子的索引
        // 左孩子索引通过右孩子索引减1得到
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
                T rightNodeData = arr[rightNodeIdx];
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
            // 右孩子索引位置+2
            rightNodeIdx += 2;
        } while (root != null);
        return realRoot;
    }
}
