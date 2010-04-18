/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.minjus.nfi.dt.jhashtools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author kojak
 */
public class DirHasher {

    private DirHasherResult results;

    private Set<String> algorithms;

    public DirHasher() {
        results = new DirHasherResult();
        algorithms = new TreeSet<String>();
    }

    public DirHasher(String algorithm) {
        results = new DirHasherResult();
        algorithms = new TreeSet<String>();
        algorithms.add(algorithm);
    }

    public void addAlgorithm(String algorithm) {
        algorithms.add(algorithm);
    }

    public DirHasherResult getDigests(final File startFile) {

        if (! startFile.exists()) {
            throw new IllegalArgumentException(String.format("File %s does not exist", startFile.toString()));
        }

        FileWalker walker = new FileWalker();
        DirVisitor visitor = new DirVisitor(algorithms);
        walker.addWalkerVisitor(visitor);
        walker.walk(startFile);
        return visitor.getResults();
    }

    public void updateDigests(DirHasherResult digests, File file) {
        if (! file.exists()) {
            throw new IllegalArgumentException(String.format("File %s does not exist", file.toString()));
        }

        FileWalker walker = new FileWalker();
        DirVisitor visitor = new DirVisitor(algorithms, digests);
        walker.addWalkerVisitor(visitor);
        walker.walk(file);
    }
}