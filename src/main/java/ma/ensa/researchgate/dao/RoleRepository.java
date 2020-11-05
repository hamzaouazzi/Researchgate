package ma.ensa.researchgate.dao;

import ma.ensa.researchgate.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long>{
    public Role findByRole(String role);
}
