package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

  private Boolean system = true;
  private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private final FuncionarioRepository funcionarioRepository;

  public RelatoriosService(FuncionarioRepository funcionarioRepository) {
    this.funcionarioRepository = funcionarioRepository;
  }

  public void inicial(Scanner scanner) {
    while(system) {
      System.out.println("Qual ação de cargo deseja executar");
      System.out.println("0 - Sair");
      System.out.println("1 - Buscar funcionário por nome");
      System.out.println("2 - Buscar funcionário por nome, salario e data de contratacao");
      System.out.println("3 - Buscar funcionário por data de contratacao");

      int action = scanner.nextInt();

      switch(action) {
        case 1:
          buscarFuncionarioPorNome(scanner);
          break;
        case 2:
          buscaFuncionarioNomeSalarioMaiorData(scanner);
          break;
        case 3:
          buscaFuncionarioDataContratacao(scanner);
          break;
        default:
          system = false;
          break;
      }
    }
  }

  private void buscarFuncionarioPorNome(Scanner scanner) {
    System.out.println("Qual nome deseja pesquisar?");

    String nome = scanner.next();
    List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);

    funcionarios.forEach(System.out::println);
  }

  private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
    System.out.println("Qual nome deseja pesquisar?");
    String nome = scanner.next();

    System.out.println("Qual data de contratação deseja pesquisar?");
    String data = scanner.next();
    LocalDate localDate = LocalDate.parse(data, format);

    System.out.println("Qual salario deseja pesquisar?");
    BigDecimal salario = scanner.nextBigDecimal();

    List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);

    funcionarios.forEach(System.out::println);
  }

  private void buscaFuncionarioDataContratacao(Scanner scanner) {
    System.out.println("Qual data de contratação deseja pesquisar?");
    String data = scanner.next();
    LocalDate localDate = LocalDate.parse(data, format);

    List<Funcionario> funcionarios = funcionarioRepository.findDataContratacaoMaior(localDate);

    funcionarios.forEach(System.out::println);
  }
}
