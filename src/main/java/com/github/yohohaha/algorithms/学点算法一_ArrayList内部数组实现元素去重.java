package com.github.yohohaha.algorithms;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * created at 2020/07/01 17:18:40
 *
 * @author Yohohaha
 */
public class 学点算法一_ArrayList内部数组实现元素去重 {

    public static class MyArrayListTest {
        /**
         * 测试元素添加
         */
        @Test
        public void testMyArrayListAdd() {
            MyArrayList myList = new MyArrayList(10);
            for (int i = 0; i < 10; i++) {
                // 依次添加0~9的元素
                myList.add(i);
            }
            Object[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            assertEquals(
                Arrays.toString(expected),
                myList.toString()
            );
        }

        /**
         * 测试元素删除
         */
        @Test
        public void testMyArrayListRemove() {
            MyArrayList myList = new MyArrayList(10);
            for (int i = 0; i < 10; i++) {
                // 依次添加0~9的元素
                myList.add(i);
            }
            myList.remove(5);
            Object[] expected = {0, 1, 2, 3, 4, 6, 7, 8, 9};
            assertEquals(
                Arrays.toString(expected),
                myList.toString()
            );
        }
    }

    public static class DeduplicateTest {
        MyArrayList myList = new MyArrayList(20);

        @Before
        public void initList() {
            // [123, 24, 42, 24, 123, 56, 92, 68, 24, 56, 73]
            myList.add(123);
            myList.add(24);
            myList.add(42);
            myList.add(24);
            myList.add(123);
            myList.add(56);
            myList.add(92);
            myList.add(68);
            myList.add(24);
            myList.add(56);
            myList.add(73);
            assertEquals("[123, 24, 42, 24, 123, 56, 92, 68, 24, 56, 73]", myList.toString());
        }

        /**
         * 测试remove方法实现去重
         */
        @Test
        public void testDeduplicate01() {
            myList.deduplicate01();
            assertEquals("[123, 24, 42, 56, 92, 68, 73]", myList.toString());
        }

        @Test
        public void testDeduplicate02() {
            myList.deduplicate02();
            assertEquals("[123, 24, 42, 56, 92, 68, 73]", myList.toString());
        }
    }




    private static class MyArrayList {
        private int size; // 规模
        private Object[] elems; // 数据区

        /**
         * 构造数组容量为c的ArrayList
         *
         * @param c 数组容量
         */
        public MyArrayList(int c) {
            this.elems = new Object[c];
            this.size = 0;
        }

        /**
         * 添加元素
         *
         * @param o 元素对象
         */
        public void add(Object o) {
            if (this.elems.length <= this.size) {
                throw new ArrayIndexOutOfBoundsException("数组容量已满，请勿添加元素");
            }
            this.elems[size++] = o;
        }

        /**
         * 删除索引为i的元素
         *
         * @param i 索引
         *
         * @return 索引为i的元素
         */
        public Object remove(int i) {
            if (i < 0 || this.size <= i) {
                throw new ArrayIndexOutOfBoundsException("数组大小为" + this.size);
            }
            Object o = elems[i];
            System.arraycopy(elems, i + 1, elems, i, this.size - (i + 1));
            size--;
            return o;
        }

        /**
         * toString方法实现，方便我们查看数组元素情况
         *
         * @return
         */
        @Override
        public String toString() {
            if (elems == null) {
                return "null";
            }

            int iMax = size - 1;
            if (iMax == -1) {
                return "[]";
            }

            StringBuilder b = new StringBuilder();
            b.append('[');
            for (int i = 0; ; i++) {
                b.append(elems[i]);
                if (i == iMax) {
                    return b.append(']').toString();
                }
                b.append(", ");
            }
        }

        /**
         * remove方法实现去重
         */
        public void deduplicate01() {
            if (this.size <= 1) {
                // 只有一个元素，无需去重，直接返回
                return;
            }
            // 从索引为1开始遍历数组元素
            for (int i = 1; i < size; i++) {
                for (int j = 0; j < i; j++) {
                    // 对于第i个元素，到该数组中继续从头开始查找之前是否出现过该元素
                    if (Objects.equals(elems[j], elems[i])) {
                        // 之前出现过该元素，调用remove方法将它删除
                        remove(i);
                        // 如果元素删除后，遍历索引也需要减一个元素大小，这步很容易遗漏，要注意
                        i--;
                        // 已经判定有重复元素，并且已经删除，查找过程结束
                        break;
                    }
                }
            }
        }

        /**
         * 标记清除法实现去重
         */
        public void deduplicate02() {
            if (this.size <= 1) {
                // 只有一个元素，无需去重，直接返回
                return;
            }
            // 重复元素个数
            int dupCnts = 0;
            // 从1开始遍历数组元素
            for (int i = 1; i < size; i++) {
                for (int j = 0; j < i; j++) {
                    if (Objects.equals(elems[j], elems[i])) {
                        // 之前出现过该元素，将它标记为null
                        elems[i] = null;
                        // 重复元素个数+1
                        dupCnts++;
                        break;
                    }
                }
            }
            if (dupCnts == 0) {
                // 没有重复元素
                return;
            }
            int j = 1; // null元素区间开始位置
            int k = 1; // 非null元素区间开始位置
            int len = 0; // 非null元素区间长度
            // 删除标记为null的元素
            for (int i = 1; // 遍历索引
                 i < size;
                 i++) {
                if (elems[i] == null) {
                    // 如果为null元素
                    if (len > 0) {
                        // 如果有待移动的非null元素
                        if (j < k) {
                            // 如果需要移动，如果j==k则不需要移动
                            // 将非null区间元素移到null元素区间开始的位置
                            System.arraycopy(elems, k, elems, j, len);
                        }
                        // 移动之后null元素的开始位置需要更新
                        j = j + len;
                        // 移动完毕，非null元素区间长度重置为0
                        len = 0;
                    }
                } else {
                    // 如果为非null元素
                    if (len == 0) {
                        // 如果非null区间元素长度为0，表示一个新的非null元素区间开始
                        // 更新k的值
                        k = i;
                    }
                    // 非null元素区间长度+1
                    len++;
                }
            }
            if (len > 0) {
                // 需要处理末尾剩余元素
                // 这里j肯定小于k，无需判断
                System.arraycopy(elems, k, elems, j, len);
            }
            // 因为删除了重复元素，所以数组大小需要减去相应元素个数
            size -= dupCnts;
        }

    }

}
