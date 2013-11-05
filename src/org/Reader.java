package org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException,NullPointerException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException,NullPointerException {
        return Integer.parseInt( next() );
    }
	
    static double nextDouble() throws IOException,NullPointerException {
        return Double.parseDouble( next() );
    }
    
    static long nextLong() throws IOException,NullPointerException {
        return Long.parseLong( next() );
    }
}
