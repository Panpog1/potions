package io.github.panpog1.potions;

import java.util.HashSet;
import java.util.Set;

public class Cauldron {

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
		add(Parser.parse(next));
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
}
