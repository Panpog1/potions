import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public abstract class Compound {
	static private class Token {
		public String s;
		public int times = 1;

		public Token(String s) {
			this.s = s;
		}

		public String toString() {
			if (times == 1)
				return s;
			return times + s;
		}
	}

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
		List<Token> tokens = tokenize(s);
		for (int i = tokens.size() - 1; i > 0; i--) {
			boolean numberable = tokens.get(i).s.matches("[a-zA-Z]+");
			if (numberable && tokens.get(i).s.equals(tokens.get(i - 1).s)) {
				tokens.get(i - 1).times += tokens.get(i).times;
				tokens.remove(i);
			}
		}
		String r = "";
		for (Token t : tokens) {
			r += t;
		}
		return r;
	}

	/**
	 * @param s
	 * @return
	 */
	private static List<Token> tokenize(String s) {
		List<Token> tokens = new ArrayList<Token>();
		int i = 0;
		// Separate the string into tokens.
		while (i < s.length()) {
			String t = "" + s.charAt(i++);
			if (t.equals(")") || t.equals((")"))) {
				tokens.add(new Token(t));
				i++;
			} else if (t.equals("\"")) {
				while (i < s.length() && s.charAt(i) != '"') {
					t += s.charAt(i++);
				}
				t += s.charAt(i++);
			} else {
				while (i < s.length() && Character.isLowerCase(s.charAt(i))) {
					t += s.charAt(i++);
				}
			}
			tokens.add(new Token(t));
		}
		return tokens;
	}

	/**
	 * @param idgs
	 *          All compounds to check reactivity with including its self
	 * @return idgs if no reactions are detected else a new Set containing the
	 *         compounds after reaction.
	 */

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
