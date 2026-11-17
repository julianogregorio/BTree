public class BTree {

    private NodeBTree raiz;
    private int grauMinimo; // Grau mínimo (t)

    public BTree(int grauMinimo) {
        this.raiz = null;
        this.grauMinimo = grauMinimo;
    }

    // Busca uma chave na árvore
    public NodeBTree buscar(int chave) {
        return (raiz == null) ? null : raiz.buscar(chave);
    }

    // Insere uma nova chave na árvore
    public void inserir(int chave) {

        if (raiz == null) {

            raiz = new NodeBTree(grauMinimo, true);
            raiz.chaves[0] = chave;
            raiz.qtdChaves = 1;

        } else {

            // Se a raiz estiver cheia, a árvore cresce em altura
            if (raiz.qtdChaves == 2 * grauMinimo - 1) {

                NodeBTree s = new NodeBTree(grauMinimo, false);

                s.filhos[0] = raiz;
                s.dividirFilho(0, raiz);

                int i = 0;

                if (s.chaves[0] < chave) {
                    i++;
                }

                s.filhos[i].inserirNaoCheio(chave);
                raiz = s;

            } else {

                raiz.inserirNaoCheio(chave);

            }
        }
    }

    // Imprime a árvore por níveis no console
    public void imprimir() {

        if (raiz != null) {
            raiz.percorrer(0);
        } else {
            System.out.println("Árvore B vazia.");
        }
    }
}