package kz.tezdet.lessons.validators;

import kz.tezdet.lessons.domains.UploadedFile;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FileValidator implements Validator{

    public boolean supports(Class<?> aClass) {
        return false;
    }

    public void validate(Object o, Errors errors) {
        UploadedFile file = (UploadedFile)o ;
        if(file.getFile().getSize() == 0){
            errors.rejectValue("file", "uploadForm.selectFile",
                    "Please select a file");
        }
    }
}
