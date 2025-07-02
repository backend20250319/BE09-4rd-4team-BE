package com.oliveyoung.admin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class StockLog {

    @Id
    @GeneratedValue
    private Long id;


}
