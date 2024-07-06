ALTER TABLE ${tablePrefix}player_playerdata ADD total_skillpoints INT NOT NULL AFTER uuid;
ALTER TABLE ${tablePrefix}player_playerdata ADD exp_progress FLOAT NOT NULL AFTER used_skillpoints;
