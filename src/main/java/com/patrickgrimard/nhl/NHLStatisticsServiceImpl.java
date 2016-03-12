package com.patrickgrimard.nhl;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created on 2016-03-12
 *
 * @author Patrick
 */
@Service
@RefreshScope
public class NHLStatisticsServiceImpl implements NHLStatisticsService {

    private Environment env;

    private RestTemplate rest;

    @Inject
    public NHLStatisticsServiceImpl(Environment env, RestTemplate rest) {
        this.env = env;
        this.rest = rest;
    }

    @Override
    public Map conferenceStats() {
        String conferenceUri = env.getProperty("stats.teams.regular");
        Map response = rest.getForObject(conferenceUri, Map.class, "20152016", "2");
        return response;
    }

    @Override
    public Map playerStats(String seasonId, String gameTypeId) {
        String httpUrl = env.getRequiredProperty("stats.players");

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(httpUrl)
                .queryParam("cayenneExp", "seasonId=" + seasonId + " and gameTypeId=" + gameTypeId)
                .build();

        return rest.getForObject(uri.toUri(), Map.class);
    }
}
