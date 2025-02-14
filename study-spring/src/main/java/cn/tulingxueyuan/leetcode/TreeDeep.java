package cn.tulingxueyuan.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Classname TreeDeep
 * @Description TODO
 * @Date 2025/2/14 11:01
 * @Created by liukaihua
 */
public class TreeDeep {

    public static void main(String[] args) {
        TreeNode node7 = new TreeNode(7, null, null);
        TreeNode node6 = new TreeNode(6, node7, null);
        TreeNode node5 = new TreeNode(5, null, null);
        TreeNode node4 = new TreeNode(4, null, null);
        TreeNode node3 = new TreeNode(3, node6, null);
        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode node1 = new TreeNode(1, node2, node3);
        System.out.println(minDepth(node1));
        System.out.println(minDepth1(node1));
    }

    /**
     * 广度优先
     * @param root
     * @return
     */
    public static int minDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        root.deep = 1;
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left == null && node.right == null) {
                return node.deep;
            }
            if (node.left != null) {
                node.left.deep = node.deep + 1;
                queue.offer(node.left);
            }
            if (node.right != null) {
                node.right.deep = node.deep + 1;
                queue.offer(node.right);
            }
        }
        return 0;
    }

    /**
     * 深度优先
     * @param root
     * @return
     */
    public static int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        int min = Integer.MAX_VALUE;
        if (root.left != null) {
            min = Math.min(min, minDepth(root.left));
        }
        if (root.right != null) {
            min = Math.min(min, minDepth(root.right));
        }
        return min + 1;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        int deep;

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.left = left;
            this.right = right;
            this.val = val;
        }
    }

}
