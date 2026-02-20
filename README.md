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
 ├── Main.java
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
- `Interface.java` → Ponto de entrada da aplicação


---

## Como Executar


Compile com:

```bash
javac -d bin src/*.java src/model/*.java src/repo/*.java src/service/*.java
```

E depois rode a aplicação:

```bash
java -cp bin Interface
```

---

## Limpar Arquivos Compilados

Linux / macOS:

```bash
rm -rf bin
```

Windows:

```cmd
rmdir /s bin
```

---

## Requisitos

- Java 25.0.2 instalado

---