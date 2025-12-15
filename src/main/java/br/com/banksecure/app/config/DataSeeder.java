package br.com.banksecure.app.config;

import br.com.banksecure.app.domain.Cliente;
import br.com.banksecure.app.domain.Funcionario;
import br.com.banksecure.app.domain.Seguro;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import br.com.banksecure.app.repository.ClienteRepository;
import br.com.banksecure.app.repository.FuncionarioRepository;
import br.com.banksecure.app.repository.SeguroRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private final FuncionarioRepository funcionarioRepository;
    private final SeguroRepository seguroRepository;
    private final ClienteRepository clienteRepository;

    public DataSeeder(FuncionarioRepository funcionarioRepository, SeguroRepository seguroRepository, ClienteRepository clienteRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.seguroRepository = seguroRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        seedFuncionarios();
        seedSeguros();
        seedClientes();
    }

    private void seedFuncionarios() {
        List<String> nomes = List.of(
                "Samuel Leite",
                "Gabriel Lijenko",
                "Anita Silva",
                "Leticia Rangel",
                "Raquel Leite"
        );
        List<String> usernames = List.of(
                "samuel.leite",
                "gabriel.lijenko",
                "anita.silva",
                "leticia.rangel",
                "raquel.leite"
        );

        for (int i = 0; i < usernames.size(); i++) {
            String username = usernames.get(i);
            if (funcionarioRepository.existsByUsername(username)) {
                continue;
            }
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(nomes.get(i));
            funcionario.setCargo("Especialista " + (i + 1));
            funcionario.setUsername(username);
            funcionario.setPassword(username);
            funcionarioRepository.save(funcionario);
        }
    }

    private void seedSeguros() {
        record SeguroSeed(String titulo, String cobertura, BigDecimal valor, TipoSeguroeBem tipo) {
        }

        var seeds = List.of(
                new SeguroSeed(
                        "Automóvel",
                        "Cobertura contra colisão, incêndio, roubo e danos materiais/corporais a terceiros.",
                        new BigDecimal("1800.00"),
                        TipoSeguroeBem.AUTO
                ),
                new SeguroSeed(
                        "Residencial",
                        "Cobertura básica contra incêndio, explosão, danos elétricos e vendaval.",
                        new BigDecimal("350.00"),
                        TipoSeguroeBem.RESIDENCIAL
                ),
                new SeguroSeed(
                        "Vida",
                        "Cobertura completa para proteção financeira da família em caso de morte ou invalidez.",
                        new BigDecimal("500.00"),
                        TipoSeguroeBem.VIDA
                ),
                new SeguroSeed(
                        "Celular",
                        "Proteção contra danos acidental, roubo, perda e cobertura de reparos.",
                        new BigDecimal("150.00"),
                        TipoSeguroeBem.CELULAR
                )
        );

        for (SeguroSeed seed : seeds) {
            if (seguroRepository.existsByTitulo(seed.titulo())) {
                continue;
            }
            Seguro seguro = new Seguro();
            seguro.setTitulo(seed.titulo());
            seguro.setCoberturaMinima(seed.cobertura());
            seguro.setValorPremioBase(seed.valor());
            seguro.setTipo(seed.tipo());
            seguroRepository.save(seguro);
        }
    }

    private void seedClientes() {
        record ClienteSeed(String nome, String cpf, LocalDate dataNascimento) {
        }

        var seeds = List.of(
                new ClienteSeed(
                        "João Silva Santos",
                        "12345678901",
                        LocalDate.of(1950, 5, 15)
                ),
                new ClienteSeed(
                        "Maria Oliveira Costa",
                        "98765432100",
                        LocalDate.of(1985, 8, 22)
                ),
                new ClienteSeed(
                        "Pedro Ferreira Rodrigues",
                        "55544433322",
                        LocalDate.of(1995, 3, 10)
                )
        );

        for (ClienteSeed seed : seeds) {
            if (clienteRepository.existsByCpf(seed.cpf())) {
                continue;
            }
            Cliente cliente = new Cliente();
            cliente.setNome(seed.nome());
            cliente.setCpf(seed.cpf());
            cliente.setDataNascimento(seed.dataNascimento());
            clienteRepository.save(cliente);
        }
    }
}

