package service.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entity.Category;
import entity.Gender;
import entity.SubCategory;
import repository.category.CategoryRepository;
import repository.category.SubCategoryRepository;
import repository.gender.GenderRepository;

public class SubCategoryService {
	private GenderRepository genderRepository = new GenderRepository();
	private SubCategoryRepository subCategoryRepository = new SubCategoryRepository();
	private CategoryRepository categoryRepository = new CategoryRepository();

	public Map<String, Map<String, List<String>>> dropListCategory() {
		List<Gender> genders = genderRepository.getAllGender();
		if (genders == null) {
			throw new RuntimeException("Error exception for get all category");
		}

		Map<String, Map<String, List<String>>> dropListCategory = new HashMap<>();

		genders.forEach(gender -> {
			Map<String, List<String>> innerMap = new HashMap<>();

			// Lấy tất cả các category dựa trên gender
			List<Category> categories = categoryRepository.getAllCategoriesByGender(gender.getName());

			categories.forEach(category -> {
				// Lấy tất cả các subcategory dựa trên category
				List<SubCategory> subCategories = subCategoryRepository.getSubCategoryByCategory(category.getName());

				// Chuyển đổi danh sách các SubCategory thành danh sách các tên (String)
				List<String> nameSubCategories = subCategories.stream().map(SubCategory::getName)
						.collect(Collectors.toList()); // Thu thập kết quả thành một danh sách

				// Thêm vào innerMap với tên category và danh sách tên subcategories
				innerMap.putIfAbsent(category.getName(), nameSubCategories);
			});

			// Thêm vào dropListCategory với tên gender và innerMap
			dropListCategory.putIfAbsent(gender.getName(), innerMap);
		});
		return dropListCategory;
	}

	public List<String> beadCrumb(String subCategory) {
		List<String> beadCrumb = subCategoryRepository.beadCrumb(subCategory);
		if (beadCrumb == null) {
			throw new RuntimeException();
		}
		return beadCrumb;
	}

	public Map<String, List<String>> getSubCategoryByGender() {
		// Lấy tất cả các giới tính từ repository
		List<Gender> genders = genderRepository.getAllGender();
		if (genders == null || genders.isEmpty()) {
			throw new RuntimeException("Error: Cannot get all genders");
		}

		// Khởi tạo Map để lưu kết quả
		Map<String, List<String>> genderToSubCategoryMap = new HashMap<>();

		// Duyệt qua từng gender
		genders.forEach(gender -> {
			// Lấy tất cả các categories dựa trên gender
			List<Category> categories = categoryRepository.getAllCategoriesByGender(gender.getName());
			if (categories == null || categories.isEmpty()) {
				return; // Bỏ qua nếu không có categories
			}

			// Lấy tất cả các subCategories thuộc các categories này
			List<String> subCategoryNames = categories.stream().flatMap(category -> {
				List<SubCategory> subCategories = subCategoryRepository.getSubCategoryByCategory(category.getName());
				if (subCategories != null) {
					return subCategories.stream().map(SubCategory::getName);
				}
				return Stream.empty();
			}).collect(Collectors.toList());

			// Đưa vào map
			genderToSubCategoryMap.put(gender.getName(), subCategoryNames);
		});
		return genderToSubCategoryMap;
	}

}
