package utils;

/**
 * @Describe:  模拟链表节点类
 * @Author: LiBaoYun
 * @Date: 2024-04-23 9:50
 */
public class ListNode {
    public int val;
    public ListNode next;
    ListNode() {
    }
    public ListNode(int val) {
        this.val = val;
    }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}