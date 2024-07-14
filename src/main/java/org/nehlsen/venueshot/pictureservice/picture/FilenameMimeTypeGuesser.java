package org.nehlsen.venueshot.pictureservice.picture;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;

@Component
public class FilenameMimeTypeGuesser implements MimeTypeGuesser {
    @Override
    public String guess(MultipartFile file) {
        return URLConnection.guessContentTypeFromName(file.getOriginalFilename());
    }
}
