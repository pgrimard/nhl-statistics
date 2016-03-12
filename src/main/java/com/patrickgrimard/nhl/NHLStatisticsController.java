package com.patrickgrimard.nhl;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created on 2016-03-12
 *
 * @author Patrick
 */
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class NHLStatisticsController {

    private NHLStatisticsService nhl;

    @Inject
    public NHLStatisticsController(NHLStatisticsService nhl) {
        this.nhl = nhl;
    }

    @RequestMapping(value = "/conference", method = RequestMethod.GET)
    public Map conference() {
        return nhl.conferenceStats();
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public Map players(@RequestParam String seasonId, @RequestParam String gameTypeId) {
        return nhl.playerStats(seasonId, gameTypeId);
    }
}
