package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

  private Boolean system = true;
  private final FuncionarioRepository funcionarioRepository;
  private final CrudCargoService crudCargoService;
  private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

  public CrudFuncionarioService(
    FuncionarioRepository funcionarioRepository,
    CrudCargoService crudCargoService,
    UnidadeTrabalhoRepository unidadeTrabalhoRepository
  ) {
    this.funcionarioRepository = funcionarioRepository;
    this.crudCargoService = crudCargoService;
    this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
  }

  public void inicial(Scanner scanner) {
    while(system) {
      System.out.println("Qual ação de funcionario deseja executar");
      System.out.println("0 - Sair");
      System.out.println("1 - Salvar");
      System.out.println("2 - Atualizar");
      System.out.println("3 - Visualizar");
      System.out.println("4 - Deletar");

      int action = scanner.nextInt();

      switch(action) {
        case 1:
          salvar(scanner);
          break;
        case 2:
          atualizar(scanner);
          break;
        case 3:
          visualizar();
          break;
        case 4:
          deletar(scanner);
          break;
        default:
          system = false;
          break;
      }
    }
  }

  private void salvar(Scanner scanner) {
    System.out.println("Nome do funcionario");
    String nome = scanner.next();

    System.out.println("CPF do funcionario");
    String cpf = scanner.next();

    System.out.println("Salário do funcionario");
    BigDecimal salario = new BigDecimal(scanner.next());

    List<UnidadeTrabalho> unidadeTrabalhos = unidades(scanner);

    Optional<Cargo> cargo = crudCargoService.buscarPorId(scanner);

    if (!cargo.isPresent()) {
      System.out.println("Cargo não encontrado!");
      return;
    }
  
    Funcionario funcionario = new Funcionario();

    funcionario.setNome(nome);
    funcionario.setCpf(cpf);
    funcionario.setSalario(salario);
    funcionario.setCargo(cargo.get());
    funcionario.setUnidadeTrabalhos(unidadeTrabalhos);
    funcionarioRepository.save(funcionario);


    System.out.println("Funcionario salvo!");
  }

  private List<UnidadeTrabalho> unidades(Scanner scanner) {
    Boolean isTrue = true;
    List<UnidadeTrabalho> unidadesTrabalhos = new ArrayList<>();

    while (isTrue) {
      System.out.println("Digite o uniadeId (Para sair digite0)");
      Integer unidadeId = scanner.nextInt();

      if (unidadeId != 0) {
        Optional<UnidadeTrabalho> unidadeTrabalho = unidadeTrabalhoRepository.findById(unidadeId);
        unidadesTrabalhos.add(unidadeTrabalho.get());
      } else {
        isTrue = false;
      }
    }

    return unidadesTrabalhos;
  }

  private void atualizar(Scanner scanner) {
    System.out.println("Id:");
    int id = scanner.nextInt();

    System.out.println("Nome do funcionario");
    String nome = scanner.next();

    System.out.println("CPF do funcionario");
    String cpf = scanner.next();

    System.out.println("Salário do funcionario");
    BigDecimal salario = new BigDecimal(scanner.next());
    
    Optional<Cargo> cargo = crudCargoService.buscarPorId(scanner);

    if (!cargo.isPresent()) {
      System.out.println("Cargo não encontrado!");
      return;
    }
  
    Funcionario funcionario = new Funcionario();

    funcionario.setId(id);
    funcionario.setNome(nome);
    funcionario.setCpf(cpf);
    funcionario.setSalario(salario);
    funcionario.setCargo(cargo.get());
    funcionarioRepository.save(funcionario);

    System.out.println("Funcionario atualizado");
  }

  private void visualizar() {
    Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
    funcionarios.forEach(funcionario -> System.out.println(funcionario));
  }

  private void deletar(Scanner scanner) {
    System.out.println("Id:");
    int id = scanner.nextInt();

    funcionarioRepository.deleteById(id);

    System.out.println("Deletado!");
  }
}
