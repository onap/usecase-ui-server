package org.onap.usecaseui.server.util;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

public class MyHttpGet extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "GET";

    public String getMethod() {
        return METHOD_NAME;
    }

    public MyHttpGet(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public MyHttpGet(final URI uri) {
        super();
        setURI(uri);
    }

    public MyHttpGet() {
        super();
    }
}