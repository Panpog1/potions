package io.github.panpog1.potions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
	static String[] constIdgNames = { "Ae", "T", "H", "Ah", "S" };
	static final String packageName = Parser.class.getPackage().getName();

	String all;

	static Compound parse(String s) throws CompoundParseException {
		return new Parser(s).parse(s, 0);
	}

	private Parser(String all) {
		this.all = all;
	}

	Compound parse(String s, int offset) throws CompoundParseException {
		// T S and H are in Add
		if (s.trim().isEmpty())
			throw new CompoundParseException(all, offset);
		s = numbersToLetters(s.trim());
		if (s == null)
			throw new CompoundParseException(all, offset);
		if (s.startsWith("U")) {
			Compound inner = parse(s.substring(1), offset + 1);
			inner.applyU();
			return inner;
		}
		if (s.startsWith("E")) {
			Compound inner = parse(s.substring(1), offset + 1);
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
			Compound inner = parse(s.substring(1), offset + 1);
			return new R(inner);
		}
		if (s.startsWith("If(")) {
			return parseIf(s, offset);
		}
		return checkConstIdgNames(s, offset);
	}

	// TODO: Make offset more accurate in CompoundParseException thrown in this
	// method
	Compound parseIf(String s, int offset) throws CompoundParseException {
		if (!s.endsWith(")"))
			throw new CompoundParseException(all, offset + s.length());
		offset += 3;
		s = s.substring(3, s.length() - 1);
		List<Compound> partsL = parseComaSeperated(s, offset);
		// make conditions an array containing the contents of parts except for the
		// last element
		Compound[] parts = new Compound[partsL.size()];
		partsL.toArray(parts);
		return new If(parts);
	}

	// TODO Keep track of offset
	private List<Compound> parseComaSeperated(String s, int offset) throws CompoundParseException {
		List<String> tokens = new ArrayList<String>(
				Arrays.asList(s.replace("(", ",(,").replace(")", ",),").replace("\"", ",\",").split(",")));
		while (tokens.remove("")); // remove all empty strings
		List<Compound> parts = parseTokens(offset, tokens);
		return parts;
	}

	private List<Compound> parseTokens(int offset, List<String> tokens)
			throws CompoundParseException {
		List<Compound> parts = new ArrayList<Compound>();
		System.out.println(tokens);
		for (int i = 0; i < tokens.size(); i++) {
			String partString = "";
			if (tokens.get(i).equals("\"")) {
				do {
					if (i >= tokens.size()) {
						throw new CompoundParseException(all, offset);
					}
					partString += tokens.get(i);
					i++;
				} while (!tokens.get(i).equals("\""));
				partString += tokens.get(i);
				System.out.println(partString);
				parts.add(parse(partString));
			} else if (tokens.get(i).equals("(")) {
				i++;
				System.out.print(i + " ");
				int start = i;
				for (int parens = 1; parens > 0; i++) {
					if (i == tokens.size()) {
						System.out.println("end");
						throw new CompoundParseException(all, i + offset);
					}
					else if (tokens.get(i).equals("(")) {
						parens++;
						System.out.println("high");
					} else if (tokens.get(i).equals(")")) {
						parens--;
						System.out.println("low");
					} else {
						System.out.println("expected");
					}
					System.out.println(tokens.get(i));
					System.out.println(parens);
				}
				System.out.println();
				String toParse = "";
				System.out.println(start + " " + i);
				List<String> subList = tokens.subList(start - 2, i);
				System.out.println(subList);
				for (String token : subList)
					toParse += token;
				System.out.println(toParse);
				parts.add(parse(toParse));
			}
		}
		return parts;
	}

	private Compound checkConstIdgNames(String s, int offset) throws CompoundParseException {
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

	String numbersToLetters(String s) {
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
