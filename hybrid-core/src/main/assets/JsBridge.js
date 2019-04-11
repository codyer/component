/**
 * Created by Cody.yi on 19/4/12.
 *
 * native结果数据返回格式:
 * var resultJs = {
    code: '200',//200成功，400失败
    message: '请求超时',//失败时候的提示，成功可为空
    data: {}//数据,无数据可以为空
};
 协定协议:js_bridge://class:port/method?params;
 params是一串json字符串
 */
(function () {
    var doc = document;
    var win = window;
    var ua = win.navigator.userAgent;
    var JS_BRIDGE_PROTOCOL_SCHEMA = "js_bridge";
    var increase = 1;
    var JsBridge = win.JsBridge || (win.JsBridge = {});

    var ExposeMethod = {

        callNative: function (clazz, method, param, callback) {
            if(PrivateMethod.isApp()){
                var port = PrivateMethod.generatePort();
                if (typeof callback !== 'function') {
                    callback = null;
                }
                PrivateMethod.registerCallback(port, callback);
                PrivateMethod.callNativeMethod(clazz, port, method, param);
            }else{
                console.error("not in you app webView, method:" + method +" cannot execute.");
            }
        },

        onComplete: function (port, result) {
            PrivateMethod.onNativeComplete(port, result);
        }

    };

    var PrivateMethod = {
        callbacks: {},
        registerCallback: function (port, callback) {
            if (callback) {
                PrivateMethod.callbacks[port] = callback;
            }
        },
        getCallback: function (port) {
            var call = {};
            if (PrivateMethod.callbacks[port]) {
                call.callback = PrivateMethod.callbacks[port];
            } else {
                call.callback = null;
            }
            return call;
        },
        unRegisterCallback: function (port) {
            if (PrivateMethod.callbacks[port]) {
                delete PrivateMethod.callbacks[port];
            }
        },
        onNativeComplete: function (port, result) {
            var resultJson = PrivateMethod.str2Json(result);
            var callback = PrivateMethod.getCallback(port).callback;
            PrivateMethod.unRegisterCallback(port);
            if (callback) {
                //执行回调
                callback && callback(resultJson);
            }
        },
        generatePort: function () {
            return Math.floor(Math.random() * (1 << 50)) + '' + increase++;
        },
        str2Json: function (str) {
            if (str && typeof str === 'string') {
                try {
                    return JSON.parse(str);
                } catch (e) {
                    return {
                           code: '400',
                           message: 'params parse error!'
                    };
                }
            } else {
                return str || {};
            }
        },
        json2Str: function (param) {
            if (param && typeof param === 'object') {
                return JSON.stringify(param);
            } else {
                return param || '';
            }
        },
        callNativeMethod: function (clazz, port, method, param) {
            if (PrivateMethod.isAndroid()) {
                var jsonStr = PrivateMethod.json2Str(param);
                var uri = JS_BRIDGE_PROTOCOL_SCHEMA + "://" + clazz + ":" + port + "/" + method + "?" + jsonStr;
                win.prompt(uri, "");
            }else if(PrivateMethod.isIos()){
                var holder = {"clazz":clazz,"port":port,"method":method,"param":param};
                win.webkit.messageHandlers.native.postMessage(holder);
            }else{
                console.error("not native webView, method:" + method +" cannot execute.");
            }
        },
        isApp: function () {
            var tmp = ua.toLowerCase();
            var app = tmp.indexOf("hybrid-core") > -1;
            return !!app;
        },
        isAndroid: function () {
            var tmp = ua.toLowerCase();
            var android = tmp.indexOf("android") > -1;
            return !!android;
        },
        isIos: function () {
            var tmp = ua.toLowerCase();
            var ios = tmp.indexOf("iphone") > -1;
            return !!ios;
        }
    };
    for (var index in ExposeMethod) {
        if (ExposeMethod.hasOwnProperty(index)) {
            if (!Object.prototype.hasOwnProperty.call(JsBridge, index)) {
                JsBridge[index] = ExposeMethod[index];
            }
        }
    }
})();