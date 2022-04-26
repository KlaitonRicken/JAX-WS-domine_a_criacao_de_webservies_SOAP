package br.com.caelum.estoque.modelo.usuario;

import javax.xml.ws.WebFault;

@WebFault(name = "AutorizacaoFault")
public class AutorizacaoException extends Exception {
    public AutorizacaoException(String tokenInvalido) {
        super(tokenInvalido);
    }

    public String getFaultInfo() {
        return "Token inválido";
    }
}
