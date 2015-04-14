package com.hvac.main;

import java.util.*;

public class ArgumentParser {

    public static String UNBOUND = "unbound";

    private Map<String, String> arguments = new HashMap<>();

    public ArgumentParser parse(String[] args) {
        if (args.length > 0) {
            parseBoundArguments(args);
        }
        return this;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    private void parseBoundArguments(String[] args) {
        final List<String> unboundArgs = new ArrayList<>();
        for(int i = 0; i < args.length; i++) {
            String flag = args[i];
            if (flag.startsWith("-")) {
                if (++i < args.length) {
                    arguments.put(flag.substring(1), args[i]);
                    continue;
                }
            }
            unboundArgs.add(flag);
        }
        arguments.put(UNBOUND, join(unboundArgs, ' '));
    }

    private String join(List<String> args, char sep) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.size(); i++){
            if (i > 0) {
                builder.append(sep);
            }
            builder.append(args.get(i));
        }
        return builder.toString();
    }
}
