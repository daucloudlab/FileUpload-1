package kz.tezdet.lessons.controllers;

import kz.tezdet.lessons.domains.UploadedFile;
import kz.tezdet.lessons.validators.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@Controller
public class UploadController {

    @Autowired
    FileValidator fileValidator ;

    @RequestMapping(value = "/fileUploadForm", method = RequestMethod.GET)
    public ModelAndView getUploadForm(
            @ModelAttribute("uploadedFile") UploadedFile uploadedFile,
            BindingResult result) {
        return new ModelAndView("uploadForm");
    }

    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public ModelAndView fileUploaded(
            @ModelAttribute("uploadedFile") UploadedFile uploadedFile,
            BindingResult result) {

        MultipartFile file = uploadedFile.getFile();
        fileValidator.validate(uploadedFile, result);

        String fileName = file.getOriginalFilename();

        if (result.hasErrors()) {
            return new ModelAndView("uploadForm");
        }

        try {

            byte[] bytes = file.getBytes() ;


            File newFile = new File("/home/daulet/InstallFolder/tempFolder/" + fileName);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile)) ;
            stream.write(bytes) ;
            stream.flush() ;
            stream.close() ;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new ModelAndView("showFile", "message", fileName);
    }

}
