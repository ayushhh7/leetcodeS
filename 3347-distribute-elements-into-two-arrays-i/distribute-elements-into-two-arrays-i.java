class Solution {
    public int[] resultArray(int[] nums) {

        int[] arr1 = new int[nums.length];
        int[] arr2 = new int[nums.length];

        int size1 = 0;
        int size2 = 0;

        arr1[size1++] = nums[0];
        arr2[size2++] = nums[1];

        for (int i = 2; i < nums.length; i++) {

            if (arr1[size1 - 1] > arr2[size2 - 1]) {
                arr1[size1++] = nums[i];
            } else {
                arr2[size2++] = nums[i];
            }
        }

        int[] result = new int[nums.length];

        int index = 0;

        for (int i = 0; i < size1; i++) {
            result[index++] = arr1[i];
        }

        for (int i = 0; i < size2; i++) {
            result[index++] = arr2[i];
        }

        return result;
    }
}