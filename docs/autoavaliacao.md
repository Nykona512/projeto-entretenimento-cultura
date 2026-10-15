# Autoavaliação

Os 10 critérios que serão avaliados na revisão do código, descritos em `Aula 10-11/AVALIACAO.md`, no repositório da disciplina. Para cada um, a marca do grupo e onde está a evidência.

- ✅ atende
- ◐ atende em parte (diga o que falta)
- ❌ não atende

| Critério | Marca | Evidência |
|---|---|---|
| P1 A API sobe seguindo o README | ✅ | `README.md` — Como executar; execução deve ser repetida em clone limpo |
| P2 Cada classe no pacote da sua camada | ✅ | `src/main/java/api/` — controller, service, repository, mapper, dto e model |
| P3 Migrations e `@Entity` de todas as tabelas, e a API sobe | ✅ | `src/main/resources/db/migration/V1__criar_tabelas.sql`; pacote `model` |
| P4 DTOs de entrada e de saída de todas as entidades | ✅ | `dto/`: cinco DTOs de resposta e DTOs de entrada de criação/itens |
| P5 Repository, Mapper, Service e Controller de todas, com o comentário no topo, e o mapa da estrutura | ✅ | Pacotes `repository`, `mapper`, `service`, `controller`; controller de ingresso com consultas |
| P6 API simples: `POST`, `GET` e `GET /{id}`, com `400` e `404` | ✅ | `FilmeController`, `SalaController`, `SessaoController`, `PedidoController`; `ApiExceptionHandler` |
| P7 No mínimo 3 regras de negócio implementadas, com `400` quando violadas | ✅ | `IngressoService` (R1), `SessaoService` (R2), `PedidoService` (R3) |
| P8 As regras atendem ao problema e ao tema | ✅ | `README.md` — O problema e regras específicas do cinema |
| P9 Código igual ao desenho | ✅ | `docs/modelo-de-dominio.md` e `docs/contrato-da-api.md` atualizados |
| P10 README completo e bem escrito | ✅ | `README.md` com integrantes, rotas e arquitetura |

