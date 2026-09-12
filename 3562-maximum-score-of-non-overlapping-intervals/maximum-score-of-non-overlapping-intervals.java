import java.util.*;

class Solution {
    private static final long EMPTY = -1L;

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Integer[] order = new Integer[n];

        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) -> {
            int x = Integer.compare(
                intervals.get(a).get(0),
                intervals.get(b).get(0)
            );

            if (x != 0) return x;

            return Integer.compare(a, b);
        });

        int[] starts = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = intervals.get(order[i]).get(0);
        }

        // Find the first interval whose start > current end
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            int r = intervals.get(order[i]).get(1);

            int lo = i + 1;
            int hi = n;

            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;

                if (starts[mid] > r) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }

            next[i] = lo;
        }

        /*
         * dp[i][k] = maximum score using at most k intervals
         * from i onward.
         *
         * We store it as a 1D array to reduce memory overhead.
         */
        long[] dp = new long[(n + 1) * 5];

        /*
         * code stores up to 4 original indices in sorted order.
         * Each index uses 16 bits.
         *
         * 65535 means "empty".
         */
        long[] code = new long[(n + 1) * 5];

        Arrays.fill(code, EMPTY);

        for (int i = n - 1; i >= 0; i--) {
            int originalIndex = order[i];
            int weight = intervals.get(originalIndex).get(2);
            int nextIndex = next[i];

            for (int k = 1; k <= 4; k++) {

                // Skip current interval
                long skipScore = dp[(i + 1) * 5 + k];
                long skipCode = code[(i + 1) * 5 + k];

                // Take current interval
                long takeScore =
                    (long) weight + dp[nextIndex * 5 + k - 1];

                long takeCode =
                    addIndex(code[nextIndex * 5 + k - 1],
                             originalIndex);

                if (takeScore > skipScore) {
                    dp[i * 5 + k] = takeScore;
                    code[i * 5 + k] = takeCode;
                } 
                else if (takeScore < skipScore) {
                    dp[i * 5 + k] = skipScore;
                    code[i * 5 + k] = skipCode;
                } 
                else {
                    // Same score -> choose lexicographically smaller
                    dp[i * 5 + k] = skipScore;

                    if (Long.compareUnsigned(takeCode, skipCode) < 0) {
                        code[i * 5 + k] = takeCode;
                    } else {
                        code[i * 5 + k] = skipCode;
                    }
                }
            }
        }

        long answerCode = code[4];

        int count = 0;

        for (int shift = 48; shift >= 0; shift -= 16) {
            int value = (int) ((answerCode >>> shift) & 0xFFFF);

            if (value != 65535) {
                count++;
            }
        }

        int[] answer = new int[count];

        int pos = 0;

        for (int shift = 48; shift >= 0; shift -= 16) {
            int value = (int) ((answerCode >>> shift) & 0xFFFF);

            if (value != 65535) {
                answer[pos++] = value;
            }
        }

        return answer;
    }

    // Adds one index to the already sorted list of indices
    private long addIndex(long code, int index) {
        int a = (int) ((code >>> 48) & 0xFFFF);
        int b = (int) ((code >>> 32) & 0xFFFF);
        int c = (int) ((code >>> 16) & 0xFFFF);
        int d = (int) (code & 0xFFFF);

        if (index < a) {
            d = c;
            c = b;
            b = a;
            a = index;
        } 
        else if (index < b) {
            d = c;
            c = b;
            b = index;
        } 
        else if (index < c) {
            d = c;
            c = index;
        } 
        else {
            d = index;
        }

        return ((long) a << 48)
             | ((long) b << 32)
             | ((long) c << 16)
             | (d & 0xFFFFL);
    }
}