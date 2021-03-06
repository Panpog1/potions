package io.github.panpog1.potions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
	static final String[] IdgVarNames = { "A", "B", "C", "D", "X", "Y", "Z" };
	static final String[] IdgNamesOfClasses = { "Ae", "T", "H", "Ah", "S" };
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
			Compound[] parts = tokenize("If(", s, offset);
			return new If(parts);
		}
		if(s.startsWith("All(")){
			return new All(tokenize("All(", s, offset));
		}
		for (String var : IdgVarNames) {
			if (s.equals(var)) {
				return new Var(s);
			}
		}
		for (String constIdgName : IdgNamesOfClasses) {
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

	private Compound[] tokenize(String prefix, String s, int offset) throws CompoundParseException {
		if (!s.endsWith(")"))
			throw new CompoundParseException(all, offset + s.length());
		offset += prefix.length();
		s = s.substring(prefix.length(), s.length() - 1);
		List<Compound> partsL = parseComaSeperated(s, offset);
		// make conditions an array containing the contents of parts except for the
		// last element
		Compound[] parts = new Compound[partsL.size()];
		partsL.toArray(parts);
		return parts;
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
				parts.add(parse(partString));
			} else if (tokens.get(i).equals("(")) {
				i++;
				int start = i;
				for (int parens = 1; parens > 0; i++) {
					if (i == tokens.size()) {
						throw new CompoundParseException(all, i + offset);
					}
					else if (tokens.get(i).equals("(")) {
						parens++;
					} else if (tokens.get(i).equals(")")) {
						parens--;
					}
				}
				String toParse = "";
				List<String> subList = tokens.subList(start - 2, i);
				for (String token : subList)
					toParse += token;
				parts.add(parse(toParse));
			} else {
				parts.add(parse(tokens.get(i)));
			}
		}
		return parts;
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
