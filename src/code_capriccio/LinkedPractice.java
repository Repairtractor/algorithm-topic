package code_capriccio;

import java.util.Objects;

//链表相关
public class LinkedPractice {


    //203.移除链表元素
   static class Solution {
        public ListNode removeElements(ListNode head, int val) {
            ListNode dummy = new ListNode(-1);
            dummy.next = head;
            ListNode current = dummy;

            while (current.next!=null) {
                if (current.next.val == val) {
                    current.next = current.next.next;
                    continue;
                }
                current = current.next;
            }
            return dummy.next;
        }

        public static void main(String[] args) {
            ListNode listNode = ListNode.buildFormArray(new int[]{1,2,6,3,4,5,6});
            ListNode listNode1 = new Solution().removeElements(listNode, 6);
            listNode1.print();
        }
    }

    //206.反转链表
    class Solution1 {
        public ListNode reverseList(ListNode head) {
            //采用头插法反转
            ListNode dummy = new ListNode(-1),curr=head;
            while (curr!=null) {
                ListNode next = curr.next;
                curr.next = dummy.next;
                dummy.next=curr;
                curr=next;
            }
            return dummy.next;
        }

    }

    //24. 两两交换链表中的节点
 private   static class Solution2 {
        public ListNode swapPairs(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }

            //利用一个假节点和快慢指针，l，r 只需要暂时保留r的next,temp，然后让r.next=l,l.next=temp,dummy=r就行，然后r直接=temp
            ListNode dummy = new ListNode(-1),curr=dummy,l=head,r=head.next;

            while (l!=null&&r!=null) {
                ListNode next = r.next;
                r.next=l;
                l.next=next;

                curr.next=r;
                curr=l;
                if (next!=null) {
                    r=next.next;
                }
                l=next;
            }

            return dummy.next;
        }

        public static void main(String[] args) {
            ListNode listNode = ListNode.buildFormArray(new int[]{1,2,6,3,4,5,6});
            ListNode listNode1 = new Solution2().swapPairs(listNode);
            listNode1.print();
        }
    }

    //19.删除链表的倒数第N个节点
   private static class Solution3 {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(-1);
            dummy.next = head;
            //倒数第n个节点，也就是说只需要双指针，fast，slow，当fast与slow相差n个节点顺序，当fast走到结尾时，只需要将slow的下一个指针去掉就好
            ListNode fast=dummy,slow=dummy;
            for (int i=0;i<=n;i++){ //因为从假节点开始，所以其实是走n+1步
                fast=fast.next;
                if (fast==null) {
                    break;
                }
            }
            while (fast!=null){
                fast=fast.next;
                slow=slow.next;
            }
            slow.next = slow.next.next;
            return dummy.next;
        }

        public static void main(String[] args) {
            ListNode listNode = ListNode.buildFormArray(new int[]{1});
            ListNode listNode1 = new Solution3().removeNthFromEnd(listNode,1);
            if (listNode1!=null)
                listNode1.print();
        }
    }

    //160.链表相交

    private static class Solution4 {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            //双指针从a和b的两端一起走，当一方走到头时，另一个的位置的位置离终点的距离就是a和b的长度差距，
            //此时直接让两个节点pa->pb走，当pb走完直接->pa走，当两个节点相等时，就说明有环，哈哈，其实就是相当与弥补相差的节点
            ListNode currA = headA,currB = headB;
            boolean flagA = false,flagB = false;
            while (currA!=null&&currB!=null){
                if (currA==currB)return currA;
                currA = currA.next;
                currB = currB.next;
                if (currA==null&&!flagA){
                    currA =headB;
                    flagA = true;
                }
                if (currB==null&&!flagB){
                    currB =headA;
                    flagB = true;
                }
            }
            return null;
        }

        public static void main(String[] args) {

        }
    }

}
