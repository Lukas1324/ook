package de.uniwue.jpp.ook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MyShell implements Shell {
    private static Util util;
    private static InputStream inputS;
    private static OutputStream outputS;
    private static InputStream readInp;
    private static Interpreter interpreter;

    public static Shell create(Ook handle, InputStream input, OutputStream output, InputStream readInput) {
        if (readInput == null || output == null || input == null || handle == null) {

            throw new NullPointerException();
        }
        Shell shell = new MyShell();
        inputS = input;
        outputS = output;
        util = handle.createUtil();
        readInp = readInput;
        interpreter = handle.createInterpreter(util.buildPipe(input), util.buildPipe(output));
        return shell;
    }

    @Override
    public Optional<Instruction> parseLine(String line) {
        return util.getInstruction(line);
    }

    @Override
    public void prepareUpdate(Instruction instruction) {
        String reading = "Reading: ";
        String writing = "Writing: ";
        String nichtsundwiedernichts = instruction + "\n";
        if (instruction.equals(Instruction.Read)) {
            try {
                outputS.write(reading.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (instruction.equals(Instruction.Write)) {
            try {
                outputS.write(writing.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                outputS.write(nichtsundwiedernichts.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void completeUpdate(Instruction instruction) {
        String garnichts = "\n";
        if (instruction.equals(Instruction.Write)) {
            try {
                outputS.write(garnichts.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (instruction.equals(Instruction.Read)) {
            ///Rufe das nächste Zeichen vom Datenstrom auf
            try {
                if (readInp.read() != '\n') {
                    outputS.write("Illegal Input: Insert only one character!\n".getBytes());
                }

            } catch (IOException e) {
            }
        }

        }


    @Override
    public void run() {

            try {
                outputS.write("--------------------------\n".getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                outputS.write("  Interactive Ook! Shell  \n".getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                outputS.write("--------------------------\n".getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Optional<Instruction> instruction1;
            String bananaOderMonkey = "leeerer String";
            Scanner sc = new Scanner(inputS);

            while (!bananaOderMonkey.equals("Monkey")) {

                bananaOderMonkey = sc.nextLine();

                if (bananaOderMonkey.equals("Monkey")) {


                    try {
                        outputS.write("Ouch!\n".getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (bananaOderMonkey.equals("Bananas")) {
                    try {
                        outputS.write("Yippee!\n".getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                if ( !(bananaOderMonkey.equals("Bananas") || bananaOderMonkey.equals("Monkey"))) {
                    instruction1 = util.getInstruction(bananaOderMonkey);
                    if (!instruction1.isEmpty()) {
                        interpreter.loadInstruction(instruction1.orElse(null));
                        while (!interpreter.reachedEnd()) {
                            prepareUpdate(interpreter.getInstructions().get(interpreter.getInstructionPointer()));
                            interpreter.update();
                            completeUpdate(interpreter.getInstructions().get(interpreter.getInstructionPointer()));
                        }
                    }
                    if (instruction1.isEmpty()){
                        try {
                            outputS.write(("Invalid instuction: "+bananaOderMonkey+"\n").getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }
        }

    }


