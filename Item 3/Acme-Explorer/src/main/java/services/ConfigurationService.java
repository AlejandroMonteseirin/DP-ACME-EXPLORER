package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {
	
	// MANAGED REPOSITORY ---------------
	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	// SUPPORTING SERVICES -------------
	
	
	// CONSTRUCTOR ---------------
	
	public ConfigurationService(){
		super();
	}
	
	// TODO SIMPLE CRUD METHODS -----------
	public Configuration create(){
		Configuration c;
		
		c = new Configuration();
		
		return c;
	}

	public Configuration save(Configuration config){
		Assert.notNull(config);
		
		Configuration c;
		
		c = configurationRepository.save(config);
		
		return c;
	}
	
	public void delete(Configuration config){
		Assert.notNull(config);
		
		configurationRepository.delete(config);
	}
	
	public Configuration findOne(int configurationId) {
		Configuration result;

		result = configurationRepository.findOne(configurationId);

		return result;
	}
	
	public Collection<Configuration> findAll() {
		Collection<Configuration> result;

		result = configurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}
	
	// OTHER BUSSINES METHODS -------------------------------
	
	public Collection<String> getSpamWords(){
		return configurationRepository.getSpamWords();
	}
	
	public double getTax(){
		return configurationRepository.getTax();
	}
	
	public String getBannerURL(){
		return configurationRepository.getBannerURL();
	}
	
	public Configuration getConfiguration(){
		return configurationRepository.getConfiguration();
	}
	
	public String checkPhoneNumber(String tlf) {

		if (!tlf.startsWith("+") && tlf.length() > 4) {
			tlf = this.getConfiguration().getCountryCode() + " " + tlf;
		}

		return tlf;

	}
	
	// B.17 Los resultados del Finder se almacenan en caché durante una hora por defecto.
	//El administrator debe poder configurar este tiempo.El minimo es una hora y el máximo es 24 horas.
	
	// B.17 El máximo numero de resultados que un finder devuelve es 10 por defecto. El admin
	//debe poder cambiar este parametro. El maximo es 100 resultados.
}
