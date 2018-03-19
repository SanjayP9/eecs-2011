package A3Q1;


/**
 * Extends the TreeMap class to allow convenient access to entries
 * within a specified range of key values (findAllInRange).
 *
 * @author jameselder
 */
public class BSTRange<K, V> extends TreeMap<K, V> {

    /* Returns the lowest (deepest) position in the subtree rooted at pos
     * that is a common ancestor to positions with
     * keys k1 and k2, or to the positions they would occupy were they present.
     */
    public Position<Entry<K, V>> findLowestCommonAncestor(K k1, K k2, Position<Entry<K, V>> pos) {
        //implement this method
        if (this.left(pos) == null && this.right(pos) == null) {
            return pos;
        }
        if (this.compare(pos.getElement().getKey(), k1) > 0 && this.compare(pos.getElement().getKey(), k2) > 0 && this.left(pos).getElement() != null) {
            return findLowestCommonAncestor(k1, k2, this.left(pos));
        }
        if (this.compare(pos.getElement().getKey(), k1) < 0 && this.compare(pos.getElement().getKey(), k2) < 0 && this.right(pos).getElement() != null) {
            return findLowestCommonAncestor(k1, k2, this.right(pos));
        }
        return pos;
    }

    /* Finds all entries in the subtree rooted at pos  with keys of k or greater
     * and copies them to L, in non-decreasing order.
     */
    protected void findAllAbove(K k, Position<Entry<K, V>> pos, PositionalList<Entry<K, V>> L) {
        //implement this method
        if (left(pos).getElement() != null) {
            findAllAbove(k, left(pos), L);
        } else {
            if (this.compare(pos.getElement().getKey(), k) >= 0) {
                L.addLast(pos.getElement());
            }

            if (right(pos).getElement() != null) {
                findAllAbove(k, right(pos), L);
            }
        }
    }

    /* Finds all entries in the subtree rooted at pos with keys of k or less
     * and copies them to L, in non-decreasing order.
     */
    protected void findAllBelow(K k, Position<Entry<K, V>> pos, PositionalList<Entry<K, V>> L) {
        //implement this method
        if (left(pos).getElement() != null) {
            findAllBelow(k, this.left(pos), L);
        } else {
            if (this.compare(pos.getElement().getKey(), k) <= 0) {
                L.addLast(pos.getElement());
            }

            if (right(pos).getElement() != null) {
                findAllBelow(k, this.right(pos), L);
            }
        }
    }

    /* Returns all entries with keys no less than k1 and no greater than k2,
     * in non-decreasing order.
     */
    public PositionalList<Entry<K, V>> findAllInRange(K k1, K k2) {
        //implement this method
        PositionalList<Entry<K, V>> list = new LinkedPositionalList<>();

        Position<Entry<K, V>> LCA = findLowestCommonAncestor(k1, k2, root());


        if (this.size() != 0 && this.compare(k1, k2) <= 0 &&
                (!(this.compare(k1, root().getElement()) < 0 && this.compare(k2, root().getElement()) < 0)) &&
                (!(this.compare(k1, this.lastEntry()) > 0 && this.compare(k2, this.lastEntry()) > 0))) {

            if (left(LCA).getElement() != null) {
                findAllAbove(k1, left(LCA), list);
            }

            list.addLast(LCA.getElement());

            if (right(LCA).getElement() != null) {
                findAllBelow(k2, right(LCA), list);
            }
        }

        return list;
    }


    /*private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        if (left(p) != null)
            inorderSubtree(left(p), snapshot);
        snapshot.add(p);
        if (right(p) != null)
            inorderSubtree(right(p), snapshot);
    }

    public Iterable<Position<E>> inorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            inorderSubtree(root(), snapshot);   // fill the snapshot recursively
        return snapshot;
    }*/

    /*public void inorder(Position<Entry<K, V>> pos, PositionalList<Entry<K, V>> L) {
        if (!this.isEmpty()) {
            inorderSubTree(pos, L);
        }
    }

    private void inorderSubTree(Position<Entry<K, V>> pos, PositionalList<Entry<K, V>> L) {
        if (left(pos) != null) {
            inorderSubTree(left(pos), L);
        }
        L.addLast(pos.getElement());
        if (right(pos) != null) {
            inorderSubTree(right(pos), L);
        }
    }*/


    /*
     if (left(pos) != null) {
            findAllBelow(k, left(pos), L);
        }
        else {
            L.addLast(pos.getElement());

            if (right(pos) != null) {
                findAllBelow(k, right(pos), L);


    * if (!this.checkKey(pos.getElement().getKey()))
        {
            return;
        }

        while (super.left(temp) != null) {
            L.addLast(super.left(temp).getElement());
            temp = super.left(temp);
        }

        L.addLast(pos.getElement());
        temp = pos;

        while (super.right(temp) != null) {
            L.addLast(super.right(temp).getElement());
            temp = super.right(temp);
        }
        */
}