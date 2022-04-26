package br.com.caelum.estoque.ws;

import br.com.caelum.estoque.modelo.item.*;
import br.com.caelum.estoque.modelo.usuario.AutorizacaoException;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;
import br.com.caelum.estoque.modelo.usuario.Usuario;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED
)
public class EstoqueWS {

    private ItemDao dao = new ItemDao();

    @WebMethod(operationName = "TodosOsItens")
    @WebResult(name = "itens")
    public ListaItens getItens(@WebParam(name = "filtros")Filtros filtros){
        System.out.println("Chamando getItens");
        List<Filtro> listaFiltros = filtros.getLista();
        List<Item> lista = dao.todosItens(listaFiltros);

//        List<Item> lista = dao.todosItens();

        return new ListaItens(lista);
    }

    @WebMethod(operationName = "CadastrarItem")
    @WebResult(name = "item")
    public Item cadastrarItem(@WebParam(name = "token", header = true)TokenUsuario token, @WebParam(name = "usuario")Usuario usuario, @WebParam(name = "item")Item item) throws AutorizacaoException {

        System.out.println("Cadastrando item " + item + ", Token: " + token);

//        boolean valido = new TokenDao().ehValido(token);
//
//        if(!valido){
//            throw new AutorizacaoException("Autorização falhou");
//        }

        if(!new TokenDao().ehValido(token)){
            throw new AutorizacaoException("Autorização falhou");
        }

        new ItemValidador(item).validate();

        this.dao.cadastrar(item);
        return item;
    }

    @WebMethod(action = "CadastrarItem2", operationName = "CadastrarItem2")
    @WebResult(name = "item")
    public Item cadastrarItem2(@WebParam(name = "token", header = true)TokenUsuario token, @WebParam(name = "usuario")Usuario usuario, @WebParam(name = "item")Item item) throws AutorizacaoException {

        return item;
    }

}
