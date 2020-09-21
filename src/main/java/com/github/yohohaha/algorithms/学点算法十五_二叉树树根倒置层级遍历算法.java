package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import static com.github.yohohaha.algorithms.学点算法十二_将数组转为二叉树.convertArray2Tree;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * created at 2020/08/03 16:35:01
 *
 * @author Yohohaha
 */
public class 学点算法十五_二叉树树根倒置层级遍历算法 {

    @Test
    public void testReverseOrder() {
        BinaryTreeNode<Integer> root = convertArray2Tree(new Integer[]{1, 2, 3, 4, 5, 8, 7, null, 9});
        Deque<Deque<BinaryTreeNode<Integer>>> outerStack = reverseOrder(root);
        List<Integer> elems = new ArrayList<>();
        for (Deque<BinaryTreeNode<Integer>> internalStack : outerStack) {
            for (BinaryTreeNode<Integer> integerBinaryTreeNode : internalStack) {
                elems.add(integerBinaryTreeNode.data);
            }
        }
        assertThat(elems, is(Arrays.asList(9, 4, 5, 8, 7, 2, 3, 1)));
    }

    public static <E extends Comparable<E>> Deque<Deque<BinaryTreeNode<E>>> reverseOrder(BinaryTreeNode<E> root) {
        if (root == null) {
            return null;
        }
        // 外部栈
        Deque<Deque<BinaryTreeNode<E>>> outerStack = new ArrayDeque<>();
        // 内部栈
        Deque<BinaryTreeNode<E>> internalStack = new ArrayDeque<>(1);
        // 遍历队列
        Deque<BinaryTreeNode<E>> queue = new ArrayDeque<>();
        // 遍历根节点
        queue.offer(root);
        internalStack.push(root);
        // 第一层级完毕
        outerStack.push(internalStack);
        // 下一层级待遍历节点数
        int toVisitedNodeCnt = 2;
        // 生成下一层级
        internalStack = new ArrayDeque<>(toVisitedNodeCnt);
        // 当前层级遍历数
        int visitedCnt = 0;
        BinaryTreeNode<E> currentNode;
        while ((currentNode = queue.poll()) != null) {
            // 有剩余节点
            // 遍历右子节点
            if (currentNode.rightChild != null) {
                queue.offer(currentNode.rightChild);
                internalStack.push(currentNode.rightChild);
            }
            // 遍历左子节点
            if (currentNode.leftChild != null) {
                queue.offer(currentNode.leftChild);
                internalStack.push(currentNode.leftChild);
            }
            // 当前层级遍历数+2
            visitedCnt += 2;
            if (visitedCnt == toVisitedNodeCnt) {
                // 如果当前层级遍历数达到要求
                if (internalStack.isEmpty()) {
                    // 如果没有新节点
                    // 直接返回
                    return outerStack;
                }
                // 添加新层级
                outerStack.push(internalStack);
                // 下一层级待遍历节点数
                toVisitedNodeCnt = internalStack.size() << 1;
                // 生成下一层级
                internalStack = new ArrayDeque<>(toVisitedNodeCnt);
                // 重置层级遍历数
                visitedCnt = 0;
            }
        }
        return outerStack;
    }
}
