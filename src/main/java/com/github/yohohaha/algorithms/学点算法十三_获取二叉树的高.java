package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.github.yohohaha.algorithms.学点算法十二_将数组转为二叉树.convertArray2Tree;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * created at 2020/09/01 16:14:01
 *
 * @author Yohohaha
 */
public class 学点算法十三_获取二叉树的高 {

    @Test
    public void testGetHeightOfBinaryTree() {
        assertThat(3, is(getHeightOfBinaryTree(convertArray2Tree(new Integer[]{1, 2, 3, 4, null, 5}))));
    }

    public static <E extends Comparable<E>> int getHeightOfBinaryTree(BinaryTreeNode<E> root) {
        if (root == null) {
            return 0;
        }
        // 当前层级节点队列
        Deque<BinaryTreeNode<E>> curLevelQueue = new ArrayDeque<>();
        // 下一层级节点队列
        Deque<BinaryTreeNode<E>> nextLevelQueue = new ArrayDeque<>();
        // 初始化高度为0
        int height = 0;
        // 将根节点放入当前层级队列
        curLevelQueue.offer(root);
        while (!curLevelQueue.isEmpty()) {
            // 只要当前层级队列不为空
            // 获取下一个队列节点
            BinaryTreeNode<E> curNode = curLevelQueue.poll();
            // 如果左孩子存在，则将左孩子放入下一层级队列
            if (curNode.leftChild != null) {
                nextLevelQueue.offer(curNode.leftChild);
            }
            // 如果右孩子存在，则将右孩子然如下一层级队列
            if (curNode.rightChild != null) {
                nextLevelQueue.offer(curNode.rightChild);
            }
            if (curLevelQueue.isEmpty()) {
                // 当前层级队列为空，表示一层已经遍历结束
                // 高度+1
                height++;
                // 将当前队列变量指向下一层级
                Deque<BinaryTreeNode<E>> tmp = curLevelQueue;
                curLevelQueue  = nextLevelQueue;
                nextLevelQueue = tmp;
            }
        }
        return height;
    }
}
