package com.patrickgrimard.nhl;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created on 2016-03-12
 *
 * @author Patrick
 */
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class NHLController {

    private final NHL nhl;

    public NHLController(NHL nhl) {
        this.nhl = nhl;
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public Map<String, Object> teams(@RequestParam String seasonId) {
        return nhl.teamStats(seasonId, "2");
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public Map<String, Object> players(@RequestParam String seasonId, @RequestParam String gameTypeId) {
        return nhl.playerStats(seasonId, gameTypeId);
    }
}
