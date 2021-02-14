package com.zandero.utils.extra;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zandero.utils.junit.AssertFinalClass;
import com.zandero.utils.test.Dummy;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

	@Test
	void testDefinition() {

		AssertFinalClass.isWellDefined(JsonUtils.class);
	}

	@Test
	void testGetObjectMapper() {

		assertNotNull(JsonUtils.getObjectMapper());
	}

	@Test
	void testToJson() {

		Dummy test = new Dummy("1", 2);
		assertEquals("{\"a\":\"1\",\"b\":2,\"hidden\":0}", JsonUtils.toJson(test));
	}

	@Test
	void testToJsonCustomFail() {

		Dummy test = new Dummy("1", 2);

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> JsonUtils.toJson(test, null));
		assertEquals("Missing custom mapper!", ex.getMessage());
	}

	@Test
	void testToJsonCustom() {

		Dummy test = new Dummy("1", 2);
		ObjectMapper custom = new ObjectMapper()
			                      .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
			                      .setSerializationInclusion(JsonInclude.Include.NON_NULL);
		assertEquals("{\"a\":\"1\",\"b\":2,\"hidden\":0}", JsonUtils.toJson(test, custom));
	}

	@Test
	void testFromJson() {

		Dummy test = JsonUtils.fromJson("{\"a\":\"1\",\"b\":2}", Dummy.class);
		assertEquals("1", test.a);
		assertEquals(2, test.b);
	}

	@Test
	void testFromJsonFail() {

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> JsonUtils.fromJson("[\"a\":1,\"b\":2]", Dummy.class));
		assertEquals(
			"Given JSON could not be de-serialized. Error: Cannot deserialize value of type `com.zandero.utils.test.Dummy` from Array value (token `JsonToken.START_ARRAY`)\n" +
				" at [Source: (String)\"[\"a\":1,\"b\":2]\"; line: 1, column: 1]",
			e.getMessage());
	}

	@Test
	void testFromJsonTypeReference() {

		ArrayList<Dummy> list = JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", new TypeReference<ArrayList<Dummy>>() {
		});
		assertEquals(2, list.size());
		assertEquals("1", list.get(0).a);
		assertEquals(2, list.get(1).b);
	}

	@Test
	void testFromJsonTypeReferenceFail() {

		TypeReference<Object> reference = null;
		IllegalArgumentException e =
			assertThrows(IllegalArgumentException.class, () -> JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", reference));
		assertEquals("Missing type reference!", e.getMessage());
	}

	@Test
	void testFromJsonTypeReferenceFail2() {

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
		                                          () -> JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]",
		                                                                   new TypeReference<ArrayList<DummyTo>>() {
		                                                                   }));
		assertEquals(
			"Given JSON could not be deserialized. Error: Cannot construct instance of `com.zandero.utils.extra.JsonUtilsTest$DummyTo`: non-static inner classes like this can only by instantiated using default, no-argument constructor\n" +
				" at [Source: (String)\"[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]\"; line: 1, column: 3] (through reference chain: java.util.ArrayList[0])",
			e.getMessage());
	}

	@Test
	void testFromJsonReferenceAndCustomMapper() {

		ObjectMapper custom = new ObjectMapper();
		ArrayList<Dummy> list = JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", new TypeReference<ArrayList<Dummy>>() {
		}, custom);

		assertEquals(2, list.size());
		assertEquals("1", list.get(0).a);
		assertEquals(2, list.get(1).b);
	}

	@Test
	void testFromJsonReferenceAndCustomMapperFail() {

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
		                                          () -> JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]",
		                                                                   new TypeReference<ArrayList<Dummy>>() {
		                                                                   }, null));
		assertEquals("Missing object mapper!", e.getMessage());
	}

	@Test
	void testFromJsonReferenceAndCustomMapperFail_2() {

		ObjectMapper custom = new ObjectMapper();
		TypeReference<Object> reference = null;

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
		                                          () -> JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", reference, custom));
		assertEquals("Missing type reference!", e.getMessage());

	}

	@Test
	void testFromJsonReferenceAndCustomMapperFail_3() {

		ObjectMapper custom = new ObjectMapper();

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
		                                          () -> JsonUtils.fromJson("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]",
		                                                                   new TypeReference<ArrayList<DummyTo>>() {
		                                                                   },
		                                                                   custom));
		assertEquals(
			"Given JSON could not be deserialized. Error: Cannot construct instance of `com.zandero.utils.extra.JsonUtilsTest$DummyTo`: non-static inner classes like this can only by instantiated using default, no-argument constructor\n" +
				" at [Source: (String)\"[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]\"; line: 1, column: 3] (through reference chain: java.util.ArrayList[0])",
			e.getMessage());
	}

	@Test
	void testFromJsonCustomMapper() {

		Dummy value = JsonUtils.fromJson("{\"a\":\"1\",\"b\":2}", Dummy.class, new ObjectMapper());
		assertEquals("1", value.a);
		assertEquals(2, value.b);
	}

	@Test
	void testFromJsonCustomMapperFail() {

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
		                                          () ->
			                                          JsonUtils.fromJson("{\"a\":\"1\",\"b\":2}", Dummy.class, null));
		assertEquals("Missing object mapper!", e.getMessage());
	}

	@Test
	void convertFail() {

		TypeReference<String> ref = null;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> JsonUtils.convert("bla", ref));
		assertEquals("Missing type reference!", e.getMessage());
	}

	@Test
	void convertFail_2() {

		Class<String> ref = null;

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> JsonUtils.convert("bla", ref));
		assertEquals("Missing class reference!", e.getMessage());

	}

	@Test
	void fromJsonFailTest() {

		ObjectMapper mapper = JsonUtils.getObjectMapper();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> JsonUtils.fromJson("BLA", String.class, mapper));
		assertEquals("Given JSON could not be deserialized. Error: Unrecognized token 'BLA': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\n" +
						 " at [Source: (String)\"BLA\"; line: 1, column: 4]", e.getMessage());
	}

	@Test
	void toJsonFailTest() {

		InputStream stream = Mockito.mock(InputStream.class);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> JsonUtils.toJson(stream));
		assertTrue(e.getMessage().startsWith("Given Object could not be serialized to JSON."));

	}

	@Test
	void toJsonFailTest_2() {

		ObjectMapper mapper = JsonUtils.getObjectMapper();
		InputStream stream = Mockito.mock(InputStream.class);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> JsonUtils.toJson(stream, mapper));
		assertTrue(e.getMessage().startsWith("Given Object could not be serialized to JSON."));
	}

	@Test
	void fromJsonAsList() {

		List<Dummy> list = JsonUtils.fromJsonAsList("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", Dummy.class);
		assertEquals(2, list.size());

		try {
			JsonUtils.fromJsonAsList("[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]", DummyTo.class);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Given JSON: '[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]' could not be deserialized to List<DummyTo>. " +
							 "Error: Cannot construct instance of `com.zandero.utils.extra.JsonUtilsTest$DummyTo`: non-static inner classes like this can only by instantiated using default, no-argument constructor\n" +
							 " at [Source: (String)\"[{\"a\":\"1\",\"b\":2},{\"a\":\"1\",\"b\":2}]\"; line: 1, column: 3] (through reference chain: java.util.ArrayList[0])",
			             e.getMessage());
		}
	}

	@Test
	void testCustomDefaultMapper() {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

		JsonUtils.setObjectMapper(objectMapper);

		Dummy test = new Dummy("1", 2);
		assertEquals("{\"a\":\"1\",\"b\":2,\"hidden\":0,\"someValue\":null}", JsonUtils.toJson(test));

		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

		JsonUtils.setObjectMapper(objectMapper);

		test = new Dummy("1", 2);
		assertEquals("{\"a\":\"1\",\"b\":2,\"hidden\":0}", JsonUtils.toJson(test));
	}

	// for testing purposes only
	class DummyTo {

	}
}