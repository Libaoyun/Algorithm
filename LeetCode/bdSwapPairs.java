import utils.ListNode;

/**
 * 24、 两两交换链表中的节点
 * 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。
 * 你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 * 示例1：
 * 输入：head = [1,2,3,4]
 * 输出：[2,1,4,3]
 * 示例 2：
 * 输入：head = []
 * 输出：[]
 * 示例 3：
 * 输入：head = [1]
 * 输出：[1]
 */
public class bdSwapPairs {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        ListNode res = swapPairs(head);
        while (res!=null){
            System.out.println(res.val);
            res = res.next;
        }
    }

    /** 最简单的方法利用数组重新构造链表不行，因为题目要求只能节点交换*/

    /** 核心代码就是交换，将1指向3，然后2指向1，最后返回2：
        node1.next = node2.next  // 1->3
        node2.next = node1       // 2->1
        最后return node2即2->1->3
     */

    public static ListNode swapPairs(ListNode head) {
        /** 时间复杂度：O(n)  空间复杂度：O(n)，因为递归就是栈，会占用空间
         * 方法一：递归
         */

        if (head == null || head.next == null) {
            return head;
        }

        // TODO 1->2->3->null，最简单的，将1.next指向3(即next.next)，然后将2.next指向1，最后将2作为头结点，就变成了2->1->3
        // 比如head = [1,2]，那么newHead = head.next = 2，head.next = 2.next即null
        // 于是就变成了head.next=null即1->null，最后一步newHead.next = head即2->1，然后返回newHead作为头节点即2->1->null
        // 暂存新节点为当前节点的下一个节点
        /*
        ListNode newHead = head.next;
        // 然后将当前节点的next再指向下一个节点的下一个节点，
        head.next = swapPairs(newHead.next);
        // 然后将新节点next指向当前节点
        newHead.next = head;
        return newHead;
        */
        /**
         * 这里其实一直是跳跃，中间隔一个来交换的，newHead已经是next了，但是传给下一次的时候传的却是newHead.next。
         * 并且利用了递归的特性，从最后的两个节点开始交换，比如[1,2,3,4]，4->3完毕后返回给递归上一层的1.next，即1->4->3，
         * 然后newHead是2(即原先的1.next)，将2.next又指向了1，最后将2作为头结点返回
         */


        /** 时间复杂度：O(n)  空间复杂度：O(1)
         * 方法2：迭代
         */
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode temp = dummy;
        // 如果为奇数节点或者末尾节点，那就不管直接按照原先指针指向最后一个节点即可
        while(temp.next!=null && temp.next.next!=null){
            ListNode node1 = temp.next;
            ListNode node2 = temp.next.next;
            temp.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            temp = node1;
            /**
             * 上面的temp.next = node2;与temp = node1;相辅相成，比如1->2->3->4,
             * 首先temp.next即头结点2，然后temp=1到下一轮，temp.next即4，
             * 这样就能每次将 上次的末尾节点 与 下一轮要排序的头结点 进行连接
             */
        }
        return dummy.next;

    }
}
