package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.service.CrudCargoService;
import br.com.alura.spring.data.service.CrudFuncionarioService;
import br.com.alura.spring.data.service.CrudUnidadeTrabalho;
import br.com.alura.spring.data.service.RelatoriosService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private Boolean system = true;

	private final CrudCargoService crudCargoService;
	private final CrudFuncionarioService crudFuncionarioService;
	private final CrudUnidadeTrabalho crudUnidadeTrabalho;
	private final RelatoriosService relatoriosService;

	public SpringDataApplication(
		CrudCargoService crudCargoService,
		CrudFuncionarioService crudFuncionarioService,
		CrudUnidadeTrabalho crudUnidadeTrabalho,
		RelatoriosService relatoriosService
	) {
		this.crudCargoService = crudCargoService;
		this.crudFuncionarioService = crudFuncionarioService;
		this.crudUnidadeTrabalho = crudUnidadeTrabalho;
		this.relatoriosService = relatoriosService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while(system) {
			System.out.println("Qual acao você quer executar");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Funcionário");
			System.out.println("3 - Unidade Trabalho");
			System.out.println("4 - Relatorios");

			int action = scanner.nextInt();

      switch(action) {
        case 1:
					crudCargoService.inicial(scanner);
          break;
        case 2:
				crudFuncionarioService.inicial(scanner);
          break;
				case 3:
				crudUnidadeTrabalho.inicial(scanner);
					break;
				case 4:
				relatoriosService.inicial(scanner);
					break;
        default:
          system = false;
          break;
      }
		}
	}

}
