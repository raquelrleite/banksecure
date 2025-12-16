/**
 * Camada de API - BankSecure
 */

const API_BASE = '';

// Função para formatar valores monetários
function formatarMoeda(valor) {
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(valor);
}

// Função para formatar CPF (xxx.xxx.xxx-xx)
function formatarCPF(cpf) {
    if (!cpf) return '';

    // Remove caracteres não numéricos
    const apenasNumeros = cpf.replace(/\D/g, '');

    // Se tiver mais de 11 dígitos, pega apenas os 11 primeiros
    const cpfLimitado = apenasNumeros.slice(0, 11);

    // Formata no padrão xxx.xxx.xxx-xx
    return cpfLimitado.replace(/(\d{3})(\d{3})(\d{3})(\d{2})?/, (match, p1, p2, p3, p4) => {
        if (p4) {
            return `${p1}.${p2}.${p3}-${p4}`;
        } else if (p3) {
            return `${p1}.${p2}.${p3}`;
        } else if (p2) {
            return `${p1}.${p2}`;
        }
        return p1;
    });
}

// Função para limpar CPF (remove formatação)
function limparCPF(cpf) {
    return cpf.replace(/\D/g, '');
}

const storage = {
    setFuncionario(funcionario) {
        console.log('Salvando funcionário na sessão:', funcionario);
        const jsonString = JSON.stringify(funcionario);
        sessionStorage.setItem('funcionario', jsonString);
        console.log('Verificando se foi salvo:', sessionStorage.getItem('funcionario'));
    },

    getFuncionario() {
        const value = sessionStorage.getItem('funcionario');
        console.log('Obtendo funcionário da sessão. Value:', value);
        if (!value) {
            console.log('sessionStorage vazio! Keys:', Object.keys(sessionStorage));
        }
        return value ? JSON.parse(value) : null;
    },

    clearFuncionario() {
        console.log('Limpando funcionário da sessão');
        sessionStorage.removeItem('funcionario');
    }
};

function requireAuth() {
    // Pequeno delay para garantir que sessionStorage está disponível
    const funcionario = storage.getFuncionario();
    console.log('requireAuth chamado. Funcionário na sessão:', funcionario);
    console.log('sessionStorage content:', sessionStorage.getItem('funcionario'));

    if (!funcionario) {
        console.log('Nenhum funcionário na sessão! Redirecionando para index.html');
        // Garantir que não há pendências antes de redirecionar
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 100);
        throw new Error('Não autenticado');
    }
    return funcionario;
}

function isAuthenticated() {
    return storage.getFuncionario() !== null;
}

function logout() {
    console.log('Limpando dados de sessão...');
    storage.clearFuncionario();

    // Garantir que a navegação aconteça com um delay ligeiramente maior
    console.log('Redirecionando para index.html...');
    setTimeout(() => {
        window.location.href = 'index.html';
    }, 200);
}

async function buildErrorMessage(response) {
    const contentType = response.headers.get('Content-Type') || '';
    const text = (await response.text()).trim();

    if (!text) return '';

    if (contentType.includes('application/json')) {
        try {
            const body = JSON.parse(text);


            if (body?.messages && typeof body.messages === 'object') {
                return Object.values(body.messages).join('\n');
            }

            return body?.message || body?.error || JSON.stringify(body);
        } catch (err) {
            return text;
        }
    }

    return text;
}

async function apiFetch(path, options = {}) {
    const funcionario = storage.getFuncionario();
    const headers = {
        'Content-Type': 'application/json',
        ...(options.headers || {})
    };

    if (funcionario) {
        headers['X-Funcionario-Id'] = funcionario.id;
    }

    const response = await fetch(`${API_BASE}${path}`, {
        ...options,
        headers
    });

    if (!response.ok) {
        const errorMessage = await buildErrorMessage(response);
        throw new Error(errorMessage || `Erro: ${response.status}`);
    }

    const contentType = response.headers.get('Content-Type') || '';
    if (contentType.includes('application/json')) {
        return await response.json();
    }

    return await response.text();
}

// Autenticação
async function loginFuncionario(credentials) {
    const response = await apiFetch('/funcionarios/login', {
        method: 'POST',
        body: JSON.stringify(credentials)
    });
    storage.setFuncionario(response);
    return response;
}

async function cadastrarFuncionario(data) {
    return await apiFetch('/funcionarios', {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

// Clientes
async function listarClientes() {
    return await apiFetch('/clientes');
}

async function cadastrarCliente(data) {
    if (data.dataNascimento && typeof data.dataNascimento === 'string' && data.dataNascimento.includes('-')) {
        const [ano, mes, dia] = data.dataNascimento.split('-');
        data.dataNascimento = `${dia}/${mes}/${ano}`;
    }
    return await apiFetch('/clientes', {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

// Seguros
async function listarSeguros() {
    return await apiFetch('/seguros');
}

async function cadastrarSeguro(data) {
    return await apiFetch('/seguros', {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

async function deletarSeguro(id) {
    return await apiFetch(`/seguros/${id}`, {
        method: 'DELETE'
    });
}

async function atualizarSeguro(id, data) {
    return await apiFetch(`/seguros/${id}`, {
        method: 'PATCH',
        body: JSON.stringify(data)
    });
}

// Cotação
function calcularCotacao(valorPremioBase, dataNascimento) {
    const taxaFixa = 0.05;
    const taxaDeRisco = 1.10;
    const taxaPorIdade = 100;

    let valorInicial = valorPremioBase + (valorPremioBase * taxaFixa);

    const hoje = new Date();
    const nascimento = new Date(dataNascimento);
    let idade = hoje.getFullYear() - nascimento.getFullYear();
    const mes = hoje.getMonth() - nascimento.getMonth();

    if (mes < 0 || (mes === 0 && hoje.getDate() < nascimento.getDate())) {
        idade--;
    }

    if (idade > 60) {
        valorInicial += taxaPorIdade;
    }

    const valorFinal = valorInicial * taxaDeRisco;
    return Math.round(valorFinal * 100) / 100;
}

// Apólices
async function gerarApolice(data) {
    return await apiFetch('/apolices', {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

async function listarApolices() {
    return await apiFetch('/apolices/lista');
}

async function apolicesAvencer() {
    return await apiFetch('/apolices');
}

async function renovarApolice(apoliceId) {
    return await apiFetch(`/apolices/renovar/${apoliceId}`, {
        method: 'POST'
    });
}

async function atualizarApolice(apoliceId, data) {
    // Converter data do formato yyyy-mm-dd para dd/MM/yyyy
    if (data.fimVigencia && typeof data.fimVigencia === 'string' && data.fimVigencia.includes('-')) {
        const [ano, mes, dia] = data.fimVigencia.split('-');
        data.fimVigencia = `${dia}/${mes}/${ano}`;
    }
    return await apiFetch(`/apolices/${apoliceId}`, {
        method: 'PATCH',
        body: JSON.stringify(data)
    });
}

async function cancelarApolice(apoliceId) {
    // Nota: O backend tem uma inversão de parâmetros internamente,
    // mas o frontend envia apenas via URL, então funciona corretamente
    return await apiFetch(`/apolices/cancelar/${apoliceId}`, {
        method: 'PUT'
    });
}

// Bens
async function listarBens(clienteId) {
    if (clienteId) {
        return await apiFetch(`/bem?clienteId=${clienteId}`);
    }
    return await apiFetch('/bem');
}

async function cadastrarBem(data) {
    return await apiFetch('/bem', {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

async function atualizarBem(bemId, data) {
    return await apiFetch(`/bem/${bemId}`, {
        method: 'PATCH',
        body: JSON.stringify(data)
    });
}

export {
    storage,
    requireAuth,
    isAuthenticated,
    logout,
    loginFuncionario,
    cadastrarFuncionario,
    listarClientes,
    cadastrarCliente,
    listarSeguros,
    cadastrarSeguro,
    deletarSeguro,
    atualizarSeguro,
    gerarApolice,
    listarApolices,
    apolicesAvencer,
    renovarApolice,
    atualizarApolice,
    cancelarApolice,
    listarBens,
    cadastrarBem,
    atualizarBem,
    calcularCotacao,
    formatarMoeda,
    formatarCPF,
    limparCPF
};

