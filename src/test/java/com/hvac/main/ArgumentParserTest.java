package com.hvac.main;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArgumentParserTest {

    @Test
    public void shouldDoNothingIfNoArgumentsArePresent() {
        String[] args = new String[]{};
        ArgumentParser parser = new ArgumentParser();
        parser.parse(args);
        assertTrue(parser.getArguments().size() == 0);
    }

    @Test
    public void shouldPopulateUnboundEntryIfOnlyInvalidArgumentsArePresent() {
        String[] args = new String[]{"blah", "I", "don't", "care"};
        ArgumentParser parser = new ArgumentParser();
        parser.parse(args);
        assertTrue(parser.getArguments().get("unbound").equals("blah I don't care"));
    }

    @Test
    public void shouldPopulateOneArgument() {
        String[] args = new String[]{"-min", "65"};
        ArgumentParser parser = new ArgumentParser();
        parser.parse(args);
        assertTrue(parser.getArguments().get("min").equals("65"));
    }

    @Test
    public void shouldPopulateTwoArguments() {
        String[] args = new String[]{"-min", "65", "-max", "70"};
        ArgumentParser parser = new ArgumentParser();
        parser.parse(args);
        assertTrue(parser.getArguments().get("min").equals("65"));
        assertTrue(parser.getArguments().get("max").equals("70"));
    }

    @Test
    public void shouldPopulateBoundAndUnboundArguments() {
        String[] args = new String[]{"-min", "65", "-max", "70", "blah", "I", "don't", "care"};
        ArgumentParser parser = new ArgumentParser();
        parser.parse(args);
        assertTrue(parser.getArguments().get("min").equals("65"));
        assertTrue(parser.getArguments().get("max").equals("70"));
        assertTrue(parser.getArguments().get("unbound").equals("blah I don't care"));
    }
}