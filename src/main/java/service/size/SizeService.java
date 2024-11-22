package service.size;

import java.util.List;

import entity.Size;
import repository.size.SizeRepository;

public class SizeService {
	private SizeRepository sizeRepository = new SizeRepository();

	public List<Size> getAllSize() {
		List<Size> sizes = sizeRepository.getAllSize();
		if (sizes == null) {
			throw new RuntimeException("Can not get all size");
		}
		return sizes;
	}
}
