package team.ccpd.demo.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ccpd.demo.response.ResponseEntity;
import team.ccpd.demo.tool.PythonThread;

import java.io.File;

@Service
public class MainService {
    private ResponseEntity responseEntity;

    private Logger log= LoggerFactory.getLogger(MainService.class);

    private final static String UPLOAD_PATH="G:/upload/";

    public ResponseEntity upload(MultipartFile multipartFile) {
        String originalname = multipartFile.getOriginalFilename();
        String type = originalname.substring(originalname.lastIndexOf('.'));
        String currentTime=String.valueOf(System.currentTimeMillis());
        String fileName =  currentTime + type;
        System.out.println(fileName);
        File des = new File(UPLOAD_PATH + fileName);
        try {
            multipartFile.transferTo(des);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity(0, "系统错误，文件上传失败");
        }
        String lpn;
        //若要测试前端与服务器交互，请将此if-else块替换为注释部分
        if((lpn=PythonThread.getInstance().send(UPLOAD_PATH+fileName)).equals("false")){
            responseEntity=new ResponseEntity(0,"识别错误");
        }else {
            responseEntity = new ResponseEntity(1, lpn, fileName);
        }
        //前端服务器交互测试
        //responseEntity = new ResponseEntity(1, "测试", fileName);
        return responseEntity;
    }

    //getRuntime()启动python进程
   /* private String invokePython(String fileName){
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
    }*/
}
