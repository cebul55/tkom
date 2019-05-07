package Parser;

/**
 * The {@code Terminal} class represents a terminal grammar symbol.
 */
class Terminal extends Symbol {

    public Terminal(int code, String name){
        super(code, name);
    }

    public boolean isTerminal() {
        return true;
    }

    public boolean isNonTerminal() {
        return false;
    }

    @Override
    public boolean equals(Object object){
        if(object == this)
            return true;
        if(object == null)
            return false;
        if(object.getClass() != Terminal.class)
            return false;
        Terminal tmpTerminal = (Terminal)object;
        return this.getCode() == tmpTerminal.getCode();
    }
}
