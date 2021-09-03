package com.nyw.pets.myinterface;

import wendu.dsbridge.CompletionHandler;

public interface JsCallback {
    void testSyn(Object params);
    void testAsyn(Object msg, CompletionHandler<String> handler);
    void jsToNative(Object msg, CompletionHandler<String> handler);
    void getWenxin(Object msg, CompletionHandler<String> handler);
}
