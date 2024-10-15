package com.example.AxmCarService.query;

public class TechnicianQuery {

    public static final String INSERT_TECHNICIAN_QUERY = "INSERT INTO techinician " +
            "(technician_id,technician_specialization,technician_experience,technician_workshop_id,technician_user_id)" +
            " VALUES(:technicianId,:technicianSpecialization,:technicianExperience,:technicianWorkshopId,:technicianUserId)";
}
