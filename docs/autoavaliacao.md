# Autoavaliação

Os critérios abaixo seguem o template da disciplina. As marcações foram feitas a partir do código disponível e dos 14 testes manuais de endpoints relatados pelo grupo. **A decisão final e a pontuação devem ser confirmadas pelos integrantes após revisão do repositório.**

- ✅ atende
- ◐ atende em parte (explicar o que falta)
- ❌ não atende

| Critério | Marca | Evidência / ponto pendente |
|---|---|---|
| P1 A API sobe seguindo o README | ✅ | Spring Boot e Swagger executados no VS Code, segundo os testes do grupo; `README.md`, seção "Como executar". Reproduzir em outra máquina. |
| P2 Cada classe no pacote da sua camada | ✅ | Pacotes `api.controller`, `api.service`, `api.repository`, `api.model`, `api.dto`; exceções e handler no projeto. |
| P3 Migrations e `@Entity` de todas as tabelas, e a API sobe | ✅ | `src/main/resources/db/migration/V1__criar_tabelas.sql` e as 5 classes de `api.model`. |
| P4 DTOs de entrada e de saída de todas as entidades | ◐ | Entradas: `FilmeRequest`, `SalaRequest`, `SessaoRequest`, `PedidoRequest`. Saídas: `PedidoResponse`, `IngressoResponse`. `Filme`, `Sala` e `Sessao` ainda retornam as próprias entidades, sem DTOs de saída específicos. |
| P5 Repository, Mapper, Service e Controller de todas, com comentário no topo, e mapa da estrutura | ◐ | Existem Repositories e Services das cinco entidades; Controllers para filme, sala, sessão e pedido. Não existem classes Mapper específicas nem Controller de ingresso. A necessidade dos comentários no topo também deve ser revisada. Mapa em `README.md`. |
| P6 API simples: `POST`, `GET` e `GET /{id}`, com `400` e `404` | ◐ | As três rotas básicas existem para filmes, salas e sessões; `ApiExceptionHandler` trata 400/404. O grupo validou criação e consultas, mas recomenda-se registrar testes adicionais de 404 e validação de DTO. |
| P7 No mínimo 3 regras de negócio implementadas, com `400` quando violadas | ✅ | R1: `IngressoService`, R2: `SessaoService`, R3: `PedidoService`. Os três casos de erro 400 foram testados pelo grupo. |
| P8 As regras atendem ao problema e ao tema | ✅ | Cinema dentro do tema Entendimento e Cultura; cota de meia-entrada, sessões e limite de compra descritos no `README.md`. |
| P9 Código igual ao desenho | ✅ | `docs/modelo-de-dominio.md` e `docs/contrato-da-api.md` refletem as entidades e rotas atuais, com pendências apontadas. Conferir após futuras alterações. |
| P10 README completo e bem escrito | ✅ | `README.md` atualizado conforme template. |

