package io.github.alathra.alathraskills.utility;

import com.github.milkdrinkers.customblockdata.CustomBlockData;
import io.github.alathra.alathraskills.AlathraSkills;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PDCUtil {

    /**
     * Utility class that implements CustomBlockData API. This class is used to
     * handle Persistent Data Containers (PDCs). PDC is used to store data in blocks.
     * This is currently used for two things; 1. Determining if the block was placed by a player
     * and 2. Determining if the block was placed by the plugin.
     */

    private static NamespacedKey unnaturalKey;
    private static NamespacedKey pluginPlacedKey;

    public static void init() {
        unnaturalKey = new NamespacedKey(AlathraSkills.getInstance(), "alathraskills_unnatural");
        pluginPlacedKey = new NamespacedKey(AlathraSkills.getInstance(), "alathraskills_pluginplaced");
    }

    public static void setUnnatural(Block block, boolean value) {
        PersistentDataContainer pdc = getPDC(block);
        pdc.set(unnaturalKey, PersistentDataType.BOOLEAN, value);
    }

    public static void clearUnnatural(Block block) {
        PersistentDataContainer pdc = getPDC(block);
        if (pdc.getKeys().contains(unnaturalKey)) {
            pdc.remove(unnaturalKey);
        }
    }

    public static boolean isUnnatural(Block block) {
        if (getPDC(block).getKeys().contains(unnaturalKey)) {
            return getPDC(block).get(unnaturalKey, PersistentDataType.BOOLEAN);
        } else {
            return false;
        }
    }

    public static void setPluginPlaced(Block block, boolean value) {
        PersistentDataContainer pdc = getPDC(block);
        pdc.set(pluginPlacedKey, PersistentDataType.BOOLEAN, value);
    }

    public static void clearPluginPlaced(Block block) {
        PersistentDataContainer pdc = getPDC(block);
        if (pdc.getKeys().contains(pluginPlacedKey))
            pdc.remove(pluginPlacedKey);
    }

    public static boolean isPluginPlaced(Block block) {
        if (getPDC(block).getKeys().contains(pluginPlacedKey)) {
            return getPDC(block).get(pluginPlacedKey, PersistentDataType.BOOLEAN);
        } else {
            return false;
        }
    }

    public static PersistentDataContainer getPDC(Block block) {
        return new CustomBlockData(block, AlathraSkills.getInstance());
    }


}
