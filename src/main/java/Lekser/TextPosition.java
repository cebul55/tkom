package Lekser;

class TextPosition {
    public int numberOfLine;
    public int numberOfChar;

    public TextPosition(int numberOfLine , int numberOfChar ){
        this.numberOfLine = numberOfLine;
        this.numberOfChar = numberOfChar;
    }

    public TextPosition(){
        this.numberOfLine = 0;
        this.numberOfChar = 0;
    }


}
