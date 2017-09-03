
package com.examle.binaryblitz.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Message {

    @SerializedName("error")
    private String mError;
    @SerializedName("status")
    private String mStatus;

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        mError = error;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
