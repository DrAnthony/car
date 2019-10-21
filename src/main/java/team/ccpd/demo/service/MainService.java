package team.ccpd.demo.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ccpd.demo.response.ResponseEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class MainService {
    private ResponseEntity responseEntity;

    private final static String UPLOAD_PATH="G:\\upload\\";

    public ResponseEntity upload(MultipartFile multipartFile) {

        String originalname = multipartFile.getOriginalFilename();
        String type = originalname.substring(originalname.lastIndexOf('.'));
        String currentTime=String.valueOf(System.currentTimeMillis());
        String fileName =  currentTime + type;
        System.out.println(fileName);
        //String outputName=currentTime+"_out"+type;
        File des = new File(UPLOAD_PATH + fileName);
        try {
            multipartFile.transferTo(des);
            //responseEntity=new ResponseEntity(1,outputName);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity(0, "系统错误，文件上传失败");
        }
        //responseEntity=new ResponseEntity(1,invokePython(UPLOAD_PATH+fileName),fileName);
        responseEntity=new ResponseEntity(1,"测试",fileName);
        return responseEntity;
    }

    //调用python
    private String invokePython(String fileName){
        Process process;
        System.out.println(fileName);
        String command="python G:\\StudyInfo\\LicensePlateNum\\CCPD-master\\CCPD-master\\rpnet\\demo_f.py -i "+fileName;
        String output=null;
        try {
            process=Runtime.getRuntime().exec(command);
            BufferedReader reader=new BufferedReader(new InputStreamReader(process.getInputStream(),"gb2312"));
            output=reader.readLine();
            //output=new String(output.getBytes("GBK"),"GBK");
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
        System.out.println(output);
        return output;
    }
}
