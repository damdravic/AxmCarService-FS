package com.example.AxmCarService.query;

public class TechnicianQuery {

    public static final String INSERT_TECHNICIAN_QUERY = "INSERT INTO technician " +
            "(technician_name," +
            "technician_user_id," +
            "technician_active," +
            "technician_workshop," +
            "technician_experience," +
            "technician_specialization)" +
            " VALUES(" +
            ":technicianName," +
            ":technicianUserId," +
            ":technicianActive," +
            ":technicianWorkshop," +
            ":technicianExperience," +
            ":technicianSpecialization)";
}
