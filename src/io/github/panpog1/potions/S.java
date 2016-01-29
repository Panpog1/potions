package io.github.panpog1.potions;

import java.util.HashSet;
import java.util.Set;

public class S extends Compound {

	@Override
	public String toStringSimple() {
		// TODO Auto-generated method stub
		return "S";
	}
	
	@Override
	public boolean react(Cauldron cauldron){
		Set<Compound> newIdgs = new HashSet<Compound>();
		for (Compound idg : cauldron.idgs) {
			idg.stabilize();
			newIdgs.add(idg);
		}
		newIdgs.remove(this);
		cauldron.idgs=newIdgs;
		return true;
	}

}
