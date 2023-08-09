package repository.implementation;

import com.example.AxmCarService.exception.ApiException;
import domain.Role;
import domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import repository.RoleRepository;
import repository.UserRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.example.AxmCarService.enumeration.RoleType.ROLE_USER;
import static com.example.AxmCarService.enumeration.VerificationType.ACCOUNT;
import static com.example.AxmCarService.query.UserQuery.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private  final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
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
            roleRepository.addRoleToUser(user.getUserId(),ROLE_USER.name());

            //Send verification URL
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            //SAve URL in verification table
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY,Map.of("userId",user.getUserId(),"url",verificationUrl));
            //Send email to user with verification url
            //emailService.sendVerificationUrl(user.getFirstName(),user.getEmail(),verificationUrl,ACCOUNT.getType());
            user.setEnabled(false);
            user.setNotLocked(true);
            return user;

        }catch (EmptyResultDataAccessException exception){
            throw new ApiException("No role found by name "+ ROLE_USER.name());
        }
        catch (Exception exception){
            throw new ApiException("AN error occurred.Please try again.");

        }
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

        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("lastName", passwordEncoder.encode(user.getLastName()));

    }
    private String getVerificationUrl(String key, String type) {

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify" + type + "/" + key).toUriString();


    }

}
