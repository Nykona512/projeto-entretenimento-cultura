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

**Simplificação acadêmica:** a cota da meia-entrada considera a capacidade da sala como total de ingressos da sessão, arredondando 40% para baixo. A regulamentação real da meia-entrada possui critérios adicionais; ver [Lei nº 12.933/2013](https://www.planalto.gov.br/ccivil_03/_ato2011-2014/2013/lei/l12933.htm).

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

As classes, rotas e a migration foram escritas neste pacote. **A compilação e a execução com PostgreSQL não foram verificadas neste ambiente**, pois o Maven Wrapper não conseguiu baixar sua distribuição. Antes da entrega, executar a aplicação, testar as regras no Postman/Swagger e registrar os resultados na autoavaliação.
