file = "D:\\GitHub_Reps\\ImageJSON\\src\\test\\simpleTest.json"
run("ImageJSON", "file="+file+" command=create name= value=");
run("ImageJSON", "file="+file+" command=string name=myString value=lalala");
run("ImageJSON", "file="+file+" command=arrayStart name=myArray value=");
run("ImageJSON", "file="+file+" command=string name= value=I");
run("ImageJSON", "file="+file+" command=string name= value=like");
run("ImageJSON", "file="+file+" command=string name= value=Ike");
run("ImageJSON", "file="+file+" command=arrayEnd name=myArray value=");
run("ImageJSON", "file="+file+" command=number name=intensity value=12.1");
run("ImageJSON", "file="+file+" command=boolean name=[camera on] value=true");
run("ImageJSON", "file="+file+" command=string name=myString value=[can put anywhere]");
run("ImageJSON", "file="+file+" command=boolean name=[do we like imagej?] value=true");
run("ImageJSON", "file="+file+" command=close name= value=");