package org.onap.usecaseui.server.util;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

public class MyHttpDelete extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public String getMethod() {
        return METHOD_NAME;
    }

    public MyHttpDelete(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public MyHttpDelete(final URI uri) {
        super();
        setURI(uri);
    }

    public MyHttpDelete() {
        super();
    }
}
