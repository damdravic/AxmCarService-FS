package com.example.AxmCarService.query;

public class WorkshopQuery {

    public static final String INSERT_NEW_WORKSHOP_QUERY ="INSERT INTO workshop (workshop_name,description) VALUES (:name,:description)";
    public static final String SELECT_ALL_WORKSHOPS_QUERY ="SELECT * FROM workshop";

   public static final String DELETE_WORKSHOP_QUERY = " DELETE FROM workshop WHERE workshop_id = :id" ;

   public static final String EDIT_WORKSHOP_QUERY = " UPDATE workshop SET workshop_name = :name ,description = :description WHERE workshop_id = :id" ;
}
