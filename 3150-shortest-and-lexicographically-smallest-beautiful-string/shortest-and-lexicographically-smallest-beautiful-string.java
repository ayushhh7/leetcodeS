class Solution {
    public String shortestBeautifulSubstring(String s, int k) {

        int left = 0;
        int ones = 0;
        int minLength = Integer.MAX_VALUE;
        String answer = "";

        for (int right = 0; right < s.length(); right++) {

            if (s.charAt(right) == '1') {
                ones++;
            }

            while (ones > k) {
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }

            if (ones == k) {

                while (s.charAt(left) == '0') {
                    left++;
                }

                int length = right - left + 1;
                String current = s.substring(left, right + 1);

                if (length < minLength ||
                    (length == minLength && current.compareTo(answer) < 0)) {

                    minLength = length;
                    answer = current;
                }
            }
        }

        return answer;
    }
}