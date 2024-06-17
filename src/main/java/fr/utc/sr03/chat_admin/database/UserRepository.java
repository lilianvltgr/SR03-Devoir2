package fr.utc.sr03.chat_admin.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import fr.utc.sr03.chat_admin.model.User;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Interface for managing {@link User} entities. This repository extends {@link JpaRepository}
 * and includes custom methods for finding, deleting, and querying users based on various attributes.
 */

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    Optional<User> findByUserId(long id);
    @Transactional
    Integer deleteByUserId(long id);
    Optional<User> findUserByMail (String mail);
    List<User> findAllByActive (boolean active);
    List<User> findAllByAdmin(boolean admin);
    Page<User> findByLastnameContainingIgnoreCase(String lastname, Pageable pageable);
    Page<User> findByActiveFalseAndLastnameContainingIgnoreCase(String lastname, Pageable pageable);
}