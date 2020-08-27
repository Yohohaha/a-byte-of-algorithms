package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.yohohaha.algorithms.学点算法十_二叉树中序遍历算法_迭代实现.inorderTraversal;
import static com.github.yohohaha.algorithms.学点算法十三_二叉树树根倒置层级遍历算法.reverseOrder;
import static com.github.yohohaha.algorithms.学点算法十二_将数组转为二叉树.convertArray2Tree;

/**
 * created at 2020/08/05 23:30:00
 *
 * @author Yohohaha
 */
public class 学点算法十四_二叉树打印美化算法 {

    private static class Position {
        public int height;
        public int order;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Position position = (Position) o;

            if (height != position.height) {
                return false;
            }
            return order == position.order;
        }

        @Override
        public int hashCode() {
            int result = height;
            result = 31 * result + order;
            return result;
        }

        public Position(int height, int order) {
            this.height = height;
            this.order = order;
        }

        @Override
        public String toString() {
            return "{" +
                "\"height\":" + height + "," +
                "\"order\":" + order +
                "}";
        }
    }

    @Test
    public void testBeautifyBinaryTreePrinting() {

        beautifyBinaryTreePrinting(convertArray2Tree(new Integer[]{1, 2, 33343434, 5, 6, null, null, 7}), 0, 4);
    }

    public static <E extends Comparable<E>> void beautifyBinaryTreePrinting(BinaryTreeNode<E> root, int levelInterval, int nodeInterval) {
        if (root == null) {
            return;
        }
        // 遍历树
        // 因为root不为null，所以高度为1
        int h = 1;
        int maxNodeLength = String.valueOf(root.data).length();
        // 当前层级的遍历队列
        Deque<BinaryTreeNode<E>> curLevelQueue = new ArrayDeque<>();
        Deque<BinaryTreeNode<E>> nextLevelQueue = new ArrayDeque<>();
        // 将root节点添加到遍历队列中作为起始遍历节点
        curLevelQueue.push(root);
        // 开始遍历
        while (!curLevelQueue.isEmpty()) {
            // 队列中还有待遍历元素
            // 取出下一个遍历元素
            BinaryTreeNode<E> currentNode = curLevelQueue.poll();
            maxNodeLength = Math.max(maxNodeLength, String.valueOf(currentNode.data).length());
            // 将左右孩子放入下一个层级的队列
            if (currentNode.leftChild != null) {
                nextLevelQueue.push(currentNode.leftChild);
            }
            if (currentNode.rightChild != null) {
                nextLevelQueue.push(currentNode.rightChild);
            }
            if (curLevelQueue.isEmpty()) {
                if (nextLevelQueue.isEmpty()) {
                    break;
                }
                Deque<BinaryTreeNode<E>> tmp = curLevelQueue;
                curLevelQueue = nextLevelQueue;
                nextLevelQueue = tmp;
                h++;
            }
        }
        // 获取最后一层元素个数
        int pow = (int) Math.pow(2.0d, h);
        Map<Position, Integer> positionMap = new HashMap<>(pow - 1);
        pow /= 2;
        for (int o = 0, col = 1; o < pow; o++, col += (maxNodeLength + nodeInterval)) {
            positionMap.put(new Position(h, o), col);
        }
        pow /= 2;
        for (int i = h - 1; i > 0; i--, pow /= 2) {
            for (int o = 0; o < pow; o++) {
                positionMap.put(new Position(i, o),
                    (positionMap.get(new Position(i + 1, o * 2)) + positionMap.get(new Position(i + 1, o * 2 + 1))) / 2
                );
            }
        }

    }
}
