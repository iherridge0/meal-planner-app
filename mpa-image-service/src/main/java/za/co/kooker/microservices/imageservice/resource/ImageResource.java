package za.co.kooker.microservices.imageservice.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import za.co.kooker.microservices.imageservice.entity.Gallary;
import za.co.kooker.microservices.imageservice.repository.GallaryRepository;
import za.co.kooker.microservices.imageservice.repository.ImageService;

@RestController
//@RequestMapping(value = "api/images")
public class ImageResource {

	@Autowired
	public ImageService imageService;

	@Autowired
	public GallaryRepository gallaryRepository;

	@PostMapping(value = "meals/{mealId}/upload")
	public ResponseEntity<Object> uploadMealImage(@PathVariable int mealId, @RequestParam MultipartFile file) {
		String downloadURI = imageService.uploadToLocalFileSystem(file);
		Gallary newGallaryEntry = new Gallary(downloadURI, mealId);
		gallaryRepository.save(newGallaryEntry);
		URI uri = URI.create(downloadURI);
		return ResponseEntity.ok().location(uri).build();
	}

	@GetMapping(value = "getImage/{imageName:.+}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public @ResponseBody byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName)
			throws IOException {

		try {
			return this.imageService.getImageWithMediaType(fileName);
		} catch (FileNotFoundException e) {
			return this.imageService.getDefaultImageWithMediaType(fileName);
		}

	}
}