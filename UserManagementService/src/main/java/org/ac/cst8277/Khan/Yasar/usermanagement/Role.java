package org.ac.cst8277.Khan.Yasar.usermanagement;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("roles") // Matches your DB table from Assignment 1
public class Role {
    @Id
    private Integer id;
    private String name;
}