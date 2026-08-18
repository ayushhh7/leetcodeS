class Solution {
    public int largestInteger(int[] nums, int k) {

        int answer = -1;

        for (int x : nums) {

            int count = 0;

            for (int i = 0; i <= nums.length - k; i++) {

                for (int j = i; j < i + k; j++) {

                    if (nums[j] == x) {
                        count++;
                        break;
                    }
                }
            }

            if (count == 1) {
                answer = Math.max(answer, x);
            }
        }

        return answer;
    }
}