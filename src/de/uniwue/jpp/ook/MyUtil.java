package de.uniwue.jpp.ook;

import java.io.*;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MyUtil implements Util{

    public static Util create() {
        //TODO Diese Zeile muss mit dem Konstruktor der konkreten Implementierung ausgetauscht werden.
        try{
            MyUtil createUtil = new MyUtil();
            return createUtil;
        }catch(IllegalStateException e){
            throw new IllegalStateException("Not implemented yet!");
        }

    }
    @Override
    public Instruction forSymbols(Symbol first, Symbol last) {
        if (first == null || last == null){
            throw new NullPointerException();
        }
        if(last.equals(first) && first.equals(Symbol.Question)){
            throw new IllegalArgumentException("No instruction is for symbol "+first+" and "+last+" present!");
            //throw new IllegalArgumentException("No instruction is for symbol \"" + first + "\" and \"" + last + "\" present");
        }
        for(Instruction wert : Instruction.values()){
            if(wert.getFirstSymbol().equals(first)&& wert.getLastSymbol().equals(last)){
                return wert;
            }

        }
        return null;
    }

    @Override
    public Symbol forToken(String token) {
        if(token == null){
            throw new NullPointerException();
        }
        for(Symbol sym : Symbol.values()){
            if(sym.getToken().equals(token)){
                return sym;
            }
        }
        //throw new IllegalArgumentException("No symbol is for token \"" + token + "\" present!");
        throw new IllegalArgumentException("No symbol is for token "+token+" present!");
    }

    @Override
    public Supplier<Integer> buildPipe(InputStream input) {
        if(input == null){
            throw new NullPointerException();
        }
        return new Supplier<Integer>() {
            @Override
            public Integer get() {
                try {
                    return input.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Override
    public Consumer<Integer> buildPipe(OutputStream output) {
        if(output == null){
            throw new NullPointerException();
        }
        return new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                try {
                    output.write(integer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Override
    public String getInstructionCode(Instruction instruction) {
        return  (instruction.getFirstSymbol().getToken() + "" + instruction.getLastSymbol().getToken());
    }

    @Override
    public Optional<Instruction> getInstruction(String code) {
        String firstToken = code.substring(0,4);
        String secondToken = code.substring(4);
        try {
            return Optional.ofNullable(forSymbols(forToken(firstToken),forToken(secondToken)));
        }catch(Exception e) {
            return Optional.empty();
        }
    }
}
