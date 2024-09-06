package code_capriccio;

import java.util.*;

//hash表练习
public class HashPractice {

    //242. 有效的字母异位词
    private static class Solution {
        //因为题目限制了字母都是小写，所以只需要声明一个长度为26的数组，然后用字母为下标，然后最后过滤一下没有0的就好
        public boolean isAnagram(String s, String t) {
            int[] nums = new int[26];
            if (s.length() != t.length()) return false;
            char[] sChars = s.toCharArray(), tChars = t.toCharArray();

            for (int i = 0; i < s.length(); i++) {
                nums[sChars[i] - 'a']++;
                nums[tChars[i] - 'a']--;
            }
            return !Arrays.stream(nums).filter(n -> n != 0).findAny().isPresent();
        }

        public static void main(String[] args) {
            System.out.println(new Solution().isAnagram("rat", "car"));
        }
    }

    //202. 快乐数
    private static class Solution1 {
        public boolean isHappy(int n) {
            Set<Integer> existed = new HashSet<>();
            int newNum = n;
            do {
                existed.add(newNum);
                newNum = getNext(newNum);
            } while (newNum != 1 && !existed.contains(newNum));

            return newNum == 1;
        }

        //可以想象成一个找环的方法，一直迭代下去直到为1，那么在没有循环的情况下，快乐数只会在1的时候相遇，非快乐数会在其他情况相遇
        //只需要使用快慢指针，这样slow总会与fast相遇，可以破除循环
        public boolean isHappy2(int n) {
            int slow = n, fast = n;
            do {
                slow = getNext(slow);
                fast = getNext(fast);
                fast = getNext(fast);
            } while (slow != fast);
            return slow == 1;
        }


        int getNext(int newNum) {
            int temp = newNum, res = 0;
            while (temp != 0) {
                res += (temp % 10) * (temp % 10);
                temp /= 10;
            }
            return res;
        }


        public static void main(String[] args) {
            System.out.println(new Solution1().isHappy2(2));
        }
    }

    //1. 两数之和
    private class Solution2 {
        public int[] twoSum(int[] nums, int target) {
            //直接用一个map来实现就好了，key为数字，value为下标
            Map<Integer, Integer> existed = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (existed.containsKey(target - nums[i])) {
                    return new int[]{existed.get(target - nums[i]), i};
                }
                existed.put(nums[i], i);
            }

            return null;
        }
    }

    //454. 四数相加 II
    class Solution3 {
        //求0 ,最简单的是暴力求和，再是搞一个map，将速度搞成O（n^2)
        public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
            if (nums1.length == 0 || nums2.length == 0 || nums3.length == 0 || nums4.length == 0) {
                return 0;
            }
            Map<Integer, Integer> map = new HashMap<>();
            for (int numA : nums1) for (int numB : nums2) map.put(numA + numB, map.getOrDefault(numA + numB, 0) + 1);
            int res = 0;
            for (int numA : nums3)
                for (int numB : nums4) if (map.containsKey(-numA - numB)) res += map.get(-numA - numB);

            return 0;
        }

    }

    //383. 赎金信
    static class Solution4 {
        //这道题比较简单，直接利用数组的下标然后减去就行了
        public boolean canConstruct(String ransomNote, String magazine) {
            if (ransomNote == null || magazine == null || magazine.length() < ransomNote.length()) return false;
            int[] nums = new int[26];
            magazine.chars().forEach(it -> {
                nums[it - 'a']++;
            });
            return ransomNote.chars()
                    .peek(num -> nums[num - 'a']--)
                    .noneMatch(num -> nums[num - 'a'] < 0);
        }

        public static void main(String[] args) {
            System.out.println(new Solution4().canConstruct("rat", "car"));
        }
    }

    //15. 三数之和
    private static class Solution5 {
        //最简单的就是暴力破解，O(n^2)
        public List<List<Integer>> threeSum(int[] nums) {
            if (nums == null || nums.length < 3) return new ArrayList<>();
            Arrays.sort(nums);
            List<List<Integer>> res = new ArrayList<>();
            //然后利用滑动窗口,假设[l,i,j]前闭后闭表示等于结果，如果j下移还是等于结果，因为有序的原因，那么循环直到不等于结果，此时l移动，因为小了往右，大了往左

            for (int l = 0; l < nums.length; l++) {
                int k = l + 1, r = nums.length - 1;
                int oneVal = nums[l];
                if (l > 0 && nums[l - 1] == oneVal) continue;
                if (oneVal > 0)
                    break;
                while (k < r) {
                    int num = nums[r] + nums[k] + oneVal;
                    if (num > 0) {
                        r--;
                    } else if (num < 0) {
                        k++;
                    } else {
                        res.add(Arrays.asList(oneVal, nums[k], nums[r]));
                        while (k < r && nums[k] == nums[k + 1]) k++;
                        while (k < r && nums[r] == nums[r - 1]) r--;
                        k++;
                        r--;
                    }
                }
            }

            return res;
        }

        public static void main(String[] args) {
            new Solution5().threeSum(new int[]{-1, 0, 1, 2, -1, -4}).forEach(System.out::println);
        }

    }

    //18. 四数之和
    private static class Solution6 {
        public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> res = new ArrayList<>();
            //四数之和，锚定两个数为基准，然后双指针？那N数之和不是G了，先写一下试试
            if (nums == null || nums.length < 4) return res;
            Arrays.sort(nums);

            for (int i = 0; i < nums.length - 3; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) continue; //锚定数据也需要过滤重复
                for (int j = i + 1; j < nums.length - 2; j++) {
                    int num = nums[i] + nums[j];
                    int k = j + 1, r = nums.length - 1;
                    if (j > i + 1 && nums[j] == nums[j - 1]) continue;//锚定数据也需要过滤重复
                    while (k < r) {
                        if (nums[k] + nums[r] < (target-num)) {
                            k++;
                        } else if (nums[k] + nums[r] > (target-num)) {
                            r--;
                        } else {
                            res.add(Arrays.asList(nums[i], nums[j], nums[k], nums[r]));
                            while (k < r && nums[k] == nums[k + 1]) k++;
                            while (k < r && nums[r] == nums[r - 1]) r--;
                            k++;
                            r--;
                        }
                    }

                }
            }
            return res;
        }

        public static void main(String[] args) {
            List<List<Integer>> lists = new Solution6().fourSum(new int[]{1000000000,1000000000,1000000000,1000000000}, -294967296);
            System.out.println(lists);
        }
    }


}
