public class NodeBTree {

    int[] chaves; // Vetor de chaves armazenadas no nó
    int grauMinimo; // Grau mínimo da Árvore B (t)
    NodeBTree[] filhos; // Vetor de ponteiros para os nós filhos
    int qtdChaves; // Número atual de chaves no nó
    boolean ehFolha; // Indica se é nó folha (true) ou nó interno (false)

    public NodeBTree(int grauMinimo, boolean ehFolha) {

        this.grauMinimo = grauMinimo;
        this.ehFolha = ehFolha;

        this.chaves = new int[2 * grauMinimo - 1]; // Capacidade máxima: 2t - 1 chaves
        this.filhos = new NodeBTree[2 * grauMinimo]; // Máximo de 2t filhos

        this.qtdChaves = 0;
    }

    // Busca recursiva de uma chave a partir deste nó
    public NodeBTree buscar(int chave) {

        int i = 0;

        while (i < qtdChaves && chave > chaves[i]) {
            i++;
        }

        if (i < qtdChaves && chaves[i] == chave) {
            return this; // Chave encontrada no nó atual
        }

        if (ehFolha) {
            return null; // Chegou à folha e não encontrou a chave
        }

        return filhos[i].buscar(chave); // Desce recursivamente para o filho correspondente
    }

    // Inserção em um nó que garante ter espaço disponível (não cheio)
    public void inserirNaoCheio(int chave) {

        int i = qtdChaves - 1;

        if (ehFolha) {

            // Desloca elementos maiores para a direita e insere a nova chave
            while (i >= 0 && chaves[i] > chave) {
                chaves[i + 1] = chaves[i];
                i--;
            }

            chaves[i + 1] = chave;
            qtdChaves++;

        } else {

            // Localiza o filho que deve receber a chave
            while (i >= 0 && chaves[i] > chave) {
                i--;
            }

            i++;

            // Se o filho selecionado estiver cheio (2t - 1 chaves),
            // realiza a divisão (Split)
            if (filhos[i].qtdChaves == 2 * grauMinimo - 1) {

                dividirFilho(i, filhos[i]);

                if (chaves[i] < chave) {
                    i++;
                }
            }

            filhos[i].inserirNaoCheio(chave);
        }
    }

    // Divisão de nó (Split): divide o filho 'y' (na posição 'i') que está cheio
    public void dividirFilho(int i, NodeBTree y) {

        NodeBTree z = new NodeBTree(y.grauMinimo, y.ehFolha);

        z.qtdChaves = grauMinimo - 1;

        // Copia as últimas (grauMinimo - 1) chaves de 'y' para o novo nó 'z'
        for (int j = 0; j < grauMinimo - 1; j++) {
            z.chaves[j] = y.chaves[j + grauMinimo];
        }

        // Copia os ponteiros de filhos de 'y' para 'z' (caso não seja folha)
        if (!y.ehFolha) {

            for (int j = 0; j < grauMinimo; j++) {
                z.filhos[j] = y.filhos[j + grauMinimo];
            }
        }

        y.qtdChaves = grauMinimo - 1; // Reduz a contagem de chaves de 'y'

        // Abre espaço no nó pai para o ponteiro do novo filho 'z'
        for (int j = qtdChaves; j >= i + 1; j--) {
            filhos[j + 1] = filhos[j];
        }

        filhos[i + 1] = z;

        // Promove a chave mediana de 'y' para o nó pai
        for (int j = qtdChaves - 1; j >= i; j--) {
            chaves[j + 1] = chaves[j];
        }

        chaves[i] = y.chaves[grauMinimo - 1];

        qtdChaves++;
    }

    // Exibição da estrutura da árvore por níveis
    public void percorrer(int nivel) {

        System.out.print("Nível " + nivel + " [ ");

        for (int i = 0; i < qtdChaves; i++) {
            System.out.print(chaves[i] + " ");
        }

        System.out.println("]");

        if (!ehFolha) {

            for (int i = 0; i <= qtdChaves; i++) {

                if (filhos[i] != null) {
                    filhos[i].percorrer(nivel + 1);
                }
            }
        }
    }
}