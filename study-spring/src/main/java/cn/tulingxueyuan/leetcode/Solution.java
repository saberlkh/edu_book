package cn.tulingxueyuan.leetcode;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Solution
 * @Description TODO
 * @Date 2025/2/12 15:08
 * @Created by liukaihua
 */
public class Solution {

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(solution1(new int[]{1, 2, 3, 4, 5, 6}, 10)));
        System.out.println(JSON.toJSONString(solution(new int[]{1, 2, 3, 4, 5, 6}, 10)));
        System.out.println(JSON.toJSONString(twoSearch(new int[]{1, 2, 3, 4, 5, 6}, 10)));
        System.out.println(JSON.toJSONString(twoPoint(new int[]{1, 2, 3, 4, 5, 6}, 10)));
    }

    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     * @param nums
     * @param target
     * @return
     */
    public static int[] solution(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i +1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    public static int[] solution1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    /**
     * 二分查找
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSearch(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int low = i, high = nums.length - 1;
            while (low <= high) {
                int mid = (high - low) / 2 + low;
                if (nums[mid] == target - nums[i]) {
                    return new int[]{i, mid};
                } else if (nums[mid] > target - nums[i]) {
                    high = mid - 1;
                } else  {
                    low = mid + 1;
                }
            }
        }
        return new int[]{};
    }

    /**
     * 双指针
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoPoint(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int sum = nums[low] + nums[high];
            if (sum == target) {
                return new int[]{low, high};
            } else if (sum < target) {
                low++;
            } else {
                high--;
            }
        }
        return new int[]{};
    }

}