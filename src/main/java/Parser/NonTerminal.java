package Parser;

/**
 * {@code NonTerminal} class represents nonterminal symbol.
 */
public class NonTerminal extends Symbol{

    public NonTerminal(int code, String name) {
        super(code, name);
    }

    public boolean isTerminal() {
        return false;
    }

    public boolean isNonTerminal() {
        return true;
    }

    @Override
    public boolean equals(Object object){
        if(object == this)
            return true;
        if(object == null)
            return false;
        if(object.getClass() != NonTerminal.class)
            return false;
        NonTerminal tmpNonTerminal = (NonTerminal) object;
        return this.getCode() == tmpNonTerminal.getCode();
    }
}
