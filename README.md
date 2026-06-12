# Projeto Integrador II-A — Agenda Telefônica (CRUD em Java)

Aplicação em **Java** que armazena e gerencia contatos telefônicos (nome,
telefone e e-mail), implementando o **CRUD completo** (Create, Read, Update,
Delete) com persistência em banco de dados **MySQL** via **JDBC**.

Autor: José Ricardo Cosme Lérias Ribeiro

---

## 1. Estrutura do projeto

```
PROJETO INTEGRADOR II-A/
├── pom.xml                          # Configuração Maven (baixa o driver MySQL)
├── README.md                        # Este arquivo
├── banco/
│   └── agenda_telefonica.sql        # Dump do banco já populado (entregável)
└── src/main/java/com/agenda/
    ├── AgendaTeste.java             # Classe principal (menu + método main)
    ├── modelo/
    │   └── Contato.java             # Classe Contato (nome, telefone, email)
    └── dao/
        ├── ConexaoBD.java           # Abre a conexão com o MySQL
        ├── AgendaTelefonica.java    # Gerencia os contatos (operações CRUD)
        └── ContatoNaoEncontradoException.java  # Exceção personalizada
```

### Classes solicitadas na proposta
| Classe | Papel |
|--------|-------|
| `Contato` | Atributos: `nome`, `telefone`, `email` (String) |
| `AgendaTelefonica` | Métodos: `adicionarContato`, `removerContato`, `buscarContato`, `listarContatos` (+ `atualizarContato` para completar o CRUD) |
| `AgendaTeste` | Método `main` com menu para o usuário interagir |

---

## 2. Pré-requisitos

- **JDK 17** (ou superior) instalado.
- **MySQL** instalado e em execução.
- Driver **mysql-connector-j** — baixado automaticamente pelo Maven, ou
  adicionado manualmente como `.jar` se usar NetBeans/Eclipse sem Maven.

---

## 3. Passo a passo para executar

### Passo 1 — Criar/importar o banco de dados
Importe o dump (já cria o banco, a tabela e os dados de exemplo):

```bash
mysql -u root -p < banco/agenda_telefonica.sql
```

> Ou abra o arquivo `banco/agenda_telefonica.sql` no **MySQL Workbench** e execute.

### Passo 2 — Configurar usuário e senha
Por segurança, as credenciais do MySQL não ficam salvas no código. Antes de
executar, defina as variáveis de ambiente.

No PowerShell:

```powershell
$env:AGENDA_DB_USUARIO = "root"
$env:AGENDA_DB_SENHA = "sua_senha"
```

No Prompt de Comando:

```bat
set AGENDA_DB_USUARIO=root
set AGENDA_DB_SENHA=sua_senha
```

A URL padrão é `jdbc:mysql://localhost:3306/agenda_telefonica`. Para usar outra
URL, defina também a variável opcional `AGENDA_DB_URL`.

### Passo 3 — Compilar e executar

**Opção A — com Maven (recomendado, baixa o driver sozinho):**
```bash
mvn compile
mvn exec:java
```

**Opção B — pela IDE (NetBeans / Eclipse / IntelliJ):**
1. Abra o projeto.
2. Adicione o `.jar` do mysql-connector ao classpath (se não usar Maven).
3. Rode a classe `com.agenda.AgendaTeste`.

---

## 4. Menu da aplicação

```
1 - Adicionar um novo contato   (CREATE)
2 - Remover um contato existente (DELETE)
3 - Buscar um contato pelo nome  (READ)
4 - Listar todos os contatos     (READ)
5 - Atualizar um contato         (UPDATE)
6 - Sair
```

---

## 5. Critérios de avaliação atendidos

- **Tratamento de exceções:** `ContatoNaoEncontradoException` para contatos
  inexistentes; `SQLException` tratada em todas as operações; entrada inválida
  no menu não quebra o programa (`InputMismatchException`).
- **Interface amigável:** menu numerado, mensagens claras de sucesso/erro.
- **CRUD completo** persistido no MySQL, usando `PreparedStatement`
  (proteção contra SQL Injection).

---

## 6. Entregáveis

- [x] Projeto Java exportado (esta pasta).
- [x] Dump do banco populado: `banco/agenda_telefonica.sql`.
- [ ] Vídeo explicativo demonstrando o CRUD completo (gravar separadamente).
