package za.co.kooker.microservices.imageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.kooker.microservices.imageservice.entity.Gallary;

@Repository
public interface GallaryRepository extends JpaRepository<Gallary, Integer> {

}
