CREATE TABLE IF NOT EXISTS ${tablePrefix}player_used_skill_points (
    uuid ${uuidType} NOT NULL,
    skills_used INT NOT NULL,
    PRIMARY KEY (uuid)
)${tableDefaults};
