package org.nehlsen.venueshot.pictureservice.picture;

import org.nehlsen.venueshot.pictureservice.Entity.PictureReference;
import org.nehlsen.venueshot.pictureservice.Entity.PictureReferenceRepository;
import org.nehlsen.venueshot.pictureservice.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

@Service
public class PictureStorage {
    private final StorageService storageService;
    private final MimeTypeGuesser mimeTypeGuesser;
    private final PictureReferenceRepository referenceRepository;

    public PictureStorage(
            StorageService storageService, 
            MimeTypeGuesser mimeTypeGuesser,
            PictureReferenceRepository referenceRepository
    ) {
        this.storageService = storageService;
        this.mimeTypeGuesser = mimeTypeGuesser;
        this.referenceRepository = referenceRepository;
    }

    public PictureReference store(MultipartFile file) {
        final Path localFile = storageService.store(file);

        final PictureReference pictureReference = new PictureReference();
        pictureReference.setOriginalFilename(file.getOriginalFilename());
        pictureReference.setMimeType(mimeTypeGuesser.guess(file));
        pictureReference.setLocalFilename(localFile.getFileName().toString());
        referenceRepository.save(pictureReference);

        return pictureReference;
    }

    public PictureReference load(String pictureId) {
        return referenceRepository
                .findById(UUID.fromString(pictureId))
                .orElseThrow(PictureNotFound::new);
    }
}
