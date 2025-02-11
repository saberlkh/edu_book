package cn.tulingxueyuan.leetcode;

import java.util.Arrays;

/**
 * @Classname ArrayCenterIndex
 * @Description TODO
 * @Date 2025/2/11 18:00
 * @Created by liukaihua
 */
public class ArrayCenterIndex {

    public static void main(String[] args) {
        System.out.println(pivotIndex(new int[]{1, 7, 3, 6, 5, 6}));
    }

    /**
     * 给定一个整数类型的数组 nums，请编写一个能够返回数组“中心索引”的方法。
     * @param nums
     * @return
     */
    public static int pivotIndex(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
            if (total == sum) {
                return i;
            }
            sum -= nums[i];
        }
        return -1;
    }

}
