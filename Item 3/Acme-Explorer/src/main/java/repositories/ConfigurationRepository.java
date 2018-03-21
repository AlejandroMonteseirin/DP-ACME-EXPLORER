package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration,Integer>{
	//TODO ¿Es necesaria esta quey teniendo un getSpamWords en la clase Config?
	@Query("select s from Configuration c join c.spamWords s")
	Collection<String> getSpamWords();
	
	// Saca el VAT Tax de la clase Config
	@Query("select c.VATTax from Configuration c")
	double getTax();
	
	@Query("select bannerURL from Configuration c")
	String getBannerURL();
	
	@Query("select c from Configuration c")
	Configuration getConfiguration();
}
