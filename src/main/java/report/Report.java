package report;

import core.base.container.Container;
import core.base.item.Item;
import core.base.item.Position;
import core.report.LPPackageEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private Container container;
    private List<LPPackageEvent> packedItems;

    private List<LPPackageEvent> unpackedItems = new ArrayList<>();

    private List<Position> unoccupiedPosition;

    private BigDecimal loadingRate;

    public Container getContainer() {
        return container;
    }
    public void setContainer(Container container){
        this.container = container;
    }
    public List<LPPackageEvent> getPackedItems() {
        return packedItems;
    }

    public void setPackedItems(List<LPPackageEvent> packedItems) {
        this.packedItems = packedItems;
    }

    public List<Position> getUnoccupiedPosition() {
        return unoccupiedPosition;
    }

    public void setUnpackedItems(List<LPPackageEvent> unpackedItems){
        this.unpackedItems = unpackedItems;
    }

    public List<LPPackageEvent> getUnpackedItems() {
        return unpackedItems;
    }

    public void setUnoccupiedPosition(List<Position> unoccupiedPosition){
        this.unoccupiedPosition = unoccupiedPosition;
    }

    public void setLoadingRate(BigDecimal loadingRate) {
        this.loadingRate = loadingRate;
    }

    public BigDecimal getLoadingRate() {
        return loadingRate;
    }

}
