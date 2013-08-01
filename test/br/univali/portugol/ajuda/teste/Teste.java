package br.univali.portugol.ajuda.teste;

import br.univali.portugol.ajuda.Ajuda;
import br.univali.portugol.ajuda.CarregadorAjuda;
import br.univali.portugol.ajuda.ObservadorCarregamentoAjuda;
import br.univali.portugol.ajuda.PreProcessadorConteudo;
import br.univali.portugol.ajuda.Topico;
import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 */
final class Teste implements ObservadorCarregamentoAjuda, PreProcessadorConteudo
{
    public static void main(String[] args) throws Exception
    {
        Teste teste = new Teste();
        teste.testar();        
    }
    
    private void testar() throws Exception
    {
        File diretorioAjuda = new File("./ajuda");
        
        CarregadorAjuda carregadorAjuda = new CarregadorAjuda();
        carregadorAjuda.adicionarObservadorCarregamento(this);
        carregadorAjuda.adicionarPreProcessadorConteudo(this);
        
        Ajuda ajuda = carregadorAjuda.carregar(diretorioAjuda);
        
        //imprimirAjuda(ajuda);
        
        Topico topico = ajuda.obterTopico("/Raiz/Tópico 2/Subtópico 2.2");
        
        System.out.println(topico.getConteudo());
                
    }
    
    private void imprimirAjuda(Ajuda ajuda)
    {
        for (Topico topico : ajuda.getTopicos())
        {
            imprimirTopico(topico, 1);
        }
    }            
    
    private void imprimirTopico(Topico topico, int nivel)
    {
        for (int i = 0; i < nivel; i++)
        {
            System.out.print("-----");
        }

        System.out.println(" " + topico.getTitulo());

        if (!topico.getSubTopicos().vazio())
        {
            for (Topico subTopico : topico.getSubTopicos())
            {
                imprimirTopico(subTopico, nivel + 1);
            }
        }
    }
    
    private int numeroTopicos;

    @Override
    public void carregamentoAjudaIniciado(int numeroTopicos) 
    {
        this.numeroTopicos = numeroTopicos;
        System.out.println("Carregando ajuda: " + numeroTopicos + " tópicos");
    }

    @Override
    public void carregamentoTopicoIniciado(int indiceTopico)
    {
    }

    @Override
    public void carregamentoTopicoFinalizado(int indiceTopico)
    {
        int perc = (indiceTopico * 100) / numeroTopicos;
        System.out.println("Carregando tópico " + indiceTopico + " de " + numeroTopicos + "(" + perc + "%)");
    }

    @Override
    public void carregamentoAjudaFinalizado()
    {
        System.out.println("Ajuda carregada");
        System.out.println("");
    }

    @Override
    public String processar(String conteudo)
    {
        conteudo = conteudo.replace("<button>", "<div>");
        conteudo = conteudo.replace("</button>", "</div>");
        
        return conteudo;
    }
}
