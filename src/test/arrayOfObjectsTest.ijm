file = "D:\\GitHub_Reps\\ImageJSON\\src\\test\\arrayOfObjectsTest.json" //array of json objects, use arrays when the order is important
run("ImageJSON", "file="+file+" command=create name= value=");

run("ImageJSON", "file="+file+" command=arrayStart name=[myArrayOfObjects] value=");
run("ImageJSON", "file="+file+" command=objStart name= value="); //object name is ignored when in an array
run("ImageJSON", "file="+file+" command=string name=[a] value=First of all:");
run("ImageJSON", "file="+file+" command=string name=[b] value=I");
run("ImageJSON", "file="+file+" command=string name=[c] value=like");
run("ImageJSON", "file="+file+" command=number name=[d] value=3.14159265");
run("ImageJSON", "file="+file+" command=objStart name=[object in array of objects ] value="); 
run("ImageJSON", "file="+file+" command=string name=[this can all] value=[be done]");
run("ImageJSON", "file="+file+" command=objStart name=[object in object in array of objects] value="); 
run("ImageJSON", "file="+file+" command=string name=[This is] value=[probably never used]");
run("ImageJSON", "file="+file+" command=arrayStart name=[Add an array] value=");
run("ImageJSON", "file="+file+" command=number name=[] value=1");
run("ImageJSON", "file="+file+" command=number name=[] value=2");
run("ImageJSON", "file="+file+" command=string name=[] value=[use of an array inside an object in an object in an array!]");
run("ImageJSON", "file="+file+" command=arrayEnd name=myArray value=");
run("ImageJSON", "file="+file+" command=objEnd name= value=");
run("ImageJSON", "file="+file+" command=objEnd name= value=");
run("ImageJSON", "file="+file+" command=objEnd name= value=");
run("ImageJSON", "file="+file+" command=objStart name= value="); //object name is ignored when in an array
run("ImageJSON", "file="+file+" command=string name=[a] value=And secondly:");
run("ImageJSON", "file="+file+" command=string name=[b] value=We");
run("ImageJSON", "file="+file+" command=string name=[c] value=want");
run("ImageJSON", "file="+file+" command=number name=[d] value=2.71828183");
run("ImageJSON", "file="+file+" command=objEnd name= value=");
run("ImageJSON", "file="+file+" command=arrayEnd name=myArray value=");
run("ImageJSON", "file="+file+" command=close name= value=");