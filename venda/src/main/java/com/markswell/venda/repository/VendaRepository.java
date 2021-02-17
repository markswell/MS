package com.markswell.venda.repository;

import com.markswell.venda.entity.Venda;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
}
