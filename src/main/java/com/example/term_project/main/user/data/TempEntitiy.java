package com.example.term_project.main.user.data;

import javax.persistence.*;

@Entity
@Table(name = "test_table")
public class TempEntitiy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
