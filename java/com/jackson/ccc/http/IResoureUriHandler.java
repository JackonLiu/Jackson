package com.jackson.ccc.http;

import java.io.IOException;

/**
 * Created by LXP on 17-6-5.
 */

public interface IResoureUriHandler {
    boolean accept(String url);
    void handle(String url,HttpContext context) throws IOException;
}
