package msn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class JamCoins {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					args[0])));
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					args[1])));
			long count = Integer.parseInt(br.readLine());
			for (long i = 0; i < count; i++) {
				StringTokenizer lenAndNum = new StringTokenizer(br.readLine());
				long len = Integer.valueOf(lenAndNum.nextToken());
				long num = Integer.valueOf(lenAndNum.nextToken());

				String caseString = String.format("Case #%s:\r\n", i+1);
				System.out.print(caseString);
				bw.write(caseString);
				long jam = 0;
				for (long j = 0; j < num; j++) {
					JamCoin result = generateJamCoin(len - 2, jam);
					jam = result.getJam() + 1;
					System.out.print(result);
					bw.write(result.toString());
				}

			}
			bw.flush();
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static JamCoin generateJamCoin(long len, long base) {
		String jam = Long.toString(base, 2);

		jammin: while (jam.length() <= len) {
			String coin = "1" + pad(jam, len) + "1";
			for (int i = 2; i < 10; i++) {
				BigInteger val = new BigInteger(coin, i);
				if (isPrime(val)) {
					jam = Long.toString(++base, 2);
					continue jammin;
				}
			}
			// Non prime build output
			JamCoin jc = new JamCoin(coin, base);
			if (jc.canFindDivisors()){
				return jc;
			}else{
				jam = Long.toString(++base, 2);
			}
		}
		return null;
	}

	private static String pad(String jam, long len) {
		while (jam.length() < len) {
			jam = "0" + jam;
		}
		return jam;
	}

	private static boolean isPrime(BigInteger num) {
		return num.isProbablePrime(1);
	}

}
