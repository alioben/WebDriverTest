import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

	public static String getConfig(int i){
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get("config.txt")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] configs = content.split("\n");
		return configs[i];
	}
}
