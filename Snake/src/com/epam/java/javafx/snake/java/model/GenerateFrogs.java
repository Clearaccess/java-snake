package com.epam.java.javafx.snake.java.model;

/**
 * Created by Aleksandr_Vaniukov on 12/5/2016.
 */
public class GenerateFrogs {
    private long numGreenFrogs;
    private long numRedFrogs;
    private long numBlueFrogs;

    private long currentNumGreenFrogs;
    private long currentNumRedFrogs;
    private long currentNumBlueFrogs;

    public GenerateFrogs(){
        this.numBlueFrogs= Math.round(Constants.COEFF_BLUE_FROGS*Options.getNumFrog());
        this.numRedFrogs=Math.round(Constants.COEFF_RED_FROGS*Options.getNumFrog());
        this.numGreenFrogs=Math.round(Constants.COEFF_GREEN_FROGS*Options.getNumFrog());
        this.currentNumGreenFrogs=0;
        this.currentNumRedFrogs=0;
        this.currentNumBlueFrogs=0;
        correctNum();
    }

    public void decrementNumFrog(Frog frog){
        switch (frog.getType()){
            case Constants.GREEN_FROG: currentNumGreenFrogs--;break;
            case Constants.RED_FROG: currentNumRedFrogs--;break;
            case Constants.BLUE_FROG: currentNumBlueFrogs--;break;
        }
    }

    public Frog getFrog(int x, int y) {

        if (currentNumBlueFrogs < numBlueFrogs) {
            currentNumBlueFrogs++;
            return new BlueFrog(x, y);
        }

        if (currentNumRedFrogs < numRedFrogs) {
            currentNumRedFrogs++;
            return new RedFrog(x, y);
        }

        currentNumGreenFrogs++;
        return new GreenFrog(x, y);
    }

    private void correctNum(){
        while(numGreenFrogs+numRedFrogs+numBlueFrogs!=Options.getNumFrog()){
            if(numGreenFrogs+numRedFrogs+numBlueFrogs>Options.getNumFrog()){
                numGreenFrogs--;
                continue;
            }
            if(numGreenFrogs+numRedFrogs+numBlueFrogs<Options.getNumFrog()){
                numGreenFrogs++;
                continue;
            }
        }
    }
}
