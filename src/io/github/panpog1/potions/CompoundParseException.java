package io.github.panpog1.potions;

import java.text.ParseException;

public class CompoundParseException extends ParseException {

	private static final long serialVersionUID = -8362875748530214017L;

	public CompoundParseException(String s, int errorOffset) {
		super(s, errorOffset);
	}

}
