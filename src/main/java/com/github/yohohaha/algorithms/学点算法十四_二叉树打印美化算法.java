package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.yohohaha.algorithms.学点算法十一_将数组转为二叉树.convertArray2Tree;

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
        beautifyBinaryTreePrinting(convertArray2Tree(new Integer[]{1,24, null,3}), 4);
    }

    public static <E extends Comparable<E>> void beautifyBinaryTreePrinting(BinaryTreeNode<E> root, int minNodeInterval) {
        if (root == null) {
            return;
        }
        if (minNodeInterval < 3) {
            throw new IllegalArgumentException("节点最小间隔必须大于3");
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
        while (true) {
            // 队列中还有待遍历元素
            // 取出下一个遍历元素
            BinaryTreeNode<E> currentNode = curLevelQueue.poll();
            maxNodeLength = Math.max(maxNodeLength, String.valueOf(currentNode.data).length());
            // 将左右孩子放入下一个层级的队列
            if (currentNode.leftChild != null) {
                nextLevelQueue.offer(currentNode.leftChild);
            }
            if (currentNode.rightChild != null) {
                nextLevelQueue.offer(currentNode.rightChild);
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
        curLevelQueue = null;
        nextLevelQueue = null;
        // 获取最后一层元素个数
        int allLen = (int) Math.pow(2.0d, h);
        int pow = allLen;
        Map<Position, Integer> positionMap = new HashMap<>(pow - 1);
        pow /= 2;
        for (int o = 0, col = 1; o < pow; o++, col += (maxNodeLength + minNodeInterval)) {
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
        List<BinaryTreeNode<E>> curList = new ArrayList<>(allLen / 2);
        List<BinaryTreeNode<E>> nextList = new ArrayList<>(allLen);
        curList.add(root);
        int order = 0;
        int height = 1;
        int levelSize = 1;
        BinaryTreeNode<E> NULL_NODE = new BinaryTreeNode<>(null);
        while (true) {
            // 队列中还有待遍历元素
            // 取出下一个遍历元素
            BinaryTreeNode<E> currentNode = curList.get(order);
            order++;
            // 将左右孩子放入下一个层级的队列
            if (currentNode.leftChild == null) {
                nextList.add(NULL_NODE);
            } else {
                nextList.add(currentNode.leftChild);
            }
            if (currentNode.rightChild == null) {
                nextList.add(NULL_NODE);
            } else {
                nextList.add(currentNode.rightChild);
            }
            if (levelSize <= order) {
                // 当前层已遍历结束
                // 打印当前层
                int startCol = 1;
                for (int o = 0; o < levelSize; o++) {
                    Integer nodeCol = positionMap.get(new Position(height, o));
                    int prefixLength = maxNodeLength / 2;
                    int nodeStartCol = nodeCol - prefixLength;
                    for (int i = 0; i < nodeStartCol - startCol; i++) {
                        System.out.print(" ");
                    }
                    BinaryTreeNode<E> node = curList.get(o);
                    if (node == NULL_NODE) {
                        for (int i = 0; i < maxNodeLength; i++) {
                            System.out.print(" ");
                        }
                    } else {
                        System.out.print(toString(node, maxNodeLength));
                    }
                    startCol = nodeStartCol + maxNodeLength;
                }
                // 当前层打印结束
                if (height + 1 > h) {
                    break;
                }
                // 打印父亲连线层
                System.out.println();
                startCol = 1;
                for (int o = 0; o < levelSize; o++) {
                    BinaryTreeNode<E> leftNode = nextList.get(o * 2);
                    BinaryTreeNode<E> rightNode = nextList.get(o * 2 + 1);
                    Integer nodeCol = positionMap.get(new Position(height, o));
                    Integer leftCol = positionMap.get(new Position(height + 1, o * 2));
                    Integer rightCol = positionMap.get(new Position(height + 1, o * 2 + 1));
                    for (int i = startCol; i <= leftCol; i++) {
                        System.out.print(" ");
                    }
                    if (leftNode != NULL_NODE) {
                        // 需打印左孩子
                        // _的起始位置由左孩子的位置决定
                        // _的结束位置由自身节点的位置决定
                        for (int i = leftCol + 1; i < nodeCol; i++) {
                            System.out.print("_");
                        }
                    } else {
                        for (int i = leftCol + 1; i < nodeCol; i++) {
                            System.out.print(" ");
                        }
                    }
                    if (leftNode == rightNode) {
                        System.out.print(" ");
                    } else {
                        System.out.print("|");
                    }
                    if (rightNode != NULL_NODE) {
                        // 需打印右孩子
                        // _的起始位置由自身节点的位置决定
                        // _的结束位置由右孩子的位置决定
                        for (int i = nodeCol + 1; i < rightCol; i++) {
                            System.out.print("_");
                        }
                    } else {
                        for (int i = nodeCol + 1; i < rightCol; i++) {
                            System.out.print(" ");
                        }
                    }
                    startCol = rightCol;
                }
                // 打印孩子连线层
                System.out.println();
                startCol = 1;
                for (int o = 0; o < levelSize * 2; o++) {
                    BinaryTreeNode<E> node = nextList.get(o);
                    Integer nodeCol = positionMap.get(new Position(height + 1, o));
                    for (int i = startCol; i < nodeCol; i++) {
                        System.out.print(" ");
                    }
                    if (node == NULL_NODE) {
                        System.out.print(" ");
                    } else {
                        System.out.print("|");
                    }
                    startCol = nodeCol + 1;
                }
                System.out.println();
                height++;
                order = 0;
                levelSize = (int) Math.pow(2.0d,height - 1);
                List<BinaryTreeNode<E>> tmp = curList;
                curList = nextList;
                nextList = tmp;
                nextList.clear();
            }
        }
    }

    private static <E extends Comparable<E>> String toString(BinaryTreeNode<E> node, int size) {
        StringBuilder builder = new StringBuilder(size);
        String nodeString = String.valueOf(node.data);
        for (int i = 0; i < size - nodeString.length(); i++) {
            builder.append("_");
        }
        builder.append(nodeString);
        return builder.toString();
    }

}
