package repository.implementation;

import com.example.AxmCarService.exception.ApiException;
import domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import repository.UserRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private static final String COUNT_EMAIL_QUERY = "" ;
    private static final String INSERT_USER_QUERY = "";
    public final NamedParameterJdbcTemplate jdbc;

    @Override
    public User create(User user) {

        //Check if email is unique
        if(checkUserEmail(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email exist" );
        //save new user
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameter = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY,parameter,holder);
            user.setUserId(Objects.requireNonNull(holder.getKey()).longValue());
        }catch (EmptyResultDataAccessException exception){}
        catch (Exception exception){

        }








        return user; //this is not correct
    }



    @Override
    public Collection listAll() {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    private Integer checkUserEmail(String email) {
        return jdbc.queryForObject(COUNT_EMAIL_QUERY, Map.of("email",email),Integer.class );
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return null;
    }
}
