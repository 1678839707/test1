package com.jt.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jt.service.FileService;
import com.jt.vo.EasyUIImage;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
	
	@Value("${image.localDirPath}")
	private String localDirPath; // "E:/image/";
	@Value("${image.localUrlPath}")
	private String localUrlPath; // "http://image.jt.com/";
	
	@Override
	public EasyUIImage updateFile(MultipartFile uploadFile) {		
		EasyUIImage easyUIImage = new EasyUIImage();
		//1.校验文件
		//  1.1 .检查文件类型
		String originalName = uploadFile.getOriginalFilename();
		originalName = originalName.toLowerCase();
		//x.jpg,xxx.png,xxx.gif (^.+\\.(jpg|png|gif)$) .JPG 
		if(!originalName.matches("^.+\\.(jpg|png|gif)$")) {			
			easyUIImage.setError(1);
			return easyUIImage;
		}		
		//  1.2.检查文件是否合法（判断文件是否有宽高属性）	
		int height;
		int width;
		try {
			BufferedImage bi = 
					ImageIO.read(uploadFile.getInputStream());
			height = bi.getHeight();
			width = bi.getWidth();
			if(height==0||width==0) {
				easyUIImage.setError(1);
				return easyUIImage;
			}			
		} catch (IOException e) {
			e.printStackTrace();
			easyUIImage.setError(1);
			return easyUIImage;
		}		
		System.out.println("文件校验完成！");
		//2.创建文件夹
		String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		File dateDir = new File(localDirPath + date);
		if(!dateDir.exists()) {
			dateDir.mkdirs();
		}
		//3.重新生成一个文件名 xxx.jpg  2020/12/01/xxx.jpg
		String fileName = UUID.randomUUID().toString();
		String fileType = originalName.substring(originalName.indexOf("."));
		//生成上传文件的完整路径
		String realName = localDirPath + date +"/"+ fileName + fileType;
		System.out.println(realName);
		//4.把文件上传到服务器上
		try {
			uploadFile.transferTo(new File(realName));
			easyUIImage.setHeight(height)
					   .setWidth(width)
					   .setUrl(localUrlPath+date +"/"+ fileName + fileType);
		} catch (Exception e) {			
			e.printStackTrace();
			easyUIImage.setError(1);
			return easyUIImage; 
		}
		return easyUIImage;
	}
}
