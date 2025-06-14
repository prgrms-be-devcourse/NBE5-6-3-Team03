package grepp.NBE5_6_2_Team03.domain.user.file;

import lombok.Getter;

@Getter
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;

    protected UploadFile(){}

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
