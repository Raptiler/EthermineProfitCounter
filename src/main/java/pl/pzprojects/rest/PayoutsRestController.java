package pl.pzprojects.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import pl.pzprojects.dto.PayoutsEthermineDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayoutsRestController extends MainRestController{
    private final String PAYOUTS_URL;
    private final String ETH_ADDRESS;

    private RestTemplate restTemplate;

    private List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

    public PayoutsRestController(String ethAddress) {
        this.ETH_ADDRESS = ethAddress;
        PAYOUTS_URL  = "https://api.ethermine.org/miner/" + ETH_ADDRESS + "/payouts";;
        restTemplate = new RestTemplate();
        restTemplate.setInterceptors(MainRestController.setEthermineHeaders());
    }

    public PayoutsEthermineDto getPayouts() throws IOException {
        ResponseEntity<String> r = restTemplate.getForEntity(PAYOUTS_URL,String.class);
        ResponseEntity<PayoutsEthermineDto> responseEntity = restTemplate.getForEntity(PAYOUTS_URL, PayoutsEthermineDto.class);

        if(HttpStatus.OK.equals(responseEntity.getStatusCode()) /*&& !responseEntity.getBody().getData().isEmpty()*/){
            return responseEntity.getBody();
        }
        else
        {
            //TODO Implement bad request
            throw new RuntimeException("Cant Load Payouts");
        }
    }


}
