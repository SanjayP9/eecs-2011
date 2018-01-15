package A1Q1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 *
 * @author jameselder
 */
public class testSparseNumericVector {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SparseNumericVector X = new SparseNumericVector();
        SparseNumericVector Y = new SparseNumericVector();
        double projection;

        X.add(new SparseNumericElement(150000, 3.1415));
        X.add(new SparseNumericElement(15, 3));
        X.add(new SparseNumericElement(1500, 3.14));
        X.add(new SparseNumericElement(150, 3.1));
        X.add(new SparseNumericElement(15000, 3.141));
        Y.add(new SparseNumericElement(150000, 1));
        Y.add(new SparseNumericElement(15, 1));
        X.remove((long) 150);

        projection = X.dot(Y);

        System.out.println("The inner product of");
        System.out.print(X.toString());
        System.out.println("and");
        System.out.print(Y.toString());
        System.out.println("is ");

       System.out.printf("%.5f\n\n",projection); //answer should be 3*1 + 3.1415*1 = 6.1415
    }

    @Test
    public static double dotTest(SparseNumericVector X, SparseNumericVector Y, double testResult)
    {
        SparseNumericIterator xIterate = new SparseNumericIterator(X);
        SparseNumericIterator yIterate = new SparseNumericIterator(Y);

        List<SparseNumericElement> xList = new ArrayList<>();
        List<SparseNumericElement> yList = new ArrayList<>();

        while(xIterate.hasNext())
        {
            xList.add(xIterate.position.getElement());
            xIterate.next();
        }
        while(yIterate.hasNext())
        {
            yList.add(yIterate.position.getElement());
            yIterate.next();
        }

        double result = 0;

        for (int i =0; i < xList.size();i++)
        {
            for (int j = 0; j < yList.size();j++)
            {
                if (xList.get(i).getIndex() == yList.get(j).getIndex())
                {
                    result += xList.get(i).getValue() * yList.get(j).getValue();
                }
            }
        }

        return result;
    }
}