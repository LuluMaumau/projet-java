package dtype;

public class GrayImage extends Data {

    private static final long serialVersionUID = 1L;
    int[][] bitmap;
    int size;

    /**
     * Make a GrayImage object from the name of the satellite and the component
     * which took the measure aswell as the size of the GrayImage
     * 
     * @param sat       Name of the satellite
     * @param component Name of the component
     * @param n         Size of the gray image
     */
    public GrayImage(String sat, String component, int n) {
        super(sat, component);
        this.bitmap = new int[n][n];
        for (int[] is : bitmap) {
            for (int i : is) {
                i = (int) Math.floor(255 * Math.random());
            }
        }
        this.size = n;
    }

    /**
     * Make a GrayImage object from the name of the satellite and the component
     * which took the measure with default size 255
     * 
     * @param sat       Name of the satellite
     * @param component Name of the component
     */
    public GrayImage(String sat, String component) {
        this(sat, component, 255);
    }

    /** make a GrayImage with names of satellite and component empty */
    public GrayImage() {
        this("", "", 255);
    }

    /**
     * Get the grayImage
     * 
     * @return The bitmap attribute
     */
    public int[][] getBitmap() {
        return this.bitmap;
    }

    /**
     * Get the size of the GrayImage
     * 
     * @return The size attribute
     */
    public int getSize() {
        return this.size;
    }

    public String toString() {
        return super.toString() + " , Image : " + bitmap.toString() + " , Size : " + this.size + " ] ";
    }

}
