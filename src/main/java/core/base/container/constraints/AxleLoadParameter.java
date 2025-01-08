package core.base.container.constraints;


public record AxleLoadParameter(
        float firstPermissibleAxleLoad,
        float secondPermissibleAxleLoad,
        float axleDistance) {
}