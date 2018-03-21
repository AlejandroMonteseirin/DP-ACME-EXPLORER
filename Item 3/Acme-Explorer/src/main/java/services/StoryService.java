package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StoryRepository;
import domain.Explorer;
import domain.Story;

@Service
@Transactional
public class StoryService {
	
	//Managed repository
	@Autowired
	private StoryRepository storyRepository;
	
	//Supporting services
	@Autowired
	private ActorService actorService;
	@Autowired
	private AdministratorService administratorService;
	
	//Constructor
	public StoryService(){
		super();
	}
	
	//Simple CRUD methods
	
		public Story create(){
			Explorer e;
			Story res;
			
			e = (Explorer) actorService.findByPrincipal();
			res = new Story();
			res.setExplorer(e);
			
			return res;
		}
		
		public Story save(Story story){
			Assert.notNull(story);

			Story result;

			result = storyRepository.save(story);
			
			//Comprobamos si es spam
			administratorService.checkIsSpam(story.getText());
			administratorService.checkIsSpam(story.getTitle());
			for(String url : story.getAttachmentURLs()){
				administratorService.checkIsSpam(url);
			}
			
			return result;
		}
		

		public Collection<Story> findAll(){
			
			Collection<Story> result;

			result = storyRepository.findAll();
			Assert.notNull(result);

			return result;
		}
		
		public Story findOne(int storyId){
			Story result;

			result = storyRepository.findOne(storyId);

			return result;
		}
		
		public void delete(Story story){
			Assert.notNull(story);
			storyRepository.delete(story);
		}
		
		// OTHER BUSSINES METHODS --------------
		
		public Collection<Story> getStoriesByExplorerId(int explorerId){
			return storyRepository.getStoriesByExplorerId(explorerId);
		}
		
}
