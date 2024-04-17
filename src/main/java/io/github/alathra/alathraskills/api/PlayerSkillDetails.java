package io.github.alathra.alathraskills.api;

import java.util.Map.Entry;
import java.util.stream.Stream;

import org.bukkit.OfflinePlayer;

public class PlayerSkillDetails {
	
	private OfflinePlayer p;
	private Stream<Entry<Integer, SkillDetails>> skillInfo;
	
	public PlayerSkillDetails(OfflinePlayer p,
			Stream<Entry<Integer, SkillDetails>> skillInfo) {
		this.setP(p);
		this.setSkillInfo(skillInfo);
	}

	public Stream<Entry<Integer, SkillDetails>> getSkillInfo() {
		return skillInfo;
	}

	public void setSkillInfo(Stream<Entry<Integer, SkillDetails>> skillInfo) {
		this.skillInfo = skillInfo;
	}

	public OfflinePlayer getP() {
		return p;
	}

	public void setP(OfflinePlayer p) {
		this.p = p;
	}

}
