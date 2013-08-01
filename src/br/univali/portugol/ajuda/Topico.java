package br.univali.portugol.ajuda;

/**
 * @author Luiz Fernando Noschang
 */
public interface Topico
{
    public String getTitulo();
    
    public String getConteudo();
    
    public String getIcone();
    
    public Topicos getSubTopicos();
    
    public int getOrdem();
    
    public void setOrdem(int ordem);
}
