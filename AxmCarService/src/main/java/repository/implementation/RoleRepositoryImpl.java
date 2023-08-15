package repository.implementation;

import com.example.AxmCarService.exception.ApiException;
import domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import repository.RoleRepository;
import roemapper.RoleRowMapper;

import java.util.Collection;
import java.util.Map;

import static com.example.AxmCarService.query.RoleQuery.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository {


    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection listAll() {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Role get(int id) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        try{
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("roleName",roleName),new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY,Map.of("userId",userId,"roleId",role.getRoleId()));


        }catch(EmptyResultDataAccessException exception){
   throw new ApiException("   Role not exist");
        }catch (Exception exception){
            throw new ApiException(" ceva e e in regula");
        }

    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
