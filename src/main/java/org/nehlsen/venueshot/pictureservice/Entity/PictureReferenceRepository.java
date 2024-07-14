package org.nehlsen.venueshot.pictureservice.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PictureReferenceRepository extends JpaRepository<PictureReference, UUID> {
}