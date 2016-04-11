package msn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.BufferedReader;

import org.apache.commons.lang3.StringUtils;

/**
 * Uses Apache Commons Lang v3 - Availble @ https://commons.apache.org/proper/commons-lang/
 * @author pth2mn
 *
 */
public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					args[0])));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					args[1])));

			int numCases = Integer.valueOf(br.readLine());
			for (int i = 0; i < numCases; i++) {
				int val = Integer.valueOf(br.readLine());
				resetNums();
				String result = processCase(val);
				bw.write(String.format("Case #%s: %s\r\n", i + 1, result));
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean[] nums = { false, false, false, false, false, false,
			false, false, false, false };

	private static void resetNums() {
		for (int i = 0; i < nums.length; i++)
			nums[i] = false;
	}

	private static String processCase(int val) {
		System.out.println("\r\n\r\n*************\r\nProcessing " + val);
		String result = "INSOMNIA";
		if (val == 0) {
			return result;
		}
		return processCase(val, val);
	}

	private static String processCase(int val, int originalVal) {
		System.out.println(String.format("%s, %s", val, originalVal));
		
		String valString = val + "";
		nums[0] = nums[0] || StringUtils.contains(valString, "0");
		nums[1] = nums[1] || StringUtils.contains(valString, "1");
		nums[2] = nums[2] || StringUtils.contains(valString, "2");
		nums[3] = nums[3] || StringUtils.contains(valString, "3");
		nums[4] = nums[4] || StringUtils.contains(valString, "4");
		nums[5] = nums[5] || StringUtils.contains(valString, "5");
		nums[6] = nums[6] || StringUtils.contains(valString, "6");
		nums[7] = nums[7] || StringUtils.contains(valString, "7");
		nums[8] = nums[8] || StringUtils.contains(valString, "8");
		nums[9] = nums[9] || StringUtils.contains(valString, "9");

		if (isFalse(nums)) {
			return val + "";
		}
		
		return processCase(val + originalVal, originalVal);
	}

	private static boolean isFalse(boolean[] nums) {
		return nums[0] && nums[1] && nums[2] && nums[3] && nums[4] && nums[5]
				&& nums[6] && nums[7] && nums[8] && nums[9];
	}

}
