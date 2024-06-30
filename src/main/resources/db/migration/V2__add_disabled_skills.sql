CREATE TABLE IF NOT EXISTS ${tablePrefix}player_disabled_skills (
    uuid ${uuidType} NOT NULL,
    skillid INT NOT NULL,
    PRIMARY KEY (uuid, skillid)
)${tableDefaults};
