package com.patrickgrimard.nhl;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Created on 2016-03-12
 *
 * @author Patrick
 */
@Service
@RefreshScope
public class NHLService implements NHL {

    private final Environment env;

    private final RestTemplate rest;

    public NHLService(Environment env, RestTemplate rest) {
        this.env = env;
        this.rest = rest;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> teamStats(String seasonId, String gameTypeId) {
        String httpUrl = env.getRequiredProperty("stats.api.teams");

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(httpUrl)
                .queryParam("cayenneExp", "seasonId=" + seasonId + " and gameTypeId=" + gameTypeId)
                .build();

        return rest.getForObject(uri.toUri(), Map.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> playerStats(String seasonId, String gameTypeId) {
        String apiUrl = env.getRequiredProperty("stats.api.url");
        String httpUrl = env.getRequiredProperty("stats.api.players");

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(httpUrl)
                .queryParam("cayenneExp", "seasonId=" + seasonId + " and gameTypeId=" + gameTypeId)
                .build();

        return rest.getForObject(uri.toUri(), Map.class);

        /*

        Map<String, Object> response = rest.getForObject(uri.toUri(), Map.class);

        Collection<Map<String, Object>> records = (Collection<Map<String, Object>>) response.get("records");
        return records.stream()
                .map(record -> (Collection<Map<String, Object>>) record.get("teamRecords"))
                .flatMap(Collection::stream)
                .map(teamRecord -> {
                    Map<String, Object> teamRef = (Map<String, Object>) teamRecord.get("team");
                    String teamLink = (String) teamRef.get("link");
                    Map<String, Object> team = rest.getForObject(apiUrl + teamLink, Map.class);
                    Collection<Map<String, Object>> teams = (Collection<Map<String, Object>>) team.get("teams");
                    Map<String, Object> teamDetails = teams.stream().findFirst().get();

                    Map<String, Object> conference = (Map<String, Object>) teamDetails.get("conference");
                    teamRecord.put("conference", conference.get("name"));

                    Map<String, Object> division = (Map<String, Object>) teamDetails.get("division");
                    teamRecord.put("division", division.get("name"));

                    Map<String, Object> leagueRecord = (Map<String, Object>) teamRecord.get("leagueRecord");
                    teamRecord.put("losses", leagueRecord.get("losses"));
                    teamRecord.put("ot", leagueRecord.get("ot"));
                    teamRecord.put("wins", leagueRecord.get("wins"));
                    teamRecord.remove("leagueRecord");

                    Map<String, Object> streak = (Map<String, Object>) teamRecord.get("streak");
                    teamRecord.put("streakCode", streak.get("streakCode"));
                    teamRecord.remove("streak");

                    teamRecord.put("teamName", teamDetails.get("name"));
                    teamRecord.put("teamAbbr", teamDetails.get("abbreviation"));
                    teamRecord.remove("team");

                    teamRecord.remove("row");
                    teamRecord.remove("lastUpdated");
                    return teamRecord;
                })
                .collect(toList());
        */
    }
}
