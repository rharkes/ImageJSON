# ImageJSON
The SR-postprocess macro is written in the ImageJ 1 macrolanguage. It has many settings that have an effect on the final rendered image. This plugin is used to save the settings in json format for easy indexing by other software. Does not support reading json from IJ1 macrolanguage.

The following macro would produce a simple json-file with a string and a number. See the [test files](src/test) for more elaborate examples.
```
file = "C:\\simpleFile.json"
run("ImageJSON", "file="+file+" command=create name= value=");
run("ImageJSON", "file="+file+" command=string name=[myString] value=[this is a string]");
run("ImageJSON", "file="+file+" command=number name=[some number] value=3.14159265");
run("ImageJSON", "file="+file+" command=close name= value=");
```
## manual
| Command | Description | name | value |
| --- | --- | --- | --- |
| create | Start a new file | ignored | ignored |
| objStart | Start JSON object | name of object | ignored |
| objEnd | End JSON object | ignored | ignored |
| arrayStart | Begin JSON array | name of array | ignored | 
| arrayEnd | End JSON array | ignored | ignored | 
| string | Put string value | name of value | value | 
| number | Put number value | name of value | value | 
| boolean | Put boolean value | name of value | value | 
| close | Close the file | ignored | ignored | 
