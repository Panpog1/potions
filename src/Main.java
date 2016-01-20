import java.util.Scanner;
import java.util.HashSet;

public class Main {
	private static Scanner in = new Scanner(System.in);
	private static HashSet<Compound> idgs = new HashSet<Compound>();

	public static void main(String[] args) {
		int skipped = 0;
		for (String arg : args) {
			boolean c = add(arg);
			if (!c) {
				System.out.printf("I don't recognise the ingridient %s.\nSkiping it.\n", arg);
				skipped++;
			}
		}
		if (skipped == 0) {
			System.out.printf("All ingidients sucsesfully added");
		}
		if (skipped > 1) {
			System.out.printf("%d ingridients skiped\n", skipped);
		}
		while (true) {
			if (!idgs.isEmpty()) {
				System.out.print("Cauldron contains: ");
				for (Compound idg : idgs) {
					System.out.print(idg + " ");
				}
				System.out.println();
			}
			System.out.print("Add ad an ingredient: ");
			// Idg is ingridient
			String nextLine = in.nextLine();
			boolean c = add(nextLine);
			if (!c) {
				System.out.println("I don't recognise that ingridient");
			}
		}
	}

	private static boolean add(String next) {
		if (next.equals("T")) {
			// because the equality relations change we need a new hash set.
			HashSet<Compound> newIdgs = new HashSet<Compound>();
			for (Compound idg : idgs) {
				if (idg.tick()) {
					newIdgs.add(idg);
				}
			}
			idgs = newIdgs;
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

	private static Compound parse(String s) {
		//T is in Add
		if (s.isEmpty())
			return null;
		s = numbersToLetters(s);
		if (s == null)
			return null;
		if (s.startsWith("U")) {
			Compound idg = parse(s.substring(1));
			idg.incrementTimeToLive();
			return idg;
		}
		if (s.startsWith("E")) {
			Compound inner = parse(s.substring(1));
			return inner == null ? null : (new E(inner));
		}
		if (s.startsWith("(") && s.endsWith(")")) {
			return new Base(s.substring(1, s.length() - 1));
		}
		if (s.equals("Ae")) {
			return new Ae();
		}
		if (s.startsWith("R")) {
			Compound inner = parse(s.substring(1));
			return inner == null ? null : (new R(inner));
		}
		return null;
	}

	private static String numbersToLetters(String s) {
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
