package com.imageupload.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imageupload.Entity.Image;
import com.imageupload.Repository.ImageRepository;

@RestController
public class ImageController {

	@Autowired
	private ImageRepository imagerepository;
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file){
		try {
			Image image= new Image();
			image.setName(file.getOriginalFilename());
			image.setType(file.getContentType());
			image.setImage(file.getBytes());
			
			imagerepository.save(image);
			return ResponseEntity.ok("upload succesfully");
		}
		catch(Exception e){
			return ResponseEntity.status(500).body("Error: "+e.getMessage());
			
		}
	}
}
