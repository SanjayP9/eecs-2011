package A1Q2;

import java.util.Arrays;

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
        if (image.length == 0 || image[0].length == 0) {
            throw new InvalidImageException();
        }

        int[][] newArray = new int[image.length][image[0].length];

        for (int i = 0; i < image.length; i++) {
            if (image[i].length != image[0].length) {
                throw new InvalidImageException();
            }
            for (int j = 0; j < image[0].length; j++) {

                if (i > 0) {
                    newArray[i][j] += newArray[i - 1][j];
                }
                if (j > 0) {
                    newArray[i][j] += newArray[i][j - 1];
                }
                if (j > 0 && i > 0) {
                    newArray[i][j] -= newArray[i - 1][j - 1];
                }
                newArray[i][j] += image[i][j];
            }
        }

        this.integralImage = newArray;
        this.imageHeight = newArray.length;
        this.imageWidth = newArray[0].length;
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
        if (top > bottom || left > right) {
            throw new NullSubImageException();
        }
        if (left < 0 || right > imageWidth || top < 0 || bottom > imageHeight) {
            throw new BoundaryViolationException();
        }

        double result = this.integralImage[bottom][right] * 1.0d;
        if (left > 0) {
            result -= this.integralImage[bottom][left - 1] * 1.0d;
        }
        if (top > 0) {
            result -= this.integralImage[top - 1][right] * 1.0d;
        }
        if (left >= 1 && top >= 1) {
            result += this.integralImage[top - 1][left - 1] * 1.0d;
        }
        double count = 1.0d * ((right - left + 1) * (bottom - top + 1));

        return (result / count);
    }
}