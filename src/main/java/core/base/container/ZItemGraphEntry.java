package core.base.container;

import core.base.item.Item;
import core.base.item.Tools;

import java.util.ArrayList;
import java.util.List;


public class ZItemGraphEntry {

    public final Item item;
    public final List<Item> lowerItemList;
    public final List<Float> cutRatioList;
    public final Object[] itemRatioArr;

    public ZItemGraphEntry(Item item, List<Item> lowerItemList) {
        this.item = item;
        this.cutRatioList = new ArrayList<>();
        this.lowerItemList = lowerItemList;
        this.itemRatioArr = new Object[]{this.lowerItemList, this.cutRatioList};

        update();
    }

    public void update() {
        float[] bCuts = new float[lowerItemList.size()];
        float sum = 0;
        for (int i = 0; i < lowerItemList.size(); i++) {
            bCuts[i] = Tools.getCutRatio(item, lowerItemList.get(i));
            sum += bCuts[i];
        }
        // Anpassen der Anteile auf 100%
        for (int i = 0; i < lowerItemList.size(); i++)
            cutRatioList.add(bCuts[i] * (1f / sum));
    }

}
