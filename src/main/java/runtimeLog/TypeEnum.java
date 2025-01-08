package runtimeLog;

public enum TypeEnum {
    PARAMETER_ERROR(1, "参数错误"),
    INPUT_ERROR(2, "输入错误"),
    MODEL_ERROR(3, "模型运行错误"),
    RESULT_ERROR(4, "结果错误"),

    OUTPUT_ERROR(5,"输出错误")
    ;
    int index;
    String description;
    TypeEnum(int index, String description){
        this.index = index;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }
    public String getDescription() {
        return description;
    }
}
