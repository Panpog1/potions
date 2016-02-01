package io.github.panpog1.potions;

import java.util.HashSet;
import java.util.Set;

public class Cauldron {
	private static String[] constIdgNames = { "Ae", "T", "H", "Ah", "S" };
	private static final String packageName = Cauldron.class.getPackage().getName();

	public Set<Compound> idgs = new HashSet<Compound>();
	public boolean h;

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

	void add(String next) throws CompoundParseException {
		add(parse(next));
	}

	void add(Compound next) {
		idgs.add(next);
		FullyReact();
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

	static Compound parse(String s) throws CompoundParseException {
		return parse(s, s, 0);
	}

	private static Compound parse(String all, String s, int offset) throws CompoundParseException {
		// T S and H are in Add
		if (s.isEmpty())
			throw new CompoundParseException(all, offset);
		s = numbersToLetters(s.trim());
		if (s == null)
			throw new CompoundParseException(all, offset);
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
				throw new CompoundParseException(all, s.indexOf("\""));
			if (s.contains(" "))
				throw new CompoundParseException(all, s.indexOf(" "));
			return new Base(s);
		}
		if (s.startsWith("R")) {
			Compound inner = parse(all, s.substring(1), offset + 1);
			return new R(inner);
		}
		if (s.startsWith("If(")) {
			if (!s.endsWith(")"))
				throw new CompoundParseException(all, offset+s.length());
			offset += 3;
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
						throw new CompoundParseException(all, offset + i);
					}
				}
				i++;
			}
			if (fail)
				throw new CompoundParseException(all, offset);
			Compound condition = parse(all, s.substring(0, i), offset);
			Compound body = parse(all, s.substring(i + 1), offset + i + 1);
			return new If(condition, body);
		}
		return checkConstIdgNames(all, s, offset);
	}

	private static Compound checkConstIdgNames(String all, String s, int offset)
			throws CompoundParseException {
		for (String constIdgName : constIdgNames) {
			if (s.startsWith(constIdgName)) {
				if (!s.equals(constIdgName)) {
					throw new CompoundParseException(all, offset + constIdgName.length());
				}
				try {
					Class<?> obj;
					obj = Class.forName(packageName + "." + constIdgName);
					@SuppressWarnings("unchecked")
					Class<Compound> clazz = (Class<Compound>) obj;
					return clazz.getConstructor().newInstance();
				} catch (Exception e) {
					throw new AssertionError(e);
				}
			}
		}
		throw new CompoundParseException(all, offset);
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
