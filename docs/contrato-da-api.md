# Contrato da API

Base: `http://localhost:8080`. Requisições/respostas JSON. Datas de sessão em `yyyy-MM-ddTHH:mm:ss`. Documentação Swagger `/swagger-ui.html`.

| Método | Rota | Entrada | Saída em sucesso | Erros |
|---|---|---|---|---|
| POST | /filmes | `FilmeRequest` | Filme (201) | 400 |
| GET | /filmes | — | Lista de filmes (200) | — |
| GET | /filmes/{id} | — | Filme (200) | 404 |
| POST | /salas | `SalaRequest` | Sala (201) | 400 |
| GET | /salas | — | Lista de salas (200) | — |
| GET | /salas/{id} | — | Sala (200) | 404 |
| POST | /sessoes | `SessaoRequest` | Sessão (201) | 400 (R2), 404 |
| GET | /sessoes | — | Lista de sessões (200) | — |
| GET | /sessoes/{id} | — | Sessão (200) | 404 |
| POST | /pedidos | `PedidoRequest` | `PedidoResponse` (201) | 400 (R1, R3, disponibilidade), 404 |
| GET | /pedidos/{id} | — | `PedidoResponse` (200) | 404 |

## DTOs de entrada

- `FilmeRequest`: `titulo` (não vazio), `duracaoMinutos` (inteiro >= 1).
- `SalaRequest`: `nome` (não vazio), `capacidade` (inteiro >= 1).
- `SessaoRequest`: `filmeId`, `salaId`, `inicio`, `precoInteira` (> 0).
- `PedidoRequest`: `sessaoId`, `ingressos` (lista não vazia de objetos `{"tipo":"INTEIRA"}` ou `{"tipo":"MEIA"}`). O servidor valida máximo de 6.

O cliente **não envia** preço do ingresso nem total do pedido. Ambos são calculados no Service.

## Exemplo de venda

`POST /pedidos`
```json
{"sessaoId":1,"ingressos":[{"tipo":"INTEIRA"},{"tipo":"MEIA"}]}
```

`201 Created` (quando a inteira custa 30 reais)
```json
{"id":1,"valorTotal":45.00,"ingressos":[{"id":1,"sessaoId":1,"pedidoId":1,"tipo":"INTEIRA","valor":30.00},{"id":2,"sessaoId":1,"pedidoId":1,"tipo":"MEIA","valor":15.00}]}
```

Em caso de erro, o `ApiExceptionHandler` do template devolve `ErroDTO` com `status`, `erro`, `mensagem`, `caminho`, `timestamp` e `campos`.
