package com.github.yohohaha.algorithms.commons;

/**
 * created at 2020/07/30 15:29:50
 *
 * @author Yohohaha
 */
public class BinaryTreeNode<E extends Comparable<E>> {
    public BinaryTreeNode<E> parent;
    public BinaryTreeNode<E> leftChild;
    public BinaryTreeNode<E> rightChild;
    public E data;

    public BinaryTreeNode(E data) {
        this.data = data;
    }

    public BinaryTreeNode(BinaryTreeNode<E> parent, E data) {
        this.parent = parent;
        this.data = data;
    }
}
