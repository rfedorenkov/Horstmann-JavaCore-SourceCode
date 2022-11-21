package corejava.v2ch12.printf4;

import java.io.PrintWriter;

/**
 * @version 1.10 1997-07-01
 * @author Cay Horstmann
 */
class Printf4Test {
    public static void main(String[] args) {
        double price = 44.95;
        double tax = 7.75;
        double amountDue = price * (1 + tax / 100);
        PrintWriter out = new PrintWriter(System.out);
        /* При этом вызове может быть сгенерировано исключение,
         * если в форматирующей строке отсутствует подстрока "%%" */
        Printf4.fprint(out, "Amount due = %%8.2f\n", amountDue);
        out.flush();
    }
}