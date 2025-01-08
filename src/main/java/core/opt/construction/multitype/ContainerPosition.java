package core.opt.construction.multitype;

import core.base.container.Container;
import core.base.position.PositionCandidate;


public class ContainerPosition {

    private final Container container;
    private final PositionCandidate position;

    public ContainerPosition(Container container, PositionCandidate position) {
        this.container = container;
        this.position = position;
    }

    public Container getContainer() {
        return container;
    }

    public PositionCandidate getPosition() {
        return position;
    }
}
