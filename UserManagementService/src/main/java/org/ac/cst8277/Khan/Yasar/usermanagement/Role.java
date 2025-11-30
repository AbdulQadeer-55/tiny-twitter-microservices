package org.ac.cst8277.Khan.Yasar.usermanagement;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("roles")
public class Role {
    @Id
    private Integer id;
    private String name;
}