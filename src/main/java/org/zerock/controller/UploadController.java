package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttatchFileDTO;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j2
public class UploadController { // 첨부파일 업로드 관리(get, post 방식)
	
	// 중복파일 방지 : 년/월/일 폴더생성
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	// 섬네일 관리
	// 1. 이미지 파일인지 판단 -> 2. 이미지 파일이면 섬네일 생성
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");
		} catch (IOException e) {
			log.error("");
			e.printStackTrace();
		}
		return false;
	}

	// M1. form 태그를 이용한 파일업로드
	// form 페이지 열기
	@GetMapping("/uploadForm") // http://localhost:80/uploadForm
	public void uploadForm() {
		log.info("UploadController.uploadForm() 메서드 실행....");
	}

	// 파일업로드 -> 업로드된 파일의 저장(transferTo())
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		String uploadFolder = "D:\\upload";

		for (MultipartFile multipartFile : uploadFile) {
			log.info("------------------------");
			log.info("업로드 파일명 : " + multipartFile.getOriginalFilename());
			log.info("업로드 파일크기 : " + multipartFile.getSize());

			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

			try {
				multipartFile.transferTo(saveFile);
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
				// e.printStackTrace();
			}
		} // --for()

	}

	// M2. Ajax를 이용한 파일업로드 // http://localhost:80/uploadAjax
	@GetMapping("/uploadAjax") // http://192.168.111.104/uploadAjax
	public void uploadAjax() {
		log.info("uploadAjax 실행.......");
	}

	//ver_AttatchFileDTO 사용안함
	/*	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("uploadAjaxPost 실행......");
		// make Folder
		String uploadFolder = "D:\\upload";
		File uploadPath = new File(uploadFolder, getFolder());
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		log.info("업로드 경로 : " + uploadPath);
		log.info("파일개수 : " + uploadFile.length); // 파일개수 : 0

		for (MultipartFile multipartFile : uploadFile) {
			log.info("--------------------------");
			log.info("원본파일명:" + multipartFile.getOriginalFilename());
			log.info("파일크기" + multipartFile.getSize());

			String fileName = multipartFile.getOriginalFilename();
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			log.info("저장파일명 : " + fileName);

			UUID uuid = UUID.randomUUID(); // 중복방지를 위한 임의의 값 생성
			fileName = uuid.toString() + "_" + fileName;
			
			try {
				File saveFile = new File(uploadPath, fileName);
				multipartFile.transferTo(saveFile);
				if(checkImageType(saveFile)) { //이미지파일이라면 섬네일 제작
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+fileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} // --for()

	} // --uploadAjaxPost() */
	
	//ver_AttatchFileDTO 사용
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttatchFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("uploadAjaxPost 실행......");
		
		List<AttatchFileDTO> list = new ArrayList<>();
		// make Folder
		String uploadFolder = "D:\\upload";
		String uploadFolderPath = getFolder();
		
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		for (MultipartFile mpf : uploadFile) {
			log.info("--------------------------");
			log.info("원본파일명:" + mpf.getOriginalFilename());
			log.info("파일크기" + mpf.getSize());
			
			AttatchFileDTO attachDTO = new AttatchFileDTO();
			String fileName = mpf.getOriginalFilename();
			//**for IE
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			log.info("저장파일명 : " + fileName);
			attachDTO.setFileName(fileName);

			UUID uuid = UUID.randomUUID(); // 중복방지를 위한 임의의 값 생성
			fileName = uuid.toString() + "_" + fileName;
			
			try {
				File saveFile = new File(uploadPath, fileName);
				mpf.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				if(checkImageType(saveFile)) { //이미지파일이라면 섬네일 제작
					attachDTO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+fileName));
					Thumbnailator.createThumbnail(mpf.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}//--if()
				//add to list
				list.add(attachDTO);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} // --for()
		return new ResponseEntity<>(list, HttpStatus.OK);
	} // --uploadAjaxPost()
	
	//섬네일 불러오기(파일이름을 받아 이미지 데이터를 전송한다.)
	@GetMapping("/display") // http://localhost:80/display?fileName=2024/08/30/cat.jpg
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("파일명:"+ fileName);
		File file = new File("D:\\upload\\"+ fileName);
		log.info("전체 파일경로 : "+file);
		
		ResponseEntity<byte[]> result = null;
				
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	//첨부파일 다운로드
	/*	@GetMapping(value = "/download", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE}) //http://localhost:80/download?fileName=2024/08/30/cat.jpg
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName){
		
		log.info("다운로드 파일 : " + fileName); //다운로드 파일 : 0603.txt
		
		Resource resource = new FileSystemResource("D:\\upload\\"+fileName);
		log.info("다운로드 파일 resource: " + resource);
		
	    String resourceName = resource.getFilename();
	    HttpHeaders header = new HttpHeaders();
	    
	    try {
			header.add("Content-Disposition", "attachment; filename="+new String(resourceName.getBytes("UTF-8"), "ISO-8859-1"));
			//content-disposition: attachment; filename=0603.txt
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
				
		return new ResponseEntity<>(resource, header, HttpStatus.OK);
	} */
	
	//첨부파일 다운로드 - IE 서비스 포함(User-Agent 정보 수집)
	@GetMapping(value = "/download", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		
		Resource resource = new FileSystemResource("D:\\upload\\"+fileName);
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		//remove uuid
		String resourceOriginName = resourceName.substring(resourceName.indexOf("_")+1);
		HttpHeaders headers = new HttpHeaders();
		
		String downloadName = null;
		try {
			if(userAgent.contains("Trident")) {
				log.info("브라우저 : IE browser");
				downloadName = URLEncoder.encode(resourceOriginName, "UTF-8").replace("\\+", "");
			}else if(userAgent.contains("Edge")) {
				log.info("브라우저 : Edge browser");
				downloadName = URLEncoder.encode(resourceOriginName, "UTF-8");
				log.info("Edge 다운로드 파일명:"+downloadName);
			}else {
				log.info("브라우저 : Chrome browser");
				downloadName = new String(resourceOriginName.getBytes("UTF-8"),"ISO-8859-1");
			}
			headers.add("Content-Disposition", "attachment; filename="+downloadName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	//업로드된 첨부파일 삭제 : fileName, type 받아서 처리, post
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		
		log.info("삭제할 파일/타입 : "+ fileName+"/"+type);
		File file;
		String result = "";
		try {
			file = new File("D:\\upload\\"+URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			result = "deleted file";
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				log.info("원본이미지파일명 : "+largeFileName);
				file = new File(largeFileName);
				file.delete();
				result="deleted image file";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	
	
	
	
	



}
