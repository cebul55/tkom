package Lekser;

class CharacterChecker {
    static boolean isNumeric(char atom){
        return( 48 <= atom && 57 >= atom );
    }

    public static boolean isNumericWithoutZero( char atom){
        return( 49 <= atom && 57 >= atom);
    }

    static boolean isAlphabeticalCharacter(char atom){
        return((65 <= atom && atom <= 90) || (97 <= atom && atom <= 122));
    }

    static boolean isAcceptableInIdentifier(char atom){
        //checks second and next chars in identifiers
        return((65 <= atom && atom <= 90) || (97 <= atom && atom <= 122)
                || atom == 95 || isNumeric(atom));
    }

    static boolean isDot(char atom){
        return atom == 46;
    }
}
