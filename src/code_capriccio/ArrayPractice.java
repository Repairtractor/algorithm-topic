package code_capriccio;

public class ArrayPractice {


    public static void main(String[] args) {
        Solution b =new ArrayPractice.Solution();
        int[] nums = {1,2,3,4,5,6,7,8,9};
        int target = 9;
        System.out.println(b.search(nums, target));
    }

   static class Solution {
       //leetCode 704 binary search
        public int search(int[] nums, int target) {
            //确定循环不变量 [l,r] 表示为target一定在这里面，因为数组是有序的，那么只需要确定target一定在[l,mid]/[mid+1,r]就可以
            //确定中间数,防止数组溢出
            int l=0,r=nums.length-1;
            while(l<=r){
                int mid=(r-l)/2+l;//防止溢出
                if (nums[mid]==target)
                    return mid;
                else if (target>nums[mid])
                    l=mid+1;
                else r=mid-1;
            }
            return -1;
        }

    }
    static class Solution1 {
        //leetCode 27 移除元素
        public int removeElement(int[] nums, int val) {
            //确定区间[l...r)为不等于val的数据，前闭后闭，指针i从前往后面移动，
            // 当等于val时与最后一位换位置，并且r缩小范围
            int i=0,r=nums.length;
            while(i<r){
                if(nums[i]==val){
                    swap(nums,i,--r);
                }else i++;
            }
            return r;
        }
        public void swap(int[] nums,int i,int j){
            int temp=nums[i];
            nums[i]=nums[j];
            nums[j]=temp;
        }

        public static void main(String[] args) {
            Solution1 solution1 = new Solution1();
            System.out.println(solution1.removeElement(new int[]{1},1));
        }

    }






}
