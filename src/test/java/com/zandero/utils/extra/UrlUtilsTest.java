package com.zandero.utils.extra;

import com.zandero.utils.UrlUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static net.trajano.commons.testing.UtilityClassTestUtil.assertUtilityClassWellDefined;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UrlUtilsTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		assertUtilityClassWellDefined(com.zandero.utils.UrlUtils.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFullUrlWithoutRootUrl() {

		com.zandero.utils.UrlUtils.composeUrl(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFullUrlWithoutRootUrl_2() {

		com.zandero.utils.UrlUtils.composeUrl("", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFullUrlWithoutRootUrl_3() {

		com.zandero.utils.UrlUtils.composeUrl("  ", null);
	}

	@Test
	public void composeUrlTest() {

		assertEquals("http://test.com", com.zandero.utils.UrlUtils.composeUrl("http://test.com", null));
		assertEquals("http://test.com", com.zandero.utils.UrlUtils.composeUrl("http://test.com/", ""));
		assertEquals("https://test.com", com.zandero.utils.UrlUtils.composeUrl("https://test.com/", null));

		assertEquals("http://test.com/test", com.zandero.utils.UrlUtils.composeUrl("http://test.com", "/test"));
		assertEquals("http://test.com/test", com.zandero.utils.UrlUtils.composeUrl("http://test.com/", "/test"));
		assertEquals("http://test.com/test", com.zandero.utils.UrlUtils.composeUrl("http://test.com", "test"));
		assertEquals("http://test.com/test", com.zandero.utils.UrlUtils.composeUrl("http://test.com/", "/test"));
	}

	@Test
	public void testGetFullUrl() {

		Assert.assertEquals("http://zandero.com", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com", null));
		Assert.assertEquals("http://zandero.com", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com/", null));

		Assert.assertEquals("http://zandero.com", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com", "/"));
		Assert.assertEquals("http://zandero.com/test", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com", "test"));
		Assert.assertEquals("http://zandero.com/test", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com", "/test"));

		Assert.assertEquals("http://zandero.com", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com/", "/"));
		Assert.assertEquals("http://zandero.com/test", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com/", "test"));
		Assert.assertEquals("http://zandero.com/test", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com/", "/test"));
	}

	@Test
	public void testGetFullUrl_2() {

		Assert.assertEquals("http://zandero.com", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com", "http://zandero.com"));
		Assert.assertEquals("http://zandero.com", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com/", "http://zandero.com"));

		Assert.assertEquals("http://zandero.com/test.html", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com", "http://zandero.com/test.html"));
		Assert.assertEquals("http://zandero.com/test/test.html", com.zandero.utils.UrlUtils.composeUrl("http://zandero.com", "http://zandero.com/test/test.html"));
	}


	@Test
	public void composeUrlTest2() {

		assertEquals("http://test.com", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80));
		assertEquals("http://test.com", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 443));
		assertEquals("https://test.com", com.zandero.utils.UrlUtils.composeUrl("https", "test.com", 443));

		assertEquals("http://test.com:5555", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 5555));
		assertEquals("http://test.com:5555", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 5555, null));
		assertEquals("http://test.com:5555", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 5555, ""));
		assertEquals("http://test.com:5555/test", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 5555, "test"));
		assertEquals("http://test.com:5555/test/test.html", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 5555, "/test/test.html"));
	}

	@Test
	public void composeUrlTestWithQuery() {

		Map<String, String> query = new HashMap<>();
		query.put("A", "1");
		query.put("B", "<?>");
		query.put("C", "a");

		assertEquals("http://test.com/somepath?A=1&B=%3C%3F%3E&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath", query));
		assertEquals("http://test.com/somepath?A=1&B=%3C%3F%3E&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?", query));
		assertEquals("http://test.com/somepath?A=1&B=%3C%3F%3E&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath/", query));

		assertEquals("http://test.com/somepath?X=1&A=1&B=%3C%3F%3E&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1", query));
		assertEquals("http://test.com/somepath?X=1&A=1&B=%3C%3F%3E&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&", query));
		assertEquals("http://test.com/somepath?X=1&Y=2&A=1&B=%3C%3F%3E&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2", query));
		assertEquals("http://test.com/somepath?X=1&Y=2&A=1&B=%3C%3F%3E&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2&", query));
	}

	@Test
	public void composeUrlTestWithQueryMissing() {

		Map<String, String> query = new HashMap<>();
		query.put("A", "");
		query.put(null, "<?>");
		query.put("C", "a");

		assertEquals("http://test.com/somepath?C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath", query));
		assertEquals("http://test.com/somepath?C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?", query));
		assertEquals("http://test.com/somepath?C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath/", query));

		assertEquals("http://test.com/somepath?X=1&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1", query));
		assertEquals("http://test.com/somepath?X=1&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&", query));
		assertEquals("http://test.com/somepath?X=1&Y=2&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2", query));
		assertEquals("http://test.com/somepath?X=1&Y=2&C=a", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2&", query));
	}

	@Test
	public void composeUrlTestWithQueryInvalid() {

		Map<String, String> query = new HashMap<>();
		query.put("A", "");
		query.put(null, "<?>");

		assertEquals("http://test.com/somepath", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath", query));
		assertEquals("http://test.com/somepath", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?", query));
		assertEquals("http://test.com/somepath", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath/", query));

		assertEquals("http://test.com/somepath?X=1", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1", query));
		assertEquals("http://test.com/somepath?X=1", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&", query));
		assertEquals("http://test.com/somepath?X=1&Y=2", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2", query));
		assertEquals("http://test.com/somepath?X=1&Y=2", com.zandero.utils.UrlUtils.composeUrl("http", "test.com", 80, "/somepath?X=1&Y=2&", query));
	}

	@Test
	public void testResolveDomainName() {

		assertEquals("test.something.com", com.zandero.utils.UrlUtils.resolveDomain("http://test.something.com/some/server.html"));
		assertEquals("test.something.com", com.zandero.utils.UrlUtils.resolveDomain("test.something.com/hello"));
		assertEquals("test.something.com", com.zandero.utils.UrlUtils.resolveDomain("test.something.com"));

		assertEquals("127.0.0.1", com.zandero.utils.UrlUtils.resolveDomain("smtp://127.0.0.1/hello"));
		assertEquals("127.0.0.1", com.zandero.utils.UrlUtils.resolveDomain("ftp://127.0.0.1/hello"));
		assertEquals("127.0.0.1", com.zandero.utils.UrlUtils.resolveDomain("https://127.0.0.1/hello"));
		assertEquals("127.0.0.1", com.zandero.utils.UrlUtils.resolveDomain("http://127.0.0.1/hello"));
		assertEquals("127.0.0.1", com.zandero.utils.UrlUtils.resolveDomain("127.0.0.1/hello"));
		assertEquals("127.0.0.1", com.zandero.utils.UrlUtils.resolveDomain("127.0.0.1"));

		assertEquals(null, com.zandero.utils.UrlUtils.resolveDomain(null));
		assertEquals(null, com.zandero.utils.UrlUtils.resolveDomain(""));
		assertEquals(null, com.zandero.utils.UrlUtils.resolveDomain(" "));
	}

	@Test
	public void urlEncodeTest() {

		assertEquals("http://cdn.jsdelivr.net/webjars/org.webjars/browser-sync/2.7.5/node_modules/istanbul/node_modules/fileset/tests/fixtures/an%20(odd)%20filename.js",
			com.zandero.utils.UrlUtils.urlEncode("http://cdn.jsdelivr.net/webjars/org.webjars/browser-sync/2.7.5/node_modules/istanbul/node_modules/fileset/tests/fixtures/an (odd) filename.js"));

		assertEquals("http://cdn.jsdelivr.net/some?pace=in%20the%20sun",
			com.zandero.utils.UrlUtils.urlEncode("http://cdn.jsdelivr.net/some?pace=in the sun"));

		assertEquals("http://cdn.jsdelivr.net/some?pace=in%20the%20sun&is='high'",
			com.zandero.utils.UrlUtils.urlEncode("http://cdn.jsdelivr.net/some?pace=in the sun&is='high'"));
	}

	@Test
	public void resolveUrlParts() {

		assertEquals("http", com.zandero.utils.UrlUtils.resolveScheme("http://test.com:80"));
		assertEquals("test.com", com.zandero.utils.UrlUtils.resolveDomain("http://test.com:80"));
		assertEquals(80, com.zandero.utils.UrlUtils.resolvePort("http://test.com:80").intValue());

		assertEquals("https", com.zandero.utils.UrlUtils.resolveScheme("https://www.zandero.co"));
		assertEquals("www.zandero.co", com.zandero.utils.UrlUtils.resolveDomain("https://www.zandero.co"));
		assertEquals(80, com.zandero.utils.UrlUtils.resolvePort("https://www.zandero.co").intValue());
	}

	@Test
	public void encodeQueryTest() {

		assertEquals("some+search+with+spaces", com.zandero.utils.UrlUtils.encodeQuery("some search with spaces"));
		assertEquals("http%3A%2F%2Ftest.com%3A80", com.zandero.utils.UrlUtils.encodeQuery("http://test.com:80"));
	}

	@Test
	public void parseQueryTest() {

		Map<String, String> map = com.zandero.utils.UrlUtils.getQuery("http://some.com/somewhere");
		assertTrue(map.isEmpty());

		map = com.zandero.utils.UrlUtils.getQuery("in=the");
		assertEquals(1, map.size());
		assertEquals("the", map.get("in"));

		map = com.zandero.utils.UrlUtils.getQuery("http://some.com/somewhere?in=the");
		assertEquals(1, map.size());
		assertEquals("the", map.get("in"));

		map = com.zandero.utils.UrlUtils.getQuery("http://some.com/somewhere?in=the&middle=of&ocean");
		assertEquals(3, map.size());
		assertEquals("the", map.get("in"));
		assertEquals("of", map.get("middle"));
		assertTrue(map.containsKey("ocean"));
	}

	@Test
	public void getBaseUrlTest() {

		assertEquals("http://some.com", com.zandero.utils.UrlUtils.getBaseUrl("http://some.com/somewhere"));
		assertEquals("http://some.com", com.zandero.utils.UrlUtils.getBaseUrl("http://some.com/"));
		assertEquals("http://build.zandero.co:8083", UrlUtils.getBaseUrl("http://build.zandero.co:8083/rest/api/2/user?username=xrado"));
	}
}