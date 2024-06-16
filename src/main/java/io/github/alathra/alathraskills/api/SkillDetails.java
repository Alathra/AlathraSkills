package io.github.alathra.alathraskills.api;

public class SkillDetails {

    private boolean existingSkill;
    private boolean selected;

    public SkillDetails(boolean existingSkill, boolean selected) {
        this.existingSkill = existingSkill;
        this.selected = selected;
    }

    public boolean isExistingSkill() {
        return existingSkill;
    }

    public void setExistingSkill(boolean existingSkill) {
        this.existingSkill = existingSkill;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
