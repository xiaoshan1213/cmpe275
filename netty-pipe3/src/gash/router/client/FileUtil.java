package gash.router.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by sam on 4/13/17.
 * chunk-thread-send individually
 */
public class FileUtil {

    public static void chunk(){
        String fname="";
        File file = new File(fname);
        FileInputStream fis;
        FileOutputStream fos;
        int file_size = (int)file.length();
        final int CHUNK_SIZE = 1024;
        int numberOfChunks = 0;
        int readLength = CHUNK_SIZE;
        byte[] byteChunk;
        int read = 0;
        String newFileName;
        try{
            fis = new FileInputStream(file);
            while(file_size > 0){
                if(file_size <= CHUNK_SIZE)
                    readLength = file_size;
                byteChunk = new byte[readLength];
                read = fis.read(byteChunk, 0, readLength);
                file_size -= read;
                assert (read == byteChunk.length);
                numberOfChunks++;
                //get hash key for store, to do

                newFileName = String.format("%s.part%06d",fname, numberOfChunks-1);
                fos = new FileOutputStream(new File(newFileName));
                fos.write(byteChunk);
                fos.flush();
                fos.close();
                byteChunk = null;
                fos = null;

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
