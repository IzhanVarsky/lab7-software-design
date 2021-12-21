package application.profiled;

import static application.profiled.RandomJobKt.randomJob;

public class First {
    public void method1(String s) {
        randomJob(0, 20);
    }

    public void method1(String s, int i) {
        randomJob(0, 30);
    }

    public void method2(int i, int j) {
        randomJob(0, 40);
    }

    public void method3(String s) {
        randomJob(0, 50);
    }
}
