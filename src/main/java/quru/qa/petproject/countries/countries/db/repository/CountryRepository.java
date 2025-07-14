package quru.qa.petproject.countries.countries.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quru.qa.petproject.countries.countries.db.entity.CountryEntity;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {

    Optional<CountryEntity> findByCode(String code);
}
