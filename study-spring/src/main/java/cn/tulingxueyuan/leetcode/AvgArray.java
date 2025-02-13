package cn.tulingxueyuan.leetcode;

/**
 * @Classname AvgArray
 * @Description TODO
 * @Date 2025/2/13 16:39
 * @Created by liukaihua
 */
public class AvgArray {

    public static void main(String[] args) {
        System.out.println(findMaxAvg(new int[]{1, 12, -5, -6, 50, 3}, 4));
    }

    public static double findMaxAvg(int[] nums, int k) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        int max = sum;
        for (int i = k; i < n; i++) {
            sum = sum - nums[i - k] + nums[i];
            max = Math.max(max, sum);
        }
        return 1.0 * max / k;
    }

}
