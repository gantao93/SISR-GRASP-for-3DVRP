package core;

import core.base.item.ItemType;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private static volatile Config instance;
    private List<ItemType> compatibleItems;

    private Config() {
        compatibleItems = new ArrayList<>();
    }

    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    public List<ItemType> getCompatibleItems() {
        return compatibleItems;
    }

    public void setCompatibleItems(List<ItemType> compatibleItems) {
        this.compatibleItems = compatibleItems;
    }
}

