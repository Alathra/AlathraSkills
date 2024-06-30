package io.github.alathra.alathraskills.db;

import io.github.alathra.alathraskills.api.PlayerExperience;
import io.github.alathra.alathraskills.api.PlayerSkillDetails;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerDisabledSkillsRecord;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerSkillcategoryinfoRecord;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerSkillinfoRecord;
import io.github.alathra.alathraskills.utility.DB;
import io.github.alathra.alathraskills.utility.Logger;
import org.bukkit.entity.Player;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.exception.DataAccessException;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static io.github.alathra.alathraskills.db.schema.Tables.*;

/**
 * A holder class for all SQL queries
 */
public abstract class DatabaseQueries {

    /**
     * Saves all data that is stored in PLAYER_PLAYERDATA
     *
     * @param uuid
     * @param usedSkillPoints
     * @param latestSkillUnlocked
     * @param cooldown
     */
    public static void savePlayerData(UUID uuid, int usedSkillPoints, int latestSkillUnlocked, Instant cooldown) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context.insertInto(PLAYER_PLAYERDATA,
                    PLAYER_PLAYERDATA.UUID,
                    PLAYER_PLAYERDATA.USED_SKILLPOINTS,
                    PLAYER_PLAYERDATA.LATEST_UNLOCKED_SKILL,
                    PLAYER_PLAYERDATA.COOLDOWN)
                .values(
                    convertUUIDToBytes(uuid),
                    usedSkillPoints,
                    latestSkillUnlocked,
                    LocalDateTime.ofInstant(cooldown, ZoneOffset.UTC)
                )
                .onDuplicateKeyUpdate()
                .set(PLAYER_PLAYERDATA.USED_SKILLPOINTS, usedSkillPoints)
                .set(PLAYER_PLAYERDATA.LATEST_UNLOCKED_SKILL, latestSkillUnlocked)
                .set(PLAYER_PLAYERDATA.COOLDOWN, LocalDateTime.ofInstant(cooldown, ZoneOffset.UTC))
                .execute();

        } catch (SQLException | DataAccessException e) {
            Logger.get().error("SQL Query threw an error: " + e);
        }
    }

    /**
     * Convenience shorthand for {@link #savePlayerData(UUID, int, int, Instant)}.
     *
     * @param p
     * @param usedSkillPoints
     * @param latestSkillUnlocked
     * @param cooldown
     */
    public static void savePlayerData(Player p, int usedSkillPoints, int latestSkillUnlocked, Instant cooldown) {
        savePlayerData(p.getUniqueId(), usedSkillPoints, latestSkillUnlocked, cooldown);
    }


    /**
     * Fetches all data stored in the PLAYER_PLAYERDATA table.
     *
     * @param uuid
     * @return A result of the data.
     */
    public static Result<Record3<Integer, Integer, LocalDateTime>> fetchPlayerData(UUID uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            return context.select(PLAYER_PLAYERDATA.USED_SKILLPOINTS,
                    PLAYER_PLAYERDATA.LATEST_UNLOCKED_SKILL,
                    PLAYER_PLAYERDATA.COOLDOWN)
                .from(PLAYER_PLAYERDATA)
                .where(PLAYER_PLAYERDATA.UUID.equal(convertUUIDToBytes(uuid)))
                .fetch();

        } catch (SQLException | DataAccessException e) {
            Logger.get().error("SQL Query threw an error: " + e);
        }
        return null;
    }

    /**
     * Convenience shorthand for {@link #fetchPlayerData(UUID)}.
     *
     * @param p player
     * @return A result of the data.
     */
    public static Result<Record3<Integer, Integer, LocalDateTime>> fetchPlayerData(Player p) {
        return fetchPlayerData(p.getUniqueId());
    }

    /**
     * Batch insert all experience values.
     *
     * @param uuid
     * @param farmingExperience
     * @param miningExperience
     * @param woodcuttingExperience
     */
    public static void saveAllSkillCategoryExperience(UUID uuid, Float farmingExperience, Float miningExperience, Float woodcuttingExperience) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context.batchMerge(
                    new PlayerSkillcategoryinfoRecord(convertUUIDToBytes(uuid), 1, farmingExperience.doubleValue()),
                    new PlayerSkillcategoryinfoRecord(convertUUIDToBytes(uuid), 2, miningExperience.doubleValue()),
                    new PlayerSkillcategoryinfoRecord(convertUUIDToBytes(uuid), 3, woodcuttingExperience.doubleValue())
                )
                .execute();

        } catch (SQLException | DataAccessException e) {
            Logger.get().error("SQL Query threw an error: " + e);
        }
    }

    /**
     * Convenience shorthand for {@link #saveAllSkillCategoryExperience(UUID, Float, Float, Float)}.
     *
     * @param p
     * @param farmingExperience
     * @param miningExperience
     * @param woodcuttingExperience
     */
    public static void saveAllSkillCategoryExperience(Player p, Float farmingExperience, Float miningExperience, Float woodcuttingExperience) {
        saveAllSkillCategoryExperience(p.getUniqueId(), farmingExperience, miningExperience, woodcuttingExperience);
    }

    /**
     * Fetches all of a player's experience.
     *
     * @return A result of the data.
     */
    public static Result<Record2<Integer, Double>> fetchAllSkillCategoryExperience(UUID uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            return context.select(PLAYER_SKILLCATEGORYINFO.SKILLCATEGORYID, PLAYER_SKILLCATEGORYINFO.EXPERIENCE)
                .from(PLAYER_SKILLCATEGORYINFO)
                .where(PLAYER_SKILLCATEGORYINFO.UUID.equal(convertUUIDToBytes(uuid)))
                .fetch();
        } catch (SQLException | DataAccessException e) {
            Logger.get().error("SQL Query threw an error: " + e);
        }
        return null;
    }

    /**
     * Convenience shorthand for {@link #fetchAllSkillCategoryExperience(UUID)}.
     *
     * @param p
     * @return
     */
    public static Result<Record2<Integer, Double>> fetchAllSkillCategoryExperience(Player p) {
        return fetchAllSkillCategoryExperience(p.getUniqueId());
    }

    /**
     * Batch deletes and inserts provided lists of skills.
     *
     * @param uuid
     * @param skillsToDelete List of skill IDs to delete.
     * @param skillsToInsert List of skill IDs to insert.
     */
    public static void saveFilteredPlayerSkills(byte[] uuid, List<Integer> skillsToDelete, List<Integer> skillsToInsert) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            Collection<PlayerSkillinfoRecord> deletedSkills = new ArrayList<>();
            Collection<PlayerSkillinfoRecord> addedSkills = new ArrayList<>();

            skillsToDelete.forEach(skill -> deletedSkills.add(new PlayerSkillinfoRecord(uuid, skill)));
            skillsToInsert.forEach(skill -> addedSkills.add(new PlayerSkillinfoRecord(uuid, skill)));

            context.batchDelete(deletedSkills).execute();

            context.batchMerge(addedSkills).execute();
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    /**
     * Convenience shorthand for {@link #saveFilteredPlayerSkills(byte[], List, List)}.
     *
     * @param uuid
     * @param skillsToDelete
     * @param skillsToInsert
     */
    public static void saveFilteredPlayerSkills(UUID uuid, List<Integer> skillsToDelete, List<Integer> skillsToInsert) {
        saveFilteredPlayerSkills(convertUUIDToBytes(uuid), skillsToDelete, skillsToInsert);
    }

    /**
     * Convenience shorthand for {@link #saveFilteredPlayerSkills(UUID, List, List)}.
     *
     * @param p
     * @param skillsToDelete
     * @param skillsToInsert
     */
    public static void saveFilteredPlayerSkills(Player p, List<Integer> skillsToDelete, List<Integer> skillsToInsert) {
        saveFilteredPlayerSkills(p.getUniqueId(), skillsToDelete, skillsToInsert);
    }

    /**
     * Saves all skills a player has disabled.
     *
     * @param uuid
     * @param disabledSkills
     */
    public static void saveDisabledSkills(byte[] uuid, List<Integer> disabledSkills) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            Collection<PlayerDisabledSkillsRecord> disabledSkillsRecords = new ArrayList<>();

            disabledSkills.forEach(i -> disabledSkillsRecords.add(new PlayerDisabledSkillsRecord(uuid, i)));

            context.batchMerge(disabledSkillsRecords).execute();
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void saveDisabledSkills(UUID uuid, List<Integer> disabledSkills) {
        saveDisabledSkills(convertUUIDToBytes(uuid), disabledSkills);
    }

    public static void saveDisabledSkills(Player p, List<Integer> disabledSkills) {
        saveDisabledSkills(p.getUniqueId(), disabledSkills);
    }

    /**
     * Attempts to fetch all disabled skills
     *
     * @param uuid
     * @return - disabled skills, or null on fail.
     */
    public static Result<Record1<Integer>> fetchDisabledSkills(byte[] uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            return context.select(PLAYER_DISABLED_SKILLS.SKILLID)
                .from(PLAYER_DISABLED_SKILLS)
                .where(PLAYER_DISABLED_SKILLS.UUID.equal(uuid))
                .fetch();

        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
        return null;
    }

    public static Result<Record1<Integer>> fetchDisabledSkills(UUID uuid) {
        return fetchDisabledSkills(convertUUIDToBytes(uuid));
    }

    public static Result<Record1<Integer>> fetchDisabledSkills(Player p) {
        return fetchDisabledSkills(p.getUniqueId());
    }

    /**
     * Attempts to save skill category experience to DB.
     *
     * @param uuid
     * @param skillCategoryId
     * @param experience
     */
    public static void saveSkillCategoryExperience(UUID uuid, int skillCategoryId, Float experience) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context
                .insertInto(PLAYER_SKILLCATEGORYINFO,
                    PLAYER_SKILLCATEGORYINFO.UUID,
                    PLAYER_SKILLCATEGORYINFO.SKILLCATEGORYID,
                    PLAYER_SKILLCATEGORYINFO.EXPERIENCE)
                .values(
                    convertUUIDToBytes(uuid),
                    skillCategoryId,
                    experience.doubleValue()
                )
                .onDuplicateKeyUpdate()
                .set(PLAYER_SKILLCATEGORYINFO.EXPERIENCE, experience.doubleValue())
                .execute();
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void saveSkillCategoryExperience(Player p, int skillCategoryId, float experience) {
        saveSkillCategoryExperience(p.getUniqueId(), skillCategoryId, experience);
    }

    /**
     * Attempts to save an unlocked skill to DB.
     *
     * @param uuid
     * @param skillId
     */
    public static void saveSkillInfo(UUID uuid, int skillId) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context
                .insertInto(PLAYER_SKILLINFO,
                    PLAYER_SKILLINFO.UUID,
                    PLAYER_SKILLINFO.SKILLID)
                .values(
                    convertUUIDToBytes(uuid),
                    skillId
                )
                .onDuplicateKeyIgnore()
                .execute();
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void saveSkillInfo(Player p, int skillId) {
        saveSkillInfo(p.getUniqueId(), skillId);
    }

    /**
     * Fetches skill category experience.
     *
     * @param uuid
     * @param skillCategoryId
     * @return record containing skill category experience.
     */
    public static Record1<Double> getSkillCategoryExperience(UUID uuid, int skillCategoryId) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            Record1<Double> returnContext = context
                .select(PLAYER_SKILLCATEGORYINFO.EXPERIENCE)
                .from(PLAYER_SKILLCATEGORYINFO)
                .where(PLAYER_SKILLCATEGORYINFO.UUID.equal(convertUUIDToBytes(uuid)))
                .and(PLAYER_SKILLCATEGORYINFO.SKILLCATEGORYID.equal(skillCategoryId))
                .fetchOne();

            if (returnContext == null) {
                context
                    .insertInto(PLAYER_SKILLCATEGORYINFO,
                        PLAYER_SKILLCATEGORYINFO.UUID,
                        PLAYER_SKILLCATEGORYINFO.SKILLCATEGORYID,
                        PLAYER_SKILLCATEGORYINFO.EXPERIENCE)
                    .values(
                        convertUUIDToBytes(uuid),
                        skillCategoryId,
                        0.00
                    )
                    .onDuplicateKeyIgnore()
                    .execute();
            }

            return returnContext;
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        }
    }

    public static Record1<Double> getSkillCategoryExperience(Player p, int skillCategoryId) {
        return getSkillCategoryExperience(p.getUniqueId(), skillCategoryId);
    }

    /**
     * Fetches the number of skills a player has used
     *
     * @param uuid
     * @return record containing used number of skills
     */
    public static int getUsedSkillPoints(UUID uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            Record1<Integer> record = context
                .select(PLAYER_PLAYERDATA.USED_SKILLPOINTS)
                .where(PLAYER_PLAYERDATA.UUID.equal(convertUUIDToBytes(uuid)))
                .fetchOne();

            if (record == null)
                return 0;

            Integer usedSkillPoints = record.component1();

            if (usedSkillPoints == null)
                return 0;
            return usedSkillPoints;
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
        return 0;
    }

    public static int getUsedSkillPoints(Player p) {
        return getUsedSkillPoints(p.getUniqueId());
    }

    /**
     * Sets the number of skills points a player has used
     *
     * @param uuid
     * @param usedSkillPoints
     */
    public static void setUsedSkillPoints(UUID uuid, int usedSkillPoints) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context
                .insertInto(PLAYER_PLAYERDATA,
                    PLAYER_PLAYERDATA.UUID,
                    PLAYER_PLAYERDATA.USED_SKILLPOINTS)
                .values(
                    convertUUIDToBytes(uuid),
                    usedSkillPoints
                )
                .onDuplicateKeyUpdate()
                .set(PLAYER_PLAYERDATA.USED_SKILLPOINTS, usedSkillPoints)
                .execute();
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void setUsedSkillPoints(Player p, Integer usedSkillPoints) {
        setUsedSkillPoints(p.getUniqueId(), usedSkillPoints);
    }

    /**
     * Convenience method for {@link #getSkillCategoryExperience(UUID, int)}
     *
     * @param uuid
     * @param skillCategoryId
     * @return skill category experience as float.
     */
    public static float getSkillCategoryExperienceFloat(UUID uuid, int skillCategoryId) {
        Record1<Double> returnRecord = getSkillCategoryExperience(uuid, skillCategoryId);
        if (returnRecord == null) {
            return 0;
        }
        return ((Double) returnRecord.getValue("EXPERIENCE")).floatValue();
    }

    public static float getSkillCategoryExperienceFloat(Player p, int skillCategoryId) {
        return getSkillCategoryExperienceFloat(p.getUniqueId(), skillCategoryId);
    }

    /**
     * Checks if player has a skill unlocked.
     *
     * @param uuid
     * @param skillId
     * @return whether the player has a skill unlocked, and null if error.
     */
    public static Boolean doesPlayerHaveSkill(UUID uuid, int skillId) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            return context
                .fetchExists(context
                    .selectFrom(PLAYER_SKILLINFO)
                    .where(PLAYER_SKILLINFO.UUID.equal(convertUUIDToBytes(uuid)))
                    .and(PLAYER_SKILLINFO.SKILLID.equal(skillId)));
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        }
    }

    public static Boolean doesPlayerHaveSkill(Player p, int skillId) {
        return doesPlayerHaveSkill(p.getUniqueId(), skillId);
    }

    /**
     * Fetches all skills a player has
     *
     * @param uuid
     * @return all skill records a player is associated with.
     */
    public static Result<PlayerSkillinfoRecord> fetchPlayerSkills(UUID uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            return context
                .fetch(context
                    .selectFrom(PLAYER_SKILLINFO)
                    .where(PLAYER_SKILLINFO.UUID.equal(convertUUIDToBytes(uuid))));
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        }
    }

    public static Result<PlayerSkillinfoRecord> fetchPlayerSkills(Player p) {
        return fetchPlayerSkills(p.getUniqueId());
    }

    /**
     * Deletes all skills a player has
     *
     * @param uuid
     */
    public static void deletePlayerSkills(UUID uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context
                .delete(PLAYER_SKILLINFO
                    .where(PLAYER_SKILLINFO.UUID.equal(convertUUIDToBytes(uuid))))
                .execute();
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void deletePlayerSkills(Player p) {
        deletePlayerSkills(p.getUniqueId());
    }

    public static void deletePlayerSkillsRecordSet(
        Stream<PlayerSkillDetails> skillInfo) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            Collection<PlayerSkillinfoRecord> records = new ArrayList<PlayerSkillinfoRecord>();

            skillInfo.forEach(psd -> psd.getSkillInfo()
                .forEach(psi -> records.add(
                    new PlayerSkillinfoRecord(
                        convertUUIDToBytes(psd.getP().getUniqueId()),
                        psi.getKey()))));
            context
                .batchDelete(records).execute();
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void insertPlayerSkillsRecordSet(
        Stream<PlayerSkillDetails> skillInfo) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            Collection<PlayerSkillinfoRecord> records = new ArrayList<PlayerSkillinfoRecord>();

            skillInfo.forEach(psd -> psd.getSkillInfo()
                .forEach(psi -> records.add(
                    new PlayerSkillinfoRecord(
                        convertUUIDToBytes(psd.getP().getUniqueId()),
                        psi.getKey()))));
            context
                .batchInsert(records).execute();
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void updatePlayerExperienceRecordSet(
        Stream<PlayerExperience> experienceInfo) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            Collection<PlayerSkillcategoryinfoRecord> records = new ArrayList<PlayerSkillcategoryinfoRecord>();

            experienceInfo.forEach(eo -> eo.getExperienceValues()
                .entrySet()
                .forEach(ev -> records.add(
                    new PlayerSkillcategoryinfoRecord(
                        convertUUIDToBytes(eo.getP().getUniqueId()),
                        ev.getKey(),
                        ev.getValue().doubleValue()))));
            context
                .batchUpdate(records).execute();
        } catch (DataAccessException | SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    /**
     * Sets the latest unlocked skill
     *
     * @param uuid
     * @param id
     */
    public static void setLatestSkillUnlocked(UUID uuid, int id) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context
                .insertInto(PLAYER_PLAYERDATA, PLAYER_PLAYERDATA.UUID, PLAYER_PLAYERDATA.LATEST_UNLOCKED_SKILL)
                .values(
                    convertUUIDToBytes(uuid),
                    id
                )
                .onDuplicateKeyUpdate()
                .set(PLAYER_PLAYERDATA.UUID, convertUUIDToBytes(uuid))
                .set(PLAYER_PLAYERDATA.LATEST_UNLOCKED_SKILL, id)
                .execute();
        } catch (SQLException | DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void setLatestSkillUnlocked(Player p, int id) {
        setLatestSkillUnlocked(p.getUniqueId(), id);
    }

    /**
     * Gets a player's latest unlocked skill.
     *
     * @param uuid
     * @return - skill ID, or -1 on fail/null.
     */
    public static int getLatestSkillUnlocked(UUID uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            Record1<Integer> record = context
                .select(PLAYER_PLAYERDATA.LATEST_UNLOCKED_SKILL)
                .from(PLAYER_PLAYERDATA)
                .where(PLAYER_PLAYERDATA.UUID.equal(convertUUIDToBytes(uuid)))
                .fetchOne();

            if (record == null || record.component1() == null) return 0;

            return record.component1();
        } catch (SQLException | DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
        return 0;
    }

    public static int getLatestSkillUnlocked(Player p) {
        return getLatestSkillUnlocked(p.getUniqueId());
    }

    /**
     * Deletes the latest unlocked skill from DB.
     *
     * @param uuid
     */
    public static void deleteLatestSkillUnlocked(UUID uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context
                .delete(PLAYER_PLAYERDATA)
                .where(PLAYER_PLAYERDATA.UUID.equal(convertUUIDToBytes(uuid)))
                .execute();
        } catch (SQLException | DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void deleteLatestSkillUnlocked(Player p) {
        deleteLatestSkillUnlocked(p.getUniqueId());
    }

    /**
     * Fetch a player's cooldown from DB.
     *
     * @param uuid
     * @return cooldown, if player is still on cooldown. Else return null.
     */
    public static Instant getResetCooldown(UUID uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            Record result = context.select()
                .from(PLAYER_PLAYERDATA)
                .where(PLAYER_PLAYERDATA.UUID.equal(convertUUIDToBytes(uuid)))
                .fetchOne();

            if (result == null) return null;

            Instant instant = result.get(PLAYER_PLAYERDATA.COOLDOWN).toInstant(ZoneOffset.UTC);
            if (instant.isBefore(Instant.now())) return null;
            else return instant;
        } catch (SQLException | DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
        return null;
    }

    public static Instant getResetCooldown(Player p) {
        return getResetCooldown(p.getUniqueId());
    }

    public static void saveResetCooldown(UUID uuid, Instant instant) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context.insertInto(PLAYER_PLAYERDATA, PLAYER_PLAYERDATA.UUID, PLAYER_PLAYERDATA.COOLDOWN)
                .values(
                    convertUUIDToBytes(uuid),
                    LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
                )
                .execute();
        } catch (SQLException | DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void saveResetCooldown(Player p, Instant instant) {
        saveResetCooldown(p.getUniqueId(), instant);
    }

    public static byte[] convertUUIDToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
