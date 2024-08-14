package LP2.Trabalho_final.exception.handler;

public class RegistroNaoEcontradoException extends RuntimeException {

    public RegistroNaoEcontradoException(String mensagem) {
        super(mensagem);
    }
}
