# Projeto Entretenimento e Cultura — Cinema

Projeto de API REST para gestão de filmes, salas, sessões e venda de ingressos, baseado no template da disciplina de Back-end (SATC). **Tema 7: Entendimento e Cultura**.

## Problema e usuários

O cinema precisa organizar sua programação e registrar vendas de ingresso sem ultrapassar a capacidade ou infringir as regras de meia-entrada. Administradores/atendentes cadastram filmes, salas e sessões. Clientes/atendentes registram pedidos de ingressos.

## Regras de negócio

| Regra | Descrição | Service | Endpoint |
|---|---|---|---|
| R1 | No máximo 40% da capacidade disponibilizada à sessão pode ser vendida como meia-entrada. O servidor calcula o preço da meia como 50% da inteira. | `IngressoService.criar` | `POST /pedidos` |
| R2 | Sessões na mesma sala não podem se sobrepor: cada sessão ocupa o horário de início, a duração do filme e mais 20 minutos de limpeza. | `SessaoService.criar` | `POST /sessoes` |
| R3 | Um pedido contém no máximo 6 ingressos. | `PedidoService.criar` | `POST /pedidos` |

Se as regras forem violadas, o Service lança `RegraDeNegocioException`, que o template converte em erro HTTP 400. O projeto também impede a venda acima da capacidade da sala ou após o início da sessão.

<<<<<<< HEAD
## Tecnologias

| O quê | Qual |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.5.16 (Web, Validation, Data JPA) |
| Banco de dados | PostgreSQL em Docker |
| Migrations | Flyway |
| Documentação da API | springdoc-openapi (Swagger UI) |

## Arquitetura

A API é organizada em camadas. Uma requisição entra pelo Controller, passa pelo Service, que aplica as regras de negócio, e chega ao banco pelo Repository; a resposta faz o caminho de volta. Os Controllers recebem RequestDTOs e devolvem ResponseDTOs; os Mappers convertem entidades em DTOs de resposta. As regras de negócio ficam nos Services.

**Mapa da estrutura.** Situação dos arquivos presentes no código entregue e testado pelo grupo:

| Entidade | Migration | Model | Repository | DTOs | Mapper | Service | Controller |
|---|---|---|---|---|---|---|---|
| Filme | `V1__criar_tabelas.sql`, pronta | `Filme`, pronto | `FilmeRepository`, pronto | `FilmeRequest`, `FilmeResponse`, prontos | `FilmeMapper`, implementado | `FilmeService`, implementado | `FilmeController`, implementado |
| Sala | `V1__criar_tabelas.sql`, pronta | `Sala`, pronta | `SalaRepository`, pronto | `SalaRequest`, `SalaResponse`, prontos | `SalaMapper`, implementado | `SalaService`, implementado | `SalaController`, implementado |
| Sessao | `V1__criar_tabelas.sql`, pronta | `Sessao`, pronta | `SessaoRepository`, pronto | `SessaoRequest`, `SessaoResponse`, prontos | `SessaoMapper`, implementado | `SessaoService`, implementado | `SessaoController`, implementado |
| Pedido | `V1__criar_tabelas.sql`, pronta | `Pedido`, pronto | `PedidoRepository`, pronto | `PedidoRequest`, `PedidoResponse`, prontos | `PedidoMapper`, implementado | `PedidoService`, implementado | `PedidoController`, implementado |
| Ingresso | `V1__criar_tabelas.sql`, pronta | `Ingresso`, pronto | `IngressoRepository`, pronto | `ItemIngressoRequest`, `IngressoResponse`, prontos | `IngressoMapper`, implementado | `IngressoService`, implementado | `IngressoController`, consultas implementadas; emissão por `PedidoController` |
=======
**Simplificação acadêmica:** a cota da meia-entrada considera a capacidade da sala como total de ingressos da sessão, arredondando 40% para baixo. A regulamentação real da meia-entrada possui critérios adicionais; ver [Lei nº 12.933/2013](https://www.planalto.gov.br/ccivil_03/_ato2011-2014/2013/lei/l12933.htm).
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a

## Entidades

| Entidade | Campos | Relações |
|---|---|---|
| `Filme` | id, titulo, duracaoMinutos | 1 filme → N sessões |
| `Sala` | id, nome, capacidade | 1 sala → N sessões |
| `Sessao` | id, filme, sala, inicio, precoInteira | N sessões → 1 filme e 1 sala |
| `Pedido` | id, dataHora, valorTotal | 1 pedido → N ingressos |
| `Ingresso` | id, sessao, pedido, tipo, valor | N ingressos → 1 sessão e 1 pedido |

Nesta versão há controle por quantidade, sem numeração de poltronas e sem pagamento real.

## Arquitetura

<<<<<<< HEAD
### As que já funcionam

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` | `/filmes` | Cadastra um filme | Nenhuma | `201` | `400` |
| `GET` | `/filmes` | Lista filmes | Nenhuma | `200` | — |
| `GET` | `/filmes/{id}` | Busca filme por ID | Nenhuma | `200` | `404` |
| `POST` | `/salas` | Cadastra uma sala | Nenhuma | `201` | `400` |
| `GET` | `/salas` | Lista salas | Nenhuma | `200` | — |
| `GET` | `/salas/{id}` | Busca sala por ID | Nenhuma | `200` | `404` |
| `POST` | `/sessoes` | Cadastra sessão e verifica conflito de horário | R2 | `201` | `400`, `404` |
| `GET` | `/sessoes` | Lista sessões | Nenhuma | `200` | — |
| `GET` | `/sessoes/{id}` | Busca sessão por ID | Nenhuma | `200` | `404` |
| `POST` | `/pedidos` | Registra uma compra com seus ingressos | R1 e R3 | `201` | `400`, `404` |
| `GET` | `/pedidos/{id}` | Consulta um pedido e seus ingressos | Nenhuma | `200` | `404` |
| `GET` | `/ingressos` | Lista ingressos emitidos | Nenhuma | `200` | — |
| `GET` | `/ingressos/{id}` | Consulta ingresso pelo ID | Nenhuma | `200` | `404` |

Exemplo de requisição:

```http
POST /filmes
Content-Type: application/json

{
  "titulo": "Interestelar",
  "duracaoMinutos": 169
}
=======
```text
Requisição HTTP / Postman
         ↓
controller (rotas e DTOs)
         ↓
service (validações e R1, R2, R3)
         ↓
repository (Spring Data JPA)
         ↓
PostgreSQL (Flyway V1__criar_tabelas.sql)
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
```

O projeto mantém o pacote `api` e reaproveita do template `Application`, `ApiExceptionHandler`, DTOs de erro e Swagger. O Service concentra as regras, e os pedidos são salvos em uma transação. Durante a compra a linha da sessão é bloqueada para evitar duas compras concorrentes excedendo a cota.

## Rotas disponíveis no código

| Método | Caminho | Descrição |
|---|---|---|
| POST | `/filmes` | Cadastrar filme |
| GET | `/filmes` | Listar filmes |
| GET | `/filmes/{id}` | Buscar filme |
| POST | `/salas` | Cadastrar sala |
| GET | `/salas` | Listar salas |
| GET | `/salas/{id}` | Buscar sala |
| POST | `/sessoes` | Cadastrar sessão; verifica R2 |
| GET | `/sessoes` | Listar sessões |
| GET | `/sessoes/{id}` | Buscar sessão |
| POST | `/pedidos` | Criar pedido, emitir ingressos; verifica R1 e R3 |
| GET | `/pedidos/{id}` | Buscar pedido e ingressos |

### Exemplos de JSON

**POST /filmes**
```json
{"titulo":"Filme Exemplo","duracaoMinutos":120}
```

**POST /salas**
```json
{"nome":"Sala 1","capacidade":100}
```

**POST /sessoes** (depois de cadastrar um filme e uma sala)
```json
{"filmeId":1,"salaId":1,"inicio":"2026-12-10T14:00:00","precoInteira":30.00}
```

**POST /pedidos** (cria um pedido com 2 ingressos; nunca enviar preço)
```json
{"sessaoId":1,"ingressos":[{"tipo":"INTEIRA"},{"tipo":"MEIA"}]}
```

O último exemplo retorna o ID do pedido, seu total e os ingressos emitidos, cada um com o preço calculado pela API.

## Como executar

1. Instale **Java 21**, **Docker Desktop** e execute o Docker.
2. No terminal da pasta do projeto, execute `docker compose up -d` (o template usa PostgreSQL exposto na porta **5437**).
3. No Windows execute `mvnw.cmd spring-boot:run`; no Linux/macOS execute `./mvnw spring-boot:run`. O Maven Wrapper baixa Maven caso necessário, exigindo acesso à internet.
4. Abra o Swagger em <http://localhost:8080/swagger-ui.html> e faça as requisições na ordem acima.
5. O Flyway cria as tabelas automaticamente no primeiro start. A configuração do banco está em `src/main/resources/application.properties`.

### Testes manuais sugeridos

- R1: numa sala com 10 lugares, tente vender um quinto ingresso meia (o limite é 4).
- R2: filme de 120 minutos iniciado às 14h na sala 1; tente outra sessão às 16h10 (erro) e às 16h20 (permitida).
- R3: envie um pedido com 7 itens de ingresso (erro).
- Capacidade: numa sala com 2 lugares, tente uma terceira venda (erro).

## Documentos

- [Modelo de domínio](docs/modelo-de-dominio.md)
- [Contrato da API](docs/contrato-da-api.md)
- [Autoavaliação](docs/autoavaliacao.md) — preencher pelo grupo após testar o projeto

## Status e limites

<<<<<<< HEAD
Para testar as rotas, use o Swagger UI ou o **Postman**. No Postman, importe todas as rotas de uma vez: **Import** → **Link** → `http://localhost:8080/v3/api-docs`.

Para parar: `Ctrl+C` na API e `docker compose stop` no banco. Para apagar o banco e rodar as migrations do zero: `docker compose down -v` e suba de novo. **Esse último comando apaga os dados armazenados no volume do banco.**

## Situação do projeto

| Parte | Situação |
|---|---|
| Desenho | Definido: problema e três regras neste README, modelo de domínio e contrato da API na pasta `docs/`. |
| Estrutura | Cinco entidades persistidas por `V1__criar_tabelas.sql`, com Models, Repositories, DTOs e Services. Há cinco Controllers, incluindo consultas próprias de ingressos; a emissão é feita pela criação de pedidos. |
| API e regras | Treze rotas implementadas. R1, R2 e R3 foram verificadas em testes manuais pelo Swagger, incluindo casos de sucesso e rejeição. |

**Ainda não funciona:** não há rota planejada pendente dentro do escopo atual. Pagamentos, escolha de poltronas e autenticação não foram implementados por estarem fora do escopo.

## Documentação

| Documento | O que tem |
|---|---|
| [Modelo de domínio](docs/modelo-de-dominio.md) | Entidades, relacionamentos, diagrama das tabelas, migrations |
| [Contrato da API](docs/contrato-da-api.md) | Todas as rotas, os DTOs, exemplos de JSON e erros |
| [Autoavaliação](docs/autoavaliacao.md) | Critérios de revisão do código para o grupo preencher e conferir |
=======
As classes, rotas e a migration foram escritas neste pacote. **A compilação e a execução com PostgreSQL não foram verificadas neste ambiente**, pois o Maven Wrapper não conseguiu baixar sua distribuição. Antes da entrega, executar a aplicação, testar as regras no Postman/Swagger e registrar os resultados na autoavaliação.
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
