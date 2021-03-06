package io.doorbell.android;

import io.doorbell.android.manavo.rest.RestApi;
import io.doorbell.android.manavo.rest.RestCache;

import org.json.JSONObject;

import android.app.Activity;


public class DoorbellApi extends RestApi {

    private static final String DOORBELL_IO_URL = "https://doorbell.io/api/";
    private static final String DOORBELL_IO_HOST = "doorbell.io";
    private static final String DOORBELL_USER_AGENT = "Doorbell Android SDK";

    private String mApiKey;
    private long mAppId;

    public DoorbellApi(Activity activity) {
        super(activity);

        this.BASE_URL = DOORBELL_IO_URL;
        this.rest.setHost(DOORBELL_IO_HOST);
        this.setUserAgent(DOORBELL_USER_AGENT);

        this.acceptAllSslCertificates();

        this.reset();
    }

    public void setAppId(long id) {
        this.mAppId = id;
    }

    public void setApiKey(String key) {
        this.mApiKey = key;
    }

    public void reset() {
        super.reset();

        this.addParameter("sdk", "android");
        this.addParameter("version", this.activity.getString(R.string.doorbell_version));

        this.cachePolicy = RestCache.CachePolicy.NETWORK_ONLY;
    }

    public void impression() {
        this.setLoadingMessage(null);
        this.post("applications/" + this.mAppId + "/impression?key=" + this.mApiKey);
    }

    public void open() {
        this.setLoadingMessage(null);
        this.post("applications/" + this.mAppId + "/open?key=" + this.mApiKey);
    }

    public void sendFeedback(String message, String email, JSONObject properties, String name) {
        this.addParameter("message", message);
        this.addParameter("email", email);

        this.addParameter("properties", properties.toString());

        this.addParameter("name", name);

        this.post("applications/" + this.mAppId + "/submit?key=" + this.mApiKey);
    }

}