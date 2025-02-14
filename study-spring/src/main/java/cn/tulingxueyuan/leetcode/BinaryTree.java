package cn.tulingxueyuan.leetcode;

/**
 * @Classname BinaryTree
 * @Description TODO
 * @Date 2025/2/14 15:28
 * @Created by liukaihua
 */
public class BinaryTree {

    public static void main(String[] args) {
        TreeDeep.TreeNode node7 = new TreeDeep.TreeNode(7, null, null);
        TreeDeep.TreeNode node6 = new TreeDeep.TreeNode(6, null, null);
        TreeDeep.TreeNode node5 = new TreeDeep.TreeNode(5, node6, node7);
        TreeDeep.TreeNode node4 = new TreeDeep.TreeNode(4, null, null);
        TreeDeep.TreeNode node3 = new TreeDeep.TreeNode(3, null, null);
        TreeDeep.TreeNode node2 = new TreeDeep.TreeNode(2, node4, node5);
        TreeDeep.TreeNode node1 = new TreeDeep.TreeNode(1, node2, node3);
        midOrder(node1);
    }

    public static void preOrder(TreeDeep.TreeNode root) {
        if (root == null) return;
        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void midOrder(TreeDeep.TreeNode root) {
        if (root == null) return;
        midOrder(root.left);
        System.out.println(root.val);
        midOrder(root.right);
    }

}
