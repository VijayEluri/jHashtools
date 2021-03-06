package nl.minjus.nfi.dt.jhashtools.utils;

import org.junit.Test;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;

import java.io.File;

public class FileOperationsTest {
    @Test
    public void testSameFileNotTheSameAndNotExist() {
        File firstFile = new File("does-not-exist");
        File secondFile = new File("does-not-exist");
        assertThat(FileOperations.isSameFile(firstFile,secondFile), is(false));
    }

    @Test
    public void testSameFileTheSame() {
        File firstFile = new File("testdata/testfile1.bin");
        File secondFile = new File("testdata/testfile1.bin");
        assertThat(FileOperations.isSameFile(firstFile,secondFile), is(true));
    }

    @Test
    public void testSameFileDifferent() {
        File firstFile = new File("does-not-exist");
        File secondFile = new File("testdata/testfile1.bin");
        assertThat(FileOperations.isSameFile(firstFile,secondFile), is(false));
    }
}
