// IBusProcess.aidl
package com.cody.component.bus.process;

// Declare any non-default types here with import statements
import com.cody.component.bus.process.IBusListener;

interface IBusProcess {
    /*void post(int value);
    void post(long value);
    void post(boolean value);
    void post(float value);
    void post(double value);*/
    void post(String process, String scope, String event, String type, String value);
    void register(IBusListener listener);
    void unregister(IBusListener listener);
}
