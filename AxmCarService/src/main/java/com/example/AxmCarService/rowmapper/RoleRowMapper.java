package com.example.AxmCarService.rowmapper;

import com.example.AxmCarService.domain.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Role.builder()
                .roleId(rs.getLong("role_id"))
                .roleName(rs.getString("role_name"))
                .permission(rs.getString("permission"))
                .build();
    }
}
