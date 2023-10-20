package view.extras;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Grácio Macuácua
 */

public class Formatador extends PlainDocument{
    public enum TipoEntrada{
        NUMERO_INTEIRO, NUMERO_DECIMAL, TEXTO, TITULO, EMAIL, ID, ENDERECO;
    };
    
    private int qtdCaracteres;
    private TipoEntrada tipoEntrada;

    public Formatador(int qtdCaracteres, TipoEntrada tipoEntrada) {
        this.qtdCaracteres = qtdCaracteres;
        this.tipoEntrada = tipoEntrada;
    }

    @Override
    public void insertString(int i, String string, AttributeSet as) throws BadLocationException {
        if(string == null || getLength() == qtdCaracteres){
            return;
        }
        int totalCaractere = getLength() + string.length(); 
        
        //FILTRA OS CARACTERES

        String regex = "";
        
        switch(tipoEntrada){
            case NUMERO_INTEIRO: regex = "[^0-9]";break;
            case NUMERO_DECIMAL: regex = "[^0-9.]";break;
            case TEXTO:          regex = "[^\\p{IsLatin} ]";break;
            case TITULO:         regex = "[^\\p{IsLatin} .;,?!#%$*/+&()-]";break;
            case ID:             regex = "[^0-9A-Z]";break;
            case EMAIL:          regex = "[^0-9a-zA-Z@._-]";break;
            case ENDERECO:       regex = "[^\\p{IsLatin} /-]";break;
        }
        
        //SUBSTITUICAO
        
        string = string.replaceAll(regex, "");
        
        if(totalCaractere <= qtdCaracteres){
            super.insertString(i, string, as); 
        }else {
            String nova = string.substring(0, qtdCaracteres);
            super.insertString(i, string, as); 
        }
    }
    
}
