public class App {

    public static void main(String[] args) {

        System.out.println("=== DEMONSTRAÇÃO DE ÁRVORE B (Grau t = 3) ===");

        BTree arvore = new BTree(3);

        // Suporta até 5 chaves por nó (2t - 1)
        int[] valores = {
            10, 20, 5, 6, 12, 30, 7, 17,
            3, 22, 27, 25, 15, 45, 2, 1
        };

        System.out.println("\nInserindo valores...");

        for (int v : valores) {
            arvore.inserir(v);
        }

        System.out.println("\nEstrutura por níveis resultante:");

        arvore.imprimir();

        System.out.println("\n--- Teste de Busca ---");

        int chaveBusca = 17;

        NodeBTree resultado = arvore.buscar(chaveBusca);

        System.out.println(
            "Busca pela chave " + chaveBusca + ": "
            + (resultado != null
                ? "ENCONTRADA com sucesso!"
                : "NÃO encontrada.")
        );
    }
}