# Contrato da API

Este documento descreve as rotas implementadas no projeto e distingue as operações ainda não disponíveis.

## Convenções

| Item | Valor |
|---|---|
| Endereço base | `http://localhost:8080` |
| Formato | JSON |
| Datas | `yyyy-MM-ddTHH:mm:ss` em `Sessao.inicio` |
| Erros | `ErroDTO` com `status`, `erro`, `mensagem`, `caminho`, `timestamp`, `campos` |
| Documentação viva | `http://localhost:8080/swagger-ui.html` |

## Rotas

### Filmes

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/filmes` | Cadastra um filme | `FilmeRequest` | `Filme` | `201` | `400` | sim |
| `GET` | `/filmes` | Lista filmes | — | Lista de `Filme` | `200` | — | sim |
| `GET` | `/filmes/{id}` | Busca por ID | — | `Filme` | `200` | `404` | sim |

### Salas

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/salas` | Cadastra uma sala | `SalaRequest` | `Sala` | `201` | `400` | sim |
| `GET` | `/salas` | Lista salas | — | Lista de `Sala` | `200` | — | sim |
| `GET` | `/salas/{id}` | Busca por ID | — | `Sala` | `200` | `404` | sim |

### Sessões

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/sessoes` | Cadastra sessão após validar horários (R2) | `SessaoRequest` | `Sessao` | `201` | `400`, `404` | sim |
| `GET` | `/sessoes` | Lista sessões | — | Lista de `Sessao` | `200` | — | sim |
| `GET` | `/sessoes/{id}` | Busca por ID | — | `Sessao` | `200` | `404` | sim |

### Pedidos e ingressos

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `POST` | `/pedidos` | Cria pedido e emite os ingressos (R1 e R3) | `PedidoRequest` | `PedidoResponse` | `201` | `400`, `404` | sim |
| `GET` | `/pedidos/{id}` | Busca pedido com seus ingressos | — | `PedidoResponse` | `200` | `404` | sim |

**Não existem endpoints independentes de `/ingressos` nesta versão.** Os ingressos são criados internamente na compra de um pedido.

### Rotas que não são CRUD

As regras de negócio foram integradas à criação de sessões e pedidos, sem criar endpoints adicionais.

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` | `/sessoes` | Verifica sobreposição de horários e 20 minutos de limpeza | R2 | `201` | `400`, `404` |
| `POST` | `/pedidos` | Verifica a cota de 40% e calcula o valor da meia-entrada | R1 | `201` | `400`, `404` |
| `POST` | `/pedidos` | Recusa pedidos com mais de 6 ingressos | R3 | `201` | `400`, `404` |

## DTOs

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `FilmeRequest` | entrada | `titulo`, `duracaoMinutos` | `@NotBlank`, `@NotNull`, `@Min(1)` |
| `SalaRequest` | entrada | `nome`, `capacidade` | `@NotBlank`, `@NotNull`, `@Min(1)` |
| `SessaoRequest` | entrada | `filmeId`, `salaId`, `inicio`, `precoInteira` | IDs e início obrigatórios; preço ≥ 0,01 |
| `ItemIngressoRequest` | entrada (item) | `tipo` (`INTEIRA` ou `MEIA`) | `@NotNull` |
| `PedidoRequest` | entrada | `sessaoId`, `ingressos` | sessão obrigatória, lista não vazia; itens válidos; máximo de 6 validado no Service |
| `IngressoResponse` | saída (item) | `id`, `sessaoId`, `pedidoId`, `tipo`, `valor` | Calculado pelo servidor |
| `PedidoResponse` | saída | `id`, `valorTotal`, `ingressos` | Calculado pelo servidor |
| `ErroDTO` | saída de erro | `status`, `erro`, `mensagem`, `caminho`, `timestamp`, `campos` | Padronizado pelo handler |
| `CampoErroDTO` | saída de erro | `campo`, `mensagem` | Identifica campo inválido |

**Observação:** `Filme`, `Sala` e `Sessao` são retornados diretamente como entidades JPA. Ainda não existem DTOs de saída separados para elas.

## Exemplos

Os IDs podem variar conforme os dados já cadastrados no banco.

### `POST /filmes`

Requisição:

```json
{"titulo":"Interestelar","duracaoMinutos":169}
```

Resposta ilustrativa, `201 Created`:

```json
{"id":1,"titulo":"Interestelar","duracaoMinutos":169}
```

### `POST /salas`

Requisição:

```json
{"nome":"Sala 01","capacidade":10}
```

Resposta ilustrativa, `201 Created`:

```json
{"id":1,"nome":"Sala 01","capacidade":10}
```

### `POST /sessoes`

Requisição:

```json
{"filmeId":1,"salaId":1,"inicio":"2026-11-20T14:00:00","precoInteira":30.00}
```

Resposta ilustrativa, `201 Created` (objetos associados podem ser exibidos como abaixo):

```json
{"id":1,"filme":{"id":1,"titulo":"Interestelar","duracaoMinutos":169},"sala":{"id":1,"nome":"Sala 01","capacidade":10},"inicio":"2026-11-20T14:00:00","precoInteira":30.00}
```

### `POST /pedidos`

Requisição:

```json
{"sessaoId":1,"ingressos":[{"tipo":"INTEIRA"},{"tipo":"MEIA"}]}
```

Resposta ilustrativa, `201 Created`, para ingresso inteiro de R$ 30:

```json
{"id":1,"valorTotal":45.00,"ingressos":[{"id":1,"sessaoId":1,"pedidoId":1,"tipo":"INTEIRA","valor":30.00},{"id":2,"sessaoId":1,"pedidoId":1,"tipo":"MEIA","valor":15.00}]}
```

O cliente não envia preços nem o total: os valores são calculados no Service.

## Erros

Resposta ilustrativa para violação de regra R3, `POST /pedidos`:

```json
{"status":400,"erro":"Bad Request","mensagem":"R3: O limite máximo permitido é de 6 ingressos por pedido.","caminho":"/pedidos","timestamp":"2026-10-14T15:00:00","campos":[]}
```

| Status | Quando acontece neste projeto |
|---|---|
| `400` | Requisição inválida ou campo inválido no DTO |
| `400` | Violação de R1, R2, R3 ou falta de ingressos disponíveis |
| `404` | ID de filme, sala, sessão ou pedido inexistente |

No caso de validação, `campos` contém objetos como `{"campo":"titulo","mensagem":"não deve estar em branco"}` (o texto específico depende da mensagem de validação configurada). Na violação de regra, `campos` é uma lista vazia.

## Rotas planejadas, ainda não implementadas

| Recurso | Possível evolução |
|---|---|
| Filmes, salas e sessões | `PUT` e `DELETE` para edição e exclusão |
| Pedidos | `GET /pedidos` para listagem e cancelamento de pedidos |
| Ingressos | Rotas de consulta individual e possível cancelamento |

Estas rotas são ideias de evolução e **não estão disponíveis no Swagger atual**.
