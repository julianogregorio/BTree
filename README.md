# Árvore B (B-Tree) — Estrutura de Dados Multidirecional em Java

> Repositório desenvolvido para a atividade avaliativa da disciplina de **Estrutura de Dados** do **IFSULDEMINAS — Campus Machado (2026)**. Contém a fundamentação teórica, análise de complexidade e a implementação prática em Java de uma **Árvore B (B-Tree)** de grau mínimo t = 3.

---

## 📌 Sumário
- [Visão Geral](#-visão-geral)
- [Por que usar Árvores B? (O Gargalo de I/O)](#-por-que-usar-árvores-b-o-gargalo-de-io)
- [Propriedades Fundamentais](#-propriedades-fundamentais)
- [Operações Principais](#-operações-principais)
- [Estrutura do Repositório](#-estrutura-do-repositório)
- [Como Executar](#-como-executar)
- [Exemplo de Saída no Console](#-exemplo-de-saída-no-console)
- [Análise de Complexidade](#-análise-de-complexidade)
- [Aplicações Práticas no Mundo Real](#-aplicações-práticas-no-mundo-real)
- [Referências Bibliográficas](#-referências-bibliográficas)

---

## 📖 Visão Geral

A **Árvore B (B-Tree)** é uma estrutura de dados de busca auto-balanceada e multidirecional, projetada originalmente por Rudolf Bayer e Edward M. McCreight (1970). 

Diferente das árvores binárias convencionais (onde cada nó armazena apenas **1 chave** e possui no máximo **2 filhos**), a Árvore B permite que cada nó contenha **múltiplas chaves ordenadas** e **múltiplos ponteiros para subárvores filhas**.

---

## ⚡ Por que usar Árvores B? (O Gargalo de I/O)

Em sistemas que gerenciam bilhões de registros (como SGBDs e Sistemas de Arquivos), o volume total de dados supera a capacidade da memória RAM principal e precisa residir em dispositivos de **armazenamento secundário** (HDs e SSDs).

* **Gargalo de I/O:** O acesso ao disco magnético ou bloco SSD é milhares de vezes mais lento que o acesso à RAM.
* **Leitura em Páginas:** O sistema operacional lê dados do disco agrupados em **blocos/páginas** (ex: 4 KB ou 8 KB).
* **Solução da Árvore B:** Agrupa centenas ou milhares de chaves em uma única página de nó. Isso reduz dramaticamente a **altura da árvore** (h), permitindo localizar um elemento entre milhões de registros realizando apenas **3 a 5 acessos a disco**.

---

## 📐 Propriedades Fundamentais

Uma Árvore B definida por um **grau mínimo t >= 2** satisfaz estritamente as seguintes regras:

1. **Capacidade dos Nós Internos:** Cada nó (exceto a raiz) contém no mínimo t - 1 chaves e no máximo 2t - 1 chaves.
2. **Quantidade de Filhos:** Um nó interno com k chaves possui obrigatoriamente k + 1 filhos.
3. **Regra da Raiz:** A raiz possui no mínimo 1 chave (se a árvore não estiver vazia) e no máximo 2t - 1 chaves.
4. **Balanceamento Perfeito:** Todas as folhas residem exatamente no **mesmo nível/profundidade**.
5. **Ordenação Interna:** As chaves dentro de qualquer nó são mantidas sempre em ordem crescente: k1 < k2 < ... < kn.

---

## 🔄 Operações Principais

### 1. Busca (`buscar`)
Navega de forma multidirecional a partir da raiz. Em cada nó, realiza uma busca sequencial (ou binária) no vetor de chaves para determinar qual subárvore descendente seguir, descendo na hierarquia até encontrar a chave ou atingir uma folha nula.

### 2. Inserção (`inserir` + `dividirFilho`)
* As inserções ocorrem sempre em **nós folhas**.
* Se o nó folha estiver cheio (com 2t - 1 chaves), ocorre o transbordamento (*overflow*).
* O estouro é resolvido pela operação de **Divisão de Nó (*Split*)**: a chave mediana promove-se ao nó pai, e o restante das chaves divide-se em dois novos nós irmãos.
* Se a raiz transbordar, ela é dividida e uma nova raiz é criada, fazendo a árvore **crescer em altura para cima**.

### 3. Remoção (Conceitual)
* Se a chave estiver em um nó folha e o nó possuir mais que o mínimo de chaves, é removida diretamente.
* Caso o nó fique com menos de t - 1 chaves (*underflow*), tenta-se o **empréstimo/redistribuição** de uma chave de um nó irmão vizinho.
* Se os irmãos também estiverem no limite mínimo, realiza-se a **fusão (*Merge*)** entre dois nós irmãos e a chave do nó pai.

---

## 📁 Estrutura do Repositório

O projeto foi construído em Java seguindo o padrão modular e orientado a objetos:

```text
├── src/
│   ├── NodeBTree.java  # Representação dos nós, vetores de chaves, filhos e operações locais (Split)
│   ├── BTree.java      # Classe operacional da Árvore B (interface de inserção, busca e impressão)
│   └── App.java        # Classe principal executável com demonstração no console
└── README.md           # Documentação técnica do projeto
```

---

## 🛠️ Como Executar

### Pré-requisitos
* **Java Development Kit (JDK)** versão 11 ou superior instalada.

### Passo a Passo

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/julianogregorio/BTree
   cd BTree
   ```

2. **Compilar as classes Java:**
   ```bash
   javac App.java BTree.java NodeBTree.java
   ```

3. **Executar a aplicação:**
   ```bash
   java App
   ```

---

## 💻 Exemplo de Saída no Console

```text
=== DEMONSTRAÇÃO DE ÁRVORE B (Grau t = 3) ===

Inserindo valores...

Estrutura por níveis resultante:
Nível 0 [ 10 ]
Nível 1 [ 3 5 6 7 ]
Nível 1 [ 12 15 17 20 22 25 27 30 45 ]

--- Teste de Busca ---
Busca pela chave 17: ENCONTRADA com sucesso!
Busca pela chave 99: NÃO encontrada.
```

---

## 📊 Análise de Complexidade

| Operação | Melhor Caso | Caso Médio | Pior Caso | Complexidade de Espaço |
| :--- | :---: | :---: | :---: | :---: |
| **Busca** | O(1) | O(log_t N) | O(log_t N) | O(1) |
| **Inserção** | O(1) | O(log_t N) | O(log_t N) | O(1) |
| **Remoção** | O(1) | O(log_t N) | O(log_t N) | O(1) |

> **Nota:** Devido à alta taxa de ramificação (fator de ramificação 2t), o valor de log_t N resulta em uma altura de árvore extremamente baixa na prática.

---

## 🌍 Aplicações Práticas no Mundo Real

* **Sistemas de Gerenciamento de Bancos de Dados (SGBDs):** Utilizada nos índices primários e secundários do **PostgreSQL**, **MySQL (InnoDB)**, **Oracle DB** e **Microsoft SQL Server**.
* **Sistemas de Arquivos Operacionais:** Estruturação de diretórios e alocação de blocos em **NTFS** (Windows), **EXT4** (Linux), **HFS+ / APFS** (macOS) e **Btrfs**.
* **Mecanismos de Busca de Dados Massivos:** Indexação em motores como **Apache Lucene** e **Elasticsearch**.

---

## 📚 Referências Bibliográficas

1. **ASCENCIO**, Ana Fernanda Gomes; **ARAÚJO**, Graziela Santos de. *Estruturas de Dados, Algoritmos, Análise da Complexidade e Implementações em Java e C/C++*. 1ª ed. São Paulo: Pearson, 2010.
2. **BAYER**, Rudolf; **MCCREIGHT**, Edward M. *Organization and Maintenance of Large Ordered Indices*. Acta Informatica, v. 1, p. 173–189, 1972.
3. **CORMEN**, Thomas H. et al. *Algoritmos: Teoria e Prática*. 3ª ed. Rio de Janeiro: Elsevier, 2012.

---

## 👤 Autores

**Juliano Gregório**
**Viviane Tamíris**
Desenvolvido como atividade prática da disciplina de **Estrutura de Dados** (IFSULDEMINAS — Campus Machado, 2026).