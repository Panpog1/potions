import java.util.List;

public abstract class Compound {
	/**
	 * @param all All compounds to check reactivity with including its self
	 * @return all if no reactions are detected else a new List containing the compounds after reaction.
	 */
	public List<Compound> react(List<Compound> all){
		return all;
	}
}