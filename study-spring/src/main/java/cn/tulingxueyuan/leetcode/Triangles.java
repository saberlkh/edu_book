package cn.tulingxueyuan.leetcode;

import java.util.Arrays;

/**
 * @Classname Triangles
 * @Description TODO
 * @Date 2025/2/14 14:47
 * @Created by liukaihua
 */
public class Triangles {

    public static void main(String[] args) {
        System.out.println(largestPerimeter(new int[]{3, 6, 2, 3}));
    }

    /**
     * 求三角形的最大周长
     * @param nums
     * @return
     */
    public static int largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        for (int i = nums.length - 1; i >= 2; i--) {
            if (nums[i - 1] + nums[i - 2] > nums[i]) {
                return nums[i - 1] + nums[i -2 ] + nums[i];
            }
        }
        return 0;
    }

}
