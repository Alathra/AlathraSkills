package io.github.alathra.alathraskills.gui;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.utility.Cfg;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GuiItemHelper {

    static {
        loadBorderItem();
        loadBackItem();
        loadExitItem();
        loadConfirmItem();
        loadDenyItem();
    }

    public static ItemStack borderItem;
    public static ItemStack backItem;
    public static ItemStack exitItem;
    public static ItemStack confirmItem;
    public static ItemStack denyItem;

    public static ItemStack skillpointsItem(Player player) {
        SkillsPlayer skillsPlayer = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player);
        if (skillsPlayer == null)
            return null;

        ItemStack skillpointsItem = new ItemStack(Material.END_CRYSTAL);
        ItemMeta skillpointsMeta = skillpointsItem.getItemMeta();
        skillpointsMeta.displayName(ColorParser.of(GuiHelper.EXPERIENCE_GRADIENT + "Skill points").build());
        List<Component> loreList = List.of(
            ColorParser.of(GuiHelper.LORETEXT + "Points: "+ skillsPlayer.getAvailableSkillpoints()).build(),
            ColorParser.of(GuiHelper.LORETEXT + "Next Point: %s/%s".formatted(Math.round(skillsPlayer.getNextSkillpointProgress()), Cfg.get().getInt("experience.perLevel"))).build()
        );
        skillpointsMeta.lore(loreList);
        skillpointsItem.setItemMeta(skillpointsMeta);
        return skillpointsItem;
    }

    private static void loadBorderItem() {
        borderItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = borderItem.getItemMeta();
        borderMeta.displayName(Component.text(""));
        borderItem.setItemMeta(borderMeta);
    }

    private static void loadBackItem() {
        backItem = new ItemStack(Material.PAPER);
        ItemMeta backMeta = backItem.getItemMeta();
        backMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Back").build().decoration(TextDecoration.ITALIC, false));
        backItem.setItemMeta(backMeta);
    }

    private static void loadExitItem() {
        exitItem = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exitItem.getItemMeta();
        exitMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Close").build().decoration(TextDecoration.ITALIC, false));
        exitItem.setItemMeta(exitMeta);
    }

    private static void loadConfirmItem() {
        confirmItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta confirmMeta = confirmItem.getItemMeta();
        confirmMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "<bold>Confirm").build());
        confirmItem.setItemMeta(confirmMeta);
    }

    private static void loadDenyItem() {
        denyItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta denyMeta = denyItem.getItemMeta();
        denyMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "<bold>Cancel").build());
        denyItem.setItemMeta(denyMeta);
    }



}
