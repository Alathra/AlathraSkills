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

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static io.github.alathra.alathraskills.db.schema.Tables.PLAYER_SKILLCATEGORYINFO;
import static io.github.alathra.alathraskills.db.schema.Tables.PLAYER_SKILLINFO;

/**
 * A holder class for all SQL queries
 */
public abstract class DatabaseQueries {


    /**
     * Attempts to save skill category exp to DB.
     *
     * @param uuid
     * @param skillCategoryId
     * @param exp
     */

    public static void saveSkillCategoryExp(UUID uuid, int skillCategoryId, Float exp) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            context
                .insertInto(PLAYER_SKILLCATEGORYINFO,
                    PLAYER_SKILLCATEGORYINFO.UUID,
                    PLAYER_SKILLCATEGORYINFO.SKILLCATEGORYID,
                    PLAYER_SKILLCATEGORYINFO.EXP)
                .values(
                    convertUUIDToBytes(uuid),
                    skillCategoryId,
                    exp.doubleValue()
                )
                .onDuplicateKeyUpdate()
                .set(PLAYER_SKILLCATEGORYINFO.EXP, exp.doubleValue())
                .execute();
        } catch (SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void saveSkillCategoryExp(Player p, int skillCategoryId, float exp) {
        saveSkillCategoryExp(p.getUniqueId(), skillCategoryId, exp);
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
        } catch (SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void saveSkillInfo(Player p, int skillId) {
        saveSkillInfo(p.getUniqueId(), skillId);
    }

    /**
     * Fetches skill category exp.
     *
     * @param uuid
     * @param skillCategoryId
     * @return record containing skill category exp.
     */

    public static Record1<Double> getSkillCategoryExp(UUID uuid, int skillCategoryId) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            return context
                .select(PLAYER_SKILLCATEGORYINFO.EXP)
                .from(PLAYER_SKILLCATEGORYINFO)
                .where(PLAYER_SKILLCATEGORYINFO.UUID.equal(convertUUIDToBytes(uuid)))
                .and(PLAYER_SKILLCATEGORYINFO.SKILLCATEGORYID.equal(skillCategoryId))
                .fetchOne();
        } catch (SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        }
    }

    public static Record1<Double> getSkillCategoryExp(Player p, int skillCategoryId) {
        return getSkillCategoryExp(p.getUniqueId(), skillCategoryId);
    }

    /**
     * Convenience method for {@link #getSkillCategoryExp(UUID, int)}
     *
     * @param uuid
     * @param skillCategoryId
     * @return skill category exp as float.
     */

    public static float getSkillCategoryExpFloat(UUID uuid, int skillCategoryId) {
        return (float) getSkillCategoryExp(uuid, skillCategoryId).getValue("EXP");
    }

    public static float getSkillCategoryExpFloat(Player p, int skillCategoryId) {
        return getSkillCategoryExpFloat(p.getUniqueId(), skillCategoryId);
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
        } catch (SQLException e) {
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
