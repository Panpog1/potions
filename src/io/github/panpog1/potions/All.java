package io.github.panpog1.potions;

public class All extends Compound {
	private Compound[] parts;

	public All(Compound[] parts) {
		this.parts = parts;
	}
	@Override
	public String toStringSimple() {
		String r = "All(";
		for (Compound part : parts) {
			r += part.toStringNoNums() + ",";
		}
		return r.substring(0, r.length() - 1) + ")";
	}

	@Override
	public boolean react(Cauldron cauldron) {
		cauldron.idgs.remove(this);
		for (Compound part : parts) {
			cauldron.idgs.add(part);
		}
		return true;
	}
}
