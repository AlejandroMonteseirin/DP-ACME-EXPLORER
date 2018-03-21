package utilities;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.MiscellaneousRecord;
import domain.Trip;

public class JacksonMapper {
	
	public static void main(String[] args) {

		ObjectMapper mapper = new ObjectMapper();

		try {

			MiscellaneousRecord mr = mapper.readValue(new File("C:\\Documents and Settings\\Student\\Desktop\\exampleJSON.txt"), MiscellaneousRecord.class);
			
			System.out.println(mr);


		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
