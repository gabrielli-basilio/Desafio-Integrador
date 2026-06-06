# Gerenciamento de Pedidos
Sistema de gerenciamento de pedidos desenvolvido em Java com banco de dados MySQL na nuvem.

## Pré-requisitos

- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- IDE de sua preferência (IntelliJ, Eclipse, VS Code, etc.)

## Banco de Dados

O projeto utiliza um banco MySQL na nuvem (Railway). Não é necessário instalar o MySQL localmente.

**Credenciais:**
- Host: `monorail.proxy.rlwy.net`
- Porta: `36115`
- Banco: `gerenciamento_pedidos`
- Usuário: `root`
- Senha: `456123`

A conexão já está configurada em `src/main/java/com/gestaopedidos/infra/ConexaoBanco.java`.

## Caso precise alterar algo no Workbench

1. Abra o MySQL Workbench e crie uma nova conexão com os dados acima
2. Abra o arquivo `DDL_-_Gerenciamento_Pedidos.sql`
3. Execute com **Ctrl+Shift+Enter**

## Executando

1. Clone o projeto
2. Importe na sua IDE
3. Execute a classe principal