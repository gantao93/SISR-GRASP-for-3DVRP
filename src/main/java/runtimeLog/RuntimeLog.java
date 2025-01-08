package runtimeLog;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuntimeLog {
    private String errNo;
    private String errMsg;
    private String result;

    public RuntimeLog withErrNo(String errNo) {
        this.errNo = errNo;
        return this;
    }

    public RuntimeLog withErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    public RuntimeLog withResult(String result) {
        this.result = result;
        return this;
    }

    public static void errExitSystem(ErrorEnum error, Class<?> T, String msg) {
        exit(error, T, msg);
    }

    public static void errExitSystem(ErrorEnum error, Class<?> T) {
        exit(error, T, error.getDescription());
    }

    public static void exit(ErrorEnum errorEnum, Class<?> T, String errMsg) {
        Logger logger = LoggerFactory.getLogger(T);
        RuntimeLog runtimeLog = new RuntimeLog().withErrNo(errorEnum.getErrNo()).withErrMsg(errMsg).withResult(ResultEnum.FAIL.value);
        String msg = runtimeLog.getJson();
        logger.error(msg);
        System.exit(1);
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
