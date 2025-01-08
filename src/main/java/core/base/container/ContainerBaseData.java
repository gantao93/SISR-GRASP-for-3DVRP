package core.base.container;

import util.collection.LPListMap;

import java.util.Map;


public interface ContainerBaseData {

    LPListMap<Integer, Integer> getXMap();
    LPListMap<Integer, Integer> getYMap();
    LPListMap<Integer, Integer> getZMap();

    ZItemGraph getZGraph();

    Map<Integer, Float> getBearingCapacities();

    float getCenterOfGravityForY();
}
