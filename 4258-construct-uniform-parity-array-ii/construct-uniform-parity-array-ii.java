class Solution {
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;

        // Find the smallest odd number
        for (int x : nums1) {
            if (x % 2 == 1) {
                minOdd = Math.min(minOdd, x);
            }
        }

        // If there is an even number smaller than the smallest odd,
        // it is impossible.
        for (int x : nums1) {
            if (x % 2 == 0 && minOdd != Integer.MAX_VALUE && x < minOdd) {
                return false;
            }
        }

        return true;
    }
}