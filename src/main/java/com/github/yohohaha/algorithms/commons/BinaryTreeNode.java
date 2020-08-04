package com.github.yohohaha.algorithms.commons;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BinaryTreeNode<?> that = (BinaryTreeNode<?>) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
