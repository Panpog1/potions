package io.github.panpog1.potions;

public class Ae extends Compound {

	@Override
	public String toStringSimple() {
		return "Ae";
	}

	@Override
	public boolean react(Cauldron cauldron) {
		for (Compound c : cauldron.idgs) {
			if (c instanceof E) {
				Compound newC = ((E) c).getInner();
				cauldron.idgs.remove(c);
				cauldron.idgs.add(newC);
				return true;
			}
		}
		return false;
	}
}
