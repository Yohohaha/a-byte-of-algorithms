package com.github.yohohaha.algorithms;

import com.github.yohohaha.algorithms.commons.BinaryTreeNode;

import java.util.Objects;

/**
 * created at 2020/07/30 15:32:46
 *
 * @author Yohohaha
 */
public class 学点算法十三_二叉搜索树算法_递归实现 {
    public static void main(String[] args) {

    }

    /**
     * 二叉搜索树
     *
     * @param <E> 二叉搜索树元素类型
     */
    public static class BinaryTree<E extends Comparable<E>> {
        private BinaryTreeNode<E> root;

        public BinaryTree(BinaryTreeNode<E> root) {
            this.root = root;
        }

        /**
         * 返回元素为指定元素的节点，（查找过程中第一次遇到即返回），
         * 如果找不到，则返回如果将节点插入后父节点
         * 注：在插入实现中，因为将元素插入到最左边，所以返回的元素是插入最早的元素
         *
         * @param e 元素
         *
         * @return 元素为指定的元素的节点
         */
        public BinaryTreeNode<E> search(E e) {
            if (e == null || this.root == null) {
                // 查找元素为null或者为空树则返回null
                return null;
            }
            BinaryTreeNode<E> currentNode = this.root;
            while (true) {
                int compareResult = currentNode.data.compareTo(e);
                if (compareResult < 0) {
                    // 目标值比根节点大
                    // 到右子树中查找
                    // 如果没有右子树，则直接返回该节点（作为父节点返回）
                    if (currentNode.rightChild == null) {
                        return currentNode;
                    }
                    currentNode = currentNode.rightChild;
                } else if (compareResult > 0) {
                    // 目标值比根节点小
                    // 到左子树中查找
                    // 如果没有左子树，则直接返回该节点（作为父节点返回）
                    if (currentNode.leftChild == null) {
                        return currentNode;
                    }
                    currentNode = currentNode.leftChild;
                } else {
                    // 找到目标值
                    return currentNode;
                }
            }
        }

        private BinaryTreeNode<E> searchIn(E e, BinaryTreeNode<E> currentNode) {
            int compareResult = currentNode.data.compareTo(e);
            if (compareResult < 0) {
                // 目标值比根节点大
                // 到右子树中查找
                // 如果没有右子树，则直接返回该节点（作为父节点返回）
                if (currentNode.rightChild == null) {
                    return currentNode;
                }
                return searchIn(e, currentNode.rightChild);
            } else if (compareResult > 0) {
                // 目标值比根节点小
                // 到左子树中查找
                // 如果没有左子树，则直接返回该节点（作为父节点返回）
                if (currentNode.leftChild == null) {
                    return currentNode;
                }
                return searchIn(e, currentNode.leftChild);
            } else {
                // 找到目标值
                return currentNode;
            }
        }

        public boolean add(E e) {
            if (e == null) {
                // 不允许添加null
                return false;
            }
            if (this.root == null) {
                this.root = new BinaryTreeNode<>(e);
                return true;
            }
            BinaryTreeNode<E> currentNode = this.root;
            while (true) {
                int compareResult = currentNode.data.compareTo(e);
                if (compareResult < 0) {
                    // 将元素插入到右子树中
                    if (currentNode.rightChild == null) {
                        // 无右子树，直接插入
                        currentNode.rightChild = new BinaryTreeNode<>(currentNode, e);
                        return true;
                    }
                    currentNode = currentNode.rightChild;
                } else {
                    // 将元素插入到左子树中
                    if (currentNode.leftChild == null) {
                        // 无左子树，直接插入
                        currentNode.leftChild = new BinaryTreeNode<>(currentNode, e);
                        return true;
                    }
                    currentNode = currentNode.leftChild;
                }
            }
        }

        public boolean addIn(E e, BinaryTreeNode<E> currentNode) {
            int compareResult = currentNode.data.compareTo(e);
            if (compareResult < 0) {
                // 将元素插入到右子树中
                if (currentNode.rightChild == null) {
                    // 无右子树，直接插入
                    currentNode.rightChild = new BinaryTreeNode<>(currentNode, e);
                    return true;
                }
                return addIn(e, currentNode.rightChild);
            } else {
                // 将元素插入到左子树中
                if (currentNode.leftChild == null) {
                    // 无左子树，直接插入
                    currentNode.leftChild = new BinaryTreeNode<>(currentNode, e);
                    return true;
                }
                return addIn(e, currentNode.leftChild);
            }
        }

        public boolean remove(E e) {
            if (e == null) {
                // 不支持查找null
                return false;
            }
            BinaryTreeNode<E> findNode = search(e);
            if (!Objects.equals(findNode.data, e)) {
                // 没有找到节点
                return false;
            }
            // 如果没有孩子节点
            if (findNode.leftChild == null && findNode.rightChild == null) {
                BinaryTreeNode<E> parent = findNode.parent;
                if (parent.leftChild == findNode) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
            }
            // 如果有一个孩子节点
            if ((findNode.leftChild == null && findNode.rightChild != null)) {
                BinaryTreeNode<E> successorNode = findSuccessor(findNode);
                moveLeafNodeToInternalAndRemoveInternalNode(findNode, successorNode);
            }
            if (findNode.leftChild != null && findNode.rightChild == null) {
                BinaryTreeNode<E> predecessorNode = findPredecessor(findNode);
                moveLeafNodeToInternalAndRemoveInternalNode(findNode, predecessorNode);
            }
            // 如果有两个孩子节点
            if (findNode.leftChild != null && findNode.rightChild != null) {
                BinaryTreeNode<E> successorNode = findSuccessor(findNode);
                moveLeafNodeToInternalAndRemoveInternalNode(findNode, successorNode);
            }
            return true;
        }

        private static <E extends Comparable<E>> void moveLeafNodeToInternalAndRemoveInternalNode(BinaryTreeNode<E> internalNode, BinaryTreeNode<E> leafNode) {
            // 临时保存内部节点的父节点
            BinaryTreeNode<E> internalParent = internalNode.parent;
            // 临时保存叶子节点的父节点
            BinaryTreeNode<E> leafParent = leafNode.parent;

            // 叶子需要更改父节点和左右孩子的绑定
            leafNode.parent = internalParent;
            leafNode.leftChild = internalNode.leftChild;
            leafNode.rightChild = internalNode.rightChild;

            // 断开叶子节点父节点绑定
            if (leafParent.leftChild == leafNode) {
                // 在左孩子处接上
                leafParent.leftChild = null;
            } else {
                // 在右孩子处接上
                leafParent.rightChild = null;
            }

            // 在内部节点处接上叶子节点
            // 父节点接上
            if (internalParent != null) {
                if (internalParent.leftChild == internalNode) {
                    // 在左孩子处接上
                    internalParent.leftChild = leafNode;
                } else {
                    // 在右孩子处接上
                    internalNode.rightChild = leafNode;
                }
            }
            // 左孩子接上
            if (leafNode.leftChild != null) {
                leafNode.leftChild.parent = leafNode;
            }
            // 右孩子接上
            if (leafNode.rightChild != null) {
                leafNode.rightChild.parent = leafNode;
            }

            // 删除内部节点
            internalNode = null;
        }

        private static <E extends Comparable<E>> BinaryTreeNode<E> findPredecessor(BinaryTreeNode<E> node) {
            // 前继者在左子树
            BinaryTreeNode<E> currentNode = node;
            // 找左子树中最左边的孩子
            while (currentNode != null) {
                if (currentNode.leftChild == null) {
                    return node;
                }
                currentNode = currentNode.leftChild;
            }
            // 找不到则返回node
            // 此时node肯定为null，所以返回null即可
            return null;
        }

        private static <E extends Comparable<E>> BinaryTreeNode<E> findSuccessor(BinaryTreeNode<E> node) {
            // 后继者在右子树
            BinaryTreeNode<E> currentNode = node.rightChild;
            // 找右子树中最左边的孩子
            while (currentNode != null) {
                if (currentNode.leftChild == null) {
                    return node;
                }
                currentNode = currentNode.leftChild;
            }
            // 找不到则返回node
            return node;
        }
    }
}
