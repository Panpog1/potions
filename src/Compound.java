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
		if (s.length() <= 1) {
			return s;
		}
		List<String> symbols = new ArrayList<String>();
		int i = 0;
		while (i < s.length()) {
			String symbol = "" + s.charAt(i++);
			while (i < s.length() && Character.isLowerCase(s.charAt(i))) {
				symbol += s.charAt(i);
				i++;
			}
			symbols.add(symbol);
		}
		return symbols.toString();
	}

	public boolean react(HashSet<Compound> idgs) {
		return false;
	}
}
