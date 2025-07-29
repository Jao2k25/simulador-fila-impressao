import java.util.NoSuchElementException;

/**
 * Classe que representa um documento a ser impresso.
 */
class Documento {
    private String nomeArquivo;
    private int numeroPaginas;

    /**
     * Construtor da classe Documento.
     * @param nomeArquivo Nome do arquivo do documento.
     * @param numeroPaginas Número de páginas do documento.
     */
    public Documento(String nomeArquivo, int numeroPaginas) {
        this.nomeArquivo = nomeArquivo;
        this.numeroPaginas = numeroPaginas;
    }

    /**
     * Retorna o nome do arquivo.
     * @return String contendo o nome do arquivo.
     */
    public String getNomeArquivo() {
        return nomeArquivo;
    }

    /**
     * Retorna o número de páginas.
     * @return Inteiro representando o número de páginas.
     */
    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    /**
     * Sobrescreve o método toString para exibição formatada do documento.
     * @return String formatada com nome e número de páginas.
     */
    @Override
    public String toString() {
        return "Documento: " + nomeArquivo + " (" + numeroPaginas + " páginas)";
    }
}

/**
 * Classe que representa um nó na implementação da fila encadeada.
 */
class No {
    Documento documento;
    No proximo;

    /**
     * Construtor da classe No.
     * @param documento O documento a ser armazenado no nó.
     */
    public No(Documento documento) {
        this.documento = documento;
        this.proximo = null;
    }
}

/**
 * Implementação da estrutura de dados Fila (Queue) para a fila de impressão,
 * utilizando uma lista encadeada.
 */
class FilaImpressao {
    private No inicio; // Ponteiro para o início da fila (primeiro a sair)
    private No fim;    // Ponteiro para o fim da fila (último a entrar)
    private int tamanho;

    /**
     * Construtor da classe FilaImpressao. Inicializa uma fila vazia.
     */
    public FilaImpressao() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    /**
     * Verifica se a fila de impressão está vazia.
     * @return true se a fila estiver vazia, false caso contrário.
     */
    public boolean estaVazia() {
        return (inicio == null);
        // Ou return tamanho == 0;
    }

    /**
     * Adiciona um documento ao final da fila de impressão (enqueue).
     * @param documento O documento a ser adicionado.
     */
    public void enfileirar(Documento documento) {
        No novoNo = new No(documento);
        if (estaVazia()) {
            // Se a fila está vazia, o novo nó é tanto o início quanto o fim.
            inicio = novoNo;
            fim = novoNo;
        } else {
            // Se a fila não está vazia, o novo nó é adicionado após o fim atual.
            fim.proximo = novoNo;
            fim = novoNo; // Atualiza o ponteiro do fim para o novo nó.
        }
        tamanho++;
        System.out.println("" + documento.getNomeArquivo() + " adicionado à fila.");
    }

    /**
     * Remove e retorna o documento do início da fila de impressão (dequeue).
     * @return O documento que estava no início da fila.
     * @throws NoSuchElementException se a fila estiver vazia.
     */
    public Documento desenfileirar() {
        if (estaVazia()) {
            throw new NoSuchElementException("Erro: A fila de impressão está vazia!");
        }
        
        Documento documentoRemovido = inicio.documento;
        inicio = inicio.proximo; // Move o início para o próximo nó.

        // Se o início se tornar null, significa que a fila ficou vazia, então o fim também deve ser null.
        if (inicio == null) {
            fim = null;
        }
        tamanho--;
        return documentoRemovido;
    }

    /**
     * Retorna o documento do início da fila sem removê-lo (peek).
     * @return O documento no início da fila.
     * @throws NoSuchElementException se a fila estiver vazia.
     */
    public Documento primeiro() {
        if (estaVazia()) {
            throw new NoSuchElementException("Erro: A fila de impressão está vazia!");
        }
        return inicio.documento;
    }

    /**
     * Retorna o número de documentos na fila.
     * @return A quantidade de documentos.
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * Exibe todos os documentos na fila, do início para o fim.
     */
    public void exibirFila() {
        if (estaVazia()) {
            System.out.println("A fila de impressão está vazia.");
            return;
        }

        System.out.println("\n=== Fila de Impressão (Ordem: Primeiro a entrar -> Último a entrar) ===");
        No atual = inicio;
        int posicao = 1;
        while (atual != null) {
            System.out.println(posicao + ". " + atual.documento);
            atual = atual.proximo;
            posicao++;
        }
        System.out.println("Total de documentos na fila: " + tamanho);
    }
}

/**
 * Classe principal que simula o funcionamento da fila de impressão.
 */
public class SimuladorImpressao {

    public static void main(String[] args) {
        FilaImpressao fila = new FilaImpressao();

        System.out.println("===== SIMULADOR DE FILA DE IMPRESSÃO =====");

        // Adicionando documentos à fila
        System.out.println("\n> Adicionando documentos...");
        fila.enfileirar(new Documento("Relatorio_Anual.pdf", 50));
        fila.enfileirar(new Documento("Apresentacao_Marketing.pptx", 25));
        fila.enfileirar(new Documento("Planilha_Custos.xlsx", 5));
        fila.enfileirar(new Documento("Convite_Evento.docx", 1));

        // Exibindo a fila
        fila.exibirFila();

        // Simulando a impressão
        System.out.println("\n> Simulando impressão...");
        try {
            Documento docImpresso1 = fila.desenfileirar();
            System.out.println("Imprimindo: " + docImpresso1);

            Documento docImpresso2 = fila.desenfileirar();
            System.out.println("Imprimindo: " + docImpresso2);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        // Exibindo a fila após a impressão
        fila.exibirFila();

        // Verificando o próximo documento
        System.out.println("\n> Verificando próximo documento...");
        try {
            Documento proximo = fila.primeiro();
            System.out.println("Próximo a ser impresso: " + proximo);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        // Adicionando mais um documento
        System.out.println("\n> Adicionando mais um documento...");
        fila.enfileirar(new Documento("Manual_Usuario.pdf", 150));

        // Exibindo a fila final
        fila.exibirFila();

        // Tentando imprimir tudo
        System.out.println("\n> Imprimindo documentos restantes...");
        while (!fila.estaVazia()) {
            try {
                Documento docImpresso = fila.desenfileirar();
                System.out.println("Imprimindo: " + docImpresso);
            } catch (NoSuchElementException e) {
                // Não deve acontecer aqui por causa do while loop
                System.out.println(e.getMessage()); 
            }
        }

        // Verificando se a fila está vazia no final
        fila.exibirFila();
        System.out.println("\nSimulação concluída.");
    }
}
