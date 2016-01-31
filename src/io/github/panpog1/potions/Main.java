package io.github.panpog1.potions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	private static final Scanner in = new Scanner(System.in);
	private static Cauldron cauldron = new Cauldron();
	private static final Map<String, Cauldron> otherCauldrons = new HashMap<String, Cauldron>();

	public static void main(String[] args) {
		parseArgs(args);
		boolean print = true;
		while (true) {
			if(print)
				System.out.println(cauldron);
			System.out.print("Add an ingredient: ");
			String input = in.nextLine();
			if (input.startsWith("#")) {
				input = input.substring(1);
				exicuteComand(input);
			} else {
				try {
					cauldron.add(input);
				} catch (CompoundParseException e) {
					System.out.println("I don't recognize that ingredient");
					System.out.println(input);
					for (int i = 0; i < e.getErrorOffset(); i++)
						System.out.print(" ");
					System.out.println("^");
				}
			}
		}
	}

	private static void exicuteComand(String s) {
		if (s.equals("done")) {
			return;
		}
		if (s.startsWith("cd ")) {
			String name = s.substring(3);
			if (otherCauldrons.containsKey(name)) {
				cauldron = otherCauldrons.get(name);
				return;
			}
		}
		if (s.startsWith("save ")) {
			otherCauldrons.put(s.substring(5), cauldron);
			cauldron = new Cauldron();
			return;
		}
		if (s.equals("ls")) {
			for (Map.Entry<String, Cauldron> entry : otherCauldrons.entrySet()) {
				System.out.println(String.format("%s %s", entry.getKey(), entry.getValue()));
			}
		}
		if(s.startsWith("add ")){
			String name = s.substring(4);
			if (otherCauldrons.containsKey(name)) {
				Cauldron toAdd = otherCauldrons.get(name);
				for(Compound idg:toAdd.idgs)
					cauldron.add(idg);
				return;
			}
		}
	}

	private static void parseArgs(String[] args) {
		if (args.length == 0) {
			return;
		}
		int skipped = 0;
		for (String arg : args) {
			try {
				cauldron.add(arg);
			} catch (CompoundParseException e) {
				System.out.printf("I don't recognize the ingredient %s.\nSkiping it.\n", arg);
				skipped++;
			}
		}
		if (skipped == 0) {
			System.out.println("All ingredients successfully added");
		}
		if (skipped > 1) {
			System.out.printf("%d ingredients skipped\n", skipped);
		}
	}
}
