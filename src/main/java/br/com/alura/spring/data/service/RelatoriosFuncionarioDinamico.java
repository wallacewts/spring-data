package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.SpecificationFuncionario;

@Service
public class RelatoriosFuncionarioDinamico {

  private Boolean system = true;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private final FuncionarioRepository funcionarioRepository;

  public RelatoriosFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
    this.funcionarioRepository = funcionarioRepository;
  }

  public void inicial(Scanner scanner) {
    while(system) {
      System.out.println("Qual ação de cargo deseja executar");
      System.out.println("0 - Sair");
      System.out.println("1 - Buscar funcionário por nome(like)");

      int action = scanner.nextInt();

      switch(action) {
        case 1:
          buscarFuncionarioPorNome(scanner);
          break;
        default:
          system = false;
          break;
      }
    }
  }

  public void buscarFuncionarioPorNome(Scanner scanner) {
    System.out.println("Digite o nome");
    String nome = scanner.next();

    if (nome.equalsIgnoreCase("NULL")) {
      nome = null;
    }
    
    System.out.println("Digite o cpf");
    String cpf = scanner.next();

    if (cpf.equalsIgnoreCase("NULL")) {
      cpf = null;
    }
    
    System.out.println("Digite o salario");
    BigDecimal salario = scanner.nextBigDecimal();

    if (salario.intValue() == 0) {
      salario = null;
    }
    
    System.out.println("Digite a data de contratacao");
    String data = scanner.next();
    LocalDate dataContratacao;

    if (data.equalsIgnoreCase("NULL")) {
      dataContratacao = null;
    } else {
      dataContratacao = LocalDate.parse(data, formatter);
    }

    List<Funcionario> funcionarios = funcionarioRepository.findAll(Specification.where(
      SpecificationFuncionario.nome(nome)
        .or(SpecificationFuncionario.cpf(cpf))
        .or(SpecificationFuncionario.salario(salario))
        .or(SpecificationFuncionario.dataContratacao(dataContratacao))
    ));

    funcionarios.forEach(System.out::println);
  }
}
