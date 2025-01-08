package routesFinding.dataImporter.processing;

import core.base.item.ItemType;
import routesFinding.dataImporter.LoadingRuleData;

import java.util.ArrayList;
import java.util.List;

/*
结果用list保存，每两可堆叠的物品类型用ItemType结构保存
 */
public class StackingRule {
    private List<LoadingRuleData> loadingRuleDatasList;

    public StackingRule(List<LoadingRuleData> loadingRuleDatas){
        this.loadingRuleDatasList = loadingRuleDatas;
    }

    public List<ItemType> getCompatibleItemsList() {
        List<ItemType> compatibleItems = new ArrayList<>();
        for(LoadingRuleData loadingRule: loadingRuleDatasList) {
            if(loadingRule.getCompatible() == 1) {
                compatibleItems.add(ItemType.of(loadingRule.getShipUnitSpec1(), loadingRule.getShipUnitSpec2()));
            }
        }
        return compatibleItems;
    }

}
