package rss.feed.reader.api.retrofit;

/**
 * Created by Andriy Ksenych on 01.11.2016.
 */

public class Error extends Exception {

    public static final int ERROR_NO_INTERNET_CONNECTION = 1;

    private int errorCode;
    private String errorMsg;

    public Error(int errorCode, String errorMsg) {
        this(errorCode, errorMsg, null);
    }

    public Error(int errorCode, String errorMsg, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
