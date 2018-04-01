package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.Trip;

@Service
@Transactional
public class CategoryService {
	
	// Managed repository
	@Autowired
	private CategoryRepository categoryRepository;
	
	// Supporting services
	@Autowired
	private TripService tripService;

	// Constructor
	public CategoryService() {
		super();
	}

	// Simple CRUD methods
	public Category create() {
		Category c;
		Collection<Category> childCategories;
		
		c = new Category();
		childCategories = new ArrayList<>();
		c.setChildCategories(childCategories);
		
		return c;
	}

	public Collection<Category> findAll() {
		Collection<Category> result = categoryRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Category findOne(int categoryId) {
		Category result = categoryRepository.findOne(categoryId);
		return result;
	}

	public Category save(Category category) {
		Assert.notNull(category);
		
		// Comprobamos que no hay dos categorías con misma parentCategory y
		// mismo nombre
//		if (category.getId() != 0) {
			Collection<Category> categories;

			if(category.getParentCategory() != null){
			//Comprobamos que una categoría no es padre/hija de sí misma
			Assert.isTrue(!category.getParentCategory().equals(category),"message.error.parentCategory");
				
			categories = categoryRepository
					.getCategoriesbByParentCategory(category
							.getParentCategory().getId());
			}else{
				
				categories = categoryRepository.getParentCategories();
			}
			for (Category c : categories) {
				if (c.getId() != category.getId()) {
					Assert.isTrue(!c.getName().equals(category.getName()),
							"message.error.childCategories");
				}
			}
		
//		}

		
		// Actualizamos el padre-----------------------------------
//		Collection<Category> aux = category.getParentCategory().getChildCategories();
//		aux.add(category);
//		category.getParentCategory().setChildCategories(aux);
		
		//Si estamos poniendo al padre como hijo de uno de sus hijos,
		// lo ponemos en el nivel en el que estaba el padre
		
//		Category parentAux = this.getParent(category.getId());
//		category.getParentCategory().setParentCategory(parentAux);
//		
//		Category parentAuxsaved = categoryRepository.save(category.getParentCategory());
//		category.setParentCategory(parentAuxsaved);
		
		//Actualizamos las hijas---------------------------------------
		
//		Collection<Category> childAux = new ArrayList<>();
//		Category childSaved;
//		
//		for(Category c : category.getChildCategories()){
//			c.setParentCategory(category);
//			childSaved = categoryRepository.save(c);
//			childAux.add(childSaved);
//		}
//		
//		category.setChildCategories(childAux);
		
		Category result = categoryRepository.save(category);
		return result;
	}

	public void delete(Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		Collection<Trip> trips;
		Collection<Category> children;
		
		children = category.getChildCategories();
		
		if(!children.isEmpty()){
			Iterator<Category> iter = children.iterator();
			while(iter.hasNext()){
				
				Category c = iter.next();
				this.delete(c);
				iter.remove();
			}

		}
		
		trips = tripService.getTripsByCategory(category.getId());
		
		// Al borrar una categoría asignamos a los viajes que tenían la categoría padre
		// que en el peor de los casos será CATEGORY (equivalente a que el Trip no tiene categoría)
		if(!trips.isEmpty()){
			for(Trip t : trips){
				Category parent;
				
				parent = this.getParent(t.getCategory().getId());
				t.setCategory(parent);
				tripService.save(t);
			}
		}
		
		categoryRepository.delete(category);
		

	}
	
	//Other business methods
	public Collection<Category> getCategoriesbByParentCategory(int id){
		return categoryRepository.getCategoriesbByParentCategory(id);
	}
	
	public Collection<Category> getParentCategories(){
		return categoryRepository.getParentCategories();
	}
	
	public Collection<Category> getChildCategories(Integer parentCategoryId){
		return categoryRepository.getChildCategories(parentCategoryId);
	}

	public Category getParent(int childCategoryId){
		return categoryRepository.getParent(childCategoryId);
	}
	
	public Collection<Category> getAllCategories(){
		return categoryRepository.getAllCategories();
	}
}
