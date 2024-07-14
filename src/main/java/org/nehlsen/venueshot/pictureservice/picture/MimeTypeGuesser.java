package org.nehlsen.venueshot.pictureservice.picture;

import org.springframework.web.multipart.MultipartFile;

public interface MimeTypeGuesser {
    String guess(MultipartFile file);
}
