package io.github.alathra.alathraskills.gui.confirm;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PopulateContent {

    static SkillsManager skillsManager = AlathraSkills.getSkillsManager();

    public static void populateContent(Gui gui, Player player, int skill, int skillCategoryId, int page) {
        ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.displayName(ColorParser.of("<dark_green><bold>Confirm").build());
        confirmMeta.lore(List.of(ColorParser.of("<yellow>Cost: " + skillsManager.getSkill(skill).getCost()).build()));
        confirm.setItemMeta(confirmMeta);

        ItemStack deny = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta denyMeta = confirm.getItemMeta();
        denyMeta.displayName(ColorParser.of("<red><bold>Cancel").build());
        deny.setItemMeta(denyMeta);

        gui.setItem(6, ItemBuilder.from(confirm).asGuiItem(event -> {
            boolean buySkill = SkillsPlayerManager.buySkill(player, skill);

            if (buySkill) {
                GuiHelper.openSkillGui(player, skillCategoryId, page);
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.AMBIENT, 1, 1);
            }
        }));
        gui.setItem(2, ItemBuilder.from(deny).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page)));

        switch (skillCategoryId) {
            case 1 -> {
                gui.setItem(4, ItemBuilder.from(skillsManager.farmingSkills.get(skill).getIcon()).asGuiItem());
            }
            case 2 -> {
                gui.setItem(4, ItemBuilder.from(skillsManager.miningSkills.get(skill).getIcon()).asGuiItem());
            }
            case 3 -> {
                gui.setItem(4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(skill).getIcon()).asGuiItem());
            }
        }
    }

}
