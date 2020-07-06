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
        assertEquals(2, findMedianSortedArrays(new int[]{1, 2}, new int[]{1, 2, 3}), 0.0001);
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
     * @param longerArr  长数组
     * @param shorterArr 短数组
     *
     * @return 中位数
     */
    private static double findWhenElementsCrossed(int[] longerArr, int[] shorterArr) {
        int m = longerArr.length;
        int n = shorterArr.length;
        // 左边应该有的元素个数
        int leftSize = (m + n + 1) / 2;
        // 长数组分隔栏最小值
        int sMin = 0;
        // 长数组分隔栏最大值（取不到）
        int sMax = m + 1;
        // 短数组分隔栏最小值
        int iMin = 0;
        // 短数组分隔栏最大值（取不到）
        int iMax = n + 1;
        // 长数组分隔栏索引
        int s;
        // 短数组分隔栏索引
        int i;
        do {
            s = (sMin + sMax) / 2;
            i = leftSize - s;
            if (iMax <= i) {
                // 说明短数组元素不够，需要从长数组中取更多的元素
                // 将长数组分隔栏往右移
                sMin = s + 1;
            } else if (i < iMin) {
                // 说明短数组元素太少，需要减少长数组中的元素
                // 将长数组分隔栏往左移
                sMax = s;
            } else {
                // 满足分隔条件
                // sMin <= s <= m
                // iMin <= i <= n
                // 判断是否满足有效条件（两个条件必须同时满足）：
                //     1. 长数组分隔栏左边的最大值 <= 短数组分隔栏右边的最小值
                //          如果不满足，说明长数组取的值大了，将长数组分隔栏往左移
                //     2. 短数组分隔栏左边的最大值 <= 长数组分隔栏右边的最小值
                //          如果不满足，说明长数组取的值小了，将长数组分隔栏往右移
                if (getSeparatorMax(longerArr, s) > getSeparatorMin(shorterArr, i)) {
                    // 不满足第1个条件
                    sMax = s;
                    continue;
                }
                if (getSeparatorMax(shorterArr, i) > getSeparatorMin(longerArr, s)) {
                    // 不满足第2个条件
                    sMin = s + 1;
                    continue;
                }
                // 两个条件都满足
                // 退出循环
                break;
            }
        } while (sMin < sMax);
        if ((m + n) % 2 == 0) {
            // 总个数是偶数
            // 取两个数组分隔栏前面的最大值和两个数组分隔栏后面的最小值求平均数
            double max = getMax(longerArr, shorterArr, s, i);
            double min = getMin(longerArr, shorterArr, s, i);
            return (max + min) / 2.0;
        } else {
            // 总个数是奇数
            // 取两个数组分隔栏前面的最大值
            return getMax(longerArr, shorterArr, s, i);
        }
    }

    /**
     * 取分隔栏右边的最小值
     * @param nums 数组
     * @param i 分隔栏
     * @return 分隔栏右边的最小值
     */
    private static int getSeparatorMin(int[] nums, int i) {
        if (i < nums.length) {
            return nums[i];
        } else {
            // 已经到达最右端，返回最大哨兵元素
            return Integer.MAX_VALUE;
        }
    }

    /**
     * 取分隔栏左边的最大值
     * @param nums 数组
     * @param i 分隔栏
     * @return 分隔栏左边的最大值
     */
    private static int getSeparatorMax(int[] nums, int i) {
        if (i > 0) {
            return nums[i - 1];
        } else {
            // 已经到达最左端，返回最小哨兵元素
            return Integer.MIN_VALUE;
        }
    }

    /**
     * 获取分隔栏左边的最大值
     * @param longerArr 长数组
     * @param shorterArr 短数组
     * @param s 长数组分隔栏索引
     * @param i 短数组分隔栏索引
     * @return 分隔栏左边的最大值
     */
    private static double getMax(int[] longerArr, int[] shorterArr, int s, int i) {
        int max;
        if (s > 0) {
            if (i > 0) {
                max = Math.max(longerArr[s - 1], shorterArr[i - 1]);
            } else {
                max = longerArr[s - 1];
            }
        } else {
            if (i > 0) {
                max = shorterArr[i - 1];
            } else {
                throw new IllegalStateException("取不到max");
            }
        }
        return max;
    }

    /**
     * 获取分隔栏左边的最小值
     * @param longerArr 长数组
     * @param shorterArr 短数组
     * @param s 长数组分隔栏索引
     * @param i 短数组分隔栏索引
     * @return 分隔栏左边的最小值
     */
    private static double getMin(int[] longerArr, int[] shorterArr, int s, int i) {
        int min;
        if (s < longerArr.length) {
            if (i < shorterArr.length) {
                min = Math.min(longerArr[s], shorterArr[i]);
            } else {
                min = longerArr[s];
            }
        } else {
            if (i < shorterArr.length) {
                min = shorterArr[i];
            } else {
                throw new IllegalStateException("取不到min");
            }
        }
        return min;
    }

}
