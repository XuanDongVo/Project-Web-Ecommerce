package service.color;

import java.util.List;
import entity.Color;
import repository.color.ColorRepository;

public class ColorService {
	private ColorRepository colorRepository = new ColorRepository();

	public List<Color> getAllColor() {
		List<Color> colors = colorRepository.getAllColor();
		if (colors == null) {
			throw new RuntimeException("Can not get all color");
		}
		return colors;
	}
}
