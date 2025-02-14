package cn.tulingxueyuan.leetcode;

/**
 * @Classname MaxSeq
 * @Description 找出最长递增有序数列
 * @Date 2025/2/14 11:33
 * @Created by liukaihua
 */
public class MaxSeq {

    public static void main(String[] args) {
        System.out.println(findLength(new int[]{1, 2, 3, 2, 3, 4, 3, 4, 5, 6, 7}));
    }

    /**
     * 贪心算法
     * @param nums
     * @return
     */
    public static int findLength(int[] nums) {
        int start = 0;
        int max = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                start = i;
            }
            max = Math.max(max, i - start + 1);
        }
        return max;
    }

}
