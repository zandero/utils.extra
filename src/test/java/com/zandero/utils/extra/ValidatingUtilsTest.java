package com.zandero.utils.extra;

import com.zandero.utils.junit.AssertFinalClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ValidatingUtilsTest {

	@Test
	public void testDefinition() {

		AssertFinalClass.isWellDefined(ValidatingUtils.class);
	}

	@Test
	public void isEmailTest() {

		assertFalse(ValidatingUtils.isEmail(null));
		assertFalse(ValidatingUtils.isEmail(""));
		assertFalse(ValidatingUtils.isEmail("  "));
		assertFalse(ValidatingUtils.isEmail("@enau  "));
		assertFalse(ValidatingUtils.isEmail(".@enau  "));
		assertFalse(ValidatingUtils.isEmail("test@enau::test"));

		assertTrue(ValidatingUtils.isEmail("a@a.com"));

		assertFalse(ValidatingUtils.isEmail("jason@jason-mbp.local"));
	}

	@Test
	public void isRegExTest() {

		assertFalse(ValidatingUtils.isRegEx(null));
		assertFalse(ValidatingUtils.isRegEx(""));
		assertFalse(ValidatingUtils.isRegEx(" "));
		assertFalse(ValidatingUtils.isRegEx("a"));
		assertFalse(ValidatingUtils.isRegEx("ABC"));
		assertFalse(ValidatingUtils.isRegEx("[ABC"));
		assertFalse(ValidatingUtils.isRegEx("(ABC"));
		assertFalse(ValidatingUtils.isRegEx("{ABC"));
		assertFalse(ValidatingUtils.isRegEx("ABC]"));
		assertFalse(ValidatingUtils.isRegEx("ABC)"));
		assertFalse(ValidatingUtils.isRegEx("ABC}"));
		assertFalse(ValidatingUtils.isRegEx("\\"));

		assertTrue(ValidatingUtils.isRegEx("."));
		assertTrue(ValidatingUtils.isRegEx(".*"));
		assertTrue(ValidatingUtils.isRegEx("A{3}"));
		assertTrue(ValidatingUtils.isRegEx("\\a"));
	}

	@Test
	public void isUrlTest() {

		assertTrue(ValidatingUtils.isUrl("http://some.com/test"));
		assertTrue(ValidatingUtils.isUrl("http://www.some.com/somewhere"));
		assertTrue(ValidatingUtils.isUrl("http://some.com/somewhere"));

		assertFalse(ValidatingUtils.isUrl("http://some/test"));
		assertFalse(ValidatingUtils.isUrl("127.0.0.1"));
		assertFalse(ValidatingUtils.isUrl("some"));
	}

	@Test
	public void isIPv4Test() {

		assertTrue(ValidatingUtils.isIPv4("127.0.0.1"));
		assertFalse(ValidatingUtils.isIPv4("127.a.0.1"));
		assertFalse(ValidatingUtils.isIPv4("http://some.com/test"));
	}
}