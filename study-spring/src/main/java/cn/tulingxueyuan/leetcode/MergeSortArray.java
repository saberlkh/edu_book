package cn.tulingxueyuan.leetcode;

import java.util.Arrays;

/**
 * @Classname MergeSortArray
 * @Description TODO
 * @Date 2025/2/13 15:33
 * @Created by liukaihua
 */
public class MergeSortArray {

    public static void main(String[] args) {
        int[] nums1 = {1, 3, 5, 7, 9, 0, 0, 0, 0};
        int[] nums2 = {2, 4, 6, 8};
//        System.out.println(Arrays.toString(merge(nums1, 5, nums2, 4)));
        System.out.println(Arrays.toString(merge2(nums1, 5, nums2, 4)));
    }

    public static int[] merge(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);
        return nums1;
    }

    public static int[] merge2(int[] nums1, int m, int[] nums2, int n) {
        int[] nums1_copy = new int[m + n];
        System.arraycopy(nums1, 0, nums1_copy, 0, m);
        int p1 = 0;
        int p2 = 0;
        int p = 0;
        while (p1 < m && p2 < n) {
            nums1[p++] = nums1_copy[p1] < nums2[p2] ? nums1_copy[p1++] : nums2[p2++];
        }
        if (p1 < m) {
            System.arraycopy(nums1_copy, p1, nums1, p1 + p2, m + n - p1 - p2);
        }
        if (p2 < n) {
            System.arraycopy(nums2, p2, nums1, p1 + p2, m + n - p1 - p2);
        }
        return nums1;
    }

}
