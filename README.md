# Controle de Ponto API

API para controle pessoal de ponto eletrônico

## Tecnologias Utilizadas

- Java
- Spring Data JDBC
- H2 Embedded Database
   - Ao executar a aplicação o banco de dados é criado e populado em memória
   - A base padrão pode ser acessada ao executar a aplicação no recurso `/h2-console`
   - Credenciais padrão: username = 'sa', password = '' 
- JUnit + Mockito

## Build e execução de testes

- Clonar ou baixar o repositório
- Acessar diretório principal `cd controleponto/`
- Executar comando maven install `./mvnw install`

