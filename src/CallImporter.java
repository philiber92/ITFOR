import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;


public class CallImporter {

	public static void main(String[] args) {
		
		File dir = new File(System.getProperty("user.dir"));
		
		File[] files = dir.listFiles(new FilenameFilter() {	
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".sql");
			}
		});
		
		for(File sql : files) {
			Process importer;
			try {
				importer = Runtime.getRuntime().exec("java importer "+sql.toString());
				importer.waitFor();
			} catch (IOException | InterruptedException e) {
				System.err.println("CThere was an error during running the process!");
				e.printStackTrace();
			}
		}

	}

}
