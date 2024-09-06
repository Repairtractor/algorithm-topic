package code_capriccio;


import java.util.*;

//栈和队列
public class StackPractice {

    //232.用栈实现队列
    private static class MyQueue {
        //栈是先进后出，所以只需要有两个栈，然后在出和入的时候做一下
        Deque<Integer> pushStack, popStack;

        public MyQueue() {
            //栈是先进后出，所以只需要有两个栈，然后在出和入的时候做一下
            pushStack = new ArrayDeque<>();
            popStack = new ArrayDeque<>();
        }

        public void push(int x) {
            pushStack.push(x);
        }

        public int pop() {
            if (popStack.isEmpty()) {
                while (!pushStack.isEmpty()) {
                    popStack.push(pushStack.pop());
                }
            }
            return popStack.pop();
        }

        public int peek() {
            if (popStack.isEmpty()) {
                while (!pushStack.isEmpty()) {
                    popStack.push(pushStack.pop());
                }
            }
            return popStack.peek();
        }

        public boolean empty() {
            return popStack.isEmpty() && pushStack.isEmpty();
        }
    }

    //232.用队列实现栈
    private static class MyStack {
        Deque<Integer> queue;

        public MyStack() {
            queue = new ArrayDeque<>();
        }

        public void push(int x) {
            queue.push(x);
        }

        public int pop() {

            for (int i = 1; i < queue.size(); i++) {
                queue.push(queue.removeLast());
            }
            return queue.removeLast();
        }

        public int top() {
            for (int i = 1; i < queue.size(); i++) {
                queue.push(queue.removeLast());
            }
            Integer res = queue.peekLast();
            queue.push(queue.removeLast());
            return res;
        }

        public boolean empty() {
            return queue.isEmpty();
        }

        public static void main(String[] args) {
            MyStack myStack = new MyStack();
            myStack.push(1);
            myStack.push(2);
            System.out.println(myStack.pop());

        }
    }

    //20. 有效的括号
    private static class Solution {
        public boolean isValid(String s) {
            // ({[]})
            Deque<Character> stack = new LinkedList<>();
            if (s.isEmpty()) return false;
            for (char c : s.toCharArray()) {
                if (c == '(' || c == '{' || c == '[') stack.push(c);
                else {
                    if (stack.isEmpty()) return false;
                    switch (c) {
                        case ')':
                            if (stack.pop() != '(') return false;
                            break;
                        case '}':
                            if (stack.pop() != '{') return false;
                            break;
                        case ']':
                            if (stack.pop() != '[') return false;
                            break;
                    }
                }
            }

            return stack.isEmpty();
        }
    }

    //1047. 删除字符串中的所有相邻重复项
    private static class Solution1 {
        public String removeDuplicates(String s) {
            if (s.isEmpty()) return "";
            Deque<Character> stack = new LinkedList<>();
            for (char c : s.toCharArray()) {
                if (stack.isEmpty()) stack.push(c);
                else {
                    if (stack.peek() == c) stack.pop();
                    else stack.push(c);
                }
            }
            StringBuilder res = new StringBuilder();
            while (!stack.isEmpty()) {
                res.append(stack.pop());
            }


            return res.reverse().toString();
        }

        public static void main(String[] args) {
            System.out.println(new Solution1().removeDuplicates("abbaca"));
        }
    }

    //150. 逆波兰表达式求值
    private static class Solution3 {
        public int evalRPN(String[] tokens) {
            if (tokens == null || tokens.length == 0) return 0;
            //tokens = ["2","1","+","3","*"]
            Deque<Integer> stack = new LinkedList<>();
            for (String token : tokens) {
                if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                    Integer t1 = stack.pop(), t2 = stack.pop();
                    switch (token) {
                        case "+":
                            stack.push(t1 + t2);
                            break;
                        case "-":
                            stack.push(t2 - t1);
                            break;
                        case "*":
                            stack.push(t1 * t2);
                            break;
                        case "/":
                            stack.push(t2 / t1);
                            break;
                    }
                    continue;
                }
                stack.push(Integer.parseInt(token));
            }


            return stack.peek();
        }

        public static void main(String[] args) {
            System.out.println(new Solution3().evalRPN(new String[]{"2", "1", "+", "3", "*"}));
        }
    }

    //239. 滑动窗口最大值
    private static class Solution2 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums == null || nums.length == 0) return new int[0];
            //利用优先最大队列试试
            PriorityQueue<Integer> queue = new PriorityQueue<>(k, ((o1, o2) -> o2 - o1));
            //首先设置初始滑动窗口
            int i = 0, l = 0;
            int[] res = new int[nums.length - k + 1]; //最大只会存储数组长度
            for (; i < k - 1; i++) {
                queue.add(nums[i]);
            }
            while (i < nums.length) {
                int max = queue.peek();
                queue.add(nums[i]);
                res[i - k + 1] = max;
                queue.remove(nums[l]);
                i++;
                l++;
            }
            return res;
        }

        public int[] maxSlidingWindow1(int[] nums, int k) {
            if (nums == null || nums.length == 0) return new int[0];
            //利用单调，队列尾部为最大的，队列首部为马上要移除的
            Deque<Integer> deque = new LinkedList<>();
            //首先设置初始滑动窗口
            int r = 0, l = 0;
            int[] res = new int[nums.length - k + 1]; //最大只会存储数组长度

            while (r < nums.length) {
                int num = nums[r];
                while (!deque.isEmpty() && num > deque.getLast()) {
                    deque.removeLast();
                }
                deque.addLast(num);
                if (r >= k - 1) {
                    res[l] = deque.peek() == nums[l] ? deque.poll() : deque.peek();
                    l++;
                }
                r++;
            }


            return res;
        }


        public static void main(String[] args) {
            System.out.println(Arrays.toString(new Solution2().maxSlidingWindow1(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
        }
    }

    //347.前 K 个高频元素
    private static class Solution4 {
        public int[] topKFrequent(int[] nums, int k) {
            if (nums == null || nums.length <= 1) return nums;
            //直接先排个序
            Arrays.sort(nums);
            //然后用优先队列
            PriorityQueue<int[]> queue = new PriorityQueue<>(k, (o1, o2) -> o2[0] - o1[0]);
            int sum = 1;
            for (int i = 0; i < nums.length; i++) {
                if (i + 1 < nums.length && nums[i] == nums[i + 1]) {
                    sum++;
                    continue;
                }
                queue.add(new int[]{sum, nums[i]});
                sum = 1;
            }
            int[] res = new int[k];
            int l = 0;
            while (!queue.isEmpty() && l < k) {
                res[l++] = queue.poll()[1];
            }


            return res;
        }

        public int[] topKFrequent1(int[] nums, int k) {
            if (nums == null || nums.length <= 1) return nums;

            //然后用优先队列
            PriorityQueue<int[]> queue = new PriorityQueue<>(k, (o1, o2) -> o2[0] - o1[0]);

            Map<Integer, Integer> map = new HashMap<>();
            for (int num : nums) {
                //compute 如果mapping返回null就会删除key，否则put
                map.compute(num, (key, val) -> val == null ? 1 : val + 1);
            }

            map.forEach((key, val) -> queue.add(new int[]{val, key}));

            int[] res = new int[k];
            int l = 0;
            while (!queue.isEmpty() && l < k) {
                res[l++] = queue.poll()[1];
            }

            return res;
        }

        public static void main(String[] args) {
            System.out.println(Arrays.toString(new Solution4().topKFrequent1(new int[]{1, 1, 1, 2, 2, 3}, 2)));
        }
    }


}
