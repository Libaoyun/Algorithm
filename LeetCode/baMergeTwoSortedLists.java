/**
 * 21、合并两个有序链表
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 * 示例 2：
 * 输入：l1 = [], l2 = []
 * 输出：[]
 * 示例 3：
 * 输入：l1 = [], l2 = [0]
 * 输出：[0]
 */
public class baMergeTwoSortedLists {

    public static void main(String[] args) {

        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);
        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);
        ListNode res = mergeTwoLists(l1, l2);
        while (res != null) {
            System.out.print(res.val + " ");
            res = res.next;
        }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        /** O(n+m)  O(1)
         * 方法一： 迭代
         */
        ListNode head = new ListNode(-1);
        ListNode prev = head;
        while (l1 != null && l2 != null){
            if (l1.val < l2.val){
                prev.next = l1;
                l1 = l1.next;
            }else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        // 如果l1已经空了，方便处理，不管l2是否空，直接把l2剩下的作为给prev.next的指向
        prev.next = l1 == null ? l2 : l1;
        return head.next;


        /** O(n+m) O(n+m)  空间需要m+n是因为地递归调用方法 需要消耗栈空间！！！
         * 方法二：递归
         * 每次往深层次递归，然后将每次递归的结果的作为当前指针的.next,然后返回当前节点，最深层的嵌套就是最后一个节点的结果
         */

//        if (l1 == null) {
//            return l2;
//        } else if (l2 == null) {
//            return l1;
//        } else if(l1.val < l2.val){
//            l1.next = mergeTwoLists(l1.next, l2);
//            return l1;
//        }else {
//            l2.next = mergeTwoLists(l1, l2.next);
//            return l2;
//        }
        // TODO 就像上面这样即可，每次遇到小的值就把这个.next及后面的节点作为新链表然后与另一个链表进行再次比较，
        //  最终返回的一定是l1或者l2的头结点，而后面的节点已经深层次被修改过了
        //  比如1->2->3->4->5->null。那么它的内容是，5->null然后把当前的5作为4.next，然后把4->5->null的4作为3.next，然后把3->4->5->null作为2.next，依此类推

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
