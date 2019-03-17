package wk.game.sweepmines;

/**
 * @author Administrator
 */
public class ResourceUtil {
    public ResourceUtil() {
    }

    public String getImagePath(String fileName) {
//        System.out.println(this.getClass().getClassLoader().getResource("image/"+fileName).getPath());
        return this.getClass().getClassLoader().getResource("image/"+fileName).getPath();
    }
}
