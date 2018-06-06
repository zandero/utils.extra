package com.zandero.utils.extra;

import com.zandero.utils.junit.AssertFinalClass;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UrlUtilsTest {

	@Test
	void testDefinition() {

		AssertFinalClass.isWellDefined(UrlUtils.class);
	}

	@Test
	void testGetFullUrlWithoutRootUrl() {

		assertThrows(IllegalArgumentException.class,
		             () -> UrlUtils.composeUrl(null, (String)null));
	}

	@Test
	void testGetFullUrlWithoutRootUrl_2() {

		assertThrows(IllegalArgumentException.class,
		             () -> UrlUtils.composeUrl("", (String)null));
	}

	@Test
	void testGetFullUrlWithoutRootUrl_3() {

		assertThrows(IllegalArgumentException.class,
		             () -> UrlUtils.composeUrl("  ", (String)null));
	}

	@Test
	void composeUrlTest() {

		assertEquals("http://test.com", UrlUtils.composeUrl("http://test.com", (String)null));
		assertEquals("http://test.com", UrlUtils.composeUrl("http://test.com/", ""));
		assertEquals("https://test.com", UrlUtils.composeUrl("https://test.com/", (String)null));
		assertEquals("https://test.com", UrlUtils.composeUrl("https://test.com/", (Map<String, String>)null));

		assertEquals("http://test.com/test", UrlUtils.composeUrl("http://test.com", "/test"));
		assertEquals("http://test.com/test", UrlUtils.composeUrl("http://test.com/", "/test"));
		assertEquals("http://test.com/test", UrlUtils.composeUrl("http://test.com", "test"));
		assertEquals("http://test.com/test", UrlUtils.composeUrl("http://test.com/", "/test"));
	}

	@Test
	void testGetFullUrl() {

		assertEquals("http://zandero.com", UrlUtils.composeUrl("http://zandero.com", (String)null));
		assertEquals("http://zandero.com", UrlUtils.composeUrl("http://zandero.com/", (String)null));
		assertEquals("http://zandero.com", UrlUtils.composeUrl("http://zandero.com/", (Map<String, String>)null));

		assertEquals("http://zandero.com", UrlUtils.composeUrl("http://zandero.com", "/"));
		assertEquals("http://zandero.com/test", UrlUtils.composeUrl("http://zandero.com", "test"));
		assertEquals("http://zandero.com/test", UrlUtils.composeUrl("http://zandero.com", "/test"));

		assertEquals("http://zandero.com", UrlUtils.composeUrl("http://zandero.com/", "/"));
		assertEquals("http://zandero.com/test", UrlUtils.composeUrl("http://zandero.com/", "test"));
		assertEquals("http://zandero.com/test", UrlUtils.composeUrl("http://zandero.com/", "/test"));
	}

	@Test
	void testGetFullUrl_2() {

		assertEquals("http://zandero.com", UrlUtils.composeUrl("http://zandero.com", "http://zandero.com"));
		assertEquals("http://zandero.com", UrlUtils.composeUrl("http://zandero.com/", "http://zandero.com"));

		assertEquals("http://zandero.com/test.html", UrlUtils.composeUrl("http://zandero.com", "http://zandero.com/test.html"));
		assertEquals("http://zandero.com/test/test.html", UrlUtils.composeUrl("http://zandero.com", "http://zandero.com/test/test.html"));
	}


	@Test
	void composeUrlTest2() {

		assertEquals("http://test.com", UrlUtils.composeUrl("http", "test.com", 80));
		assertEquals("http://test.com", UrlUtils.composeUrl("http", "test.com", 443));
		assertEquals("https://test.com", UrlUtils.composeUrl("https", "test.com", 443));

		assertEquals("http://test.com:5555", UrlUtils.composeUrl("http", "test.com", 5555));
		assertEquals("http://test.com:5555", UrlUtils.composeUrl("http", "test.com", 5555, null));
		assertEquals("http://test.com:5555", UrlUtils.composeUrl("http", "test.com", 5555, ""));
		assertEquals("http://test.com:5555/test", UrlUtils.composeUrl("http", "test.com", 5555, "test"));
		assertEquals("http://test.com:5555/test/test.html", UrlUtils.composeUrl("http", "test.com", 5555, "/test/test.html"));
	}

	@Test
	void composeUrlTestWithQuery() {

		Map<String, String> query = new HashMap<>();
		query.put("A", "1");
		query.put("B", "<?>");
		query.put("C", "a");

		assertEquals("http://test.com/somepath?A=1&B=%3C%3F%3E&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath", query));
		assertEquals("http://test.com/somepath?A=1&B=%3C%3F%3E&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?", query));
		assertEquals("http://test.com/somepath?A=1&B=%3C%3F%3E&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath/", query));

		assertEquals("http://test.com/somepath?X=1&A=1&B=%3C%3F%3E&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1", query));
		assertEquals("http://test.com/somepath?X=1&A=1&B=%3C%3F%3E&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&", query));
		assertEquals("http://test.com/somepath?X=1&Y=2&A=1&B=%3C%3F%3E&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2", query));
		assertEquals("http://test.com/somepath?X=1&Y=2&A=1&B=%3C%3F%3E&C=a",
		             UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2&", query));
	}

	@Test
	void composeUrlTestWithQueryMissing() {

		Map<String, String> query = new HashMap<>();
		query.put("A", "");
		query.put(null, "<?>");
		query.put("C", "a");

		assertEquals("http://test.com/somepath?C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath", query));
		assertEquals("http://test.com/somepath?C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?", query));
		assertEquals("http://test.com/somepath?C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath/", query));

		assertEquals("http://test.com/somepath?X=1&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1", query));
		assertEquals("http://test.com/somepath?X=1&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&", query));
		assertEquals("http://test.com/somepath?X=1&Y=2&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2", query));
		assertEquals("http://test.com/somepath?X=1&Y=2&C=a", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2&", query));
	}

	@Test
	void composeUrlTestWithQueryInvalid() {

		Map<String, String> query = new HashMap<>();
		query.put("A", "");
		query.put(null, "<?>");

		assertEquals("http://test.com/somepath", UrlUtils.composeUrl("http", "test.com", 80, "/somepath", query));
		assertEquals("http://test.com/somepath", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?", query));
		assertEquals("http://test.com/somepath", UrlUtils.composeUrl("http", "test.com", 80, "/somepath/", query));

		assertEquals("http://test.com/somepath?X=1", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1", query));
		assertEquals("http://test.com/somepath?X=1", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&", query));
		assertEquals("http://test.com/somepath?X=1&Y=2", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2", query));
		assertEquals("http://test.com/somepath?X=1&Y=2", UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2&", query));
	}

	@Test
	void testResolveDomainName() {

		assertEquals("test.something.com", UrlUtils.resolveDomain("http://test.something.com/some/server.html"));
		assertEquals("test.something.com", UrlUtils.resolveDomain("test.something.com/hello"));
		assertEquals("test.something.com", UrlUtils.resolveDomain("test.something.com"));

		assertEquals("127.0.0.1", UrlUtils.resolveDomain("smtp://127.0.0.1/hello"));
		assertEquals("127.0.0.1", UrlUtils.resolveDomain("ftp://127.0.0.1/hello"));
		assertEquals("127.0.0.1", UrlUtils.resolveDomain("https://127.0.0.1/hello"));
		assertEquals("127.0.0.1", UrlUtils.resolveDomain("http://127.0.0.1/hello"));
		assertEquals("127.0.0.1", UrlUtils.resolveDomain("127.0.0.1/hello"));
		assertEquals("127.0.0.1", UrlUtils.resolveDomain("127.0.0.1"));

		assertEquals(null, UrlUtils.resolveDomain(null));
		assertEquals(null, UrlUtils.resolveDomain(""));
		assertEquals(null, UrlUtils.resolveDomain(" "));
	}

	@Test
	void urlEncodeTest() {

		assertEquals(
			"http://cdn.jsdelivr.net/webjars/org.webjars/browser-sync/2.7.5/node_modules/istanbul/node_modules/fileset/tests/fixtures/an%20(odd)%20filename.js",
			UrlUtils.urlEncode(
				"http://cdn.jsdelivr.net/webjars/org.webjars/browser-sync/2.7.5/node_modules/istanbul/node_modules/fileset/tests/fixtures/an (odd) filename.js"));

		assertEquals("http://cdn.jsdelivr.net/some?pace=in%20the%20sun",
		             UrlUtils.urlEncode("http://cdn.jsdelivr.net/some?pace=in the sun"));

		assertEquals("http://cdn.jsdelivr.net/some?pace=in%20the%20sun&is='high'",
		             UrlUtils.urlEncode("http://cdn.jsdelivr.net/some?pace=in the sun&is='high'"));
	}

	@Test
	void resolveUrlParts() {

		assertEquals("http", UrlUtils.resolveScheme("http://test.com:80"));
		assertEquals("test.com", UrlUtils.resolveDomain("http://test.com:80"));
		assertEquals(80, UrlUtils.resolvePort("http://test.com:80").intValue());

		assertEquals("https", UrlUtils.resolveScheme("https://www.zandero.co"));
		assertEquals("www.zandero.co", UrlUtils.resolveDomain("https://www.zandero.co"));
		assertEquals(80, UrlUtils.resolvePort("https://www.zandero.co").intValue());
	}

	@Test
	void encodeQueryTest() {

		assertEquals("some+search+with+spaces", UrlUtils.encodeQuery("some search with spaces"));
		assertEquals("http%3A%2F%2Ftest.com%3A80", UrlUtils.encodeQuery("http://test.com:80"));
	}

	@Test
	void parseQueryTest() {

		Map<String, String> map = UrlUtils.getQuery("http://some.com/somewhere");
		assertTrue(map.isEmpty());

		map = UrlUtils.getQuery("in=the");
		assertEquals(1, map.size());
		assertEquals("the", map.get("in"));

		map = UrlUtils.getQuery("http://some.com/somewhere?in=the");
		assertEquals(1, map.size());
		assertEquals("the", map.get("in"));

		map = UrlUtils.getQuery("http://some.com/somewhere?in=the&middle=of&ocean");
		assertEquals(3, map.size());
		assertEquals("the", map.get("in"));
		assertEquals("of", map.get("middle"));
		assertTrue(map.containsKey("ocean"));
	}

	@Test
	void getBaseUrlTest() {

		assertEquals("http://some.com", UrlUtils.getBaseUrl("http://some.com/somewhere"));
		assertEquals("http://some.com", UrlUtils.getBaseUrl("http://some.com/"));
		assertEquals("http://build.zandero.co:8083", UrlUtils.getBaseUrl("http://build.zandero.co:8083/rest/api/2/user?username=xrado"));
	}

	@Test
	void composeQuery() {

		assertEquals("", UrlUtils.composeQuery(null));
		assertEquals("", UrlUtils.composeQuery(new HashMap<>()));

		Map<String, String> map = new HashMap<>();
		map.put("a", "1");
		assertEquals("a=1", UrlUtils.composeQuery(map));

		map.put("file", "an (odd) filename.js");
		assertEquals("a=1&file=an+%28odd%29+filename.js", UrlUtils.composeQuery(map));
	}

	@Test
	void composeUrl() {

		assertEquals("http://some.domain.com", UrlUtils.composeUrl("http://some.domain.com", (String)null));
		assertEquals("http://some.domain.com", UrlUtils.composeUrl("http://some.domain.com", new HashMap<>()));

		Map<String, String> map = new HashMap<>();
		map.put("a", "1");
		assertEquals("http://some.domain.com?a=1", UrlUtils.composeUrl("http://some.domain.com", map));

		map.put("file", "an (odd) filename.js");
		assertEquals("http://some.domain.com?a=1&file=an+%28odd%29+filename.js", UrlUtils.composeUrl("http://some.domain.com/", map));
	}
}