/**
 * 2. 两数相加
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * eg:
 * 2 -> 4 -> 3
 * 5 -> 6 -> 4
 * ==========>   因为342 + 465 = 807
 * 7 -> 0 -> 8
 * 1 6 3
 * 4 5 5
 * 5 1 9
 */
public class bTwoNumberAdd {

    public static void main(String[] args) {
        // 243
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        // 564
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode result = addTwoNumbers(l1, l2);
        // 输出
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }

    /**
     *由于俩都是逆序，因此直接各位相加即可，因为比如2 -> 4 -> 3与5 -> 6 -> 4，
     * 即342+465显然个位相加是2+5,十位是4+6，百位是3+4，因此直接相加即可
     * 就和日常加法计算是一样的只不过刚好反过来更有利于链表相加了：
     *   百 十 个
     *   3  4  2
     * + 4  6  5
     * ==========>
     *   8  0  7
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int sum = n1 + n2 + carry;
            if (head == null) {
                head = tail = new ListNode(sum % 10);
            } else {
                // 尾指针更新为新的最后一个节点
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        // TODO 最后的进位不要忘！
        if (carry > 0) {
            tail.next = new ListNode(carry);
        }
        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {
        }
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
