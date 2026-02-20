# Agência de Viagens

Aplicação Java estruturada em camadas (model, repo e service), compilada manualmente utilizando Java 25.0.2.

---

## Versão do Java

Este projeto utiliza:

**Java 25.0.2**

Verifique sua versão instalada:

```bash
java -version
```

---

## Estrutura do Projeto

```
src/
 ├── Interface.java
 ├── model/
 │    └── ...
 ├── repo/
 │    └── ...
 └── service/
      └── ...
bin/ (gerado após compilação)
```

### Organização em Camadas

- `model` → Classes de domínio (entidades)
- `repo` → Camada de persistência de dados
- `service` → Regras de negócio
- `seed` → Seed data predefinido
- `storage` → Arquivos de armazenamewnto
- `Interface.java` → Ponto de entrada da aplicação

---

## Como Executar

Compile com:

```bash
javac -d bin src/*.java src/model/*.java src/repo/*.java src/service/*.java src/seed/*.java
```

E depois rode a aplicação:

```bash
java -cp bin Interface
```

---

# Testando a Aplicação

A aplicação já possui **dados pré-carregados (seed data)** para facilitar os testes.

---

## Localizações Disponíveis

Para testar a criação de viagens, utilize uma das seguintes cidades:

- São Paulo  
- Florianópolis  
- Rio de Janeiro  
- Belo Horizonte  
- Salvador  
- Curitiba  

---

## Período para Testes

As viagens devem:

- **Iniciar em:** 20/02/2025  
- **Finalizar até:** 25/02/2025  

---

## Acesso Administrativo

Existe um usuário administrador disponível no seed data:

- **Usuário:** `admin`  
- **Senha:** `admin`  

Utilize esse login para validar as funcionalidades administrativas do sistema.

---

## Requisitos

- Java 25.0.2 instalado

---
