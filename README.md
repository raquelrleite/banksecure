# BankSecure - Sistema de Gerenciamento de Apólices e Seguros

Um sistema de gerenciamento de apólices e propostas de seguros desenvolvido com Java Spring Boot e frontend minimalista.

## 🚀 Tecnologias

- **Backend:** Java 17, Spring Boot, Spring Data JPA, H2 Database
- **Frontend:** HTML5, CSS3, JavaScript (ES6 Modules)
- **Testes:** JUnit 5, Mockito, JaCoCo

## 📋 Requisitos Funcionais

### RF01 - Gestão de Tipos de Seguros
Usuários logados podem cadastrar, alterar e excluir tipos de seguros com:
- Título (obrigatório)
- Cobertura Mínima (string)
- Valor de Prêmio Base (decimal positivo, obrigatório)
- Tipo (VIDA, AUTO, RESIDENCIAL, CELULAR)

### RF02 - Efetuar Login
Sistema de autenticação simples com usuário e senha.

### RF03 - Validação de Seguro
- Título: obrigatório
- Valor de Prêmio Base: obrigatório e positivo

### RF04 - Gestão de Clientes
Cadastro e listagem de clientes com:
- Nome
- CPF
- Data de Nascimento

### RF05 - Regra de Elegibilidade
Clientes devem ter no mínimo 18 anos para cadastro.

### RF06/RF07 - Cotação e Cálculo de Prêmio
Cálculo do prêmio final:
1. Valor Inicial = Valor Base
2. Taxa Padrão: +5% sobre valor base
3. Bônus por Idade: +R$100 se idade > 60 anos
4. Fator Risco: ×1.10 (taxa fixa de risco)

Fórmula: `((Valor Base + 5%) + (100 se idade > 60)) × 1.10`

### RF08 - Registro de Apólice
Geração de apólice com:
- Cliente
- Seguro
- Valor Final
- Data de Início (data atual)
- Data de Fim (data atual + 1 ano)

### RF09 - Renovação de Apólice
Listagem e renovação de apólices a vencer.

### RF10 - Dashboard
Exibição de:
- Quantidade de clientes
- Quantidade de seguros
- Quantidade de apólices registradas
- Receita total de apólices
- Receita por tipo de seguro

## 🔐 Segurança

### Autenticação
- **Usuários Anônimos:** Podem visualizar seguros disponíveis
- **Usuários Logados:** Acesso completo ao sistema

### Cadastro de Funcionário
- Requer token de validação: `gof1rst`
- Token não é exibido no frontend
- Campo de token validado apenas no cadastro

## 🎨 Interface

### Layout Minimalista
- Design limpo e responsivo
- Cores: Vermelho Santander (#EC0000) e tons neutros
- Sidebar para navegação (apenas usuários logados)
- Modais para cadastro de dados

### Páginas

1. **index.html** - Login e Cadastro de Funcionário
2. **dashboard.html** - Resumo do sistema (apenas logados)
3. **seguros.html** - Gestão de seguros
4. **clientes.html** - Gestão de clientes (apenas logados)
5. **cotacao.html** - Cálculo de cotações (apenas logados)
6. **apolices.html** - Gestão de apólices (apenas logados)

## 📦 Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/banksecure/app/
│   │   ├── controller/        # Controllers REST
│   │   ├── domain/            # Entidades JPA
│   │   ├── dto/               # DTOs de Request/Response
│   │   ├── service/           # Lógica de negócio
│   │   ├── repository/        # Data Access
│   │   ├── mapper/            # MapStruct mappers
│   │   ├── exception/         # Exceções customizadas
│   │   └── config/            # Configurações
│   └── resources/
│       ├── application.yml    # Configuração da aplicação
│       └── static/            # Frontend (HTML, CSS, JS)
└── test/
    └── java/                  # Testes unitários
```

## ▶️ Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+

### Executar a Aplicação

```bash
mvn clean spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Compilar

```bash
mvn clean compile
```

### Testes

```bash
mvn clean test
```

### Cobertura de Testes

```bash
mvn clean verify jacoco:report
# Relatório em: target/site/jacoco/index.html
```

## 🔧 Configuração

### Application.yml

```yaml
spring:
  application:
    name: banksecure
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: false
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:testdb
```

## 📊 Dados Iniciais

O projeto inclui um `DataSeeder` que carrega dados iniciais:

### Seguros Pré-cadastrados
1. **Automóvel** - R$ 1.800,00
2. **Residencial** - R$ 350,00
3. **Vida** - R$ 500,00
4. **Celular** - R$ 150,00

### Clientes Pré-cadastrados
1. **João Silva** - CPF: 12345678901 - Data: 1980-01-15
2. **Maria Santos** - CPF: 23456789012 - Data: 1990-06-20
3. **Pedro Oliveira** - CPF: 34567890123 - Data: 2005-12-10

### Funcionário Padrão
- **Usuário:** samuel.leite
- **Senha:** senha123
- **Cargo:** Gerente

## 🌐 API Endpoints

### Autenticação
- `POST /funcionarios/login` - Login de funcionário
- `POST /funcionarios` - Cadastro de funcionário

### Seguros (Todos podem listar, apenas logados podem CRUD)
- `GET /seguros` - Listar todos os seguros
- `POST /seguros` - Cadastrar seguro (requer autenticação)
- `PATCH /seguros/{id}` - Atualizar seguro (requer autenticação)
- `DELETE /seguros/{id}` - Deletar seguro (requer autenticação)

### Clientes (Apenas logados)
- `GET /clientes` - Listar clientes
- `POST /clientes` - Cadastrar cliente

### Apólices (Apenas logados)
- `POST /apolices` - Gerar apólice
- `GET /apolices` - Listar apólices

## ✅ Checklist de Implementação

- [x] Gestão de Tipos de Seguros (RF01)
- [x] Efetuar Login (RF02)
- [x] Validação de Seguro (RF03)
- [x] Gestão de Clientes (RF04)
- [x] Regra de Elegibilidade (RF05)
- [x] Realizar Cotação (RF06)
- [x] Cálculo de Prêmio (RF07)
- [x] Registro de Apólice (RF08)
- [ ] Renovação de Apólice (RF09) - Em desenvolvimento
- [x] Dashboard (RF10)
- [x] Tratamento de Exceções (NF01)
- [ ] Cobertura de Testes 50% (NF02) - Em desenvolvimento

## 🐛 Problemas Conhecidos Resolvidos

- ✅ Campo "tipo" estava undefined no Seguro - **CORRIGIDO**
- ✅ Modal aparecia sempre mesmo para usuários anônimos - **CORRIGIDO**
- ✅ Estilos CSS duplicados e conflitantes - **CORRIGIDO**
- ✅ Topbar-actions não era escondida corretamente - **CORRIGIDO**
- ✅ Sidebar aparecia para usuários anônimos - **CORRIGIDO**

## 📝 Notas

- O token de cadastro de funcionário ("gof1rst") é validado apenas no frontend
- Usuários anônimos podem visualizar a lista de seguros
- O dashboard mostra dados dinâmicos carregados da API
- Cálculo de cotação segue exatamente os requisitos especificados

## 👥 Contribuidores

Projeto desenvolvido para FIAP - Faculdade de Informática e Administração Paulista

## 📄 Licença

Todos os direitos reservados © 2025 BankSecure

