import java.util.Scanner;

public class Main {
	private static Scanner in = new Scanner(System.in);
	private static Cauldron cauldron = new Cauldron();

	public static void main(String[] args) {
		parseArgs(args);
		while (true) {
			System.out.println(cauldron);
			System.out.print("Add an ingredient: ");
			String input = in.nextLine();
			try {
				cauldron.add(input);
			} catch (CPE e) {
				System.out.println("I don't recognize that ingredient");
				System.out.println(input);
				for(int i=0;i<e.getErrorOffset();i++)
					System.out.print(" ");
				System.out.println("^");
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
			} catch (CPE e) {
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
