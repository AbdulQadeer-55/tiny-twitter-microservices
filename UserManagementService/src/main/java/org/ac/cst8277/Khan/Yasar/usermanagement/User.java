package org.ac.cst8277.Khan.Yasar.usermanagement;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("users")
public class User {
    @Id
    private Long id;
    private String username;
    private String email;
}