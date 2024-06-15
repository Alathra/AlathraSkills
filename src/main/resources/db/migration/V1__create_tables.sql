CREATE TABLE IF NOT EXISTS ${tablePrefix}player_skillcategoryinfo (
    uuid ${uuidType} NOT NULL,
    skillcategoryid INT NOT NULL,
    experience FLOAT NOT NULL,
    PRIMARY KEY (uuid, skillcategoryid)
)${tableDefaults};


CREATE TABLE IF NOT EXISTS ${tablePrefix}player_skillinfo (
    uuid ${uuidType} NOT NULL,
    skillid INT NOT NULL,
    PRIMARY KEY (uuid, skillid)
)${tableDefaults};

CREATE TABLE IF NOT EXISTS ${tablePrefix}player_playerdata (
    uuid ${uuidType} NOT NULL,
    used_skillpoints INT NOT NULL,
    latest_unlocked_skill INT NOT NULL,
    cooldown TIMESTAMP,
    PRIMARY KEY (uuid)
)${tableDefaults};
