package grepp.NBE5_6_2_Team03.domain.user.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Profile("local")
public class LocalStore implements FileStore {

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public String getFullPath(String fileName){
        return fileDir + fileName;
    }

    @Override
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException{
        if(multipartFile == null || multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }

    @Override
    public String getFileDir() {
        return fileDir;
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
