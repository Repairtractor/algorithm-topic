package code_capriccio;

import java.util.*;

//二叉树的一些题目
public class TreePractice {
    //给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
    //前序遍历就是 中 左 右  中序:左 中 右  后序遍历：左 右 中
    private static class Solution {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            if (root == null) return res;
            treeHelper3(root, res);
            return res;
        }

        private void treeHelper(TreeNode node, List<Integer> res) {
            if (node == null) return;
            treeHelper(node.left, res);
            treeHelper(node.right, res);
            res.add(node.val);
        }

        //迭代的前序遍历,就是层序遍历
        private void treeHelper1(TreeNode node, List<Integer> res) {
            Deque<TreeNode> queue = new LinkedList<>();
            queue.push(node);
            while (!queue.isEmpty()) {
                TreeNode pop = queue.pop();
                res.add(pop.val);
                if (pop.left != null) queue.addLast(pop.left);
                if (pop.right != null) queue.addLast(pop.right);
            }
        }

        //迭代的中序遍历,理解了每次都需要先把左指针放入栈中，使用栈，每次先把左指针放进去栈，从左边开始遍历
        private void treeHelper2(TreeNode node, List<Integer> res) {
            Deque<TreeNode> stack = new LinkedList<>();
            stack.push(node);
            TreeNode curr = node;
            //将左边全部放进去

            while (!stack.isEmpty() && curr != null) {
                while (curr.left != null) {
                    stack.push(curr.left);
                    curr = curr.left;
                }

                TreeNode pop = stack.pop();
                res.add(pop.val);
                if (pop.right != null) {
                    stack.push(pop.right);
                    curr = pop.right;
                }
            }
        }

        //迭代的后序遍历,前序遍历是中左右，那么只需要改变左右的顺序，然后反转遍历就好了
        private void treeHelper3(TreeNode node, List<Integer> res) {
            Deque<TreeNode> stack1 = new LinkedList<>(), stack2 = new LinkedList<>();
            stack1.push(node);

            while (!stack1.isEmpty()) {
                TreeNode pop = stack1.pop();
                stack2.push(pop);
                if (pop.left != null) stack1.push(pop.left);
                if (pop.right != null) stack1.push(pop.right);
            }
            while (!stack2.isEmpty()) {
                res.add(stack2.pop().val);
            }

        }


        public static void main(String[] args) {
            TreeNode tree = TreeNode.createTree(new Integer[]{1, 2, 3, 4, 5, null, 8, null, null, 6, 7, 9});
//            tree.printTree();
            System.out.println(new Solution().preorderTraversal(tree));
            // 4 2  1
            //4
        }

    }

    //二叉树的层序遍历 II
    private static class Solution1 {
        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            if (root == null) return new ArrayList<>();
            //从上往下一层一层的放进去，然后再反转
            LinkedList<List<Integer>> res = new LinkedList<>();
            Deque<TreeNode> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                List<Integer> list = new ArrayList<>();
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    list.add(node.val);
                    if (node.left != null) queue.add(node.left);
                    if (node.right != null) queue.add(node.right);
                }
                res.addFirst(list);
            }

            return res;
        }

        public static void main(String[] args) {
            TreeNode tree = TreeNode.createTree(new Integer[]{3, 9, 20, null, null, 15, 7});
            System.out.println(new Solution1().levelOrderBottom(tree));
        }
    }

    //二叉树的右视图
    private static class Solution2 {
        public List<Integer> rightSideView(TreeNode root) {
            //层序遍历，每层只放一个就完了，所以只需要将层次遍历出来，然后取集合中的第一个
            if (root == null) return new ArrayList<>();
            //从上往下一层一层的放进去，然后再反转
            Deque<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            List<Integer> res = new ArrayList<>();
            while (!queue.isEmpty()) {
                List<Integer> list = new ArrayList<>();
                int size = queue.size();
                int num = 0;
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    num = node.val;
                    if (node.left != null) queue.add(node.left);
                    if (node.right != null) queue.add(node.right);
                }
                res.add(num);
            }
            return res;
        }
    }

    //二叉树的层平均值
    private static class Solution3 {
        public List<Double> averageOfLevels(TreeNode root) {
            if (root == null) return Collections.emptyList();
            Deque<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            List<Double> res = new ArrayList<>();
            while (!queue.isEmpty()) {
                int size = queue.size();
                double sum = 0;
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    sum += node.val;
                    if (node.left != null) queue.add(node.left);
                    if (node.right != null) queue.add(node.right);
                }
                res.add(sum / size);
            }
            return res;
        }
    }

    //N 叉树的层序遍历
    private static class Solution4 {
        public List<List<Integer>> levelOrder(Node root) {
            if (root == null) return Collections.emptyList();
            Deque<Node> queue = new LinkedList<>();
            queue.add(root);
            List<List<Integer>> res = new ArrayList<>();
            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    Node node = queue.poll();
                    list.add(node.val);
                    if (Objects.nonNull(node.children)) {
                        queue.addAll(node.children);
                    }
                }
                res.add(list);
            }
            return res;
        }
    }

    //翻转二叉树
    private static class Solution5 {
        public TreeNode invertTree(TreeNode root) {
            if (root == null) return null;
            //遍历的时候直接反转
            return treeHelper(root);
        }

        private TreeNode treeHelper(TreeNode root) {
            if (root == null) return null;
            TreeNode treeNode = new TreeNode(root.val);
            treeNode.right = treeHelper(root.left);
            treeNode.left = treeHelper(root.right);
            return treeNode;
        }

        public static void main(String[] args) {
            TreeNode tree = TreeNode.createTree(new Integer[]{4, 2, 7, 1, 3, 6, 9});
            TreeNode treeNode = new Solution5().invertTree(tree);
            treeNode.printTree();
        }
    }

    //完全二叉树的节点个数
    private static class Solution6 {
        public int countNodes(TreeNode root) {
            return root == null ? 0 : countNodes(root.left) + countNodes(root.right) + 1;
        }
    }

    //110. 平衡二叉树
    private static class Solution7 {
        public boolean isBalanced(TreeNode root) {
            if (root == null) return true;
            return treeCount(root) != -1;
        }

        private int treeCount(TreeNode node) {
            if (node == null) return 0;
            int l = treeCount(node.left);
            int r = treeCount(node.right);
            if (l == -1 || r == -1 || Math.abs(l - r) > 1) return -1;
            return Math.max(l, r) + 1;
        }

        public static void main(String[] args) {
            TreeNode tree = TreeNode.createTree(new Integer[]{1, 2, 2, 3, null, null, 3, 4, null, null, 4});
            System.out.println(new Solution7().isBalanced(tree));
        }
    }

    //二叉树的所有路径
    private static class Solution8 {
        public List<String> binaryTreePaths(TreeNode root) {
            if (root == null) return Collections.emptyList();
            List<String> res = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            helper(root, res, sb);
            return res;
        }

        private void helper(TreeNode node, List<String> res, StringBuilder sb) {
            if (node == null) return;
            sb.append(node.val);
            if (node.left == null && node.right == null) {
                res.add(sb.toString());
            } else {
                sb.append("->");
                helper(node.left, res, sb);
                helper(node.right, res, sb);
                sb.delete(sb.length() - 2, sb.length());
            }
            sb.delete(sb.length() - 1, sb.length());
        }

        public static void main(String[] args) {
            TreeNode tree = TreeNode.createTree(new Integer[]{1, 2, 3, null, 5});
            System.out.println(new Solution8().binaryTreePaths(tree));
        }

    }


}
