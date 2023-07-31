package com.web.relocation.repository;

import com.web.relocation.dto.account.LoginDto;
import com.web.relocation.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<UserEntity, String>, UserRepository {
    UserEntity findByUserIdAndPassword(String userId, String password);

    Optional<UserEntity> findByUserId(String userId);

    List<UserEntity> findAll(Specification<UserEntity> specification, Sort sort);

    @Modifying
    @Query(
        "Update UserEntity Set userLoginIp = :#{#login.userLoginIp}, userLoginDate = :#{#login.userLoginDate} " +
        "Where userNo = :#{#login.userNo}")
    int updateLoginInfo(LoginDto login);
}
