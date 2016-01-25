import java.util.Scanner;

public class Main {
	private static Scanner in = new Scanner(System.in);
	private static Cauldron cauldron = new Cauldron();

	public static void main(String[] args) {
		parseArgs(args);
		while (true) {
			System.out.println(cauldron);
			System.out.print("Add an ingredient: ");
			if (!cauldron.add(in.nextLine())) {
				System.out.println("I don't recognize that ingredient");
			}
		}
	}

	private static void parseArgs(String[] args) {
		if (args.length == 0) {
			return;
		}
		int skipped = 0;
		for (String arg : args) {
			if (!cauldron.add(arg)) {
				System.out.printf("I don't recognize the ingredient %s.\nSkiping it.\n", arg);
				skipped++;
			}
		}
		if (skipped == 0) {
			System.out.printf("All ingredients successfully added");
		}
		if (skipped > 1) {
			System.out.printf("%d ingredients skipped\n", skipped);
		}
	}
}
