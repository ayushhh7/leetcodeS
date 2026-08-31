class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int first = -1;
        int previous = -1;
        int minDistance = Integer.MAX_VALUE;

        int index = 1;

        ListNode prev = head;
        ListNode curr = head.next;

        while (curr != null && curr.next != null) {

            if ((curr.val > prev.val && curr.val > curr.next.val) ||
                (curr.val < prev.val && curr.val < curr.next.val)) {

                if (first == -1) {
                    first = index;
                }

                if (previous != -1) {
                    minDistance = Math.min(minDistance, index - previous);
                }

                previous = index;
            }

            prev = curr;
            curr = curr.next;
            index++;
        }

        if (previous == first) {
            return new int[]{-1, -1};
        }

        int maxDistance = previous - first;

        return new int[]{minDistance, maxDistance};
    }
}