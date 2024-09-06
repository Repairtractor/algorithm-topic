package code_capriccio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//字符串练习
public class StringPractice {
    //344. 反转字符串
    private static class Solution {
        //需要原地修改，不能用额外的数组空间
        public void reverseString(char[] s) {
            if (s == null || s.length == 0 || s.length == 1)
                return;
            int l = 0, r = s.length - 1;
            while (l < r) {
                char tmp = s[l];
                s[l] = s[r];
                s[r] = tmp;
                l++;
                r--;
            }

            return;
        }
    }

    //151. 反转字符串中的单词
    private static class Solution1 {
        public String reverseWords(String s) {
            if (s == null || s.isEmpty() || s.length() == 1)
                return s;
            //循环遍历，查到空格就往下走，结果放入list，然后再反转list
            List<String> ans = new ArrayList<>();
            StringBuilder schar = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (c == ' ') {
                    if (schar.length() > 0) {
                        ans.add(schar.toString());
                        schar = new StringBuilder();
                    }
                    continue;
                }
                schar.append(c);
            }
            if (schar.length() > 0) {
                ans.add(schar.toString());
            }

            StringBuilder sb = new StringBuilder();
            for (int i = ans.size() - 1; i >= 0; i--) {
                sb.append(ans.get(i));
                if (i != 0)
                    sb.append(" ");
            }
            return sb.toString();
        }

        public static void main(String[] args) {
            System.out.println(new Solution1().reverseWords("the sky is blue"));
        }
    }

    private static class Solution2 {
        public int strStr(String haystack, String needle) {
            if (haystack == null || needle == null || haystack.length() == 0 || needle.length() == 0)
                return 0;
            //先利用滑动窗口写写看, 假设[l...r]为符合条件的字符串，那么r遍历时需要判断needle[r-l]位置下是否相同，如果相同r前进，继续判断，否则r前进，l=r
            int l = 0, r = 0, k = 0;
            char[] haystackChars = haystack.toCharArray(), needleChars = needle.toCharArray();
            while (r < haystack.length()) {
                if (needleChars[0] == haystackChars[r]) {
                    l = r;
                }
                while (k < needleChars.length&&r<haystack.length() && needleChars[k] == haystackChars[r]) {
                    k++;
                    r++;
                }
                if (k == needleChars.length) {
                    return l;
                }
                l++;
                r = l;
                k=0;


            }
            return -1;
        }

        public int strStr1(String haystack, String needle) {
            if (haystack == null || needle == null || haystack.isEmpty() || needle.isEmpty())
                return 0;

            //todo 待定
            return 0;
        }

        public static void main(String[] args) {
            System.out.println(new Solution2().strStr("aaa", "aaaa"));
        }
    }

}
