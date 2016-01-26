import java.text.ParseException;

public class CPE extends ParseException {

	private static final long serialVersionUID = -8362875748530214017L;

	public CPE(String s, int errorOffset) {
		super(s, errorOffset);
	}

}
