package contradiction.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.controller.dto.OpinionsDTO;
import api.model.Opinion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/11/13
 * Time: 9:12 PM
 */
@Component
public class OpinionClient {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${opinion.repository.endpoint}")
    private String opinionsEndpointURL;

    public List<Opinion> getAll() {
        String serviceEndpointURL = opinionsEndpointURL + "/findall";

        return restTemplate.getForObject(serviceEndpointURL, OpinionsDTO.class,null);
    }

    public List<Opinion> findByHolder(String holderName) {
        String serviceEndpointURL = opinionsEndpointURL + "/holder/{holderName}";

        Map<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("holderName", holderName);

        List<Opinion> results = restTemplate.getForObject(serviceEndpointURL,OpinionsDTO.class, urlVariables);

        return results;
    }

    public List<Opinion> findByHolderAndTarget(String holderName, String target) {
        String serviceEndpointURL = opinionsEndpointURL + "/holder/{holderName}/target/{target}";

        Map<String, String> urlVariables = new HashMap<String, String>();
        urlVariables.put("holderName", holderName);
        urlVariables.put("target", target);

        List<Opinion> results = restTemplate.getForObject(serviceEndpointURL,OpinionsDTO.class, urlVariables);

        return results;
    }

    public List<Opinion> findByTarget(String target) {
        String serviceEndpointURL = opinionsEndpointURL + "/target/{target}";

        Map<String, String> urlVariables = new HashMap<String, String>();

        urlVariables.put("target", target);

        List<Opinion> results = restTemplate.getForObject(serviceEndpointURL,OpinionsDTO.class, urlVariables);

        return results;
    }



}
