package com.neueda.interview.urlshortener.common;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UrlUtilTest {

    @Test
    public void shouldThrowExceptionWhenMalformedUrlSuppliedWithoutProtocol() throws MalformedURLException {
        assertThrows(MalformedURLException.class, () -> UrlUtil.getBaseUrl("malformed url dummy text"));
    }

    @Test
    public void shouldThrowExceptionWhenMalformedUrlSuppliedWithIllegalChars() throws MalformedURLException {
        assertThrows(MalformedURLException.class, () -> UrlUtil.getBaseUrl("malformed://example.com/foo"));
    }

    @Test
    public void shouldReturnBaseUrlWhenValidUrlSuppliedWithoutPort() throws MalformedURLException {
        assertEquals(UrlUtil.getBaseUrl("http://example.com/foo"), "http://example.com/");
    }

    @Test
    public void shouldReturnBaseUrlWhenValidUrlSuppliedWithPort() throws MalformedURLException {
        assertEquals(UrlUtil.getBaseUrl("http://example.com:8080/foo"), "http://example.com:8080/");
    }
}
