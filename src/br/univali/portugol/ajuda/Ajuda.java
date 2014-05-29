package br.univali.portugol.ajuda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Ajuda 
{
    private static final Pattern padraoCaminho = Pattern.compile("^(/([^/])+)+$");
    
    private Topicos topicos;

    Ajuda()
    {
        topicos = new Topicos();
    }

    public Topicos getTopicos()
    {
        return topicos;
    }
    
    public Topico obterTopico(String caminho) throws ErroCaminhoTopicoInvalido, ErroTopicoNaoEncontrado
    {
        if (caminhoValido(caminho))
        {
            List<String> titulos = extrairTitulos(caminho);
            
            Topicos subtopicos = null;
            Topico topico = this.topicos.obter(titulos.get(0));
            
            if (topico != null)
            {            
                for (int i = 1; i < titulos.size(); i++)
                {
                    if (topico != null)
                    {
                        subtopicos = topico.getSubTopicos();
                        topico = subtopicos.obter(titulos.get(i));
                    }
                }
            }
            
            if (topico != null)
            {            
                return topico;
            }
            
            throw new ErroTopicoNaoEncontrado(caminho);
        }
        
        throw new ErroCaminhoTopicoInvalido(caminho);
    }
    
    private boolean caminhoValido(String caminho) throws ErroCaminhoTopicoInvalido
    {
        return (caminho != null && padraoCaminho.matcher(caminho).find());
    }
    
    private List<String> extrairTitulos(String caminho)
    {
        List<String> titulos = new ArrayList<>();
        
        titulos.addAll(Arrays.asList(caminho.split("/")));
        titulos.remove(0);
        
        return titulos;
    }
}