package code_capriccio;

public  class ListNode {
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

        public static ListNode buildFormArray(int [] nums){
            if (nums.length == 0) return null;
            ListNode head = new ListNode(nums[0]);
            ListNode current = head;
            for (int i = 1; i < nums.length; i++) {
                current.next = new ListNode(nums[i]);
                current = current.next;
            }
            return head;
        }

        public void print(){
            ListNode current = this;
            do {
                System.out.print(current.val+"\t");
                current = current.next;
            }while (current != null);
        }

    }