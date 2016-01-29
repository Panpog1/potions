package io.github.panpog1.potions;

public class Ah extends Compound {

	@Override
	public String toStringSimple() {
		return "Ah";
	}

	@Override
	public boolean react(Cauldron cauldron) {
		cauldron.idgs.remove(this);
		cauldron.h = false;
		return true;
	}
}
