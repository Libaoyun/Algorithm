/**
 * 19、删除链表的倒数第 N 个结点
 * 输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 * 输入：head = [1], n = 1
 * 输出：[]
 * 输入：head = [1,2], n = 1
 * 输出：[1]
 * 进阶：你能尝试使用一趟扫描实现吗？
 */
public class aiRemoveNthFromEnd {

    public static void main(String[] args) {

        ListNode head =  new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ListNode test = head;
        while(test!=null){
            System.out.print(test.val);
            test = test.next;
        }
        System.out.println();
        ListNode result = removeNthFromEnd(head, 2);
        while(result!=null){
            System.out.print(result.val);
            result = result.next;
        }
    }


    public static ListNode removeNthFromEnd(ListNode head, int n) {
        /** O(L)  O(1)
         * 方法一：最容易想到但是却又要遍历两次的方法，先遍历一趟获取长度，再遍历一趟，只要到length - n + 1，然后next=next.next即可
         * 比如head = [1,2,3,7,5]，len=5，n = 2可知要删除的是7，即5-2+1=4，head[4]=7，这里是从1开始因为不是数组
         */
        /*
        ListNode dummy = new ListNode(0, head);
        int len = 0;
        while (head != null){
            head = head.next;
            len++;
        }
        ListNode cur = dummy;
        for (int i = 0; i < len - n + 1; i++) {
            cur = cur.next;
        }
        // TODO 因为平时cur = cur.next;相当于只是把值赋给了cur而并没有覆盖
        // 相当于覆盖，直接跳过了一个节点付给了下一个节点
        cur.next = cur.next.next;
        // 因为dummy初始化时，NodeList的构造方法就是this.next = next;，即this.next=head。
        return dummy.next;
        */

        /** O(L)  O(1)
         * 方法二：双指针  定义两个指针，中间的间隔为n，然后直到头指针达到末尾，就意味着另一个指针就是需要删除的节点
         */
        ListNode dummy = new ListNode(0, head);
        ListNode first = head;
        ListNode second = dummy;
//        System.out.println(first.val);
//        System.out.println(second.val+"   "+ second.next.val);
        // TODO 倒数第一个就是最后一个，倒数第二个中间才隔了一个，因此从1开始，然后前面first初始就是第一个，second初始是第0个。
        //  second相当于前置了0,但是没关系还是正常比对，因为最后会second.next = second.next.next;即还是会往后隔一个
        int count = 1;
        while (first.next != null){
            first = first.next;
            if (count < n){
                count++;
            }else {
                second = second.next;
            }
        }
        second.next = second.next.next;
        return dummy.next;

        /**
         * 还有一种最容易想到的方法就是利用栈特性。先将数据全放进去栈，然后再出栈，第n个的时候忽略，最后再倒序输出
         * 考虑到取出和倒序麻烦，因此用双端队列：
         */

        /**
         * ListNode dummy = new ListNode(0, head);
         *         Deque<ListNode> stack = new LinkedList<ListNode>();
         *         ListNode cur = dummy;
         *         while (cur != null) {
         *             stack.push(cur);
         *             cur = cur.next;
         *         }
         *         // 12345，n=2，目前stack为123
         *         for (int i = 0; i < n; ++i) {
         *             stack.pop();
         *         }
         *         // 然后stack.peek()把双端队列的头取出，即3，因为是后进先出因此取得是最后一个
         *         ListNode prev = stack.peek();
         *         // 找到3后把后续的链修改即可
         *         prev.next = prev.next.next;
         *         ListNode ans = dummy.next;
         *         return ans;
         */
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
