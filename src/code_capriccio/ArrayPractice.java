package code_capriccio;

import java.util.Arrays;
import java.util.Scanner;

public class ArrayPractice {

    //leetCode 704 binary search
    private static class Solution {
        public int search(int[] nums, int target) {
            //确定循环不变量 [l,r] 表示为target一定在这里面，因为数组是有序的，那么只需要确定target一定在[l,mid]/[mid+1,r]就可以
            //确定中间数,防止数组溢出
            int l = 0, r = nums.length - 1;
            while (l <= r) {
                int mid = (r - l) / 2 + l;//防止溢出
                if (nums[mid] == target)
                    return mid;
                else if (target > nums[mid])
                    l = mid + 1;
                else r = mid - 1;
            }
            return -1;
        }

    }

    //leetCode 27 移除元素
    private static class Solution1 {
        public static void main(String[] args) {
            Solution1 solution1 = new Solution1();
            System.out.println(solution1.removeElement(new int[]{1}, 1));
        }

        public int removeElement(int[] nums, int val) {
            //确定区间[l...r)为不等于val的数据，前闭后闭，指针i从前往后面移动，
            // 当等于val时与最后一位换位置，并且r缩小范围
            int i = 0, r = nums.length;
            while (i < r) {
                if (nums[i] == val) {
                    swap(nums, i, --r);
                } else i++;
            }
            return r;
        }

        public void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }

    }

    //977. 有序数组的平方
    private static class Solution2 {
        public int[] sortedSquares(int[] nums) {
            //非递减顺序，那么也就是说是有序的，
            //确定两个指针一前一后开始执行，直到相交结束，[l...k)表示新的数组下表排好序的下标
            int l = 0, r = nums.length - 1, k = nums.length;
            int[] res = new int[k];


            while (l <= r) {
                if (Math.abs(nums[l]) > Math.abs(nums[r])) {
                    res[--k] = nums[l] * nums[l++];
                } else {
                    res[--k] = nums[r] * nums[r--];
                }

            }


            return res;
        }

        public static void main(String[] args) {
            int[] nums = {-2, 0, 1, 3, 5, 7};
            System.out.println(Arrays.toString(new Solution2().sortedSquares(nums)));
        }
    }

    //209.长度最小的子数组

    /**
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * <p>
     * 找出该数组中满足其总和大于等于 target 的长度最小的
     * 子数组
     * [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     */
    private static class Solution3 {
        public int minSubArrayLen(int target, int[] nums) {
            if (nums.length == 0) return 0;

            //确定[l...i]为大于等于target的数组，那么当需要记录最小的数字，然后因为要求最小的，所以i不断移动的同时，l要不断地增加，利用滑动窗口，
            // 不断记录长度值，直到i遍历完毕，并且[l...i]小于target
            int l = 0, i = 0, min = Integer.MAX_VALUE, sum = nums[0];
            while (i < nums.length - 1 || sum >= target) {
                if (sum >= target) {
                    min = Math.min(min, i - l);
                    sum -= nums[l++];
                    continue;
                }
                sum += nums[++i];
            }
            if (min == Integer.MAX_VALUE) return 0;
            return min + 1;
        }

        public int minSubArrayLen1(int target, int[] nums) {
            if (nums.length == 0) return 0;

            //确定[l...i]为大于等于target的数组，那么当需要记录最小的数字，然后因为要求最小的，所以i不断移动的同时，l要不断地增加，利用滑动窗口，
            // 不断记录长度值，直到i遍历完毕，并且[l...i]小于target
            int l = 0, min = Integer.MAX_VALUE, sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                //当达到临界值时，左边可以多层循环移动，时间复杂度还是O(N)，比我自己写出来的要好，
                // 我自己其实是将里面的循环跟外面循环结合，其实下面这种更好理解
                while (sum >= target) {
                    min = Math.min(min, i - l + 1);
                    sum -= nums[l++];
                }
            }
            return min == Integer.MAX_VALUE ? 0 : min;
        }

        public static void main(String[] args) {
            System.out.println(new Solution3().minSubArrayLen1(5, new int[]{2, 0, 1, 3, 4}));
        }
    }


    //59. 螺旋矩阵 II

    /**
     * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
     */
    private static class Solution4 {
        public int[][] generateMatrix(int n) {
            //确定四个字段，l，r，down，on，确定他们的下标，然后循环画
            //首先确定循环不变量，[on][l...r] 顺时针从左往右画，[l] [on...down]从上往下画，[down][r...l]从右往左，[r][down...on]从下往上
            int l = 0, r = n - 1, on = 0, down = n - 1, lap = 0;
            int tar = n * n, num = 1;
            int[][] res = new int[n][n];

            while (num <= tar) {
                for (int j = l; j <= r; j++) { //从左往右,不画最后一个
                    res[on][j] = num++;
                }
                on++;
                for (int j = on; j <= down; j++) { //从上往下 前闭后开
                    res[j][r] = num++;
                }
                r--;
                for (int j = r; j >= l; j--) { //从右往左 前闭后开
                    res[down][j] = num++;
                }
                down--;
                for (int j = down; j >= on; j--) { //从下往上，前闭后开
                    res[j][l] = num++;
                }
                l++;
            }

            return res;
        }

        public static void main(String[] args) {
            int[][] ints = new Solution4().generateMatrix(3);
            System.out.println(Arrays.deepToString(ints));
        }
    }

    //58. 区间和
    //给定一个整数数组 Array，请计算该数组在每个指定区间内元素的总和。
    private static class Solution5 {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt(),sum=0;
            int []nums = new int[n],cache=new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = scanner.nextInt();
                sum+=nums[i];
                cache[i]=sum;
            }
            //用一个数组p[i]记录p[l]~p[r]的和，如果想要找到p[l]~p[r]的和，只需要用p[r]-p[l-1]就可以了
            while (scanner.hasNextInt()) {
                int l = scanner.nextInt();
                int r = scanner.nextInt();

                System.out.println(cache[r]-(l-1<0?0:cache[l-1]));
            }
        }
    }
    //44. 开发商购买土地



}
