package io.github.alathra.alathraskills.db;

import io.github.alathra.alathraskills.api.PlayerExperience;
import io.github.alathra.alathraskills.api.PlayerSkillDetails;
import io.github.alathra.alathraskills.api.SkillDetails;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerSkillcategoryinfoRecord;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerSkillinfoRecord;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerUsedSkillPointsRecord;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

import static io.github.alathra.alathraskills.db.schema.Tables.PLAYER_SKILLCATEGORYINFO;
import static io.github.alathra.alathraskills.db.schema.Tables.PLAYER_SKILLINFO;
import static io.github.alathra.alathraskills.db.schema.Tables.PLAYER_USED_SKILL_POINTS;
import static io.github.alathra.alathraskills.db.schema.Tables.PLAYER_LATEST_SKILL;

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
        } catch (SQLException e) {
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
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        } catch (SQLException e) {
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
            
            if(returnContext == null) {
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
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        } catch (SQLException e) {
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

    public static Record1<Integer> getUsedSkillPoints(UUID uuid) {
        try (
            Connection con = DB.getConnection()
        ) {
            DSLContext context = DB.getContext(con);

            return context
                .select(PLAYER_USED_SKILL_POINTS.SKILLS_USED)
                .from(PLAYER_USED_SKILL_POINTS)
                .where(PLAYER_USED_SKILL_POINTS.UUID.equal(convertUUIDToBytes(uuid)))
                .fetchOne();
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        } catch (SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;        	
        }
    }

    public static Integer getUsedSkillPoints(Player p) {
    	Record1<Integer> returnRecord = getUsedSkillPoints(p.getUniqueId());
    	if (returnRecord == null) {
    		return 0;
    	}
        return (Integer) returnRecord.getValue("SKILLS_USED");
    }

    /**
     * Sets the number of skills points a player has used
     *
     * @param uuid
     * @param usedSkillPoints
     */

    public static void setUsedSkillPoints(UUID uuid, Integer usedSkillPoints) {
        try (
                Connection con = DB.getConnection()
            ) {
                DSLContext context = DB.getContext(con);

                context
                    .insertInto(PLAYER_USED_SKILL_POINTS,
                		PLAYER_USED_SKILL_POINTS.UUID,
                        PLAYER_USED_SKILL_POINTS.SKILLS_USED)
                    .values(
                        convertUUIDToBytes(uuid),
                        usedSkillPoints
                    )
                    .onDuplicateKeyUpdate()
                    .set(PLAYER_USED_SKILL_POINTS.SKILLS_USED, usedSkillPoints)
                    .execute();
            } catch (DataAccessException e) {
                Logger.get().error("SQL Query threw an error!", e);
            } catch (SQLException e) {
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
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        } catch (SQLException e) {
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
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
            return null;
        } catch (SQLException e) {
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
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        } catch (SQLException e) {
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

            skillInfo.forEach( psd -> psd.getSkillInfo()
            		.forEach(psi -> records.add(
            				new PlayerSkillinfoRecord(
            						convertUUIDToBytes(psd.getP().getUniqueId()),
            						psi.getKey()))));
            context
                .batchDelete(records).execute();
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        } catch (SQLException e) {
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

            skillInfo.forEach( psd -> psd.getSkillInfo()
            		.forEach(psi -> records.add(
            				new PlayerSkillinfoRecord(
            						convertUUIDToBytes(psd.getP().getUniqueId()),
            						psi.getKey()))));
            context
                .batchInsert(records).execute();
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        } catch (SQLException e) {
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

            experienceInfo.forEach( eo -> eo.getExperienceValues()
            		.entrySet()
            		.forEach(ev -> records.add(
            				new PlayerSkillcategoryinfoRecord(
            						convertUUIDToBytes(eo.getP().getUniqueId()),
            						ev.getKey(),
            						ev.getValue().doubleValue()))));
            context
                .batchUpdate(records).execute();
        } catch (DataAccessException e) {
            Logger.get().error("SQL Query threw an error!", e);
        } catch (SQLException e) {
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
                .insertInto(PLAYER_LATEST_SKILL, PLAYER_LATEST_SKILL.UUID, PLAYER_LATEST_SKILL.SKILLID)
                .values(
                    convertUUIDToBytes(uuid),
                    id
                )
                .onDuplicateKeyUpdate()
                .set(PLAYER_LATEST_SKILL.UUID, convertUUIDToBytes(uuid))
                .set(PLAYER_LATEST_SKILL.SKILLID, id)
                .execute();
        } catch (SQLException e) {
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
                .select(PLAYER_LATEST_SKILL.SKILLID)
                .from(PLAYER_LATEST_SKILL)
                .where(PLAYER_LATEST_SKILL.UUID.equal(convertUUIDToBytes(uuid)))
                .fetchOne();

            if (record.component1() == null) return -1;

            return record.component1();
        }catch (SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
        return -1;
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
                .delete(PLAYER_LATEST_SKILL)
                .where(PLAYER_LATEST_SKILL.UUID.equal(convertUUIDToBytes(uuid)))
                .execute();
        } catch (SQLException e) {
            Logger.get().error("SQL Query threw an error!", e);
        }
    }

    public static void deleteLatestSkillUnlocked(Player p) {
        deleteLatestSkillUnlocked(p.getUniqueId());
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
