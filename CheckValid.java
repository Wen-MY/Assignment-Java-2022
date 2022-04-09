public class CheckValid {
    private int lowerLimit, upperLimit;
    private int intInput;
    private String[] validInput;
    private String stringInput;
    

    public CheckValid(int initLower, int initUpper, int initInput){
        lowerLimit = initLower;
        upperLimit = initUpper;
        intInput = initInput;
    }
    public CheckValid(String[] initValidInput, String initInput){
        validInput = initValidInput;
        stringInput = initInput;
    }
    public Boolean checkIntValid(){
        if (intInput >= lowerLimit && intInput <= upperLimit){
            return true;
        }

        return false;
    }
    public Boolean checkStirngValid(){
        for (int i=0; i<validInput.length; i++){
            if (stringInput.equals(validInput[i])){
                return true;
            }
        }
        return false;
    }
    public void setInput(int input){
        intInput = input;
    }
    public void setInput(String input){
        stringInput = input;
    }
}
