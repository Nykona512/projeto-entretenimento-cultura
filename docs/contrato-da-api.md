# Contrato da API

Base: `http://localhost:8080`. Requisições/respostas JSON. Datas de sessão em `yyyy-MM-ddTHH:mm:ss`. Documentação Swagger `/swagger-ui.html`.

| Método | Rota | Entrada | Saída em sucesso | Erros |
|---|---|---|---|---|
<<<<<<< HEAD
| POST | /filmes | `FilmeRequest` | `FilmeResponse` (201) | 400 |
| GET | /filmes | — | Lista de `FilmeResponse` (200) | — |
| GET | /filmes/{id} | — | `FilmeResponse` (200) | 404 |
| POST | /salas | `SalaRequest` | `SalaResponse` (201) | 400 |
| GET | /salas | — | Lista de `SalaResponse` (200) | — |
| GET | /salas/{id} | — | `SalaResponse` (200) | 404 |
| POST | /sessoes | `SessaoRequest` | `SessaoResponse` (201) | 400 (R2), 404 |
| GET | /sessoes | — | Lista de `SessaoResponse` (200) | — |
| GET | /sessoes/{id} | — | `SessaoResponse` (200) | 404 |
| POST | /pedidos | `PedidoRequest` | `PedidoResponse` (201) | 400 (R1, R3, disponibilidade), 404 |
| GET | /pedidos/{id} | — | `PedidoResponse` (200) | 404 |
| GET | /ingressos | — | Lista de `IngressoResponse` (200) | — |
| GET | /ingressos/{id} | — | `IngressoResponse` (200) | 404 |
=======
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
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a

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
<<<<<<< HEAD

## DTOs de saída

- `FilmeResponse`: `id`, `titulo`, `duracaoMinutos`.
- `SalaResponse`: `id`, `nome`, `capacidade`.
- `SessaoResponse`: `id`, `filmeId`, `salaId`, `inicio`, `precoInteira`.
- `PedidoResponse`: `id`, `valorTotal`, lista de `IngressoResponse`.
- `IngressoResponse`: `id`, `sessaoId`, `pedidoId`, `tipo`, `valor`.

As classes em `api.mapper` constroem os DTOs sem retornar entidades JPA diretamente.
A emissão de ingresso ocorre em `POST /pedidos`; em `/ingressos` há apenas consultas.

## Exemplos adicionais

`POST /filmes`
```json
{"titulo":"Interestelar","duracaoMinutos":169}
```
Resposta `201`:
```json
{"id":1,"titulo":"Interestelar","duracaoMinutos":169}
```

`POST /sessoes`
```json
{"filmeId":1,"salaId":1,"inicio":"2026-12-10T14:00:00","precoInteira":30.00}
```
Resposta `201`:
```json
{"id":1,"filmeId":1,"salaId":1,"inicio":"2026-12-10T14:00:00","precoInteira":30.00}
```

Buscas com ID inexistente retornam `404`. Violações R1, R2 e R3 retornam `400` com mensagem explicativa.
=======
>>>>>>> e67d45c8d29cad88b0a5b4c4a3ccbbf854ec651a
