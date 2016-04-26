package msn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Driver {
	private static BufferedReader br;
	private static BufferedWriter bw;
	private static int bestCount = 0;
	private static HashMap<Integer, Integer> originalMap = new HashMap<Integer, Integer>();

	/*
	 * Apache Commons Lang Availble @
	 * https://commons.apache.org/proper/commons-lang/
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			br = new BufferedReader(new FileReader(new File("input.txt")));
			bw = new BufferedWriter(new FileWriter(new File("output.txt")));

			final long numCases = nextLong();
			for (long i = 1; i <= numCases; i++) {
				bestCount = 0;
				// read the inputs
				int numKids = nextInt();
				String kids = br.readLine();
				List<Integer> bffs = new ArrayList<Integer>();
				StringTokenizer st = new StringTokenizer(kids, " ");
				HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

				Integer count = 1;
				while (st.hasMoreTokens()) {
					Integer bff = Integer.parseInt(st.nextToken());
					bffs.add(bff);
					map.put(count++, bff);
				}

				// recursive?
				originalMap.clear();
				originalMap.putAll(map);
				for (Integer kid : map.keySet()) {
					List<Integer> result = new ArrayList<Integer>();
					result.add(kid);
					HashMap<Integer, Integer> newMap = new HashMap<Integer, Integer>();
					newMap.putAll(map);
					// newMap.remove(kid);
					result = take2(result, newMap);
					Integer last = result.get(result.size() - 1);
					if (map.get(last) != null && map.get(last) != result.get(0)
							&& map.get(last) != result.get(result.size() - 2)) {
						// System.out.println(map.get(last) + " " +
						// result.get(0) + " " + result.get(result.size() - 2));
						// System.out.println("Remove " +
						// result.get(result.size() - 1));
						result.remove(result.size() - 1);
					}
					if (result.size() > bestCount) {
						System.out.println("b.c" + result);

						bestCount = result.size();
					}
				}

				// do stuff
				String result = "" + bestCount;

				// write the results
				writeCaseHeaderSingleResult(i, result);
			}
			bw.flush();
			bw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<Integer> take2(List<Integer> current, HashMap<Integer, Integer> map) {
		if (map.isEmpty())
			return current;

		List<Integer> result = new ArrayList<Integer>();
		result.addAll(current);
		Integer last = current.get(current.size() - 1);
		Integer kid = map.get(last);
		if (!result.contains(kid) && kid != null)
			result.add(kid);
		else {
			// can only do this if kid wants to sit beside his left friend.
			// System.out.println(map.get(last) + " " + map.get(kid));
			if (kid == originalMap.get(last) && last == originalMap.get(kid)) {

				// look for someone who wants to sit by kid
				for (Integer key : map.keySet()) {
					if (map.get(key) == last) {
						result.add(key);

						HashMap<Integer, Integer> map2 = new HashMap<Integer, Integer>();
						map2.putAll(map);
						map2.remove(key);
						return take2(result, map2);
					}

				}
			}
			return result;
		}
		// result.add(map.get(last));

		HashMap<Integer, Integer> map2 = new HashMap<Integer, Integer>();
		map2.putAll(map);
		map2.remove(last);
		return take2(result, map2);
	}

	private static List<Integer> recurse(List<Integer> current, HashMap<Integer, Integer> map) {
		// System.out.println(result);
		// System.out.println(current);
		Integer last = current.get(current.size() - 1);
		int best = current.size();
		List<Integer> bestList = new ArrayList<Integer>();
		;
		for (Integer kid : map.keySet()) {
			List<Integer> result = new ArrayList<Integer>();
			result.addAll(current);

			Integer bff = map.get(kid);
			if (current.get(0) == 4) {
				// System.out.println("### " + current);
				// System.out.println(bff + " " + last);
			}

			if (bff == last) {
				// potential BFF situation.

				if (result.contains(kid)) {
					continue;
				}
				// System.out.println("xxx " + bff + " " + " k=" + kid + " " +
				// result + " Keys: " + map.keySet());
				result.add(kid);

				HashMap<Integer, Integer> bffs = new HashMap<Integer, Integer>();
				bffs.putAll(map);
				bffs.remove(kid);

				List<Integer> kids = recurse(result, bffs);
				checkBest(kids);
				if (kids.size() > best) {
					best = kids.size();
					bestList = kids;
				}

				// System.out.println(kids.size() + " <> " + current.size() + "
				// " + kids);
				// if (kids.size() >= current.size() + 1) {
				// return recurse(kids, bffs);
				// }
			}
			// System.out.println(bestList);
		}
		Integer kid = map.get(last);
		if (kid != null && !bestList.contains(kid)) {
			if (!bestList.contains(last))
				bestList.add(last);
			bestList.add(kid);
			// System.out.println("msn " + last + " " + bestList);
			HashMap<Integer, Integer> bffs = new HashMap<Integer, Integer>();
			bffs.putAll(map);
			bffs.remove(kid);
			List<Integer> recurse = recurse(bestList, bffs);
			System.out.println("zzz " + recurse);
			checkBest(recurse);
			return recurse;
		}
		//
		// return result;
		// System.out.println("Done " + bestList);
		checkBest(bestList);
		checkBest(current);
		return current;

	}

	private static void checkBest(List<Integer> current) {
		if (current.size() > bestCount) {
			System.out.println(current);
			bestCount = current.size();
		}

	}

	// private static Set<Integer> recurse(HashMap<Integer, Integer> bffs,
	// String currentResult, String bestResult) {
	// Set<Integer> result = new HashSet<Integer>();
	// Set<Integer> keys = bffs.keySet();
	//
	// for (Integer key : bffs.keySet()) {
	// keys.remove(key);
	// Set<Integer> newKeys = new HashSet<Integer>();
	// newKeys.addAll(keys);
	// Set<Integer> possible = recurse2(result, buildNewMap(bffs, newKeys));
	//
	//
	// }
	// return result;
	// }
	//
	// private static Set<Integer> recurse2(Set<Integer> s, HashMap<Integer,
	// Integer> bffs) {
	// for(Integer key: bffs.keySet()){
	//
	// }
	// return null;
	// }

	private static long nextLong() throws IOException {
		return Long.parseLong(br.readLine());
	}

	private static int nextInt() throws IOException {
		return Integer.parseInt(br.readLine());
	}

	private static double nextDouble() throws IOException {
		return Double.parseDouble(br.readLine());
	}

	private static void writeCaseHeaderSingleResult(long caseNum, String result) throws IOException {
		String caseString = String.format("Case #%s: %s\r\n", caseNum, result);
		write(caseString);
	}

	private static void writeCaseHeaderMultiResult(long caseNum) throws IOException {
		String caseString = String.format("Case #%s:\r\n", caseNum);
		write(caseString);
	}

	private static void write(String toWrite) throws IOException {
		System.out.print(toWrite);
		bw.write(toWrite);
	}

	static class BFF {
		int kid;
		int bff;
	}
}
