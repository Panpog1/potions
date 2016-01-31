package io.github.panpog1.potions;

import java.util.HashSet;
import java.util.Set;

public class T extends Compound {

	@Override
	public String toStringSimple() {
		return "T";
	}

	@Override
	public boolean react(Cauldron cauldron) {
		Set<Compound> newIdgs = new HashSet<Compound>();
		for (Compound idg : cauldron.idgs) {
			if (idg.tick()) {
				newIdgs.add(idg);
			}
		}
		newIdgs.remove(this);
		cauldron.idgs = newIdgs;
		return true;
	}
}
