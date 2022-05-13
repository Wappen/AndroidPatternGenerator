package me.wappen.constraints;

public class AndroidPatternConstrainer extends Constrainer {
    public AndroidPatternConstrainer() {
        addConstraint(AndroidUtil::patternConstraint);
    }
}
