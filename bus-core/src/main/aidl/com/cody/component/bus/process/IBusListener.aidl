// IBusListener.aidl
package com.cody.component.bus.process;

// Declare any non-default types here with import statements

interface IBusListener {
    String process();
    void onPost(String scope, String event, String type, String value);
    void onPostInit(String scope, String event, String type, String value);
}
