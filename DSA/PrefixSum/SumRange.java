public class SumRange {

    private int[] prefixSum;

    private SumRange(int[] nums){
        prefixSum = new int[nums.length + 1];
        prefixSum[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
    }
    public int sumRange(int left, int right) {
        return prefixSum[right + 1] - prefixSum[left];
    }


    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        SumRange ps = new SumRange(nums);
        System.out.println(ps.sumRange(1,3)); // Output: 6 (1 + 2 + 3)
    }
}
