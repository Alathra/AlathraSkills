package io.github.alathra.alathraskills.gui.confirm;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.gui.GuiHelper;

public class PopulateContent {

    static SkillsManager skillsManager = AlathraSkills.getSkillsManager();

    public static void populateContent(Gui gui, Player player, int skill, int skillCategoryId, int page) {
        ItemStack confirm = new ItemStack(Material.DARK_GREEN_WOOL);
        ItemMeta comfirmMeta = confirm.getItemMeta();
        confirmMeta.displayName(ColorParser.of("<dark_green><bold>Confirm"));
        confirm.setItemMeta(confirmMeta);

        ItemStack deny = new ItemStack(Material.RED_WOOL);
        ItemMeta denyMeta = confirm.getItemMeta();
        denyMeta.displayName(ColorParser.of("<dark_red><bold>Go back"));
        deny.setItemMeta(confirmMeta);

        gui.setItem(2, ItemBuilder.from(confirm).asGuiItem(event -> {
            SkillsPlayerManager.addPlayerSkill(player, skill);
            GuiHelper.openSkillGui(player, skillCategoryId, page);
        }));
        gui.setItem(6, ItemBuilder.from(deny).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page)));

        switch (skillCategoryId) {
            case 1 -> {
                gui.setItem(4, ItemBuider.from(skillsManager.miningSkills.get(skill).getIcon()).asGuiItem());
            }
            case 2 -> {
                gui.setItem(4, ItemBuilder.from(skillsManager.farmingSkills.get(skill).getIcon()).asGuiItem());
            }
            case 3 -> {
                gui.setItem(4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(skill).getIcon()).asGuiItem());
            }
        }
    }

}
