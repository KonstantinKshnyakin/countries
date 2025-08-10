package quru.qa.petproject.countries.countries.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quru.qa.petproject.countries.countries.db.entity.CountryEntity;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {

    default Optional<CountryEntity> findById(String id) {
        return findById(UUID.fromString(id));
    }
}
