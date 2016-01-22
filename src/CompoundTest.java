import static org.junit.Assert.*;

import org.junit.Test;

public class CompoundTest {

	@Test
	public void testLetersToNumbers() {
		String x = Compound.letersToNumbers("RRR");
		if (!x.equals("3R"))
			fail(x);
	}

}
