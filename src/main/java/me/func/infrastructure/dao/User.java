package me.func.infrastructure.dao;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    private Date dateOfBirth;
    
    private String password;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EmailData> emails;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PhoneData> phones;
} 