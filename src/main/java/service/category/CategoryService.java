package service.category;

import java.util.List;

import repository.category.CategoryRepository;

public class CategoryService {
	private CategoryRepository categoryRepository = new CategoryRepository();

	public List<String> beadCrumb(String category) {
		List<String> beadCrumb = categoryRepository.beadCrumb(category);
		if (beadCrumb == null) {
			throw new RuntimeException();
		}
		return beadCrumb;
	}

}
