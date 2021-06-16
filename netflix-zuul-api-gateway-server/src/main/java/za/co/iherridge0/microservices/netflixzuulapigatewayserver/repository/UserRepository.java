package za.co.iherridge0.microservices.netflixzuulapigatewayserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.iherridge0.microservices.netflixzuulapigatewayserver.entity.Login;

@Repository
public interface UserRepository extends JpaRepository<Login, String> {

}
