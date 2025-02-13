package cn.tulingxueyuan.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @Classname LinkCycle
 * @Description TODO
 * @Date 2025/2/13 15:11
 * @Created by liukaihua
 */
public class LinkCycle {

    public static void main(String[] args) {
        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        node5.next = node1;
        System.out.println(hasCycle(node1));
        System.out.println(hasCycle2(node1));
    }

    public static boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode quick = head.next;
        while (slow != quick) {
            if (quick == null || quick.next == null) {
                return false;
            }
            quick = quick.next.next;
            slow = slow.next;
        }
        return true;
    }

    public static boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (!set.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }



}
