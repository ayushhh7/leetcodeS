class Solution {
    public int distinctSubseqII(String s) {
        long MOD = 1000000007;
        long[] dp = new long[26];

        long total = 0;

        for (char c : s.toCharArray()) {
            int index = c - 'a';

            long newSubsequences = (total + 1) % MOD;

            total = (total + newSubsequences - dp[index] + MOD) % MOD;

            dp[index] = newSubsequences;
        }

        return (int) total;
    }
}