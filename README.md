<h1 style="text-align: center;"> Restful Booker </h1>

---

## Índice

1. [Sobre o Projeto](#sobre-o-projeto)  
2. [Funcionalidades](#funcionalidades)  
3. [Tecnologias Utilizadas](#tecnologias-utilizadas)  
4. [Como Executar](#como-executar)  
5. [Autor](#autor)

---

## Sobre o projeto

Bem-vindo ao Restful-booker, uma API que você pode usar para aprender mais sobre testes de API ou experimentar ferramentas de teste de API. O Restful-booker é uma API Web de Create, Read, Update e Delete (CRUD) que inclui recursos de autenticação e diversos bugs para você explorar.

## Cenários contemplados

Os testes incluem os seguintes recursos da API:

- **Auth** – Geração de token de autenticação.
- **Booking**  
  - a. Listagem de reservas (GetBookingIds).
  - b. Detalhes de uma reserva específica (GetBooking). 
  - c. Criação de reserva (CreateBooking). 
  - d. Atualização total e parcial (UpdateBooking e PartialUpdateBooking). 
  - e. Exclusão de reserva (DeleteBooking).
- **Healthcheck** 
  - a. Smoke test para teste de sinal de vida da API.
- **Validação de Status Code** –  (200, 201, 400, 403, 404, 500, etc.).
- **Validação de Contrato (Schema)** –  Garantir que a estrutura do JSON de  resposta esteja correta.
- **Validação Funcional** – Verificar se os dados enviados no Request são os  mesmos retornados no Response ou persistidos no banco.
- **Cenários Negativos** – Tentativas de acesso sem token, IDs inexistentes, payload inválido.

Cada cenário contempla testes positivos e negativos, validando tanto respostas esperadas quanto comportamentos em situações inválidas.

---

## Tecnologias usadas

### Liguagem de programação:
* [JAVA (JDK 17)](https://www.oracle.com/java/technologies/downloads/#java17)

### Frameworks de teste:
* [JUnit 5](https://junit.org/)

### Ferramenta:
* [Intellij IDEA](https://www.jetbrains.com/pt-br/idea/)
* [Maven](https://maven.apache.org/)

---

## Como executar

### Pré-requisitos

Certifique-se de que sua máquina possui os seguintes itens instalados:

- Git  
- Maven  
- JDK 17  

> **Recomendação:** Utilize uma IDE como o IntelliJ IDEA para facilitar o desenvolvimento e execução.

---

### Passo a passo

#### 🔧 Opções para executar os testes:

1. **Clone o repositório:**
```bash
git clone https://github.com/JefersonEugenio/Desafio-Tecnico_Automacao-de-Testes-API_Restful-Booker.git
cd Desafio-Tecnico_Automacao-de-Testes-API_Restful-Booker
mvn test
```
O comando ``mvn test`` é padrão para executar o fluxo de testes, mas é possível especificar uma classe de teste específica.
| SUITE/ANOTAÇÃO | COMANDO | O QUE EXECUTA |
| :------- | :---- | :---------- |
| Independente | ``mvn test`` | Todos os testes |
| Independente | ``mvn -Dtest=ReservaGetTest test`` | Apenas essa classe GET |
| Independente | ``mvn -Dtest=ReservaPostTest test`` | Apenas essa classe POST |
| Independente | ``mvn -Dtest=ReservaPutTest test`` | Apenas essa classe PUT |
| Independente | ``mvn -Dtest=ReservaPatchTest test`` | Apenas essa classe PATCH |
| Independente | ``mvn -Dtest=ReservaDeleteTest test`` | Apenas essa classe DELETE |
| @SelectPackages | ``mvn -Dtest=AllTestSuite test`` | Apenas pacote configurado |
| @Tag("fulano") | ``mvn -Dtest=AllTestSuite test`` | Apenas testes com essa tag |

##### Exemplo:
Pacote suite classe AllTestSuite

@Suite
@SelectPackages("restfulBookerTests.reserva")

No terminal, execute o comando: ``mvn -Dtest=AllTestSuite test``

    └─ restfulBookerTests
        ├─ autenticacao
        ├─ ping
        └─ reserva

Executa só pasta dentro todos classes

---

Pacote suite classe AllTestSuite

@Suite
@SelectPackages("restfulBookerTests.reserva")
@IncludeTags("get")

No terminal, execute o comando: ``mvn -Dtest=AllTestSuite test``

    └─ restfulBookerTests
        ├─ autenticacao
        ├─ ping
        └─ reserva
            └─ReservaGetTest <- tem anotação @Tag

Executa só tag

---

### Visualize os relatórios:
Após a execução dos testes, acesse o diretório:
```Restful_Booker/reports```
Lá você encontrará os arquivos HTML com o relatório detalhado da execução.

---

### Estratégia de Testes

A estratégia de testes foi definida com foco na validação dos principais fluxos da API, priorizando as operações essenciais do CRUD (GET, POST, PUT, PATCH e DELETE).

Inicialmente, foram priorizados os cenários positivos, garantindo que as operações funcionassem corretamente com dados válidos e retornassem os status codes esperados (200, 201, 204).

Em seguida, foram implementados cenários negativos, validando comportamentos como:

Requisições com dados inválidos

ID inexistente

Token inválido ou ausente

Campos obrigatórios nulos ou incorretos

Esses cenários foram escolhidos porque representam situações reais que podem ocorrer em produção e ajudam a garantir a robustez e confiabilidade da API.

A suíte de testes foi organizada por métodos HTTP, separando as responsabilidades em classes específicas para cada operação. Além disso, foram utilizadas Tags do JUnit 5 para permitir execuções filtradas (por grupo), facilitando testes específicos durante o desenvolvimento.

Também foi criada uma suíte principal para execução completa do projeto via Maven, garantindo integração contínua e padronização da execução.

Essa abordagem permitiu manter os testes organizados, legíveis e escaláveis.

---

### Comportamentos Observados / Relatório de Bugs

API Restful Booker utilizada neste projeto é prática de testes automatizados.

Ela não representa um padrão oficial de API REST, apresentando inconsistências de comportamento, regras de negócio e códigos de status HTTP.

Durante os testes, foram observados os seguintes comportamentos:

#### Instabilidade da API

* A API é apenas para treinamento.
* Alguns dados aparecem e desaparecem.
* Não há persistência confiável de dados.

#### Problemas relacionados à autenticação (Auth)

* Quando enviado usuário/senha inválidos, a API retorna:
 ``200 OK
{
  "reason": "Bad credentials"
}
``

Em uma API real, o comportamento esperado seria: 
400 Bad Request (requisição inválida) ou 401 Unauthorized (credenciais inválidas)

Conclusão: Funciona para demonstração, mas não segue padrão REST ideal.

#### GET - GetBookingIds

* Retorna muitos IDs.
* Para organização dos testes, foi limitado a 20 registros.
* O retorno é apenas lista de IDs (não o objeto completo).

#### GET - GetBooking

* Campo "additionalneeds" às vezes aparece e às vezes não.
* Isso pode quebrar validação de contrato (JSON Schema).

Em uma API real, o contrato deveria ser consistente.

#### PUT com ID inexistente

* Ao atualizar um ID inexistente, a API retorna:
``405 Method Not Allowed``
* O comportamento esperado seria:
``404 Not Found``

405 indica que o método não é permitido, mas o endpoint existe.
404 seria mais adequado para recurso inexistente.

#### DELETE com ID inexistente

* Mesmo com token válido, retorna:
``405 Method Not Allowed``
* O esperado seria:
``404 Not Found``

#### DELETE com ID existente

* Em alguns testes, retornou:
``201 Created``
Isso está incorreto

* O correto seria:
``200 OK`` ou ``204 No Content``

201 é utilizado apenas para criação de recurso (POST).

#### Regra de Negócio – Totalprice Negativo
* API aceita valores negativos no campo totalprice.
* Retorna 200 OK e cria a reserva normalmente.

Em uma aplicação real, o esperado seria:

``400 Bad Request``


Pois valor negativo viola regra de negócio.

---

## Autor
| [<img src="https://avatars.githubusercontent.com/u/122066021?v=4" width=115><br><sub>Jeferson Lopes Eugenio</sub>](https://github.com/JefersonEuenio) |
| :---: |