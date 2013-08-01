package br.univali.portugol.ajuda;

/**
 *
 * @author Luiz Fernando
 */
final class TopicoImpl implements Topico
{
    private String titulo;
    private String conteudo;
    private String icone;
    private int ordem;
    private Topicos subTopicos;

    public TopicoImpl()
    {
        this.ordem = Integer.MAX_VALUE;
        this.subTopicos = new Topicos();
    }
    
    @Override
    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    @Override
    public String getConteudo()
    {
        return conteudo;
    }

    public void setConteudo(String conteudo)
    {
        this.conteudo = conteudo;
    }

    @Override
    public String getIcone()
    {
        return icone;
    }

    public void setIcone(String icone)
    {
        this.icone = icone;
    }    

    @Override
    public Topicos getSubTopicos()
    {
        return subTopicos;
    }

    @Override
    public int getOrdem()
    {
        return ordem;
    }

    @Override
    public void setOrdem(int ordem)
    {
        this.ordem = ordem;
    }
}
