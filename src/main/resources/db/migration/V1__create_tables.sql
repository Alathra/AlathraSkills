CREATE TABLE IF NOT EXISTS ${tablePrefix}player_skillcategoryinfo (
    uuid ${uuidType} NOT NULL,
    skillcategoryid INT NOT NULL,
    exp FLOAT NOT NULL,
    PRIMARY KEY (uuid, skillcategoryid),
    FOREIGN KEY (skillcategoryid)
)${tableDefaults};


CREATE TABLE IF NOT EXISTS ${tablePrefix}player_skillinfo (
    uuid ${uuidType} NOT NULL,
    skillid INT NOT NULL,
    PRIMARY KEY (uuid, skillid),
    FOREIGN KEY (skillid)
)${tableDefaults};
