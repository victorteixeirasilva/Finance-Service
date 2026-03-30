# Finance Core API — Microsserviço de Finanças Pessoais

> **Planejamento, transações e visão consolidada do seu dinheiro — com API segura, assíncrona e pronta para escalar.**

---

## Slogan / Chamada curta

**Finanças pessoais sob controle: uma API que transforma dados em decisões.**

---

## Introdução e visão geral

Pessoas e negócios precisam **organizar renda, custos de vida, investimentos e entradas extras** sem perder o fio da meada. Planilhas soltas e sistemas monolíticos geram retrabalho, inconsistência e pouca segurança quando vários canais consomem as mesmas informações.

O **Finance Core API** é um **microsserviço backend** focado em **finanças pessoais**: centraliza **planejamento financeiro por usuário**, **registro tipado de transações** (custo de vida, investimento, contribuição extra) e **consultas por intervalo de datas**, com **autenticação baseada em token JWT (RSA)** integrada ao ecossistema de autenticação da plataforma.

### O que você ganha

| Benefício | Descrição |
|-----------|-----------|
| **Segurança em camadas** | Validação de JWT com chave pública RSA, *audience* e *issuer* alinhados ao serviço de autenticação. |
| **Responsividade da API** | Endpoints expostos de forma **assíncrona** (`CompletableFuture`), com *pool* de threads dedicado. |
| **Operação em arquitetura distribuída** | Integração via **OpenFeign** com serviço de autenticação e **Spring Cloud Config** para configuração externa. |
| **Transparência para integradores** | Documentação interativa com **OpenAPI / Swagger UI**. |
| **Observabilidade** | **Spring Boot Actuator** para métricas e saúde da aplicação em ambientes corporativos. |

Este repositório é ideal para **portfólio técnico**: demonstra domínio de **Java moderno**, **Spring Boot 3**, **JPA**, **microsserviços**, **segurança JWT** e **boas práticas de API REST**.

---

## Funcionalidades principais

1. **Criação de planejamento financeiro no cadastro do usuário**  
   - **Endpoint:** `POST /ms/finance/{idUser}/{token}`  
   - **Propósito:** Ao registrar um usuário no ecossistema, inicializa o **planejamento financeiro** associado.  
   - **Valor:** Garante que todo usuário já nasça no sistema com uma base financeira consistente, sem passos manuais extras.

2. **Atualização de salário (wage)**  
   - **Endpoint:** `PUT /ms/finance/wage/{idUser}/{token}`  
   - **Propósito:** Manter o **salário declarado** alinhado à realidade do usuário.  
   - **Valor:** Base para cálculos, metas e relatórios; reduz divergência entre “o que a pessoa ganha” e “o que o sistema assume”.

3. **Registro de transação — custo de vida**  
   - **Endpoint:** `POST /ms/finance/transaction/cost_of_living/{token}`  
   - **Propósito:** Lançar despesas recorrentes ou de **custo de vida**.  
   - **Valor:** Separação clara entre obrigações do dia a dia e outros tipos de movimentação.

4. **Registro de transação — investimento**  
   - **Endpoint:** `POST /ms/finance/transaction/investment/{token}`  
   - **Propósito:** Registrar aportes ou movimentações classificadas como **investimento**.  
   - **Valor:** Visibilidade de quanto está sendo direcionado a longo prazo, sem misturar com despesas correntes.

5. **Registro de transação — contribuição extra**  
   - **Endpoint:** `POST /ms/finance/transaction/extra_contribution/{token}`  
   - **Propósito:** Registrar **entradas adicionais** de saldo (bônus, freelas, presentes em dinheiro, etc.).  
   - **Valor:** Modelo financeiro mais fiel à vida real, além do salário fixo.

6. **Exclusão de transação**  
   - **Endpoint:** `DELETE /ms/finance/transaction/{idUser}/{idTransaction}/{token}`  
   - **Propósito:** Remover lançamentos incorretos ou duplicados com rastreabilidade por usuário e ID.  
   - **Valor:** Qualidade de dados e confiança nos relatórios.

7. **Consulta de transação individual**  
   - **Endpoint:** `GET /ms/finance/transaction/{idUser}/{idTransaction}/{token}`  
   - **Propósito:** Obter detalhes de um lançamento específico.  
   - **Valor:** Suporte a telas de detalhe, auditoria e integrações pontuais.

8. **Painel financeiro por intervalo de datas**  
   - **Endpoint:** `GET /ms/finance/{idUser}/{startDate}/{endDate}/{token}`  
   - **Propósito:** Agregar **informações financeiras** no período informado (datas no formato compatível com a API).  
   - **Valor:** Base para dashboards, exportações e análise de período (mês, trimestre, ano fiscal, etc.).

> Em todos os fluxos acima, o **token** é validado antes da execução da regra de negócio; respostas **401 Unauthorized** protegem o domínio contra acesso indevido.

---

## Tecnologias utilizadas

| Categoria | Tecnologias |
|-----------|-------------|
| **Linguagem** | Java **21** |
| **Runtime / build** | Maven, Spring Boot **3.4.5** |
| **Framework web e DI** | Spring Web, Spring Boot Auto-configuration |
| **Persistência** | Spring Data **JPA**, **MySQL** (`mysql-connector-j`) |
| **Microsserviços** | **Spring Cloud OpenFeign**, **Spring Cloud Config** (cliente) |
| **Documentação API** | **springdoc-openapi** (OpenAPI 3 + **Swagger UI**) |
| **Segurança / JWT** | **Auth0 java-jwt** — verificação RSA256 com *issuer* e *audience* |
| **Assíncrono** | `@EnableAsync`, `ThreadPoolTaskExecutor` (pool configurável) |
| **Agendamento** | `@EnableScheduling` (habilitado na aplicação; extensível para jobs) |
| **Utilitários** | **Lombok** |
| **Observabilidade** | **Spring Boot Actuator** |
| **Testes** | **REST Assured**, **JavaFaker** (apoio a dados de teste) |
| **Container** | **Docker** (imagem base **Eclipse Temurin 21** Alpine), **Docker Compose** |
| **Dev experience** | Spring Boot **DevTools** (opcional, runtime) |

### Por que algumas escolhas importam

- **Java 21 + Spring Boot 3.4:** stack atual, com suporte de longo prazo e ecossistema maduro para APIs corporativas.  
- **JPA + MySQL:** modelo relacional sólido para integridade de dados financeiros e consultas transacionais.  
- **OpenFeign:** contratos HTTP declarativos — ideal para falar com o **serviço de autenticação** sem boilerplate manual.  
- **JWT com RSA:** validação local com **chave pública** (`public.pem`), padrão em arquiteturas de identidade distribuída.  
- **Config Server:** separa **segredos e URLs de ambiente** do código — essencial em **dev / staging / produção**.

---

## Demonstração visual

![Documentação Swagger](docs/assets/swagger-finance.png)

## Contribuição

Contribuições são bem-vindas para evoluir o portfólio e a qualidade do código.

1. Faça um **fork** do repositório.  
2. Crie uma **branch** descritiva: `git checkout -b feature/nome-da-melhoria`.  
3. Commit com mensagens claras, em português ou inglês, alinhadas ao padrão do projeto.  
4. Abra um **Pull Request** explicando **o problema**, **a solução** e **como validar** (inclua prints da API/Swagger se aplicável).  
5. Mantenha **compatibilidade** com o Config Server e com o contrato de autenticação JWT existente.

*Pull requests que alterem credenciais, chaves privadas ou dados sensíveis não serão aceitos — use variáveis de ambiente e Config Server.*

---

## Licença

Este repositório **não inclui um arquivo `LICENSE` na raiz** no estado atual do projeto. Para uso em **portfólio pessoal**, recomenda-se adicionar explicitamente uma licença (por exemplo, **[MIT License](https://opensource.org/licenses/MIT)**) se você for o detentor dos direitos e desejar permitir reutilização.

- **Se o código for proprietário da empresa ou de terceiros:** mantenha **todos os direitos reservados** e documente isso em um arquivo `LICENSE` ou no contrato vigente.  
- **Antes de reutilizar trechos em outro projeto:** confirme a política de IP com o mantenedor.

---

## Agradecimentos

- Comunidade **Spring** e **Spring Cloud** pela documentação e pelos guias oficiais.  
- Time e ecossistema **InEvolving** pelo contexto de produto e integração entre microsserviços.  
- Mantenedores de **springdoc-openapi**, **OpenFeign** e **Auth0 java-jwt**.

---

## Contato

**Personalize esta seção com seus dados reais.**

- **LinkedIn:** [https://www.linkedin.com/in/victor-teixeira-354a131a3/](https://www.linkedin.com/in/victor-teixeira-354a131a3/)  
- **GitHub:** [https://github.com/victorteixeirasilva](https://github.com/victorteixeirasilva)  
- **E-mail:** `victor.teixeira@inovasoft.tech`  

---

*Documento elaborado para destacar **valor de negócio**, **segurança**, **escalabilidade** e **competências técnicas** — pilares que recrutadores e clientes B2B buscam em backends de finanças e microsserviços.*
