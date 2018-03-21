package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Category;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest{
	
	// Service under test ---------------------------------
		@Autowired
		private CategoryService categoryService;
		
//		@Test
//		public void createSaveAndDelete(){
//			Category cc;
//			Category ccSaved;
//			Collection<Category> ccBefore = new ArrayList<>();
//			Collection<Category> ccAfter = new ArrayList<>();
//			
//			cc = categoryService.create();
//			Assert.notNull(cc);
//			
//			cc.setName("name");
//			
//			ccSaved = categoryService.save(cc);
//			Assert.notNull(ccSaved);
//			
//			ccBefore = categoryService.findAll();
//			Assert.isTrue(ccBefore.contains(ccSaved));
//			
//			categoryService.delete(ccSaved);
//			
//			ccAfter = categoryService.findAll();
//			Assert.isTrue(!ccAfter.contains(ccSaved));
//			
//		}
//		
//		//Probamos la relaci�n con parentCategory: dos categor�as hijas no pueden tener
//		//mismo nombre si tienen la misma parentCategory
//		@Test
//		public void parentCategoryTest(){
//			Category cc,cc2,ccParent;
//			Category ccSaved, cc2Saved, ccParentSaved;
//			
//			cc = categoryService.create();
//			cc2 = categoryService.create();
//			ccParent = categoryService.create();
//			cc.setName("name");
//			cc2.setName("name2");
//			ccParent.setName("nameParent");
//			ccParentSaved = categoryService.save(ccParent);
//			
//			
//			cc.setParentCategory(ccParentSaved);
//			cc2.setParentCategory(ccParentSaved);
//			
//			ccSaved = categoryService.save(cc);
//			cc2Saved = categoryService.save(cc2);
//			
//		}
		
		@Test
		public void deleteTest(){
			Category c = categoryService.findOne(3293);
			categoryService.delete(c);
		}
		
}
