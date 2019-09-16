/* ImageJSON
(c) 2019 Rolf Harkes, Netherlands Cancer Institute.
This implementation is not very pretty. It puts all commands in a textfile, when 
close is called it reads the text file and constructs a json file using the 
reference implementation by Douglas Crockford. 

For an image analysis pipeline I needed something to save variables in a 
jsonfile that comes with the resulting image.

This software is released under the GPL v3. You may copy, distribute and modify 
the software as long as you track changes/dates in source files. Any 
modifications to or software including (via compiler) GPL-licensed code 
must also be made available under the GPL along with build & install instructions.
https://www.gnu.org/licenses/gpl-3.0.en.html

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package nl.nkiavl.rharkes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import org.scijava.app.StatusService;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * Subtracts the temporal median
 */
@Plugin(type = Command.class, headless = true, menuPath = "Plugins>ImageJSON")
public class ImageJSON implements Command{

	@Parameter
	private LogService log;

	@Parameter
	private StatusService statusService;
	
	@Parameter(label = "File", description = "the json file")
	private File file;
	
	@Parameter(label = "Command", description = "the command to pass")
	private String command;

	@Parameter(label = "Name", description = "the nam")
	private String name;
	
	@Parameter(label = "Value", description = "the value")
	private String value;

	public static void main(final String... args) throws Exception {

	}

	public void run() {
		try { 
			String write;
			if (name.contains(" && ")) {
				log.error("&& is not allowed in name ; "+name); 
			}
			if (name.isEmpty()){name = " ";}
			switch (command) {
				case "create":
					Path path = Paths.get(file.getAbsolutePath());
					Files.createDirectories(path.getParent());
					if(Files.exists(path)) {
						Files.delete(path);
					}
					Files.createFile(path);
					break;
				case "objStart": //name
					write = "0";
					write += name;
					write += "\n";
					Files.write(Paths.get(file.getAbsolutePath()), write.getBytes(), StandardOpenOption.APPEND);
					break;
				case "objEnd":
					write = "1";
					write += "\n";
					Files.write(Paths.get(file.getAbsolutePath()), write.getBytes(), StandardOpenOption.APPEND);
					break;
				case "arrayStart": //name
					write = "2";
					write += name;
					write += "\n";
					Files.write(Paths.get(file.getAbsolutePath()), write.getBytes(), StandardOpenOption.APPEND);
					break;
				case "arrayEnd":
					write = "3";
					write += "\n";
					Files.write(Paths.get(file.getAbsolutePath()), write.getBytes(), StandardOpenOption.APPEND);
					break;
				case "string": //name & value
					write = "4";
					write += name;
					write += " && ";
					if (value.contains(" && ")) {
						log.error("&& is not allowed in value ; "+value); 
					}
					if (value.isEmpty()) {
						value = " ";
					}
					write += value;
					write += "\n";
					Files.write(Paths.get(file.getAbsolutePath()), write.getBytes(), StandardOpenOption.APPEND);
					break;
				case "number": //name & value 
					write = "5";
					write += name;
					write += " && ";
					write += value;
					write += "\n";
					Files.write(Paths.get(file.getAbsolutePath()), write.getBytes(), StandardOpenOption.APPEND);
					break;
				case "boolean": //name & value
					write = "6";
					write += name;
					write += " && ";
					String[] truevals = {"Yes","yes","1"};
					if(Arrays.asList(truevals).contains(value)) {
						value = "true";
					}
					String[] accepted = {"true","True","false","False","0","no","No"};
					if(!Arrays.asList(accepted).contains(value)) {
						log.warn("use true or false for boolean. \""+value+"\" will default to false.");
					}
					write += value;
					write += "\n";
					Files.write(Paths.get(file.getAbsolutePath()), write.getBytes(), StandardOpenOption.APPEND);
					break;
				case "close": //all information collected, time to put it all in JSON objects
					List<String> data = Files.readAllLines(Paths.get(file.getAbsolutePath()));
					List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
					List<JSONArray> jsonArrays = new ArrayList<JSONArray>();
					List<String> jsonNames = new ArrayList<String>();
					List<Boolean> inArray = new ArrayList<Boolean>();
					jsonObjects.add(new JSONObject());
					inArray.add(false);
					String[] namevalue = new String[2];
					//information is always added to a JSON object
					//if working in an array the data is added to the array
					for (String line : data) {
						switch (line.substring(0, 1)) {
							case "0": //objStart
								jsonObjects.add(new JSONObject());
								jsonNames.add(line.substring(1));
								inArray.add(false);
								break;
							case "1": //objEnd
								if (inArray.get(inArray.size()-1)) {log.error("objEnd before arrayEnd");}
								inArray.remove(inArray.size()-1);
								if (inArray.get(inArray.size()-1)) { //append last jsonObject to array
									jsonNames.remove(jsonNames.size()-1); //objectNames in arrays are ignored
									jsonArrays.get(jsonArrays.size()-1).put(jsonObjects.remove(jsonObjects.size()-1));
								} else { //put last jsonObject on but-last jsonObject
									jsonObjects.get(jsonObjects.size()-2).put(jsonNames.remove(jsonNames.size()-1), jsonObjects.remove(jsonObjects.size()-1));
								}
								break;
							case "2": //arraystart
								jsonNames.add(line.substring(1));
								inArray.add(true);
								jsonArrays.add(new JSONArray());
								break;
							case "3": //arrayend
								if (!inArray.get(inArray.size()-1)) {log.error("arrayEnd before objEnd");}
								inArray.remove(inArray.size()-1);
								if (inArray.get(inArray.size()-1)) { //add last array to but-last array
									jsonNames.remove(jsonNames.size()-1); //objectNames in arrays are ignored
									jsonArrays.get(jsonArrays.size()-2).put(jsonArrays.remove(jsonArrays.size()-1));
								} else { //put last array on last jsonObject
									jsonObjects.get(jsonObjects.size()-1).put(jsonNames.remove(jsonNames.size()-1), jsonArrays.remove(jsonArrays.size()-1));
								}
								break;
							case "4":
								namevalue = line.substring(1).split(" && ");
								if (inArray.get(inArray.size()-1)) {
									jsonArrays.get(jsonArrays.size()-1).put(namevalue[1]);
								} else {
									jsonObjects.get(jsonObjects.size()-1).put(namevalue[0],namevalue[1]);
								}
								break;
							case "5": //number
								namevalue = line.substring(1).split(" && ");
								Double numericValue = null;
								try {
									numericValue = Double.parseDouble(namevalue[1]);
								}catch (Exception e) {
									
								}
								if (inArray.get(inArray.size()-1)) {
									jsonArrays.get(jsonArrays.size()-1).put(numericValue);
								} else {
									jsonObjects.get(jsonObjects.size()-1).put(namevalue[0],numericValue);
								}
								break;
							case "6": //boolean
								namevalue = line.substring(1).split(" && ");
								boolean boolValue = Boolean.parseBoolean(namevalue[1]);
								if (inArray.get(inArray.size()-1)) {
									jsonArrays.get(jsonArrays.size()-1).put(boolValue);
								} else {
									jsonObjects.get(jsonObjects.size()-1).put(namevalue[0],boolValue);
								}
								break;
							default:
								log.error("Invalid line in .json. Started with \""+line.substring(0,1)+"\"");
						}
					}
					//write the main JSON object
					path = Paths.get(file.getAbsolutePath());
					Files.delete(path);
					Files.createFile(path);
					Files.write(path, jsonObjects.get(jsonObjects.size()-1).toString().getBytes(), StandardOpenOption.CREATE);
					break;
				default:
					log.error("Command \""+ command + "\" not recognised");
					break;
			}
		}catch (IOException e) {
			log.error("FileIO error");
		}
	}
}