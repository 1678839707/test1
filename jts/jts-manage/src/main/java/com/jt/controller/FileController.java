package com.jt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.EasyUIImage;


@Controller
public class FileController {
	/**
	 * 文件上传：
	 *    1.页面form表单 enctype="multipart/form-data"
	 *    2.接收参数时使用MultipartFile
	 *    3.利用fileImage.tranferTo(dest)上传
	 * 步骤：
	 *    1.准备文件存储目录
	 *    2.准备文件名称
	 *    3.实现文件上传
	 */
//	@RequestMapping("file")
//	public String uploadFile(MultipartFile fileImage) {
//		String url = "E:/image/";
//		//创建文件夹
//		File file = new File(url);
//		if(!file.exists()) {
//			//如果文件夹不存在，就创建一个新的
//			file.mkdirs();
//		}
//		try {
//			String name = UUID.randomUUID().toString();
//			String original = fileImage.getOriginalFilename();
//			String type = 
//			    original.substring(original.indexOf("."));			
//			String filename = name+type;
//			String f = url+filename;
//			fileImage.transferTo(new File(f));
//			
//		
//		} catch (Exception e) {			
//			e.printStackTrace();
//		}		
//		return "redirect:file.jsp";
//	}
	@Autowired
	private FileService fileService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public EasyUIImage uploadFile(MultipartFile uploadFile) {
		
		return fileService.updateFile(uploadFile);
	}

}
