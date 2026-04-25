# Catalogo - FATEC

Sistema web em Spring Boot para gerenciamento de produtos, categorias e usuarios, com controle de acesso por perfil.

## Como executar

1. Configure o PostgreSQL em `src/main/resources/application.properties`.
2. Execute a aplicacao pela classe `CatalogoApplication`.
3. Acesse no navegador:
   - `http://localhost:8080/login`

## Usuarios para login

Usuarios padrao cadastrados automaticamente no `DataLoader`:

- Admin (professor)
  - Login: `mateus`
  - Senha: `Mateus@1234`
  - Permissao: criar, editar e excluir produtos e categorias

- User (aluno)
  - Login: `jose`
  - Senha: `Jose@1234`
  - Permissao: apenas visualizar produtos e categorias

## Regras de acesso (resumo)

- `ROLE_USER`: visualiza produtos e categorias.
- `ROLE_ADMIN`: gerencia produtos, categorias e usuarios.
- Botoes de gestao ficam ocultos para quem nao e admin (`sec:authorize`).

## Funcionalidades implementadas

- Dashboard com resumo do sistema.
- CRUD de produtos.
- CRUD de categorias.
- Filtro de produtos por nome e categoria.
- Login customizado.
- Navbar com usuario logado e botao sair a direita.
- Validacao de categoria:
  - nome obrigatorio
  - minimo de 3 caracteres
  - mensagens de erro exibidas em vermelho no formulario

## Observacao para testes

Se executar a classe de teste `CatalogoApplicationTests`, o sistema nao sobe interface web; apenas valida o contexto da aplicacao.
Para usar o sistema no navegador, execute a classe principal `CatalogoApplication`.
