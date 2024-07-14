package org.nehlsen.venueshot.pictureservice.Controller;

import org.nehlsen.venueshot.pictureservice.Entity.PictureReference;
import org.nehlsen.venueshot.pictureservice.picture.PictureNotFound;
import org.nehlsen.venueshot.pictureservice.picture.PictureStorage;
import org.nehlsen.venueshot.pictureservice.storage.StorageFileNotFoundException;
import org.nehlsen.venueshot.pictureservice.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PictureController {
    private final PictureStorage pictureStorage;
    private final StorageService storageService;

    public PictureController(PictureStorage pictureStorage, StorageService storageService) {
        this.pictureStorage = pictureStorage;
        this.storageService = storageService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public PictureReference uploadPicture(@RequestParam("file") MultipartFile file) {
        return pictureStorage.store(file);
    }

    @GetMapping("/{pictureId}")
    public ResponseEntity<Resource> downloadPicture(@PathVariable String pictureId) {

        final PictureReference pictureReference = pictureStorage.load(pictureId);
        Resource file = storageService.loadAsResource(pictureReference.getLocalFilename());

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, pictureReference.getMimeType())
                .body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PictureNotFound.class)
    public ResponseEntity<?> handlePictureNotFound(PictureNotFound exc) {
        return ResponseEntity.notFound().build();
    }
}
