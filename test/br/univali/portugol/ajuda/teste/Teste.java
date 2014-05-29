package br.univali.portugol.ajuda.teste;

import br.univali.portugol.ajuda.Ajuda;
import br.univali.portugol.ajuda.CarregadorAjuda;
import br.univali.portugol.ajuda.ObservadorCarregamentoAjuda;
import br.univali.portugol.ajuda.PreProcessadorConteudo;
import br.univali.portugol.ajuda.Topico;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Luiz Fernando Noschang
 */
final class Teste implements ObservadorCarregamentoAjuda, PreProcessadorConteudo
{
    private static Pattern padraoTagPre = Pattern.compile("<pre[^>]*>(?<conteudo>[^<]*)</pre>", Pattern.CASE_INSENSITIVE);
    private static Pattern padraoAtributoClass = Pattern.compile("(class)[^=]*=[^(\"|')]*(\"|')(?<valor>[^(\"|')]*)(\"|')");
    
    public static void main(String[] args) throws Exception
    {
        String s = 
                "<pre class=\"codigo-portugol\">\n" +
"programa\n" +
"{\n" +
"    //Função com retorno do tipo vazio sem parâmetro\n" +
"    funcao vazio imprime_linha()\n" +
"    {\n" +
"        escreva(\"\\n-----------------------------\\n\")	\n" +
"    }\n" +
"\n" +
"    //Função com retorno do tipo vazio e com um vetor como parâmetro\n" +
"    funcao inicio(cadeia argumentos[])\n" +
"    {\n" +
"        //Imprime o retorno da função media\n" +
"        escreva(media(4,9,8))\n" +
"\n" +
"        imprime_linha()\n" +
"\n" +
"        inteiro variavel = 123\n" +
"\n" +
"        zera_valor(variavel) \n" +
"\n" +
"        //Imprime 0\n" +
"        escreva(variavel) \n" +
"    }\n" +
"\n" +
"    //Função com retorno do tipo real e três parâmetros do tipo inteiro\n" +
"    funcao real media(inteiro m1, inteiro m2, inteiro m3) \n" +
"    {\n" +
"        retorne (m1 * 2 + m2 * 3 + m3 * 8) / 13.0	\n" +
"    }\n" +
"\n" +
"    //Função com retorno vazio e parâmetro por referência\n" +
"    funcao zera_valor(inteiro &valor)\n" +
"    {\n" +
"        valor = 0\n" +
"    }\n" +
"}\n" +
"            </pre>";
        Matcher avaliadorTagPre = padraoTagPre.matcher(s);
        
        while (avaliadorTagPre.find())
        {
            String tag = avaliadorTagPre.group();
            Matcher avaliadorAtributoClass = padraoAtributoClass.matcher(tag);
            
            if (avaliadorAtributoClass.find())
            {
                String conteudo = avaliadorTagPre.group("conteudo");
                
                System.out.println(conteudo.trim());
                
            }
        }
        //Teste teste = new Teste();
        //teste.testar();        
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
    public String processar(String conteudo, Topico topico)
    {
        conteudo = conteudo.replace("<button>", "<div>");
        conteudo = conteudo.replace("</button>", "</div>");
        
        return conteudo;
    }
}
