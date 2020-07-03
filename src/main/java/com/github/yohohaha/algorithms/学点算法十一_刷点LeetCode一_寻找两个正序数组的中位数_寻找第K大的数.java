package com.github.yohohaha.algorithms;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * created at 2020/07/02 16:54:17
 *
 * @author Yohohaha
 */
public class 学点算法十一_刷点LeetCode一_寻找两个正序数组的中位数_寻找第K大的数 {
    @Test
    public void test() {
        assertEquals(2.0, findMedianSortedArrays(new int[]{1, 3}, new int[]{2}), 0.0001);
        assertEquals(2.5, findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}), 0.0001);
    }

    /**
     * 寻找两个正序数组的中位数
     *
     * @param nums1 正序数组1
     * @param nums2 正序数组2
     *
     * @return 两个正序数组的中位数
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) {
            // nums1为空
            // 根据假设，nums2不会为空
            return findWhenThereIsAnArrEmpty(nums2);
        }
        if (nums2 == null || nums2.length == 0) {
            // nums2为null
            return findWhenThereIsAnArrEmpty(nums1);
        }
        if (nums1[nums1.length - 1] <= nums2[0]) {
            // nums1在前，nums2在后
            return findWhenElementsNotCrossed(nums1, nums2);
        } else if (nums2[nums2.length - 1] <= nums1[0]) {
            // nums2在前，nums1在后
            return findWhenElementsNotCrossed(nums2, nums1);
        } else {
            // nums1和nums2数组元素之间有交叉
            if (nums1.length < nums2.length) {
                return findWhenElementsCrossed(nums2, nums1);
            } else {
                return findWhenElementsCrossed(nums1, nums2);
            }
        }
    }

    /**
     * 当两个数组中有一个为空的时候，null也为空
     *
     * @param theNotEmptyArr 不为空的数组
     *
     * @return 中位数
     */
    private static double findWhenThereIsAnArrEmpty(int[] theNotEmptyArr) {
        int len = theNotEmptyArr.length;
        // 找到中位数所在的索引位置
        int targetIdx = len >> 1;
        if (len % 2 == 0) {
            // 总个数为偶数
            // 需要获取目标索引位置前面的元素
            // 两个数求平均数作为中位数返回
            return (theNotEmptyArr[targetIdx] + theNotEmptyArr[targetIdx - 1]) / 2.0;
        } else {
            // 总个数为奇数
            // 直接返回目标索引的元素
            return theNotEmptyArr[targetIdx];
        }
    }

    /**
     * 当两个数组的元素完全排序后没有交叉
     *
     * @param formerArr 顺序在前的数组
     * @param latterArr 顺序在后的数组
     *
     * @return 中位数
     */
    private static double findWhenElementsNotCrossed(int[] formerArr, int[] latterArr) {
        int formerArrLen = formerArr.length;
        int latterArrLen = latterArr.length;
        int targetIdx = (formerArrLen + latterArrLen) >> 1;
        if (targetIdx < formerArrLen) {
            // 目标元素位于前面的数组中
            if ((formerArrLen + latterArrLen) % 2 == 0) {
                // 总个数为偶数
                // 需要获取目标索引位置前面的元素
                // 两个数求平均数作为中位数返回
                return (formerArr[targetIdx] + formerArr[targetIdx - 1]) / 2.0;
            } else {
                // 总个数为奇数
                // 直接返回目标索引的元素
                return formerArr[targetIdx];
            }
        } else {
            // 目标元素位于后面的数组中
            if ((formerArrLen + latterArrLen) % 2 == 0) {
                // 总个数为偶数
                if (targetIdx == formerArrLen) {
                    // 目标元素刚好是后面的数组的起始位置
                    // 取前面数组的最后一个元素和后面数组的第一个元素返回
                    return (formerArr[formerArrLen - 1] + latterArr[0]) / 2.0;
                } else {
                    // 目标元素不在后面数组的起始位置
                    // 需要获取目标索引位置前面的元素
                    // 两个数求平均数作为中位数返回
                    return (latterArr[targetIdx - formerArrLen] + latterArr[targetIdx - formerArrLen - 1]) / 2.0;
                }
            } else {
                // 总个数为奇数
                // 直接返回目标索引的元素
                return latterArr[targetIdx - formerArrLen];
            }
        }
    }

    /**
     * 当两个数组的元素完全排序后有交叉
     *
     * @param toArr   数组1
     * @param fromArr 数组2
     *
     * @return 中位数
     */
    private static double findWhenElementsCrossed(int[] longerArr, int[] shorterArr) {
        int m = longerArr.length;
        int n = shorterArr.length;
        // 左边应该有的元素个数
        int leftSize = (m + n + 1) / 2;
        int sMin = 0;
        int sMax = m + 1;
        int iMax = n;
        int s;
        int i;
        do {
            s = (sMin + sMax) / 2;
            i = leftSize - s;
            if (iMax < i) {
                // 说明短数组元素不够，需要从长数组中取元素
                // 将长数组分隔栏往右移
                sMin = s + 1;
            } else if (i < 0) {
                // 说明短数组元素太少，需要从短数组中取元素
                // 将长数组分隔栏往左移
                sMax = s;
            } else {
                // 满足分隔条件
                // 判断是否满足有效条件：
                //     1. 长数组分隔栏左边的最大值 < 短数组分隔栏右边的最小值
                //     2. 短数组分隔栏左边的最大值 < 长数组分隔栏右边的最小值
                if (isSorted(longerArr, s, shorterArr, i)) {
                    // 如果满足条件，说明找到元素，退出
                    break;
                }
            }
        } while (true);
        if ((m + n) % 2 == 0) {
            // 总个数是偶数
            // 取两个数组分隔栏前面的最大值和两个数组分隔栏后面的最小值求平均数

        } else {
            //
        }
        return 0.0;
    }

    private static boolean isSorted(int[] nums1, int i1, int[] nums2, int i2) {
        return !((i1 > 0 && i2 < nums2.length && nums1[i1 - 1] >= nums2[i2]) ||
            (i2 > 0 && i1 < nums1.length && nums2[i2 - 1] >= nums1[i1]));
    }

    /**
     * 查找特定元素插入一个有序数组后使数组继续有序的索引位置
     *
     * @param num 特定元素
     * @param arr 有序数组
     *
     * @return 特定元素插入一个有序数组后使数组继续有序的索引位置
     */
    private static int findIndexInSortedArr(int num, int[] arr) {
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            // 区间中至少有一个元素
            int mid = (lo + hi) / 2;
            int tmp = arr[mid];
            if (num < tmp) {
                // 往区间左边查找
                hi = mid;
            } else if (tmp < num) {
                // 往区间右边查找
                lo = mid + 1;
            } else {
                // 如果找到，那么此时的mid位置就是num要插入的index索引位置
                return mid;
            }
        }
        // 如果没有找到，那么此时的lo位置就是num要插入的index索引位置
        return lo;
    }
}
