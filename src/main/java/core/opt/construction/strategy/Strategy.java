package core.opt.construction.strategy;

public enum Strategy {

    TOUCHING_PERIMETER(new TouchingPerimeter()),
    HIGH_LOW_LEFT(new HighestLowerLeft()),
    SAME_BASE(new SameBaseStrategy()),
    WIDTH_PROPORTION(new WidthProportionFactor()),
    DSC_SPECIAL_STACKED_RULE(new DscSpecialStackedStrategy()),

    BOTTOM_WIDTH_PROPORTION(new BottomAreaFirstWidthProportionFactor()),

    LAYER_BUILDING(new LayerBuilding());


    private final BaseStrategy strategy;

    Strategy(BaseStrategy strategy) {
        this.strategy = strategy;
    }

    public BaseStrategy getStrategy() {
        return strategy;
    }
}
