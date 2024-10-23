package de.uniwue.jpp.ook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MyExecutor implements Executor{

    static Util util;
    private static Parser parser;
    private static Interpreter interpreter;
    private static Ook handlee;
    public static Executor create(Ook handle){
        //MyUtil, MyPaser, MyInterpreter
        handlee = handle;
        MyExecutor xyz = new MyExecutor();
        util = handle.createUtil();
        parser = handle.createParser();
        //interpreter = handle.createInterpreter(Supplier<Integer>, Consumer<Integer>);
        return xyz;
    }
    @Override
    public void execute(Path program, Path input, Path output) throws IOException {
        //Files.newInputStream
        String outpuut = String.valueOf(output);
        String inpuut = String.valueOf(input);
        String zuu = Files.readString(program);

        List<Instruction> instlist = parser.parse(zuu);
        InputStream inputstrem = new FileInputStream(inpuut);
        OutputStream outputStream = new FileOutputStream(outpuut);
        Supplier<Integer> supplier = util.buildPipe(inputstrem);
        Consumer<Integer> consumer = util.buildPipe(outputStream);
        Interpreter interpreter = handlee.createInterpreter(supplier,consumer);

        for(int i = 0; i<instlist.size(); i++){
            interpreter.loadInstruction(instlist.get(i));
            interpreter.update();
        }




    }

    @Override
    public void execute(String[] args) throws IOException {

        Path program = Path.of(args[0]);
        Path input = Path.of(args[1]);
        Path output = Path.of(args[2]);
        execute(program, input, output);


    }
}
