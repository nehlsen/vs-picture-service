package org.nehlsen.venueshot.pictureservice.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    Path store(MultipartFile file);

    Resource loadAsResource(String filename);
}
