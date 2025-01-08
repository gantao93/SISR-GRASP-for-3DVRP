package core.base.container;

import core.base.container.constraints.AxleLoadParameter;


public class DirectContainerParameter implements ContainerParameter {

    private float lifoImportance;
    private GroundContactRule groundContactRule;
    private AxleLoadParameter axleLoad;

    @Override
    public void add(ParameterType type, Object value) {
        switch (type) {
            case LIFO_IMPORTANCE -> lifoImportance = (Float) value;
            case GROUND_CONTACT_RULE -> groundContactRule = (GroundContactRule) value;
            case AXLE_LOAD -> axleLoad = (AxleLoadParameter) value;
        }
    }

    @Override
    public Object get(ParameterType type) {
        return switch (type) {
            case LIFO_IMPORTANCE -> lifoImportance;
            case GROUND_CONTACT_RULE -> groundContactRule;
            case AXLE_LOAD -> axleLoad;
        };
    }
}
