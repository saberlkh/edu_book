package cn.tulingxueyuan.leetcode;

import java.util.Arrays;

/**
 * @Classname MaxProduct
 * @Description TODO
 * @Date 2025/2/12 14:53
 * @Created by liukaihua
 */
public class MaxProduct {

    public static void main(String[] args) {
        System.out.println(sort(new int[]{1, 2, 3, 4, 5, 6}));
        System.out.println(getMaxNum(new int[]{1, 2, 3, 4, 5, 6}));
    }

    public static int sort(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return Math.max(nums[0] * nums[1] * nums[n - 1], nums[n - 1] * nums[n - 2] * nums[n - 3]);
    }

    public static int getMaxNum(int[] nums) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        for (int x : nums) {
            if (x <= min1) {
                min2 = min1;
                min1 = x;
            } else if (x <= min2) {
                min2 = x;
            }
            if (x >= max1) {
                max3 = max2;
                max2 = max1;
                max1 = x;
            } else if (x >= max2) {
                max3 = max2;
                max2 = x;
            } else if (x >= max3) {
                max3 = x;
            }
        }
        return Math.max(min1 * min2 * max1, max1 * max2 * max3);
    }

}
