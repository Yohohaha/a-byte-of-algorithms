package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * created at 2020/07/30 15:37:45
 *
 * @author Yohohaha
 */
public class 学点算法十_二叉树中序遍历算法_迭代实现 {
    public static void main(String[] args) {

    }

    /**
     * 二叉树中序遍历算法(迭代实现)
     * @param root 二叉树根节点
     * @return 二叉树遍历结果列表
     */
    public static List<Integer> inorderTraversal(BinaryTreeNode<Integer> root) {
        if (root == null) {
            // 根节点为null，返回空列表
            return Collections.emptyList();
        }
        // 结果列表
        List<Integer> resultList = new ArrayList<>();
        // 缓存节点的栈
        Deque<BinaryTreeNode<Integer>> stack = new ArrayDeque<>();
        // 当前节点，可理解为遍历的指针
        BinaryTreeNode<Integer> currentNode = root;
        // 遍历开始
        while (true) {
            // 寻找当前节点的最左子孩子，并且将途中遇到的节点入栈
            while (currentNode != null) {
                // 将节点入栈
                stack.push(currentNode);
                // 继续查找左孩子
                currentNode = currentNode.leftChild;
            }
            if (stack.isEmpty()) {
                // 栈为空，表示已经没有剩余节点了，遍历结束，退出
                break;
            }
            // 前面while条件判断currentNode为null时，可以理解为遍历了左子节点
            // 弹出之前保存的中间节点，即为父节点
            currentNode = stack.pop();
            // 将值添加到结果集中，此为遍历方法
            resultList.add(currentNode.data);
            // 将遍历指针转向右子孩子，继续遍历右子孩子
            currentNode = currentNode.rightChild;
        }
        return resultList;
    }
}
