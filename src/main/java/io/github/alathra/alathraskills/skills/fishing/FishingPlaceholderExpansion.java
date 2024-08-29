package io.github.alathra.alathraskills.skills.fishing;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.skills.farming.util.FastHarvest;
import io.github.alathra.alathraskills.skills.farming.util.ReadyToEat;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FishingPlaceholderExpansion extends PlaceholderExpansion {

    @Override
    public @Nullable String getRequiredPlugin() {
        return "AlathraSkills";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "alathraskills_fishing";
    }

    @Override
    public @NotNull String getAuthor() {
        return "rooooose-b, ShermansWorld, ChrisViera";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        SkillsPlayer skillsPlayer = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player);
        switch (params) {
            case "a_long_fight" -> {
                boolean[] aLongFight = new boolean[SkillsManager.A_LONG_FIGHT_IDS.length];

                int i = 0;
                for (int id : SkillsManager.A_LONG_FIGHT_IDS) {
                    aLongFight[i] = skillsPlayer.playerHasSkill(id);
                    i++;
                }

                i = 0;
                for (boolean hasSkill : aLongFight) {
                    if (hasSkill) {
                        return String.valueOf(FishingUtils.A_LONG_FIGHT_MAX_LEVEL - i);
                    }
                    i++;
                }
                return "0";
            }
            case "big_catch" -> {
                boolean[] bigCatch = new boolean[SkillsManager.BIG_CATCH_IDS.length];

                int i = 0;
                for (int id : SkillsManager.BIG_CATCH_IDS) {
                    bigCatch[i] = skillsPlayer.playerHasSkill(id);
                    i++;
                }

                i = 0;
                for (boolean hasSkill : bigCatch) {
                    if (hasSkill) {
                        return String.valueOf(FishingUtils.BIG_CATCH_MAX_LEVEL - i);
                    }
                    i++;
                }
                return "0";
            }
            case "high_quality_catch" -> {
                boolean[] highQualityCatch = new boolean[SkillsManager.HIGH_QUALITY_CATCH_IDS.length];

                int i = 0;
                for (int id : SkillsManager.HIGH_QUALITY_CATCH_IDS) {
                    highQualityCatch[i] = skillsPlayer.playerHasSkill(id);
                    i++;
                }

                i = 0;
                for (boolean hasSkill : highQualityCatch) {
                    if (hasSkill) {
                        return String.valueOf(FishingUtils.HIGH_QUALITY_CATCH_MAX_LEVEL - i);
                    }
                    i++;
                }
                return "0";
            }
            case "one_mans_trash" -> {
                boolean[] oneMansTrash = new boolean[SkillsManager.ONE_MANS_TRASH_IDS.length];

                int i = 0;
                for (int id : SkillsManager.ONE_MANS_TRASH_IDS) {
                    oneMansTrash[i] = skillsPlayer.playerHasSkill(id);
                    i++;
                }

                i = 0;
                int j = 0;
                for (boolean hasSkill : oneMansTrash) {
                    if (hasSkill) {
                        return String.valueOf(FishingUtils.ONE_MANS_TRASH_MAX_LEVEL - j);
                    }

                    // handles one man's trash being in both branches of the skill tree
                    if (i == 1 || i >= 3) j++;
                    i++;
                }
                return "0";
            }
            case "quick_to_bite" -> {
                boolean[] quickToBite = new boolean[SkillsManager.QUICK_TO_BITE_IDS.length];

                int i = 0;
                for (int id : SkillsManager.QUICK_TO_BITE_IDS) {
                    quickToBite[i] = skillsPlayer.playerHasSkill(id);
                    i++;
                }

                i = 0;
                for (boolean hasSkill : quickToBite) {
                    if (hasSkill) {
                        return String.valueOf(FishingUtils.QUICK_TO_BITE_MAX_LEVEL - i);
                    }
                    i++;
                }
                return "0";
            }
            default -> {
                return null;
            }
        }
    }
}
