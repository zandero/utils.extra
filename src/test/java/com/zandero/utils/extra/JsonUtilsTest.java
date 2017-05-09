package com.zandero.utils.extra;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zandero.utils.test.Dummy;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static net.trajano.commons.testing.UtilityClassTestUtil.assertUtilityClassWellDefined;
import static org.junit.Assert.*;

public class JsonUtilsTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		assertUtilityClassWellDefined(com.zandero.utils.extra.JsonUtils.class);
	}

	@Test
	public void testGetObjectMapper() {

		assertNotNull(com.zandero.utils.extra.JsonUtils.getObjectMapper());
	}

	@Test
	public void testToJson() {

		Dummy test = new Dummy("1", 2);
		assertEquals("{\"a\":\"1\",\"b\":2,\"hidden\":0}", com.zandero.utils.extra.JsonUtils.toJson(test));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToJsonCustomFail() {

		Dummy test = new Dummy("1", 2);
		try {
			com.zandero.utils.extra.JsonUtils.toJson(test, null);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Missing custom mapper!", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testToJsonCustom() {

		Dummy test = new Dummy("1", 2);
		ObjectMapper custom = new ObjectMapper();
		assertEquals("{\"a\":\"1\",\"b\":2,\"hidden\":0}", com.zandero.utils.extra.JsonUtils.toJson(test, custom));
	}

	@Test
	public void testFromJson() {

		Dummy test = com.zandero.utils.extra.JsonUtils.fromJson("{\"a\":\"1\",\"b\":2}", Dummy.class);
		org.junit.Assert.assertEquals("1", test.a);
		Assert.assertEquals(2, test.b);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromJsonFail() {

		try {
			com.zandero.utils.extra.JsonUtils.fromJson("[\"a\":1,\"b\":2]", Dummy.class);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Given JSON could not be de-serialized. Error: Can not deserialize instance of com.zandero.utils.test.Dummy out of START_ARRAY token\n" +
				" at [Source: (String)\"[\"a\":1,\"b\":2]\"; line: 1, column: 1]", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testFromJsonTypeReference() {

		ArrayList<Dummy> list = com.zandero.utils.extra.JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", new TypeReference<ArrayList<Dummy>>() {
		});
		assertEquals(2, list.size());
		Assert.assertEquals("1", list.get(0).a);
		Assert.assertEquals(2, list.get(1).b);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromJsonTypeReferenceFail() {

		try {
			TypeReference<Object> reference = null;
			com.zandero.utils.extra.JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", reference);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Missing type reference!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromJsonTypeReferenceFail2() {

		try {
			com.zandero.utils.extra.JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", new TypeReference<ArrayList<DummyTo>>() {
			});
		}
		catch (IllegalArgumentException e) {
			assertEquals("Given JSON could not be deserialized. Error: Can not construct instance of com.zandero.utils.extra.JsonUtilsTest$DummyTo (although at least one Creator exists): can only " +
					"instantiate non-static inner class by using default, no-argument constructor\n" +
					" at [Source: (String)\"[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]\"; line: 1, column: 3] (through reference chain: java.util.ArrayList[0])",
						 e.getMessage());
			throw e;
		}
	}

	@Test
	public void testFromJsonReferenceAndCustomMapper() {

		ObjectMapper custom = new ObjectMapper();
		ArrayList<Dummy> list = com.zandero.utils.extra.JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", new TypeReference<ArrayList<Dummy>>() {
		}, custom);
		assertEquals(2, list.size());
		Assert.assertEquals("1", list.get(0).a);
		Assert.assertEquals(2, list.get(1).b);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromJsonReferenceAndCustomMapperFail() {

		try {
			com.zandero.utils.extra.JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", new TypeReference<ArrayList<Dummy>>() {
			}, null);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Missing object mapper!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromJsonReferenceAndCustomMapperFail_2() {

		ObjectMapper custom = new ObjectMapper();
		TypeReference<Object> reference = null;

		try {
			com.zandero.utils.extra.JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", reference, custom);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Missing type reference!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromJsonReferenceAndCustomMapperFail_3() {

		ObjectMapper custom = new ObjectMapper();

		try {
			com.zandero.utils.extra.JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", new TypeReference<ArrayList<DummyTo>>() {
			}, custom);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Given JSON could not be deserialized. Error: Can not construct instance of com.zandero.utils.extra.JsonUtilsTest$DummyTo (although at least one Creator exists): can only " +
				"instantiate non-static inner class by using default, no-argument constructor\n" +
				" at [Source: (String)\"[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]\"; line: 1, column: 3] (through reference chain: java.util.ArrayList[0])", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testFromJsonCustomMapper() {

		Dummy value = com.zandero.utils.extra.JsonUtils.fromJson("{\"a\":\"1\",\"b\":2}", Dummy.class, new ObjectMapper());
		Assert.assertEquals("1", value.a);
		Assert.assertEquals(2, value.b);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromJsonCustomMapperFail() {

		try {
			com.zandero.utils.extra.JsonUtils.fromJson("{\"a\":\"1\",\"b\":2}", Dummy.class, null);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Missing object mapper!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void convertFail() {

		TypeReference<String> ref = null;

		try {
			com.zandero.utils.extra.JsonUtils.convert("bla", ref);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Missing type reference!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void convertFail_2() {

		Class<String> ref = null;

		try {
			com.zandero.utils.extra.JsonUtils.convert("bla", ref);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Missing class reference!", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void fromJsonFailTest() {

		ObjectMapper mapper = com.zandero.utils.extra.JsonUtils.getObjectMapper();
		try {
			com.zandero.utils.extra.JsonUtils.fromJson("BLA", String.class, mapper);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Given JSON could not be deserialized. Error: Unrecognized token 'BLA': was expecting ('true', 'false' or 'null')\n" +
				" at [Source: (String)\"BLA\"; line: 1, column: 7]", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void toJsonFailTest() {

		try {
			InputStream stream = Mockito.mock(InputStream.class);
			com.zandero.utils.extra.JsonUtils.toJson(stream);
		}
		catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().startsWith("Given Object could not be serialized to JSON."));
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void toJsonFailTest_2() {

		ObjectMapper mapper = com.zandero.utils.extra.JsonUtils.getObjectMapper();
		try {
			InputStream stream = Mockito.mock(InputStream.class);
			com.zandero.utils.extra.JsonUtils.toJson(stream, mapper);
		}
		catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().startsWith("Given Object could not be serialized to JSON."));
			throw e;
		}
	}

	@Test
	public void fromJsonAsList() throws IOException {

		List<Dummy> list = com.zandero.utils.extra.JsonUtils.fromJsonAsList("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", Dummy.class);
		assertEquals(2, list.size());

		try {
			com.zandero.utils.extra.JsonUtils.fromJsonAsList("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", DummyTo.class);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Given JSON: '[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]' could not be deserialized to List<DummyTo>. Error: Can not construct instance of com.zandero.utils.extra" +
				".JsonUtilsTest$DummyTo (although at least one Creator exists): can only instantiate non-static inner class by using default, no-argument constructor\n" +
				" at [Source: (String)\"[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]\"; line: 1, column: 3] (through reference chain: java.util.ArrayList[0])", e.getMessage());
		}
	}


	// for testing purposes only
	class DummyTo {

	}
}