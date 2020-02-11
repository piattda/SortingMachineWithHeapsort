import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 * 
 * @author David Piatt & Paul Shortman
 * 
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     * 
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * 
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size
    
    @Test
    public final void testAddNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,"blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue","green");
        m.add("green");
        assertEquals(mExpected, m);
    }
    @Test
    public final void testAddMany(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,"blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue","green","brown","yellow");
        
        m.add("green");
        m.add("brown");
        m.add("yellow");
        
        assertEquals(mExpected, m);
    }

    
    @Test
    public final void testChangeToExtractionModeNonEmpty(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,"blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void changeToExtractionModeEmpty(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false
                );
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }
    
    
    
    @Test
    public final void testRemoveFirstToEmpty(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,"blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String returned = m.removeFirst();
        assertEquals(mExpected, m);
        assertEquals(returned,"blue");
    }
    
    @Test
    public final void testRemoveFirst(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,"blue","green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        String returned  = m.removeFirst();
        assertEquals(mExpected, m);
        assertEquals(returned, "blue");
    }
    
    @Test
    public final void testIsInInsertionMode(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,"blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue");
        boolean isInInsertionMode = m.isInInsertionMode();
        assertEquals(isInInsertionMode, mExpected.isInInsertionMode());
        assertEquals(m,mExpected);
    }
    @Test 
    public final void TestIsInInsertionModeFalse(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,"blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue");
        assertEquals(m.isInInsertionMode(),mExpected.isInInsertionMode());
        assertEquals(m,mExpected);
    }
    
    @Test
    public final void testOrder(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,"zeta","blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue","zeta");
        
        
        assertEquals(m.order(),mExpected.order());
        
    }
    
    @Test
    public final void testOrderInsertionModeFalse(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,"blue","zeta");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue","zeta");
        
        
        assertEquals(m.order(),mExpected.order());
        
    }
    
    @Test 
    public final void sizeNonZero(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,"blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue");
        
        assertEquals(m.size(), mExpected.size());
    }
    @Test
    public final void sizeZero(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(m.size(), mExpected.size());
    }
    
    @Test 
    public final void sizeNonZeroInsertionModeFalse(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,"blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue");
        
        assertEquals(m.size(), mExpected.size());
    }
    @Test
    public final void sizeZeroInsertionModeFalse(){
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(m.size(), mExpected.size());
    }
    
}
