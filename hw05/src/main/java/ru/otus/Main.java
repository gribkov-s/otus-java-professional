package ru.otus;

public class Main {
    public static void main(String[] args) {

        TestLoggingInterface testLogging =
                (TestLoggingInterface) IoC.createWithLogging(TestLoggingImpl.class);

        System.out.println("------------------------------------");
        long startTime = System.currentTimeMillis();

        testLogging.printName();
        Long sum1 = testLogging.sum(1L, 2L);
        Long sum2 = testLogging.sum(1L, 2L, 3L);
        Double sum3 = testLogging.sum(0.5, 0.5);
        Double share = testLogging.share(1000.00, 500.00);

        long endTime = System.currentTimeMillis();
        System.out.println("------------------------------------");
        System.out.println("Worked " + (endTime - startTime) + " ms");
    }
}
