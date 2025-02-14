package cn.tulingxueyuan.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        ArrayList result = new ArrayList<>();
        levelOrder(node1, 1, result);
        System.out.println(Arrays.toString(result.toArray()));
    }

    public static void levelOrder(TreeDeep.TreeNode root, int i, ArrayList list) {
        if (root == null) return;
        int length = list.size();
        if (length <= i) {
            for (int j = 0; j <= i - length; j++) {
                list.add(length + j, null);
            }
        }
        list.set(i, root.val);
        levelOrder(root.left, 2 * i, list);
        levelOrder(root.right, 2 * i + 1, list);
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

    public static void lastOrder(TreeDeep.TreeNode root) {
        if (root == null) return;
        lastOrder(root.left);
        lastOrder(root.right);
        System.out.println(root.val);
    }

}
