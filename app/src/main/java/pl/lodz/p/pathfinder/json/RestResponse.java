package pl.lodz.p.pathfinder.json;

/**
 * Created by QDL on 2017-01-14.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestResponse {


    @SerializedName("args")
    @Expose
    private Args args;
    @SerializedName("headers")
    @Expose
    private Headers headers;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("url")
    @Expose
    private String url;

    public Args getArgs() {
        return args;
    }

    public void setArgs(Args args) {
        this.args = args;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}