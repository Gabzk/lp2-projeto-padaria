# Padaria - Sistema de Gerenciamento

Este é um sistema de gerenciamento para uma padaria, desenvolvido em Java utilizando JavaFX para a interface gráfica e MySQL para armazenamento de dados.

## Configuração do Banco de Dados

Antes de executar o sistema, siga os passos abaixo para configurar o banco de dados:

1. **Criar o banco de dados no MySQL:**
   - Execute os scripts SQL fornecidos no projeto para criar o banco de dados e inserir dados iniciais.
   - Certifique-se de que o MySQL está rodando e acessível.

2. **Configurar o arquivo `DB.java`:**
   - Edite o arquivo `DB.java` e configure as credenciais de acesso ao banco de dados:
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/seu_banco";
     private static final String USER = "seu_usuario";
     private static final String PASSWORD = "sua_senha";
     ```
   - Substitua `seu_banco`, `seu_usuario` e `sua_senha` pelos valores corretos.

## Executando o Sistema

### Usando o Maven

Se você tiver o Maven instalado, execute o seguinte comando no terminal dentro do diretório do projeto:
```sh
mvn javafx:run
```

### Usando uma IDE

Se estiver usando uma IDE que suporta Maven (como IntelliJ IDEA, Eclipse ou NetBeans):
1. Importe o projeto como um projeto Maven.
2. Certifique-se de que todas as dependências estão resolvidas.
3. Execute o programa a partir da opção "Run" da IDE.

## Dependências

O projeto utiliza as seguintes tecnologias:
- JavaFX
- JDBC (para conexão com MySQL)
- Maven (para gerenciamento de dependências e build)

## Problemas e Soluções

Caso encontre problemas ao rodar o sistema:
- Verifique se o MySQL está rodando e as credenciais estão corretas.
- Confirme que o banco de dados foi criado corretamente com os scripts SQL fornecidos.
- Certifique-se de que o Maven está instalado e configurado corretamente no sistema.

---

Desenvolvido por Gabriel Alves

