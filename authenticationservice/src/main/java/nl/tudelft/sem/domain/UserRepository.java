package nl.tudelft.sem.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

public interface UserRepository extends JpaRepository<UserDto, String> {

    /**
     * Override the default Save method for UserDto.
     * This is done to encrypt the password before saving the user instance.
     *
     * @param entity is the User to be saved
     * @param <S> is the primitive type of user
     * @return the saved non encrypted version of user
     */
    @Override
    default <S extends UserDto> S save(S entity) {
        UserDto encryptedUser =
            new UserDto(entity.getUsername(),
                BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt()));
        this.saveAndFlush(encryptedUser);
        return entity;
    }

}
