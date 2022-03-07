package com.shubhayan.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Configuration
public class FileUploadController {

	@Value("${file.path}")
	private String UPLOAD_FOLDER;

	@RequestMapping("/upload")
	public ModelAndView showUpload() {
		return new ModelAndView("upload");
	}

	@PostMapping("/fileUpload")
	public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			return new ModelAndView("status", "message", "Please select a file and try again");
		}

		try {
			byte[] bytes = file.getBytes();
			String actualFileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf("."));
			String genericFileName = actualFileName + LocalTime.now().getNano() +"."+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1);
			Path path = Paths.get(UPLOAD_FOLDER + genericFileName);
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ModelAndView("status", "message", "File Uploaded sucessfully");
	}

}
