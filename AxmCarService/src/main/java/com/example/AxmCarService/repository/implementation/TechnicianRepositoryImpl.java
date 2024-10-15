package com.example.AxmCarService.repository.implementation;

import com.example.AxmCarService.domain.Technician;
import com.example.AxmCarService.exception.ApiException;
import com.example.AxmCarService.repository.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.AxmCarService.query.TechnicianQuery.INSERT_TECHNICIAN_QUERY;

@RequiredArgsConstructor
@Repository
public class TechnicianRepositoryImpl implements TechnicianRepository<Technician> {


    private final NamedParameterJdbcTemplate jdbc;



    @Override
    public Technician create(Technician data) {
        KeyHolder kh = new GeneratedKeyHolder();
        SqlParameterSource params = getSqlParameterSource(data);

       try{
           jdbc.update(INSERT_TECHNICIAN_QUERY, params, kh);
       }catch(Exception e){
              throw new ApiException(e.getMessage() + "Error in creating technician");
       }
       data.setTechnicianId(kh.getKey().longValue());
       return data;


    }

    private SqlParameterSource getSqlParameterSource(Technician data) {
        return new MapSqlParameterSource()
                .addValue("technicianId",data.getTechnicianId())
                .addValue("technicianSpecialization ", data.getTechnicianSpecialization())
                .addValue("technicianExperience",data.getTechnicianExperience())
                .addValue("technicianWorkshopId",data.getTechnicianWorkshopId())
                .addValue("technicianUserId",data.getTechnicianUserId());

    }

    @Override
    public Technician getTechnicianById(Long id) {
        return null;
    }

    @Override
    public List<Technician> getAllTechnicians() {
        return null;
    }
}
