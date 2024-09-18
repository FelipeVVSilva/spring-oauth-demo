package com.demo.oauth.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.oauth.entities.User;
import com.demo.oauth.projections.UserDetailsProjection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(nativeQuery = true, value = ""
			+ "SELECT \r\n"
			+ "    tb_user.email AS username,\r\n"
			+ "    tb_user.password,\r\n"
			+ "    tb_role.id AS roleId,\r\n"
			+ "    tb_role.authority\r\n"
			+ "FROM\r\n"
			+ "    tb_user\r\n"
			+ "INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id\r\n"
			+ "INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id\r\n"
			+ "WHERE\r\n"
			+ "    tb_user.email = :email ")
	List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
	
}
