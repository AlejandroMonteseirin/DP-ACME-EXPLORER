package utilities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import utilities.internal.ConsoleReader;
import utilities.internal.EclipseConsole;
import utilities.internal.SchemaPrinter;

import domain.Trip;
 

public class FullTextSearch {
 
	//This will be the String read from the console. We set it as an attribute because
	// we will need to use it in both methods
    private static String line;
    
   
    public static void main(String[] args) throws Throwable{
    	
    	
    	//By the use of this function we disable the logs in the console
    	EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);
		
		
    	//We write all inside a do/while block in order to have the chance of doing a new
		//search without executing the application again
    	do{
	    	System.out.println("\nSearch a trip by its ticker, title or description");
	    	System.out.println("------------------------------------------------------------");
	    	
	    	//Creation of a ConsoleReader object to read from the console
	    	ConsoleReader reader;
	    	reader = new ConsoleReader();
				line = reader.readLine();
				System.out.print("\nPlease stand by while the database is searched...");
				
			//Creation of an EntityManagerFactory object. To do this we indicate our 
			//persistence unit, 'Acme-Explorer'
	        EntityManagerFactory factory = Persistence
	                .createEntityManagerFactory("Acme-Explorer");
	        
	        //Creation of a EntityManager object by using the previous factory
	        EntityManager manager = factory.createEntityManager();
	        
	        //Creation of a FullTextSearch object we will use to do the search
	        FullTextSearch search = new FullTextSearch();
	        
	        //We call our searchTrips method
	        search.searchTrips(manager);
	        
    	}while(true);
    }
 
 
    private void searchTrips(EntityManager em) {
    	
 
    	//Creation of FullTextEntityManager object, which extends the EntityManager interface
    	//with full-text search capabilities
    	//By using this we will be able to create a QueryBuilder next
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
                .getFullTextEntityManager(em);
 
        //We use the following methods to create the indexes the first time we execute our application 
        //inside a try/catch block, to capture the exception if necessary
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        //We indicate the beginning of the transaction
        em.getTransaction().begin();
 
        //We create a QueryBuilder object and indicate we will do the search in Trip class
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Trip.class).get();
        
        //Here we do a Lucene Query object, indicate the attributes we will use in the search
        //and the keyword ('line' attribute we create first)
        org.apache.lucene.search.Query query = qb.keyword().onFields("title","ticker","description")
                .matching(line).createQuery();
 
        // We wrap the Lucene Query in a javax.persistence.Query
        javax.persistence.Query persistenceQuery = fullTextEntityManager
                .createFullTextQuery(query, Trip.class);
 
        //We get the results
        @SuppressWarnings("unchecked")
		List<Trip> objects = persistenceQuery.getResultList();
        
        //We print the number of objects our application has found and we show them
        System.out.printf("%d object%s selected%n", objects.size(), (objects.size() == 1 ? "" : "s"));
			SchemaPrinter.print(objects);
			
		//We end the transaction
        em.getTransaction().commit();
        em.close();
 
    }
 
}
