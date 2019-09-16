file = "D:\\GitHub_Reps\\ImageJSON\\src\\test\\nestedArraysTest.json" //array of json objects, use arrays when the order is important
run("ImageJSON", "file="+file+" command=create name= value=");

run("ImageJSON", "file="+file+" command=arrayStart name=[my2DArray] value=");
run("ImageJSON", "file="+file+" command=arrayStart name=[myArrayX1] value=");
run("ImageJSON", "file="+file+" command=number name=[d] value=1");
run("ImageJSON", "file="+file+" command=number name=[d] value=2");
run("ImageJSON", "file="+file+" command=number name=[d] value=3");
run("ImageJSON", "file="+file+" command=arrayEnd name= value=");
run("ImageJSON", "file="+file+" command=arrayStart name=[myArrayX2] value=");
run("ImageJSON", "file="+file+" command=number name=[d] value=4");
run("ImageJSON", "file="+file+" command=number name=[d] value=5");
run("ImageJSON", "file="+file+" command=number name=[d] value=6");
run("ImageJSON", "file="+file+" command=arrayEnd name= value=");
run("ImageJSON", "file="+file+" command=arrayStart name=[myArrayX3] value=");
run("ImageJSON", "file="+file+" command=number name=[d] value=7");
run("ImageJSON", "file="+file+" command=number name=[d] value=8");
run("ImageJSON", "file="+file+" command=number name=[d] value=9");
run("ImageJSON", "file="+file+" command=arrayEnd name= value=");
run("ImageJSON", "file="+file+" command=arrayEnd name= value=");
run("ImageJSON", "file="+file+" command=close name= value=");