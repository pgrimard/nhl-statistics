package com.patrickgrimard.nhl;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.toList;

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
    public Collection<Map<String, Object>> teamStats(String seasonId) {
        String apiUrl = env.getRequiredProperty("stats.api");
        String httpUrl = env.getRequiredProperty("stats.teams");

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(httpUrl)
                .queryParam("season", seasonId)
                .build();

        Map<String, Object> response = rest.getForObject(uri.toUri(), Map.class);

        Collection<Map<String, Object>> records = (Collection<Map<String, Object>>) response.get("records");
        return records.stream()
                .map(record -> (Collection<Map<String, Object>>) record.get("teamRecords"))
                .flatMap(Collection::stream)
                .map(teamRecord -> {
                    Map<String, Object> teamRef = (Map<String, Object>) teamRecord.get("team");
                    String link = (String) teamRef.get("link");
                    Map<String, Object> team = rest.getForObject(apiUrl + link, Map.class);
                    Collection<Map<String, Object>> teams = (Collection<Map<String, Object>>) team.get("teams");
                    Map<String, Object> teamDetails = teams.stream().findFirst().get();
                    teamRecord.put("conference", teamDetails.get("conference"));
                    teamRecord.put("division", teamDetails.get("division"));
                    teamRecord.put("teamAbbr", teamDetails.get("abbreviation"));
                    return teamRecord;
                })
                .collect(toList());
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
