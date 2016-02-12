package io.github.panpog1.potions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cauldron {
	private static String[] constIdgNames = { "Ae", "T", "H", "Ah", "S" };
	private static final String packageName = Cauldron.class.getPackage().getName();

	public Set<Compound> idgs = new HashSet<Compound>();
	public boolean h;

	@Override
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
		Compound c = parse2(all, s, offset);
		System.out.println(c + ": " + c.getClass().getName());
		return c;
	}

	private static Compound parse2(String all, String s, int offset) throws CompoundParseException {
		// T S and H are in Add
		if (s.trim().isEmpty())
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
			offset++;
			if (s.length() == 1)
				throw new CompoundParseException(all, offset);
			s = s.substring(1, s.length() - 1);
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
			return parseIf(all, s, offset);
		}
		return checkConstIdgNames(all, s, offset);
	}

	// TODO: Make offset more accurate in CompoundParseException thrown in this
	// method
	static Compound parseIf(String all, String s, int offset) throws CompoundParseException {
		if (!s.endsWith(")"))
			throw new CompoundParseException(all, offset + s.length());
		offset += 3;
		s = s.substring(3, s.length() - 1);
		List<String> tokens = new ArrayList<String>(
				Arrays.asList(s.replace("(", ",(,").replace(")", ",),").replace("\"", ",\",").split(",")));
		while (tokens.remove(""));// remove all empty strings
		List<Compound> parts = parseParts(all, offset, tokens);
		System.out.println(parts);
		// make conditions an array containing the contents of parts except for the
		// last element
		Compound[] conditions = new Compound[0];
		final List<Compound> subList = parts.subList(0, parts.size());
		subList.toArray(conditions);
		System.out.println(conditions);
		return new If(conditions, parts.get(parts.size() - 1));
	}

	private static List<Compound> parseParts(String all, int offset, List<String> tokens)
			throws CompoundParseException {
		List<Compound> parts = new ArrayList<Compound>();
		System.out.println(tokens);
		for (int i = 0; i < tokens.size(); i++) {
			String partString = "";
			if (tokens.get(i).equals("\"")) {
				do {
					System.out.print(i + tokens.get(i) + " ");
					if (i >= tokens.size()) {
						throw new CompoundParseException(all, offset);
					}
					partString += tokens.get(i);
					i++;
					System.out.println(i + tokens.get(i) + " " + partString);
				} while (!tokens.get(i).equals("\""));
				partString += tokens.get(i);
				System.out.println(partString);
				parts.add(parse(partString));
			}
		}
		return parts;
	}

	private static Compound checkConstIdgNames(String all, String s, int offset)
			throws CompoundParseException {
		System.out.println(s);
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
