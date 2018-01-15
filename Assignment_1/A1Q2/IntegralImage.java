package A1Q2;

/**
 * Represents an integer integral image, which allows the user to query the mean
 * value of an arbitrary rectangular subimage in O(1) time.  Uses O(n) memory,
 * where n is the number of pixels in the image.
 *
 * @author jameselder
 */
public class IntegralImage {

    private final int[][] integralImage;
    private final int imageHeight; // height of image (first index)
    private final int imageWidth; // width of image (second index)

    /**
     * Constructs an integral image from the given input image.
     *
     * @param image The image represented
     * @throws InvalidImageException Thrown if input array is not rectangular
     * @author jameselder
     */
    public IntegralImage(int[][] image) throws InvalidImageException {
        //implement this method.
        int temp = 0;
        int[][] original = image;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++)
            {
                if (image[j].length!=image[0].length)
                {
                    throw new InvalidImageException();
                }
                if (i!=0)
                {
                    temp +=image[i-1][j];
                }
                if (j!=0)
                {
                    temp+= image[i][j-1];
                }
            }
            temp = 0;
        }

        System.out.println(original);
        System.out.println(image.toString());

        this.integralImage = image;
        this.imageHeight = image.length;
        this.imageWidth = temp;
    }

    /**
     * Returns the mean value of the rectangular sub-image specified by the
     * top, bottom, left and right parameters. The sub-image should include
     * pixels in rows top and bottom and columns left and right.  For example,
     * top = 1, bottom = 2, left = 1, right = 2 specifies a 2 x 2 sub-image starting
     * at (top, left) coordinate (1, 1).
     *
     * @param top    top row of sub-image
     * @param bottom bottom row of sub-image
     * @param left   left column of sub-image
     * @param right  right column of sub-image
     * @return
     * @throws BoundaryViolationException if image indices are out of range
     * @throws NullSubImageException      if top > bottom or left > right
     * @author jameselder
     */
    public double meanSubImage(int top, int bottom, int left, int right) throws BoundaryViolationException, NullSubImageException {
        //implement this method
        return 0.0d;
    }

    public static void main(String[] args) {
        int[][]testArray = {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };

        try
        {
            IntegralImage test = new IntegralImage(testArray);
        }
        catch(InvalidImageException e)
        {
        }
    }
}