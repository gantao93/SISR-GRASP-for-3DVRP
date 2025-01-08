package runtimeLog;

import java.lang.reflect.Type;

public enum ErrorEnum {
    NO_SUPPORTD_SCENARIOS(TypeEnum.INPUT_ERROR, "001", "仅支持装箱、装托和装车场景，不支持其他"),
    READ_TABLE_PATH_ERROR(TypeEnum.PARAMETER_ERROR,"007","无法读取数据表，检查数据表路径正确性"),
    OUTPUT_HEADER_ERROR(TypeEnum.OUTPUT_ERROR, "001", "输出文件输出错误"),

    OUTPUT_DATA_ERROR(TypeEnum.INPUT_ERROR, "002", "输出数据行出错")
    ;

    String errNo;
    String description;

    ErrorEnum(TypeEnum type, String errNo, String description) {
        this.errNo = type.index + errNo;
        this.description = type.description + ":" + description;
    }

    public String getErrNo() {
        return errNo;
    }

    public String getDescription() {
        return description;
    }
}
