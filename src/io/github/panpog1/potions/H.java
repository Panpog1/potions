package io.github.panpog1.potions;

public class H extends Compound {

	@Override
	public String toStringSimple() {
		return "H";
	}

	@Override
	public boolean react(Cauldron cauldron) {
		cauldron.idgs.remove(this);
		cauldron.h = true;
		return true;
	}

}
