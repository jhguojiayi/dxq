package cn.edu.zucc.controller;


import cn.edu.zucc.common.R;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.edu.zucc.common.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/files")
public class FileListController {

    private static final Logger log = LoggerFactory.getLogger(FileListController.class);

    @PostMapping("/upload")
    @ResponseBody
    public R<Map<String,String>> uoload(@RequestParam("file") MultipartFile file) throws IOException{
        log.info("[文件类型]-[{}]",file.getContentType());
        log.info("[文件名称]-[{}]",file.getOriginalFilename());
        log.info("[文件大小]-[{}]",file.getSize());
        //文件上传的目录
        file.transferTo(new File("C:\\Users\\58363\\Desktop\\2019短学期\\FileUpload"+file.getOriginalFilename()));
        Map<String, String> result = new HashMap<>(16);
        result.put("contentType",file.getContentType());
        result.put("fileName",file.getOriginalFilename());
        result.put("fileSize",file.getSize()+"");
        return  R.data(result);

        //Jws<Claims> jwt = Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);

    }

    @PostMapping("/getFileList")
    @ResponseBody
    public List<String> getFileList(){
        File file  = new File("C:\\Users\\58363\\Desktop\\2019短学期");
        List<String> list = new ArrayList<String>();
        for(int i = 0; i<file.list().length; i++ ){
            list.add(file.list()[i]);
        }
        return list;
    }

    @PostMapping("/deleteFileByName")
    @ResponseBody
    public R<String> deleteFileByName(){
        File file = new File("C:\\Users\\58363\\Desktop\\2019短学期\\FileUpload");
        if(!file.exists()){
            return R.fail("文件不存在");
        }else {
            file.delete();
            return R.success("删除成功");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/downloadfile/{filename}",method = RequestMethod.GET)
    public R<String> downloadfile(@PathVariable String filename) throws IOException {
        System.out.println(filename);
        String url1 ="C:\\Users\\58363\\Desktop\\2019短学期\\"+filename;// 源文件路径
        System.out.println(url1);
        String url2 = "C:\\Users\\58363\\Desktop\\2019短学期\\download\\"+filename;       // 复制到目标路
        FileInputStream in = new FileInputStream(new File(url1));
        FileOutputStream out = new FileOutputStream(new File(url2));
        byte[] buff = new byte[512];
        int n = 0;
//        System.out.println("复制文件：" + "\n" + "源路径：" + url1 + "\n" + "目标路径：" + url2);
        while ((n = in.read(buff)) != -1) {
            out.write(buff, 0, n);
        }
        out.flush();
        in.close();
        out.close();
        return R.success("已成功下载"+filename);
    }


}
