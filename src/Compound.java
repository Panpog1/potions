import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class Compound {
	/**
	 * @param idgs
	 *          All compounds to check reactivity with including its self
	 * @return all if no reactions are detected else a new List containing the
	 *         compounds after reaction.
	 */
	public abstract String toStringSimple();

	public String toString() {
		String s = toStringSimple();
		List<String> tokens = new ArrayList<String>();
		int i = 0;
		// Separate the string into tokens. (foo) becomes [(foo, )
		while (i < s.length()) {
			String token = "" + s.charAt(i++);
			if (s == "(") {
				while (i < s.length() && s.charAt(i) != ')') {
					token += s.charAt(i++);
				}
			} else {
				while (i < s.length() && Character.isLowerCase(s.charAt(i))) {
					token += s.charAt(i++);
				}
			}
			tokens.add(token);
		}
		int count = 1;
		String r = "";
		String last = tokens.get(0);
		for (int j = 1; j < tokens.size(); j++) {
			String next = tokens.get(j);
			if (next.equals(last)) {
				count++;
			} else {
				if (count == 1) {
					r += last;
				} else {
					r += count+last;
				}
				last = next;
				count = 1;
			}
		}
		if (count == 1) {
			return r + last;
		} else {
			return r + count+last;
		}
	}

	public boolean react(HashSet<Compound> idgs) {
		return false;
	}
}
