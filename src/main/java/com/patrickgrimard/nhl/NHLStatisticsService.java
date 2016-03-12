package com.patrickgrimard.nhl;

import java.util.Map;

/**
 * Created on 2016-03-12
 *
 * @author Patrick
 */
public interface NHLStatisticsService {

    Map conferenceStats();

    Map playerStats(String seasonId, String gameTypeId);
}
