package io.github.panpog1.potions;

import java.util.HashSet;
import java.util.Set;

public class Cauldron {
	public Set<Compound> idgs = new HashSet<Compound>();
	public boolean h;

	static Compound parse(String s) throws CPE {
		return parse(s, s, 0);
	}

	static Compound parse(String all, String s, int offset) throws CPE {
		// T S and H are in Add
		if (s.isEmpty())
			throw new CPE(all, offset);
		s = numbersToLetters(s.trim());
		if (s == null)
			throw new CPE(all, offset);
		if (s.startsWith("U")) {
			Compound inner = parse(all, s.substring(1), offset + 1);
			inner.applyU();
			return inner;
		}
		if (s.startsWith("E")) {
			Compound inner = parse(all, s.substring(1), offset + 1);
			return new E(inner);
		}
		if (s.startsWith("\"") && s.endsWith("\"")) {
			s = s.substring(1, s.length() - 1);
			offset++;
			if (s.contains("\""))
				throw new CPE(all, s.indexOf("\""));
			if (s.contains(" "))
				throw new CPE(all, s.indexOf(" "));
			return new Base(s);
		}
		if (s.equals("Ae")) {
			return new Ae();
		}
		if (s.equals("H")) {
			return new H();
		}
		if (s.equals("Ah")) {
			return new Ah();
		}
		if (s.startsWith("R")) {
			Compound inner = parse(all, s.substring(1), offset + 1);
			return new R(inner);
		}
		if (s.startsWith("If(")) {
			offset += 3;
			if (!s.endsWith(")"))
				throw new CPE(all, offset);
			s = s.substring(3, s.length() - 1);
			int i = 0;
			int parens = 0;
			boolean fail = true;
			while (i < s.length()) {
				if (s.charAt(i) == ',' && parens == 0) {
					fail = false;
					break;
				} else if (s.charAt(i) == '(') {
					parens++;
				} else if (s.charAt(i) == ')') {
					parens--;
					if (parens > 0) {
						throw new CPE(all, offset + i);
					}
				}
				i++;
			}
			if (fail)
				throw new CPE(all, offset);
			Compound condition = parse(all, s.substring(0, i), offset);
			Compound body = parse(all, s.substring(i + 1), offset + i + 1);
			return new If(condition, body);
		}
		throw new CPE(all, offset);

	}

	boolean add(String next) throws CPE {
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
			Set<Compound> newIdgs = new HashSet<Compound>();
			for (Compound idg : idgs) {
				idg.stabilize();
				newIdgs.add(idg);
			}
			idgs = newIdgs;
			return true;
		}
		Compound nextIdg;
		nextIdg = parse(next);
		idgs.add(nextIdg);
		FullyReact();
		return true;
	}

	void FullyReact() {
		boolean done = false;
		while (!done) {
			done = true;
			for (Compound idg : idgs) {
				if (idg.react(this)) {
					done = false;
					break;
				}
			}
		}
	}

	public String toString() {
		if (idgs.isEmpty()) {
			return "Cauldron is empty";
		}
		String r = "Cauldron contains: ";
		for (Compound idg : idgs) {
			String prefix = (h ? Integer.toHexString(idg.hashCode()) + ":" : "");
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
