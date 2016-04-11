package msn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Pancakes {

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
				String cakesString = br.readLine();
				boolean[] cakes = buildingMadStacks(cakesString);
				int result = flipCakes(cakes, 0);

				String answer = String.format("Case #%s: %s\r\n", i + 1, result);
				System.out.println(answer);
				bw.write(answer);
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean[] buildingMadStacks(String cakesString) {
		boolean[] cakes = new boolean[cakesString.length()];
		for(int i = 0; i< cakesString.length(); i++){
			cakes[i] = cakesString.charAt(i) == '+';
		}
		return cakes;
	}

	private static int flipCakes(boolean[] cakes, int counter) {
		if (allHappy(cakes)) {
			return counter;
		}
		
		// find index of lowest unhappy
		int i = goDeep(cakes);
		// do some flipping magic
		flippingMagic(cakes, i);
		
		// try again, hurry up!
		return flipCakes(cakes, ++counter);
	}

	private static boolean allHappy(boolean[] cakes) {
		for (int i = 0; i < cakes.length; i++) {
			if (!cakes[i])
				return false;
		}
		return true;
	}
	
	private static int goDeep(boolean[] cakes){
		for (int i = cakes.length; i>0; i--){
			if(!cakes[i-1]){
				return i;
			}
		}
		return -1;
	}
	
	private static void flippingMagic(boolean[] cakes, int i){
		for(int j = 0; j< i; j++){
			cakes[j] = !cakes[j];
		}
	}
	
	private static String cakesPeek(boolean[] cakes){
		String result = "";
		for(int i = 0; i < cakes.length; i++)
			result += cakes[i]?"+":"-";
		return result;
	}

}
