package io.github.alathra.alathraskills.db;

import io.github.alathra.alathraskills.utility.DB;
import io.github.alathra.alathraskills.utility.Logger;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.util.UUID;

import static io.github.alathra.alathraskills.db.schema.Tables.PLAYER_SKILLCATEGORYINFO;
import static io.github.alathra.alathraskills.db.schema.Tables.PLAYER_SKILLINFO;

/**
 * A holder class for all SQL queries
 */
public abstract class DatabaseQueries {


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
        } catch (DataAccessException e) {
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
                .execute();
        } catch (DataAccessException e) {
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

            return context
                .select(PLAYER_SKILLCATEGORYINFO.EXPERIENCE)
                .from(PLAYER_SKILLCATEGORYINFO)
                .where(PLAYER_SKILLCATEGORYINFO.UUID.equal(convertUUIDToBytes(uuid)))
                .and(PLAYER_SKILLCATEGORYINFO.SKILLCATEGORYID.equal(skillCategoryId))
                .fetchOne();
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        }
    }

    public static Record1<Double> getSkillCategoryExperience(Player p, int skillCategoryId) {
        return getSkillCategoryExperience(p.getUniqueId(), skillCategoryId);
    }

    /**
     * Convenience method for {@link #getSkillCategoryExperience(UUID, int)}
     *
     * @param uuid
     * @param skillCategoryId
     * @return skill category experience as float.
     */

    public static float getSkillCategoryExperienceFloat(UUID uuid, int skillCategoryId) {
        return (float) getSkillCategoryExperience(uuid, skillCategoryId).getValue("EXP");
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
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        }
    }

    public static Boolean doesPlayerHaveSkill(Player p, int skillId) {
        return doesPlayerHaveSkill(p.getUniqueId(), skillId);
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
