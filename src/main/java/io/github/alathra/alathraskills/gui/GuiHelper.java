package io.github.alathra.alathraskills.gui;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class GuiHelper {

    public enum GuiType {
        MAIN,
        SKILL
    }

    public static Gui buildGui(GuiType type) {
        Gui gui;
        switch (type) {
            case MAIN -> {
                gui = Gui.gui()
                    .rows(5)
                    .title(ColorParser.of("<color:#00B300>AlathraSkills").build()) // TODO: config
                    .disableItemDrop()
                    .disableItemPlace()
                    .disableItemSwap()
                    .disableItemTake()
                    .create();
            }
            case SKILL -> {
                gui = Gui.gui()
                    .rows(4)
                    .title(ColorParser.of("<white>Choose skill tree").build()) // TODO: config
                    .disableItemDrop()
                    .disableItemPlace()
                    .disableItemSwap()
                    .disableItemTake()
                    .create();
            }
            default -> gui = null;
        }
        return gui;
    }

    public static Gui buildSkillGui(int skillCategoryId) {
        String skillCategoryString;

        switch (skillCategoryId) {
            case 1 -> skillCategoryString = "Farming";
            case 2 -> skillCategoryString = "Mining";
            case 3 -> skillCategoryString = "Woodcutting";
            default -> skillCategoryString = null;
        }

        Gui gui;
        gui = Gui.gui()
            .rows(5)
            .title(ColorParser.of("<white>" + skillCategoryString).build())
            .disableItemDrop()
            .disableItemPlace()
            .disableItemSwap()
            .disableItemTake()
            .create();
        return gui;
    }

    public static Gui buildConfirmGui() {
        Gui gui;
        gui = Gui.gui()
            .rows(1)
            .title(ColorParser.of("<white>Are you sure?").build())
            .disableItemDrop()
            .disableItemPlace()
            .disableItemSwap()
            .disableItemTake()
            .create();
        return gui;
    }

    public static Gui buildResetGui() {
        Gui gui;
        gui = Gui.gui()
            .rows(3)
            .title(ColorParser.of("<white>Reset options").build())
            .disableItemDrop()
            .disableItemPlace()
            .disableItemSwap()
            .disableItemTake()
            .create();
        return gui;
    }

    public static void openSkillCategoryGui(Player player) {
        Gui gui = buildGui(GuiHelper.GuiType.SKILL);
        populateSkillCategoryGui(gui, player);
        gui.open(player);
    }

    public static void openSkillGui(Player player, int skillCategoryId, int page) {
        Gui gui = buildSkillGui(skillCategoryId);
        populateSkillGui(gui, player, skillCategoryId, page);
        gui.open(player);
    }

    public static void openConfirmGui(Player player, int skill, int skillCategoryId, int page) {
        if (SkillsPlayerManager.canSkillBeUnlocked(player, skillCategoryId, skill)) {
            Gui gui = buildConfirmGui();
            populateConfirmGui(gui, player, skill, skillCategoryId, page);
            gui.open(player);
            return;
        }
        player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1, 1);
    }

    public static void openResetGui(Player player) {
        Gui gui = buildResetGui();
        populateResetGui(gui, player);
        gui.open(player);
    }

    public static void populateMainGui(Gui gui, Player player) {
        io.github.alathra.alathraskills.gui.main.PopulateContent.populateContent(gui, player);
    }

    public static void populateSkillCategoryGui(Gui gui, Player player) {
        io.github.alathra.alathraskills.gui.skillcategory.PopulateButtons.populateButtons(gui, player);
        io.github.alathra.alathraskills.gui.skillcategory.PopulateContent.populateContent(gui, player);
    }

    public static void populateSkillGui(Gui gui, Player player, int skillCategoryId, int page) {
        io.github.alathra.alathraskills.gui.skill.PopulateButtons.populateButtons(gui, player, skillCategoryId, page);
        io.github.alathra.alathraskills.gui.skill.PopulateContent.populateContent(gui, player, skillCategoryId, page);
    }

    public static void populateConfirmGui(Gui gui, Player player, int skill, int skillCategoryId, int page) {
        io.github.alathra.alathraskills.gui.skill.confirm.PopulateContent.populateContent(gui, player, skill, skillCategoryId, page);
    }

    public static void populateResetGui(Gui gui, Player player) {
        io.github.alathra.alathraskills.gui.reset.PopulateButtons.populateButtons(gui, player);
        io.github.alathra.alathraskills.gui.reset.PopulateContent.populateContent(gui, player);
    }

}
