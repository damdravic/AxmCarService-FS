package com.example.AxmCarService.repository.implementation;

import com.example.AxmCarService.domain.RepairOrder;
import com.example.AxmCarService.exception.ApiException;
import com.example.AxmCarService.repository.RepairOrderRepository;
import com.example.AxmCarService.rmapper.RoRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.AxmCarService.query.RepairOrderQuery.INSERT_ORDER_QUERY;
import static com.example.AxmCarService.query.RepairOrderQuery.SELECT_ALL_REPAIR_ORDERS;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RepairOrderRepositoryImpl implements RepairOrderRepository<RepairOrder> {

    private final NamedParameterJdbcTemplate jdbc;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    String currentDateTime = format.format(date);




    @Override
    public RepairOrder create(RepairOrder repairOrder) {
        log.info(String.valueOf(repairOrder.getEstimatedCompletionDate()));



        KeyHolder kh = new GeneratedKeyHolder();
        SqlParameterSource params = getSqlParameterSource(repairOrder);
        try {
            jdbc.update(INSERT_ORDER_QUERY, params, kh);
        } catch (BadSqlGrammarException exception) {
            throw new BadSqlGrammarException(exception.getSql(), exception.getSql(), exception.getSQLException());
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        //this are for object that is returned ,not for object that is added to db
        repairOrder.setOrderId(Objects.requireNonNull(kh.getKey()).longValue());
        repairOrder.setStatus("In Progress");
        repairOrder.setDateReceived(new Date());

        return repairOrder;
    }

    private SqlParameterSource getSqlParameterSource(RepairOrder repairOrder) {

        String estimatedDate = getEstimatedDate(repairOrder.getEstimatedCompletionDate());
        log.info("Estimated Date: "+ estimatedDate);

        return new MapSqlParameterSource()
                .addValue("workshopId", repairOrder.getWorkshopId())
                .addValue("vehicleId", repairOrder.getVehicleId())
                .addValue("technicianId", repairOrder.getTechnicianId())
                .addValue("description", repairOrder.getDescription())
                .addValue("receivedDate", new Date())
                .addValue("diagnosis", repairOrder.getDiagnosis())
                .addValue("status","In Progress")
                .addValue("estimatedCompletionDate", estimatedDate )
                .addValue("estimatedCost", repairOrder.getEstimatedCost())
                .addValue("totalCost", repairOrder.getTotalCost());
    }

    private String getEstimatedDate(Date estimatedCompletionDate) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat requiredFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date date = originalFormat.parse(String.valueOf(estimatedCompletionDate));
            log.info("Date: "+ requiredFormat.format(date));
            return requiredFormat.format(date);

        }catch (ParseException e){
            throw new ApiException("Invalid date format");
        }


    }

    @Override
    public List<RepairOrder> getAll() {
        List<RepairOrder> orders = jdbc.query(SELECT_ALL_REPAIR_ORDERS,new RoRowMapper());


        return orders;
    }
}
