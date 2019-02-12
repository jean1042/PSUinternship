package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.portlet.ModelAndView;
import sun.misc.Request;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping(name="/uploadFile", method=RequestMethod.GET)
    public String uploadFile(Model model){
        return "index";
    }


    @RequestMapping(name="/uploadFilepost", method=RequestMethod.POST)
    public String uploadFile(HttpServletRequest request, @RequestParam("files") MultipartFile files
            , Model model){
        String savePath = request.getRealPath("folder"); // 파일이 저장될 프로젝트 안의 폴더 경로
        System.out.println("savepath"+savePath);
        String originalFilename = files.getOriginalFilename(); // fileName.jpg
        String onlyFileName = originalFilename.substring(0, originalFilename.indexOf(".")); // fileName
        String extension = originalFilename.substring(originalFilename.indexOf(".")); // .jpg

        String rename = onlyFileName + "_" + getCurrentDayTime() + extension; // fileName_20150721-14-07-50.jpg
        String fullPath = savePath + "\\" + rename;

        if (!files.isEmpty()) {
            try {
                byte[] bytes = files.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
                stream.write(bytes);
                stream.close();
                model.addAttribute("resultMsg", "파일을 업로드 성공!");
            } catch (Exception e) {
                model.addAttribute("resultMsg", "파일을 업로드하는 데에 실패했습니다.");
            }
        } else {
            model.addAttribute("resultMsg", "업로드할 파일을 선택해주시기 바랍니다.");
        }

        return "index";
    }

    public String getCurrentDayTime(){
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMdd-HH-mm-ss", Locale.KOREA);
        return dayTime.format(new Date(time));
    }


}

