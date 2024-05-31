package org.megaproject.chatboot.Repository;

import org.megaproject.chatboot.Models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {
    Users findByEmail(String email);
}
