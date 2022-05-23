package br.com.alura.spring.data.repository;

import java.math.BigDecimal;

public interface FuncionarioProjecao {
  Integer getId();
  String getNome();
  BigDecimal getSalario();
}
