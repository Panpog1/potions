import java.util.Scanner;
import java.util.HashSet;

public class Main {
	private static Scanner in = new Scanner(System.in);
	private static Cauldron cauldron = new Cauldron(new HashSet<Compound>());

	public static void main(String[] args) {
		if (args.length != 0) {
			int skipped = 0;
			for (String arg : args) {
				boolean c = cauldron.add(arg);
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
		}
		while (true) {
			System.out.println(cauldron);
			System.out.print("Add ad an ingredient: ");
			String nextLine = in.nextLine();
			boolean c = cauldron.add(nextLine);
			if (!c) {
				System.out.println("I don't recognise that ingridient");
			}
		}
	}
}
