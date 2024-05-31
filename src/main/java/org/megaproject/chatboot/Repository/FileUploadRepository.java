package org.megaproject.chatboot.Repository;

import org.megaproject.chatboot.Models.Files;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends MongoRepository<Files, String> {

}
