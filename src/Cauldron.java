import java.util.HashSet;
import java.util.Set;

public class Cauldron {
	public Set<Compound> idgs = new HashSet<Compound>();
	public boolean H;

	static Compound parse(String s) {
		// T S and H are in Add
		if (s.isEmpty())
			return null;
		s = numbersToLetters(s);
		if (s == null)
			return null;
		if (s.startsWith("U")) {
			Compound inner = parse(s.substring(1));
			if (inner == null)
				return null;
			inner.applyU();
			return inner;
		}
		if (s.startsWith("E")) {
			Compound inner = parse(s.substring(1));
			if (inner == null)
				return inner;
			return new E(inner);
		}
		if (s.startsWith("(") && s.endsWith(")")) {
			s = s.substring(1, s.length() - 1);
			if (s.contains("(") || s.contains(")"))
				return null;
			return new Base(s);
		}
		if (s.equals("Ae")) {
			return new Ae();
		}
		if (s.startsWith("R")) {
			Compound inner = parse(s.substring(1));
			if (inner == null)
				return null;
			return new R(inner);
		}
		return null;
	}

	boolean add(String next) {
		if (next.equals("T")) {
			// because the equality relations change we need a new hash set.
			Set<Compound> newIdgs = new HashSet<Compound>();
			for (Compound idg : idgs) {
				if (idg.tick()) {
					newIdgs.add(idg);
				}
			}
			idgs = newIdgs;
			return true;
		}
		if (next.equals("S")) {
			for (Compound idg : idgs) {
				idg.stabilize();
			}
			return true;
		}
		if (next.equals("H")) {
			H = !H;
			return true;
		}
		Compound nextIdg = parse(next);
		if (nextIdg == null) {
			return false;
		}
		idgs.add(nextIdg);
		boolean done = false;
		while (!done) {
			done = true;
			for (Compound idg : idgs) {
				if (idg.react(idgs)) {
					done = false;
					break;
				}
			}
		}
		return true;
	}

	public String toString() {
		if (idgs.isEmpty()) {
			return "Cauldron is empty";
		}
		String r = "Cauldron contains: ";
		for (Compound idg : idgs) {
			String prefix = (H ? Integer.toHexString(idg.hashCode()) + ":" : "");
			r += prefix + idg + " ";
		}
		return r;
	}

	static String numbersToLetters(String s) {
		if (Character.isDigit(s.charAt(0))) {
			boolean failure = true;
			for (int i = 1; i < s.length(); i++) {
				if (!Character.isDigit(s.charAt(i))) {
					String prefix = "";
					int times = Integer.parseInt(s.substring(0, i)) - 1;
					s = s.substring(i);
					String first = s.substring(0, 1);
					for (int j = 0; j < times; j++) {
						prefix += first;
					}
					failure = false;
					s = prefix + s;
					break;
				}
			}
			if (failure)
				return null;
		}
		return s;
	}
}
