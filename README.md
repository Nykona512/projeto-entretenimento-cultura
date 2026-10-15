# Sistema de Ingressos de Cinema

API REST para gerenciar filmes, salas, sessões e compras de ingressos de cinema. O sistema permite organizar a programação e vender ingressos inteiros ou meia-entrada, aplicando regras de negócio no servidor.

Projeto Final da disciplina de Backend (Engenharia de Software, SATC). **Etapa 1: arquitetura do backend.**

## Índice

- [Integrantes](#integrantes)
- [O problema](#o-problema)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Entidades](#entidades)
- [Rotas](#rotas)
- [Como executar](#como-executar)
- [Situação do projeto](#situação-do-projeto)
- [Documentação](#documentação)

## Integrantes

| Nome | GitHub |
|---|---|
| Lucas Raupp Ferreira | [@Nykona512](https://github.com/Nykona512) |
| João Victor | [@Santx](https://github.com/santx2767-crypto) |
| Victor Valentim Brignoli | [@Victor Valentim Brignoli](https://github.com/VictorBrignoli) |

## O problema

**Tema sorteado:** 7 — Entendimento e Cultura

Cinemas precisam organizar seus filmes, salas, horários de exibição e vendas de ingressos. Sem verificações centralizadas, podem ocorrer sessões com horários conflitantes, compras acima do limite definido e vendas de meia-entrada além da cota permitida. A API permite cadastrar a programação e registrar compras, aplicando automaticamente essas regras para manter os dados consistentes.

**Quem usa o sistema:**

| Quem | O que faz no sistema |
|---|---|
| Administrador ou atendente do cinema | Cadastra filmes, salas e sessões; consulta a programação e acompanha as compras. |
| Cliente | Consulta filmes e sessões disponíveis e solicita a compra de ingressos. |

**Regras de negócio.** As regras são verificadas na camada Service, e não apenas por validações de formato dos DTOs.

| # | Regra | Onde está no código | Rota que usa |
|---|---|---|---|
| R1 | No máximo 40% da capacidade de ingressos de uma sessão podem ser vendidos como meia-entrada. O servidor calcula o valor da meia como 50% do preço da inteira, sem receber o preço do cliente. | `IngressoService.criar()` (chamado por `PedidoService`) | `POST /pedidos` |
| R2 | Sessões da mesma sala não podem ter horários sobrepostos; o intervalo ocupado considera o início, a duração do filme e 20 minutos adicionais de limpeza. | `SessaoService.criar()` | `POST /sessoes` |
| R3 | Cada pedido pode conter no máximo 6 ingressos. | `PedidoService.criar()` | `POST /pedidos` |

**Fora do escopo:** pagamentos reais, reserva de poltronas numeradas, autenticação de usuários, integração com sistemas externos de bilheteria e emissão de notas fiscais. A comprovação de elegibilidade legal à meia-entrada não é automatizada nesta etapa.

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

## Entidades

| Entidade | O que representa | Como se relaciona |
|---|---|---|
| Filme | Filme exibido no cinema, com título e duração em minutos | Pode aparecer em várias sessões. |
| Sala | Sala de cinema com nome e capacidade de ingressos | Pode receber várias sessões, desde que não haja conflito de horários. |
| Sessao | Exibição de um filme em uma sala, com horário de início e preço da inteira | Pertence a um filme e a uma sala; possui ingressos vendidos. |
| Pedido | Compra que agrupa ingressos e registra o valor total | Contém de 1 a 6 ingressos. |
| Ingresso | Unidade comprada, inteira ou meia, com preço calculado | Pertence a um pedido e a uma sessão. |

Atributos, tipos e o diagrama das tabelas estão em [docs/modelo-de-dominio.md](docs/modelo-de-dominio.md).

## Rotas

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
```

Resposta, `201 Created` (exemplo, considerando o primeiro ID gerado):

```json
{
  "id": 1,
  "titulo": "Interestelar",
  "duracaoMinutos": 169
}
```

### Erros

Todo erro tratado pela API segue o mesmo formato. Este é o `400` devolvido quando a regra R3 é violada (exemplo ilustrativo):

```json
{
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "R3: O limite máximo permitido é de 6 ingressos por pedido.",
  "caminho": "/pedidos",
  "timestamp": "2026-10-14T15:30:00",
  "campos": []
}
```

Um campo inválido também devolve `400`, com a lista `campos` identificando o problema. Um ID inexistente devolve `404`.

### As planejadas

No escopo definido para esta etapa, **não há outras rotas planejadas ainda sem implementação**. As rotas previstas no contrato atual estão respondendo, conforme os testes funcionais realizados pelo grupo. Funcionalidades futuras, como seleção de assentos e pagamentos reais, estão fora do escopo.

O contrato completo, com todas as rotas e os DTOs, está em [docs/contrato-da-api.md](docs/contrato-da-api.md).

## Como executar

Pré-requisitos: JDK 21, Docker Desktop aberto e Git.

```bash
git clone https://github.com/Nykona512/projeto-entretenimento-cultura.git
cd projeto-entretenimento-cultura

docker compose up -d          # sobe o PostgreSQL na porta 5437
./mvnw spring-boot:run        # Windows: .\mvnw.cmd spring-boot:run
```

> Para executar o projeto por clone, os arquivos da API implementada precisam já estar presentes na branch selecionada. Se o código ainda estiver apenas nos ZIPs do grupo, abra a pasta extraída no VS Code e execute os mesmos comandos nela.

Com a API no ar:

| Endereço | O que é |
|---|---|
| <http://localhost:8080/swagger-ui.html> | Swagger UI: rotas com **Try it out** para enviar requisições |
| <http://localhost:8080/v3/api-docs> | Contrato OpenAPI em JSON |

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
