package com.example.AxmCarService.repository.implementation;

import com.example.AxmCarService.domain.Workshop;
import com.example.AxmCarService.exception.ApiException;
import com.example.AxmCarService.repository.WorkshopRepository;
import com.example.AxmCarService.rmapper.WorkshopMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.AxmCarService.query.WorkshopQuery.*;

@Repository
@RequiredArgsConstructor
public class WorkshopRepositoryImpl implements WorkshopRepository {

    private final NamedParameterJdbcTemplate jdbc;
    /*
    public List<Workshop> getAllWorkshops() {
        List<Workshop> workshops = new ArrayList<>();
        workshops.add(new Workshop(1L, "Workshop Mechanic", "mechanical repairs"));
        workshops.add(new Workshop(2L, "Workshop Electrician", "electrical repairs"));
        workshops.add(new Workshop(3L, "Workshop Bodywork", "bodywork repairs"));
        workshops.add(new Workshop(4L, "Workshop Paint", "paint repairs"));
        return workshops;

    }*/


    public List<Workshop> getAllWorkshops(){

        try{
           return  jdbc.query(SELECT_ALL_WORKSHOPS_QUERY, new WorkshopMapper());
        }catch (DataAccessException dae){
            dae.getStackTrace();
            throw new ApiException(dae.getMessage());
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
            throw new ApiException(ex.getMessage());
        }

    }

    @Override
    public Workshop create(Workshop workshop) {
        KeyHolder kh =new GeneratedKeyHolder();
        try{
        jdbc.update(INSERT_NEW_WORKSHOP_QUERY, getParams(workshop),kh);}
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        workshop.setId(kh.getKey().longValue());

         return workshop;

    }

    @Override
    public void deleteWorkshop(Long id) {
        try{
         int  affectedRows =  jdbc.update(DELETE_WORKSHOP_QUERY, Map.of("id",id));
            System.out.println(affectedRows);
        }catch (DataAccessException dae){
            dae.printStackTrace();
        }catch (Exception ex)
        {
            throw new ApiException("Error to delete workshop");
        }
    }

    @Override
    public Workshop edit(Workshop newWorkshop) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id",newWorkshop.getId())
                .addValue("name",newWorkshop.getName())
                .addValue("description",newWorkshop.getDescription());


      try{
          jdbc.update(EDIT_WORKSHOP_QUERY, params);
      }catch (DataAccessException dae){
          dae.printStackTrace();
          throw new ApiException(dae.getStackTrace().toString());
      }catch (Exception ex){
          ex.printStackTrace();
      }


      return newWorkshop;

    }


    private SqlParameterSource getParams(Workshop workshop ) {

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name",workshop.getName())
                .addValue("description",workshop.getDescription());
        return params;
    }


}
