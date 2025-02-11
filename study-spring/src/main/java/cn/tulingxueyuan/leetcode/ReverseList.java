package cn.tulingxueyuan.leetcode;

import com.alibaba.fastjson.JSON;

public class ReverseList {

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(ListNode next, int val) {
            this.next = next;
            this.val = val;
        }
    }

    public static ListNode iterate(ListNode head) {
        ListNode pre = null, next;
        ListNode curr = head;
        while (curr != null) {
            next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    /**
     * 递归
     * @param head
     * @return
     */
    public static ListNode recursion(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = recursion(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(null, 1);
        ListNode head2 = new ListNode(head, 2);
        ListNode head3 = new ListNode(head2, 3);
        ListNode head4 = new ListNode(head3, 4);
        ListNode prev = recursion(head4);
        System.out.println(JSON.toJSONString(prev));
    }

}
