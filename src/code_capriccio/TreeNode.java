package code_capriccio;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     * 构建树，按照中，左，右的顺序进行构建，当为null时不会构建该节点
     *
     * @param nums 入参数组，当为null时不会构建数据
     * @return 返回二叉树的头结点
     */
    public static TreeNode createTree(Integer[] nums) {
        //利用层序遍历进行创建
        if (nums.length == 0) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(nums[0]);
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            Integer num;
            if (i < nums.length && (num = nums[i++]) != null) {
                queue.add(node.left = new TreeNode(num));
            }
            if (i < nums.length && (num = nums[i++]) != null) {
                queue.add(node.right = new TreeNode(num));
            }
        }
        return root;
    }

    /**
     * 以层序遍历打印二叉树
     */
    public void printTree() {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(this);
        int space = 10;
        while (!queue.isEmpty()) {
            int num = queue.size();
            for (int i = 0; i < num; i++) {
                TreeNode node = queue.poll();
                if (Objects.isNull(node)) {
                    System.out.print("n\t");
                    continue;
                }
                System.out.print(node.val + "\t");

                queue.offer(node.left);
                queue.offer(node.right);
            }
            space -= 2;
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TreeNode tree = TreeNode.createTree(new Integer[]{1, 2, 3, 4, 5, null, 8, null, null, 6, 7, 9});
        tree.printTree();
    }

    // 1,2,3,4,5,null,8,null,null,6,7,9
    /**
     *          1
     *       2    3
     *     4  5 n  8
     *    n n 6 7 9
     */

}