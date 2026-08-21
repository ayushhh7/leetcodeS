class Solution {
    public long findKthSmallest(int[] coins, int k) {

        int n = coins.length;

        long left = 1;
        long right = (long) k * coins[0];

        for (int coin : coins) {
            right = Math.min(right, (long) k * coin);
        }

        while (left < right) {

            long mid = left + (right - left) / 2;

            if (count(mid, coins) >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private long count(long x, int[] coins) {

        int n = coins.length;
        long result = 0;

        for (int mask = 1; mask < (1 << n); mask++) {

            long lcm = 1;
            int bits = 0;
            boolean valid = true;

            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {

                    bits++;

                    long gcd = gcd(lcm, coins[i]);
                    lcm = (lcm / gcd) * coins[i];

                    if (lcm > x) {
                        valid = false;
                        break;
                    }
                }
            }

            if (!valid) {
                continue;
            }

            if (bits % 2 == 1) {
                result += x / lcm;
            } else {
                result -= x / lcm;
            }
        }

        return result;
    }

    private long gcd(long a, long b) {

        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}