package cn.tulingxueyuan.leetcode;

import java.util.*;

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
//        ArrayList result = new ArrayList<>();
//        levelOrder(node1, 1, result);
//        System.out.println(Arrays.toString(result.toArray()));
        morrisPost(node1);
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
        levelOrder(root.left, i * 2, list);
        levelOrder(root.right, i * 2 + 1, list);
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

    /**
     * 前序遍历  根左右
     * @param root
     */
    public static void preOrderIter(TreeDeep.TreeNode root) {
        if (root != null) {
            Stack<TreeDeep.TreeNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                if (root != null) {
                    System.out.println(root.val);
                    stack.push(root.right);
                    stack.push(root.left);
                }
            }
        }
    }

    /**
     * 中序遍历
     * @param root
     */
    public static void midOrderIter(TreeDeep.TreeNode root) {
        if (root != null) {
            Stack<TreeDeep.TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    System.out.println(root.val);
                    root = root.right;
                }
            }
        }
    }

    /**
     * 层序遍历
     * @param root
     */
    public static void levelOrderIter(TreeDeep.TreeNode root) {
        Queue<TreeDeep.TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeDeep.TreeNode node = queue.poll();
            if (node != null) {
                System.out.println(node.val);
                queue.add(node.left);
                queue.add(node.right);
            }
        }
    }

    /**
     * 后续遍历
     * @param root
     */
    public static void postOrderIter(TreeDeep.TreeNode root) {
        if (root != null) {
            Stack<TreeDeep.TreeNode> stack = new Stack<>();
            TreeDeep.TreeNode pre = null;
            while (!stack.isEmpty() || root != null) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                if (root.right == null || root.right == pre) {
                    System.out.println(root.val);
                    pre = root;
                    root = null;
                } else {
                    stack.push(root);
                    root = root.right;
                }
            }
        }
    }

    public static void morrisMid(TreeDeep.TreeNode cur) {
        if (cur == null) {
            return;
        }
        TreeDeep.TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
//                    System.out.println(cur.val);
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
//                System.out.println(cur.val);
            }
            System.out.println(cur.val);
            cur = cur.right;
        }
    }

    public static void morrisPost(TreeDeep.TreeNode cur) {
        if (cur == null) {
            return;
        }
        TreeDeep.TreeNode root = cur;
        TreeDeep.TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                    printNode(cur.left);
                }
            }
            cur = cur.right;
        }
        printNode(root);
    }

    private static void printNode(TreeDeep.TreeNode head) {
        TreeDeep.TreeNode tail = reverse(head);
        while (tail != null) {
            System.out.println(tail.val);
            tail = tail.right;
        }
        reverse(tail);
    }

    private static TreeDeep.TreeNode reverse(TreeDeep.TreeNode head) {
        TreeDeep.TreeNode prev = null, curr, next;
        curr = head;
        while (curr != null) {
            next = curr.right;
            curr.right = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }


    public static void morrisPre(TreeDeep.TreeNode cur) {
        if (cur == null) {
            return;
        }
        TreeDeep.TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    System.out.println(cur.val);
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                System.out.println(cur.val);
            }
            cur = cur.right;
        }
    }

}
