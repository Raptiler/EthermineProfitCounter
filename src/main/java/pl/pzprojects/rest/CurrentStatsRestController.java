package pl.pzprojects.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.pzprojects.dto.CurrentStatsDto;

public class CurrentStatsRestController extends pl.pzprojects.rest.MainRestController {
    private final String CURRENT_STATS_URL;


    private RestTemplate restTemplate;

    public CurrentStatsRestController(String ethAddress) {
        CURRENT_STATS_URL = "https://api.ethermine.org/miner/" + ethAddress + "/currentStats";
        restTemplate = new RestTemplate();
        restTemplate.setInterceptors(MainRestController.setEthermineHeaders());
    }

    public CurrentStatsDto getCurrentStats(){

        ResponseEntity<CurrentStatsDto> responseEntity = restTemplate.getForEntity(CURRENT_STATS_URL, CurrentStatsDto.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode()) /*&& !responseEntity.getBody().getData().equals("NO DATA")*/){
            return responseEntity.getBody();
        }
        else
        {
            //TODO Implement bad request
            throw new RuntimeException("Cant Load Statistics");
        }
    }
}
