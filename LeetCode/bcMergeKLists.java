import java.util.PriorityQueue;

/**
 * 23、合并 K 个升序链表
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * 示例 1：
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 * 示例 2：
 *
 * 输入：lists = []
 * 输出：[]
 * 示例 3：
 *
 * 输入：lists = [[]]
 * 输出：[]
 */
public class bcMergeKLists {

    public static void main(String[] args) {
        ListNode[] lists = new ListNode[3];
        lists[0] = new ListNode(1, new ListNode(4, new ListNode(5)));
        lists[1] = new ListNode(1, new ListNode(3, new ListNode(4)));
        lists[2] = new ListNode(2, new ListNode(6));
        ListNode result = mergeKLists(lists);
        while (result != null){
            System.out.print(result.val + " ");
            result = result.next;
        }
    }

    /**
     * 合并k个有序链表之前，我们要先知道如何合并两个有序链表，这里我们看下ba(第21题)的方法
     * ListNode head = new ListNode(-1);
     *         ListNode prev = head;
     *         while (l1 != null && l2 != null){
     *             if (l1.val < l2.val){
     *                 prev.next = l1;
     *                 l1 = l1.next;
     *             }else {
     *                 prev.next = l2;
     *                 l2 = l2.next;
     *             }
     *             prev = prev.next;
     *         }
     *         // 如果l1已经空了，方便处理，不管l2是否空，直接把l2剩下的作为给prev.next的指向
     *         prev.next = l1 == null ? l2 : l1;
     *         return head.next;
     */

    public static ListNode mergeKLists(ListNode[] lists) {
        /** 时间复杂度O(n*k^2),空间复杂度O(1)
         * 方法一：顺序合并：循环lists.length次，每次两两比较，拿lists中的一个链表与当前答案ans进行两两排序
         */
        /*ListNode ans = null;
        for (ListNode list : lists) {
            ans = mergeTwoLists(ans, list);
        }
        return ans;*/


        /** O(kn×logk) O(logk)  提交耗时1ms，超过100%
         * 方法二：分治法递归，每次将k个链表分成两半，分别排序，然后合并后继续排序
         * 分治算法基于递归实现，这种结构使得问题可以被分解成更小的、相同或相似的子问题，然后递归地解决这些子问题，
         * 并将其结果组合以获得原问题的解。这种递归结构有助于清晰地表达问题的解法，并可以更容易地进行优化和调试。
         * 比如1~3，下标就是0~2，那么步骤就是0-1(0-0,1-1这两个合并),2-2，在0-1返回的一个链表后再与2进行合并
         */
        return merge(lists, 0,  lists.length - 1);
    }

    // 公共的方法：合并两个有序链表
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2){
        if (l1 == null || l2 == null) {
            return l1 != null ? l1 : l2;
        }
        ListNode head = new ListNode(-1);
        ListNode tail = head, a = l1, b = l2;
        while (a != null && b != null){
            if (a.val < b.val){
                tail.next = a;
                a = a.next;
            }else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null ? a : b);
        return head.next;
    }

    public static ListNode merge(ListNode[] lists, int left, int right){
        if (left == right){
            return lists[left];
        }
        if (left > right){
            return null;
        }
        // 这句代码就相当于是求平均值，(left + right) >> 1就是(left + right) / 2，右移一位就是除2
        int mid = (left + right) >> 1;
        // TODO 分治递归，每次递归到底以后，合并左右两个链表然后作为返回值再与上一层的左/右链表继续合并
        //  好处就是分治思想，将问题分解成更小的子问题，然后递归地解决这些子问题，最后将结果组合以获得原问题的解
        return mergeTwoLists(merge(lists, left, mid), merge(lists, mid+1, right));
    }


    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {
        }
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    /** O(kn×logk)   O(k)   4ms击败69.49%使用 Java 的用户
     * 方法三：使用优先队列合并
     * 优先队列：优先队列是一种特殊的队列，它允许插入元素并删除队列中的最小（或最大）元素。
     * 优先队列有offer()入列、poll()出列、peek()返回头索引等方法。
     * 在offer(x)操作后，会自动将x插入到队列中，并保持队列的顺序。内部是堆排序，将x入堆。
     * poll()操作后，会返回队列中的最小元素，并将其从队列中删除。
     */
  /*
    class Solution3 {
        class Status implements Comparable<Status> {
            int val;
            // TODO 这里的ptr是指向当前节点的，既定义了指向链表节点，又保存了val
            ListNode ptr;

            Status(int val, ListNode ptr) {
                this.val = val;
                this.ptr = ptr;
            }

            public int compareTo(Status status2) {
                return this.val - status2.val;
            }
        }

        // TODO 优先队列的元素Status，或者其他类，都必须要继承Comparable接口，否则会报错，
        //  且继承后必须有重写compareTo()方法。因为内部使用的是堆排序，必须要有比较的条件
        PriorityQueue<Status> queue = new PriorityQueue<Status>();

        public ListNode mergeKLists(ListNode[] lists) {
            // TODO 先将每个链表的头元素放入队列
            for (ListNode node: lists) {
                if (node != null) {
                    queue.offer(new Status(node.val, node));
                }
            }
            // 定义最终结果链表
            ListNode head = new ListNode(0);
            ListNode tail = head;
            while (!queue.isEmpty()) {
                // TODO 出列，取出最小元素放入结果的链表尾
                Status f = queue.poll();
                // 就相当于tail.next = list(某节点)  ; tail = tail.next
                tail.next = f.ptr;
                tail = tail.next;
                if (f.ptr.next != null) {
                    queue.offer(new Status(f.ptr.next.val, f.ptr.next));
                }
            }
            return head.next;
        }
    }
*/
}
