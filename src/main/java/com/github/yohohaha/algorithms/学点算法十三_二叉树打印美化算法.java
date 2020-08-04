package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import static com.github.yohohaha.algorithms.学点算法十二_将数组转为二叉树.convertArray2Tree;

/**
 * created at 2020/08/03 16:35:01
 *
 * @author Yohohaha
 */
public class 学点算法十三_二叉树打印美化算法 {

    @Test
    public void testReverseOrder() {
        BinaryTreeNode<Integer> root = convertArray2Tree(new Integer[]{1, 2, 3, 4, 5});
        Deque<Deque<BinaryTreeNode<Integer>>> stack = reverseOrder(root);
        Iterator<Deque<BinaryTreeNode<Integer>>> iter = stack.descendingIterator();
        while (iter.hasNext()) {
            Deque<BinaryTreeNode<Integer>> deque = iter.next();
            for (BinaryTreeNode<Integer> integerBinaryTreeNode : deque) {
                System.out.println(integerBinaryTreeNode.data);
            }
        }
    }

    private static <E extends Comparable<E>> Deque<Deque<BinaryTreeNode<E>>> reverseOrder(BinaryTreeNode<E> root) {
        if (root == null) {
            return null;
        }
        // 外部栈
        Deque<Deque<BinaryTreeNode<E>>> stack = new ArrayDeque<>();
        // 内部栈
        Deque<BinaryTreeNode<E>> deque = new ArrayDeque<>(1);
        // 遍历队列
        Deque<BinaryTreeNode<E>> queue = new ArrayDeque<>();
        BinaryTreeNode<E> currentNode = root;
        deque.push(currentNode);
        stack.push(deque);
        deque = new ArrayDeque<>(deque.size() * 2);
        do {
            boolean isLast = false;
            if (currentNode.rightChild != null) {
                queue.offer(currentNode.rightChild);
                deque.push(currentNode.rightChild);
                if (currentNode.parent == stack.peekFirst().peekLast()) {
                    isLast = true;
                }
            }
            if (currentNode.leftChild != null) {
                queue.offer(currentNode.leftChild);
                deque.push(currentNode.leftChild);
                if (currentNode.parent == stack.peekFirst().peekLast()) {
                    isLast = true;
                }
            }
            if (isLast) {
                stack.push(deque);
                deque = new ArrayDeque<>(deque.size() * 2);
            }
        } while ((currentNode = queue.poll()) != null);
        return stack;
    }
}
