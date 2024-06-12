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

CREATE TABLE IF NOT EXISTS ${tablePrefix}player_used_skill_points (
    uuid ${uuidType} NOT NULL,
    skills_used INT NOT NULL,
    PRIMARY KEY (uuid)
)${tableDefaults};

