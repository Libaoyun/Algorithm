import utils.ListNode;

/**
 * 25、 K 个一组翻转链表
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * k小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * 你不能只是修改节点值而是需要实际进行节点交换。
 * 示例：
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 * 输入：head = [1,2,3,4,5], k = 3
 * 输出：[3,2,1,4,5] //每三个一组反转，剩下的不够一组则直接不管
 */
public class beReverseKGroup {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ListNode result = reverseKGroup(head, 3);
        while (result != null){
            System.out.println(result.val);
            result = result.next;
        }
    }



    public static ListNode reverseKGroup(ListNode head, int k) {
        /**
         * 时间复杂度为 O(n*K),即O(n)~O(n^2); 空间复杂度为O(1)
         * 相关：https://leetcode.cn/problems/reverse-nodes-in-k-group/solutions/10416/tu-jie-kge-yi-zu-fan-zhuan-lian-biao-by-user7208t/
         */
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode end = dummy;
        while(end.next != null){
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) break;
            ListNode next = end.next;
            ListNode start = pre.next;
            // 这里要先给end.next指向null，这样反转的时候就只会从start到这里，而不会继续往后。
            end.next = null;
            pre.next = reverse(start);
            // 这里需要将start.next指向next，因为反转前的链表头结点是反转后的尾结点，所以需要指向下一个节点
            // 一开始可能start是1，反转后就变成还是1，但是位置却加了k,start之前的指向已经被reverse给改过了，即start前驱是2
            // 因此这里start引用还是没变的，只是之前的节点被修改了
            start.next = next;
            pre = start;
            end = start;
        }
        return dummy.next;

    }

    // TODO 这段翻转链表的代码很重要！！！
    public static ListNode reverse(ListNode head) {
        /**
         * 将某链表进行反转，使用递归方法，最终返回反转后的头结点
         */

        // 定义前驱节点和当前节点，用来让当前节点指向前驱节点，并且每次循环的当前节点作为下一次的前驱节点被指向，
        // 同时在最后让当前节点指向下一个节点，但已被篡改所以需要保存next。在最开始pre=null，因为反转后链表尾肯定是null
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        // 确保反转后的头结点是反转前的尾结点(空之前的，即有效的节点)
        return pre;
    }
}
