package grepp.NBE5_6_2_Team03.domain.user.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStore {

    String getFullPath(String storeFileName);
    UploadFile storeFile(MultipartFile multipartFile) throws IOException;
    String getFileDir();
}
