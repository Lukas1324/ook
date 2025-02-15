package de.uniwue.jpp.ook;

import java.util.ArrayList;
import java.util.List;

public class MyParser implements Parser{
    private Util util;

    public MyParser(Util util){
        this.util = util;
    }
    public static Parser create(Ook handle){
        Util util = handle.createUtil();
        return new MyParser(util);
    }
    @Override
    public List<Instruction> parse(String program) {

        return asInstructions(asSymbols(program));

    }

    @Override
    public boolean isValid(String program) {
        program = program.replaceAll("\\s", "");
        String string;
        if (program.length() % 8 != 0){
            return false;
        }
        for (int i = 0; i<program.length(); i = i + 8) {
            //// Substring (0,8)
            string = program.substring(i, i + 8);
            switch (string) {
                case "Ook.Ook.":
                    break;
                case "Ook.Ook?":
                    break;
                case "Ook!Ook?":
                    break;
                case "Ook?Ook!":
                    break;
                case "Ook?Ook.":
                    break;
                case "Ook!Ook.":
                    break;
                case "Ook.Ook!":
                    break;
                case "Ook!Ook!":
                    break;
                default:
                    return false;
            }



        /*for(int i = 0; i<program.length(); i = i + 4){
            if(program.charAt(i) != 'O' &&
               program.charAt(i+1) != 'o'&&
               program.charAt(i+2) != 'k'&& !(
               program.charAt(i+3) == '.'||
               program.charAt(i+3) == '!'||
               (program.charAt(i+3) == '?'))) {
                return false;
            }
        }
        */
        }

        /// Modulo 8 machen
        /// Fall ? ?
        return true;
    }

    @Override
    public List<Symbol> asSymbols(String program) {
        program = program.replaceAll("\\s", "");
        ArrayList<Symbol> symbolList = new ArrayList<Symbol>();
        if(isValid(program) == false){
            throw new IllegalArgumentException("Program is not valid!");
        }
        for (int i = 0; i<program.length(); i = i +4){
            symbolList.add(util.forToken(program.substring(i, i+4)));
        }

        return symbolList;
    }

    @Override
    public List<Instruction> asInstructions(List<Symbol> symbols) {
        if(symbols.size() % 2 != 0){
            throw new IllegalArgumentException("List of symbols is malformed!");
        }
        ArrayList<Instruction> instructionList = new ArrayList<Instruction>();
        for(int i = 0; i<symbols.size(); i = i + 2){
            if(symbols.get(i) == null || symbols.get(i+1) == null){
                throw new NullPointerException("Ein element der symbols ist null");
            }
            if(symbols.get(i).equals(Symbol.Question) && symbols.get(i+1).equals(Symbol.Question)){
                throw new IllegalArgumentException("No instruction is for symbol "+symbols.get(i)+" and "+symbols.get(i+1)+" present!");
            }
            if(!isValid(symbols.get(i).getToken() + "" + symbols.get(i+1).getToken())){
                throw new IllegalArgumentException("Program is not valid!");
            }
            instructionList.add(util.forSymbols(symbols.get(i),symbols.get(i+1)));
        }
        return instructionList;
    }
}
