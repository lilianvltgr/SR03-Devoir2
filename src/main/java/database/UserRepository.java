package database;

import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.JpaRepository;
import model.User;

import java.util.Optional;
/*
Classe repository qui contient toutes les méthodes propres à la base de données choisies
 */
// Utilisez l'interface, pas la classe
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    Optional<User> findByUserId (Integer id);
    // ajouter toutes les méthodes nécessaires qui requièrent un échange avec la bdd

}