package A3Q1;

import java.util.Iterator;

public class test {
    public test()
    {
        BSTRange<Integer, String> medals = new BSTRange<Integer, String>();
        PositionalList<Entry<Integer, String>> medalList;
        Iterator<Entry<Integer, String>> entryIter;
        int k1 = 3;
        int k2 = 7;

        medals.put(1, "Norway");
        medals.put(2, "Germany");
        medals.put(3, "Canada");
        medals.put(4, "USA");
        medals.put(5, "Netherlands");
        medals.put(6, "Sweden");
        medals.put(7, "South Korea");
        medals.put(8, "Switzerland");
        medals.put(9, "France");
        medals.put(10, "Austria");

        PositionalList<Entry<Integer, String>> L = new LinkedPositionalList<>();

        //medals.inorder(medals.root(), L);

        //System.out.println(L.toString());

        //medals.findLowestCommonAncestor(k1,k2,medals.root());

       medalList = medals.findAllInRange(k1, k2);
        entryIter = medalList.iterator();

        System.out.println("The countries ranked from " + k1 + " to " + k2 + " in medal standings are:");
        while (entryIter.hasNext()) {
            String temp = (entryIter.next().getValue());
            if (temp == null)
            {
                temp ="null";
            }

            System.out.println(temp);
        }
    }

    public static void main(String[] args) {
        test reeee = new test();
    }
}
