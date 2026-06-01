# Gerenciamento de Pedidos
Sistema de gerenciamento de pedidos desenvolvido em Java com banco de dados MySQL.
 
## Pré-requisitos

- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [MySQL 8.0+](https://dev.mysql.com/downloads/installer/)
- IDE de sua preferência (IntelliJ, Eclipse, VS Code, etc.)

## Configuração do Banco de Dados
 
1. Abra o **MySQL Workbench** (ou outra interface de sua preferência)
2. Conecte-se ao servidor local
3. Abra o arquivo `DDL_-_Gerenciamento_Pedidos.sql` e execute
O script criará automaticamente o banco `gerenciamento_pedidos` com as tabelas:
`cliente`, `produto`, `pedido` e `item_pedido`.
 
## Configuração do Projeto Java
 
Atualize as credenciais de conexão no projeto com os dados do seu MySQL local:
 
```
URL: jdbc:mysql://localhost:3306/gerenciamento_pedidos
Usuário: root
Senha: sua_senha
```
 
## Executando 
Importe o projeto na sua IDE e execute a classe principal.