package za.co.kooker.microservice.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.kooker.microservice.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}