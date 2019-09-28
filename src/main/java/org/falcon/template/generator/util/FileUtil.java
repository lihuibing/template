package org.falcon.template.generator.util;

import java.io.*;

public class FileUtil {

    /**
     * 创建文件夹
     * @param packageStr
     */
    public static void createDirs(String packageStr){
        File dirs = new File(packageStr);
        dirs.mkdirs();
    }

    /**
     * 写入文件
     * @param dir 文件夹
     * @param fileName 文件名称
     * @param content 内容
     * @throws IOException
     */
    public static void writeFile(String dir,String fileName,String content) throws IOException {
        File file = new File(dir,fileName);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(writer);
        out.write(content);
        out.flush();
    }

    public static void fileUpload(InputStream is, OutputStream os) throws IOException {

        byte[] b = new byte[1024 * 1024 * 10];
        int length = 0;
        while (true) {
            length = is.read(b);
            if (length < 0)
                break;
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

}
