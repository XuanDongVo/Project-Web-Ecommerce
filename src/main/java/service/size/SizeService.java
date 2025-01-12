package service.size;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import entity.Size;
import entity.SizeType;
import repository.size.SizeRepository;
import repository.size.SizeTypeRepository;

public class SizeService {
	private SizeRepository sizeRepository = new SizeRepository();
	private SizeTypeRepository sizeTypeRepository = new SizeTypeRepository();

	public List<Size> getAllSize() {
		List<Size> sizes = sizeRepository.getAllSize();
		if (sizes == null) {
			throw new RuntimeException("Can not get all size");
		}
		return sizes;
	}

	public Map<String, List<String>> getSizeBySizeType() {
		List<SizeType> sizeTypes = sizeTypeRepository.getAllSizeType();
		if (sizeTypes == null || sizeTypes.isEmpty()) {
			throw new RuntimeException("Cannot get all size types");
		}

		Map<String, List<String>> map = new HashMap<>();

		sizeTypes.forEach(sizeType -> {
			List<Size> sizes = sizeRepository.getSizeBySizeType(sizeType.getId());
			if (sizes == null) {
				throw new RuntimeException("Cannot get sizes for sizeType: " + sizeType.getName());
			}
			List<String> sizeNames = sizes.stream().map(Size::getName).collect(Collectors.toList());
			map.put(sizeType.getName(), sizeNames);
		});
		return map;
	}

}
