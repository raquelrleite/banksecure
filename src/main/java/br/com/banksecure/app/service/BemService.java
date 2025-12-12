package br.com.banksecure.app.service;

import br.com.banksecure.app.domain.Bem;
import br.com.banksecure.app.dto.request.BemRequest;
import br.com.banksecure.app.dto.request.BemUpdateRequest;
import br.com.banksecure.app.dto.response.BemResponse;
import br.com.banksecure.app.enums.TipoSeguroeBem;
import br.com.banksecure.app.exception.BemNaoEncontradoException;
import br.com.banksecure.app.exception.ClienteNaoEncontradoException;
import br.com.banksecure.app.mapper.BemMapper;
import br.com.banksecure.app.repository.BemRepository;
import br.com.banksecure.app.repository.ClienteRepository;
import br.com.banksecure.app.util.ValidarAcesso;
import org.springframework.stereotype.Service;

import static br.com.banksecure.app.enums.ErrorMessage.BEM_NAO_ENCONTRADO;
import static br.com.banksecure.app.enums.ErrorMessage.CLIENTE_NAO_ENCONTRADO;

@Service
public class BemService {

    private final BemRepository repository;
    private final ClienteRepository clienteRepository;
    private final BemMapper mapper;
    private final ValidarAcesso acesso;

    public BemService(BemRepository repository, ClienteRepository clienteRepository, BemMapper mapper, ValidarAcesso acesso) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
        this.acesso = acesso;
    }

    public BemResponse cadastrar(BemRequest request, Long funcionarioId){

        acesso.validarAcesso(funcionarioId);

        var cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException(CLIENTE_NAO_ENCONTRADO.getMessage()));

        Bem bem = mapper.converterParaEntity(request);

        bem.setCliente(cliente);

        repository.save(bem);
        return mapper.converterParaResponse(bem);
    }

    public BemResponse atualizar(Long id, BemUpdateRequest request, Long funcionarioId){
        acesso.validarAcesso(funcionarioId);

        var bem =  repository.findById(id)
                .orElseThrow(
                () -> new BemNaoEncontradoException(BEM_NAO_ENCONTRADO.getMessage()));

        if(request.tipo() != null && !request.tipo().isBlank()){
            bem.setTipo(TipoSeguroeBem.valueOf(request.tipo()));
        }

        if(request.descricao() != null && !request.descricao().isBlank()){
            bem.setDescricao(request.descricao());
        }

        Bem bemAtualizado = repository.save(bem);
        return mapper.converterParaResponse(bemAtualizado);
    }
}
