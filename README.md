# Sistema de Gerenciamento de Estudantes — Java + PostgreSQL

![Java](https://img.shields.io/badge/Java-11-orange?logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-driver%2042.6.0-blue)
![Eclipse](https://img.shields.io/badge/IDE-Eclipse-2C2255?logo=eclipseide&logoColor=white)

Sistema de console para **gerenciamento de estudantes**, desenvolvido como trabalho final da disciplina de **Programação Orientada a Objetos** do **Grupo 6** da Residência 2023.2 do **Serratec**, sob orientação do professor **Élec do Canto**.

O programa conecta-se a um banco **PostgreSQL** via **JDBC**, cria a tabela automaticamente e oferece um menu interativo para adicionar, editar, remover e listar estudantes.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Regras e operações](#regras-e-operações)
- [Tecnologias](#tecnologias)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do banco](#configuração-do-banco)
- [Como executar](#como-executar)
- [Exemplo de uso](#exemplo-de-uso)
- [Modelo de dados](#modelo-de-dados)
- [Conceitos de POO aplicados](#conceitos-de-poo-aplicados)
- [Limitações e próximos passos](#limitações-e-próximos-passos)

---

## Funcionalidades

- **Adicionar estudante** com nome e curso;
- **Editar estudante**:
  - Nome;
  - Curso;
  - Nome e curso.
- **Remover estudante**:
  - Por nome;
  - Por ID;
  - Todos os estudantes de um curso.
- **Listar estudantes**:
  - Todos;
  - Por nome;
  - Por curso.
- **Criação automática da tabela** `estudantes` na primeira execução;
- **Tratamento de exceções** de SQL e de entradas inválidas no menu;
- **Encerramento organizado** da conexão com o banco.

## Regras e operações

| Operação | SQL executado |
| --- | --- |
| Adicionar | `INSERT INTO estudantes (nome, curso) VALUES (...)` |
| Editar nome | `UPDATE estudantes SET nome = ... WHERE nome = ...` |
| Editar curso | `UPDATE estudantes SET curso = ... WHERE nome = ...` |
| Editar ambos | `UPDATE estudantes SET nome = ..., curso = ... WHERE nome = ...` |
| Remover por nome | `DELETE FROM estudantes WHERE nome = ...` |
| Remover por ID | `DELETE FROM estudantes WHERE id = ...` |
| Remover por curso | `DELETE FROM estudantes WHERE curso = ...` |
| Listar todos | `SELECT * FROM estudantes` |
| Listar por nome | `SELECT * FROM estudantes WHERE nome = ...` |
| Listar por curso | `SELECT * FROM estudantes WHERE curso = ...` |

## Tecnologias

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Java 11 |
| Banco de dados | PostgreSQL |
| Acesso a dados | JDBC (`java.sql`) |
| Driver | `postgresql-42.6.0.jar` (incluído em `lib/`) |
| Interface | Console (`Scanner` + `System.out`) |
| IDE | Eclipse (`.project` / `.classpath`) |

## Estrutura do projeto

```text
.
├── src/
│   ├── main/
│   │   └── SistemaGerenciamentoEstudantes.java  # main()
│   ├── model/
│   │   └── Estudante.java                       # Entidade
│   ├── view/
│   │   └── Menu.java                            # Interface com o usuário
│   └── data/
│       ├── BancoDeDados.java                    # Operações SQL
│       ├── ConexaoBancoDeDados.java             # Conexão JDBC
│       └── CriadorDeTabelas.java                # DDL
├── lib/
│   └── postgresql-42.6.0.jar
├── bin/                                         # Classes compiladas (Eclipse)
├── .classpath
├── .project
└── Trabalho Final Programação Orientadas a Objetos.pdf
```

## Pré-requisitos

- **JDK 11** ou superior;
- **PostgreSQL** em execução na porta `5432`;
- Driver JDBC já incluído em `lib/postgresql-42.6.0.jar`.

## Configuração do banco

O projeto usa as seguintes credenciais **hardcoded** em `src/data/ConexaoBancoDeDados.java`:

| Parâmetro | Valor padrão |
| --- | --- |
| URL | `jdbc:postgresql://localhost:5432/Escola` |
| Usuário | `postgres` |
| Senha | `serratec` |

Crie o banco (o nome é case-sensitive por estar com maiúscula):

```sql
CREATE DATABASE "Escola";
```

Ou via terminal:

```bash
createdb -U postgres Escola
```

> Se o banco/credenciais forem diferentes, ajuste o arquivo `ConexaoBancoDeDados.java`. Para um projeto real, use variáveis de ambiente ou um arquivo de configuração.

A tabela é criada automaticamente:

```sql
CREATE TABLE IF NOT EXISTS estudantes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(20) NOT NULL,
    curso VARCHAR(20) NOT NULL
);
```

## Como executar

### Opção 1 — Eclipse (recomendado)

1. Abra o Eclipse;
2. **File → Import → General → Existing Projects into Workspace**;
3. Selecione a pasta do projeto;
4. Verifique se o JAR `lib/postgresql-42.6.0.jar` está no *Build Path*;
5. Execute a classe `main.SistemaGerenciamentoEstudantes`.

### Opção 2 — Linha de comando

#### Windows

```bash
javac -d bin -cp "lib\postgresql-42.6.0.jar" src\main\SistemaGerenciamentoEstudantes.java src\model\Estudante.java src\view\Menu.java src\data\*.java
java -cp "bin;lib\postgresql-42.6.0.jar" main.SistemaGerenciamentoEstudantes
```

#### Linux/macOS

```bash
javac -d bin -cp "lib/postgresql-42.6.0.jar" src/main/SistemaGerenciamentoEstudantes.java src/model/Estudante.java src/view/Menu.java src/data/*.java
java -cp "bin:lib/postgresql-42.6.0.jar" main.SistemaGerenciamentoEstudantes
```

## Exemplo de uso

```text
     ****** Gerenciamento de Estudantes ******
     *                                       *
     *        1 -> Adicionar estudante       *
     *        2 -> Editar estudante          *
     *        3 -> Remover estudante         *
     *        4 -> Listar estudantes         *
     *        5 -> Sair                      *
     *                                       *
     *****************************************

Escolha uma das opções acima: 1
Deseja realmente adicionar um estudante? (S/N): S
Insira o nome do estudante: Rayan
Insira o curso do estudante: Sistemas
Estudante adicionado com sucesso!
```

## Modelo de dados

**Tabela `estudantes`**

| Coluna | Tipo | Restrições |
| --- | --- | --- |
| `id` | `SERIAL` | Chave primária |
| `nome` | `VARCHAR(20)` | Obrigatório |
| `curso` | `VARCHAR(20)` | Obrigatório |

**Classe `Estudante`**

| Atributo | Tipo |
| --- | --- |
| `nome` | `String` |
| `curso` | `String` |

> O `id` existe na tabela, mas **não** faz parte da classe `Estudante` — ele é lido diretamente do `ResultSet`.

## Conceitos de POO aplicados

- **Encapsulamento** — atributos privados com getters/setters em `Estudante`;
- **Separação de responsabilidades** — pacotes `model`, `view` e `data`;
- **Construtores** — `Estudante(nome, curso)` e construtor padrão;
- **Composição** — `Menu` recebe `BancoDeDados` e `Scanner`;
- **Tratamento de exceções** — `try/catch` para `SQLException` e entradas inválidas;
- **Abstração de acesso a dados** — operações SQL isoladas em `BancoDeDados`.

## Limitações e próximos passos

- **SQL Injection** — as queries são montadas por concatenação de strings. O correto é usar `PreparedStatement` com parâmetros.
- **Credenciais hardcoded** — usuário/senha do banco no código-fonte.
- **Campos limitados a 20 caracteres** — nomes e cursos longos causam erro no banco.
- **Fall-through no menu** — após “Adicionar estudante” (opção 1), o `switch` não possui `break` e cai na opção 2 (menu de edição).
- **Edição/remoção por nome** afetam **todos** os registros com o mesmo nome.
- **Sem validação de duplicidade** de estudantes.
- **Sem paginação** na listagem.
- **Sem testes automatizados**.
- **`System.exit(0)`** encerra a JVM abruptamente; o ideal é apenas retornar do `main`.
- **Melhorias sugeridas**:
  - Migrar para `PreparedStatement`;
  - Adicionar `id` ao modelo `Estudante`;
  - Usar um pool de conexões (HikariCP);
  - Criar testes com JUnit;
  - Externalizar configurações;
  - Corrigir o `break` do menu.

## Contexto acadêmico

Trabalho final de **Programação Orientada a Objetos** — Grupo 6 — Residência 2023.2 — **Serratec**.
Professor: **Élec do Canto**.

## Autor

Desenvolvido por **Rayan Ferraz** e pelo Grupo 6.

- GitHub: [@rayanferrazz](https://github.com/rayanferrazz)
