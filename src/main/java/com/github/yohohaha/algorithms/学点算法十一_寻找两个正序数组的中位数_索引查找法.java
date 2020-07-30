package com.github.yohohaha.algorithms;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * created at 2020/07/02 16:54:17
 *
 * @author Yohohaha
 */
public class 学点算法十一_寻找两个正序数组的中位数_索引查找法 {
    @Test
    public void test() {
        assertEquals(2.0, findMedianSortedArrays(new int[]{1, 3}, new int[]{2}), 0.0001);
        assertEquals(2.5, findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}), 0.0001);
    }
    /**
     * 寻找两个正序数组的中位数
     *
     * @param nums1
     * @param nums2
     *
     * @return
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
                return findWhenElementsCrossed(nums2, nums1).get();
            } else {
                return findWhenElementsCrossed(nums1, nums2).orElseGet(() -> findWhenElementsCrossed(nums2, nums1).get());
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
    private static Optional<Double> findWhenElementsCrossed(int[] toArr, int[] fromArr) {
        int m = toArr.length;
        int n = fromArr.length;
        int targetIdx = (m + n) >> 1;
        // 从一个数组中二分法取出查找它在另一个的数组中的索引位置
        int lo = 0;
        int hi = n;
        while (lo < hi) {
            // 区间中至少有一个元素
            int mid = (lo + hi) >> 1;
            int idxInToArr = findIndexInSortedArr(fromArr[mid], toArr);
            int resultIdx = idxInToArr + mid;
            if (targetIdx < resultIdx) {
                // 往区间左边查找
                hi = mid;
            } else if (resultIdx < targetIdx) {
                // 往区间右边查找
                lo = mid + 1;
            } else {
                // 找到
                if ((m + n) % 2 == 0) {
                    // 总个数为偶数
                    // 那么我们还需要前一个元素的索引
                    int prevNum;
                    if (mid > 0) {
                        // 如果fromArr中可以取到前一个元素
                        if (idxInToArr > 0) {
                            // 如果toArr中可以取到前一个元素
                            // 两个数组都可以取到前一个元素
                            // 进行对比，哪个大，就取哪个作为前一个元素
                            // 取到前一个元素后与目标元素求平均值返回
                            prevNum = Math.max(fromArr[mid - 1], toArr[idxInToArr - 1]);
                        } else {
                            // 如果toArr中取不到前一个元素
                            // fromArr中可以取到前一个元素，toArr中取不到前一个元素
                            // 直接取fromArr中的元素作为前一个元素
                            // 取到前一个元素后与目标元素求平均值返回
                            prevNum = fromArr[mid - 1];
                        }
                    } else {
                        // 如果fromArr中取不到前一个元素
                        if (idxInToArr > 0) {
                            // 如果toArr中可以取到前一个元素
                            // fromArr中可以取到前一个元素，toArr中取不到前一个元素
                            // 直接取toArr中的元素作为前一个元素
                            prevNum = toArr[idxInToArr - 1];
                        } else {
                            // 如果toArr中取不到前一个元素
                            // fromArr和toArr都取不到前一个元素
                            // 这种情况不可能
                            throw new IllegalStateException("不可能两个数组都取不到前一个元素");
                        }
                    }
                    return Optional.of((fromArr[mid] + prevNum) / 2.0);
                } else {
                    // 总个数为奇数
                    // 直接取该元素作为中位数
                    return Optional.of(((double) (fromArr[mid])));
                }
            }
        }
        // 此时 hi <= lo
        // 说明没有找到
        return Optional.empty();
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
