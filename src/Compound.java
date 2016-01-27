import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public abstract class Compound {
	/**
	 * @param idgs
	 *          All compounds to check reactivity with including its self
	 * @return all if no reactions are detected else a new List containing the
	 *         compounds after reaction.
	 */
	private int timeToLive = 0;
	private boolean stable = true;

	public abstract String toStringSimple();

	public String toStringNoNums() {
		String s = toStringSimple();
		for (int i = 0; i < timeToLive; i++)
			s = "U" + s;
		return s;
	}

	public String toString() {
		return letersToNumbers(toStringNoNums());
	}

	static String letersToNumbers(String s) {
		System.out.println(s);
		List<String> tokens = tokenize(s);
		for (String token : tokens)
			System.out.println(token);
		System.out.println("End of tokens");
		int count = 1;
		String r = "";
		String last = tokens.get(0);
		for (int j = 1; j < tokens.size(); j++) {
			String next = tokens.get(j);
			if (next.equals(")") || next.equals("(")) {
				r += next;
				if (count == 1) {
					r += last;
				} else {
					r += count + last;
				}
				last = next;
			} else if (next.equals(last)) {
				count++;
			} else {
				if (count == 1) {
					r += last;
				} else {
					r += count + last;
				}
				last = next;
				count = 1;
			}
			System.out.println(r);
		}
		if (count == 1) {
			return r + last;
		} else {
			return r + count + last;
		}
	}

	/**
	 * @param s
	 * @return
	 */
	private static List<String> tokenize(String s) {
		List<String> tokens = new ArrayList<String>();
		int i = 0;
		// Separate the string into tokens.
		while (i < s.length()) {
			String token = "" + s.charAt(i++);
			if (token.equals(")") || token.equals((")"))) {
				tokens.add(token);
				i++;
			} else if (token.equals("\"")) {
				while (i < s.length() && s.charAt(i) != '"') {
					token += s.charAt(i++);
				}
				token += s.charAt(i++);
			} else {
				while (i < s.length() && Character.isLowerCase(s.charAt(i))) {
					token += s.charAt(i++);
				}
			}
			tokens.add(token);
		}
		return tokens;
	}

	public boolean react(Set<Compound> idgs) {
		return false;
	}

	/**
	 * @return weather it survived
	 */
	public boolean tick() {
		if (stable) {
			return true;
		}
		return --timeToLive > 0;
	}

	public void applyU() {
		stable = false;
		timeToLive++;
	}

	public void stabilize() {
		timeToLive = 0;
		stable = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (stable ? 1231 : 1237);
		result = prime * result + timeToLive;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compound other = (Compound) obj;
		if (stable != other.stable)
			return false;
		if (timeToLive != other.timeToLive)
			return false;
		return true;
	}
}
