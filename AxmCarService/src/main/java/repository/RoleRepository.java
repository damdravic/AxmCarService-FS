package repository;

import domain.Role;

import java.util.Collection;

public interface RoleRepository<T extends Role>{

    /* Basic CRUD operations */

    T create (T data);
    Collection<T> listAll();
    T update(T data);
    T get(int id);
    Boolean delete(int id);

    /* more complex operations */
    void addRoleToUser(Long userId,String roleName);


}
