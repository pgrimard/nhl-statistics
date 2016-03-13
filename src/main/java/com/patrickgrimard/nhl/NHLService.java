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
public class NHLService implements NHL {

    private Environment env;

    private RestTemplate rest;

    @Inject
    public NHLService(Environment env, RestTemplate rest) {
        this.env = env;
        this.rest = rest;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> teamStats(String seasonId) {
        String httpUrl = env.getRequiredProperty("stats.teams");

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(httpUrl)
                .queryParam("season", seasonId)
                .build();

        return rest.getForObject(uri.toUri(), Map.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> playerStats(String seasonId, String gameTypeId) {
        String httpUrl = env.getRequiredProperty("stats.players");

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(httpUrl)
                .queryParam("cayenneExp", "seasonId=" + seasonId + " and gameTypeId=" + gameTypeId)
                .build();

        return rest.getForObject(uri.toUri(), Map.class);
    }
}
