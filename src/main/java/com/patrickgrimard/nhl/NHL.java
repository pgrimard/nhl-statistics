package com.patrickgrimard.nhl;

import java.util.Map;

/**
 * Created on 2016-03-12
 *
 * @author Patrick
 */
public interface NHL {

    Map<String, Object> teamStats(String seasonId, String gameTypeId);

    Map<String, Object> playerStats(String seasonId, String gameTypeId);
}
