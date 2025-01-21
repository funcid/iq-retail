package me.func.infrastructure.dao;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "email_data")
public class EmailData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String email;
} 