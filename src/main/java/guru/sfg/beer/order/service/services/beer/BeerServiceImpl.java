package guru.sfg.beer.order.service.services.beer;

import guru.sfg.beer.order.service.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Service
public class BeerServiceImpl implements BeerService {
	public final static String BEER_PATH_V1 = "/api/v1/beer/";
	public final static String BEER_UPC_PATH_V1 = "/api/v1/beerUpc/";
	private final RestTemplate restTemplate;

	private String beerServiceHost;

	public BeerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public Optional<BeerDto> getBeerById(UUID uuid){
		String beerServiceCallStr = beerServiceHost + BEER_PATH_V1 + uuid.toString();
		return Optional.of(restTemplate.getForObject(beerServiceCallStr, BeerDto.class));
	}

	@Override
	public Optional<BeerDto> getBeerByUpc(String upc) {
		String beerServiceCallStr = beerServiceHost + BEER_UPC_PATH_V1 + upc;

		log.debug("  =========== beerServiceCallStr: " + beerServiceCallStr);

		Optional<BeerDto> beerResult = Optional.of(restTemplate.getForObject(beerServiceCallStr, BeerDto.class));

		log.debug("  =========== beerResult" + beerResult);

		return beerResult;
	}

	public void setBeerServiceHost(String beerServiceHost) {
		this.beerServiceHost = beerServiceHost;
	}
}