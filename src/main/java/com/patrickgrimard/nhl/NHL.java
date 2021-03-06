package com.patrickgrimard.nhl;

import java.util.Map;

/**
 * NHL service which will fetch various statistical data.
 * <p>
 * Created on 2016-03-12
 *
 * @author Patrick
 */
public interface NHL {

    /**
     * Returns team stats.
     *
     * @param seasonId   Season to fetch stats for.
     * @param gameTypeId Game type to fetch stats for.
     * @return Map of data.
     */
    Map<String, Object> teamStats(String seasonId, String gameTypeId);

    /**
     * Returns conference stats to get team rank.
     *
     * @param seasonId Season to fetch the conference stats for.
     * @return Map of data.
     */
    Map<String, Object> conferenceStats(String seasonId);

    /**
     * Returns player stats.
     *
     * @param seasonId   Season to fetch stats for.
     * @param gameTypeId Game type to fetch stats for.
     * @return Map of data.
     */
    Map<String, Object> playerStats(String seasonId, String gameTypeId);
}
