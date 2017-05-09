package com.zandero.utils.extra;

import net.trajano.commons.testing.UtilityClassTestUtil;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ValidatingUtilsTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		UtilityClassTestUtil.assertUtilityClassWellDefined(ValidatingUtils.class);
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
		assertFalse(ValidatingUtils.isUrl("some"));
	}
}