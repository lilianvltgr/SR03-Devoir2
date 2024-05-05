package fr.utc.sr03.chat_admin.database;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.utc.sr03.chat_admin.model.User;

import java.util.Optional;
/*
Classe repository qui contient toutes les méthodes propres à la base de données choisies
 */
// Utilisez l'interface, pas la classe
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    Optional<User> findByUserId (long id);
    boolean deleteByUserId(long id);

    // ajouter toutes les méthodes nécessaires qui requièrent un échange avec la bdd

}