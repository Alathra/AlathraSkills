package io.github.alathra.alathraskills.gui.skill.confirm;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.gui.GuiItemHelper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PopulateContent {

    static final SkillsManager skillsManager = AlathraSkills.getSkillsManager();

    public static void populateContent(Gui gui, Player player, int skill, int skillCategoryId, int page) {
        gui.setItem(6, ItemBuilder.from(GuiItemHelper.confirmItem).asGuiItem(event -> {
            boolean buySkill = AlathraSkills.getSkillsPlayerManager().buySkill(player, skill);

            if (buySkill) {
                GuiHelper.openSkillGui(player, skillCategoryId, page);
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.AMBIENT, 1, 1);
            }
        }));
        gui.setItem(2, ItemBuilder.from(GuiItemHelper.denyItem).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page)));

        gui.setItem(4, ItemBuilder.from(skillsManager.skills.get(skill).getIcon()).asGuiItem());
    }

}
