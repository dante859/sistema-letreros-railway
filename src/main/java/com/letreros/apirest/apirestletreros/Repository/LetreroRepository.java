package com.letreros.apirest.apirestletreros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letreros.apirest.apirestletreros.Entities.Letrero;

@Repository
public interface LetreroRepository extends JpaRepository<Letrero, Long> {

}
