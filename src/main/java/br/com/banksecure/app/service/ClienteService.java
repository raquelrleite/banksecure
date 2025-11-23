package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.entity.Cliente;
import br.com.banksecure.app.dto.request.ClienteRequest;
import br.com.banksecure.app.dto.response.ClienteResponse;
import br.com.banksecure.app.exception.MenorIdadeException;
import br.com.banksecure.app.mapper.ClienteMapper;
import br.com.banksecure.app.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static br.com.banksecure.app.enums.ErrorMessage.MENOR_IDADE;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    public ClienteResponse create(ClienteRequest request) {
        Cliente cliente = mapper.converterParaEntity(request);

        validarMaioridade(cliente.getDataNascimento());

        clienteRepository.save(cliente);

        return mapper.converterParaResponse(cliente);
    }


    private void validarMaioridade(LocalDate dataNascimento) {
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();

        if (idade < 18) {
            throw new MenorIdadeException(MENOR_IDADE.getMessage());
        }
    }

    public List<ClienteResponse> listarTodosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(cliente -> mapper.converterParaResponse(cliente))
                .toList();
    }





}
