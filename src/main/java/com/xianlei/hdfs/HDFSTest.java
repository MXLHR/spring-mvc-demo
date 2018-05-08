package com.xianlei.hdfs;

import java.io.*;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HDFSTest {

    static Configuration conf = new Configuration();

    static {
        conf.set("fs.defaultFS", "hdfs://nameservice1");
        conf.set("dfs.nameservices", "nameservice1");
        conf.set("dfs.ha.namenodes.nameservice1", "nn1,nn2");
        conf.set("dfs.namenode.rpc-address.nameservice1.nn1", "xxx:8020");
        conf.set("dfs.namenode.rpc-address.nameservice1.nn2", "xxx:8020");
        conf.set("dfs.client.failover.proxy.provider.nameservice1"
                ,"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");


//        conf.addResource("classpath:/hadoop/core-site.xml");
//        conf.addResource("classpath:/hadoop/hdfs-site.xml");
//        conf.addResource("classpath:/hadoop/mapred-site.xml");
    }

    //创建新文件
    public static void createFile(String dst , byte[] contents) throws IOException{
        FileSystem fs = FileSystem.get(conf);
        Path dstPath = new Path(dst); //目标路径
        //打开一个输出流
        FSDataOutputStream outputStream = fs.create(dstPath);
        outputStream.write(contents);
        outputStream.close();
        fs.close();
        System.out.println("文件创建成功！");
    }

    //上传本地文件
    public static void uploadFile(String src,String dst) throws IOException{
        //Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path srcPath = new Path(src); //本地上传文件路径
        Path dstPath = new Path(dst); //hdfs目标路径
        //调用文件系统的文件复制函数,前面参数是指是否删除原文件，true为删除，默认为false
        fs.copyFromLocalFile(false, srcPath, dstPath);

        //打印文件路径
        System.out.println("Upload to "+conf.get("fs.default.name"));
        System.out.println("------------list files------------"+"\n");
        FileStatus [] fileStatus = fs.listStatus(dstPath);
        for (FileStatus file : fileStatus)
        {
            System.out.println(file.getPath());
        }
        fs.close();
    }

    //文件重命名
    public static void rename(String oldName,String newName) throws IOException{
        //Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path oldPath = new Path(oldName);
        Path newPath = new Path(newName);
        boolean isok = fs.rename(oldPath, newPath);
        if(isok){
            System.out.println("rename ok!");
        }else{
            System.out.println("rename failure");
        }
        fs.close();
    }
    //删除文件
    public static void delete(String filePath) throws IOException{
        //Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path(filePath);
        boolean isok = fs.deleteOnExit(path);
        if(isok){
            System.out.println("delete ok!");
        }else{
            System.out.println("delete failure");
        }
        fs.close();
    }

    //创建目录
    public static void mkdir(String path) throws IOException{
        //Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path srcPath = new Path(path);
        boolean isok = fs.mkdirs(srcPath);
        if(isok){
            System.out.println("create " + path + " dir ok!");
        }else{
            System.out.println("create " + path + " dir failure");
        }
        fs.close();
    }

    //读取文件的内容
    public static void readFile(String filePath) throws IOException{
        //Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path srcPath = new Path(filePath);
        InputStream in = null;
        try {
            in = fs.open(srcPath);
            IOUtils.copyBytes(in, System.out, 4096, false); //复制到标准输出流
        } finally {
            IOUtils.closeStream(in);
        }
    }

    /**
     * 遍历指定目录(direPath)下的所有文件
     */
    public static void  getDirectoryFromHdfs(String direPath){
        try {
            FileSystem fs = FileSystem.get(URI.create(direPath),conf);
            FileStatus[] filelist = fs.listStatus(new Path(direPath));
            for (int i = 0; i < filelist.length; i++) {
                System.out.println("_________" + direPath + "目录下所有文件______________");
                FileStatus fileStatus = filelist[i];
                System.out.println("Name:"+fileStatus.getPath().getName());
                System.out.println("Size:"+fileStatus.getLen());
                System.out.println("Path:"+fileStatus.getPath());
            }
            fs.close();
        } catch (Exception e){

        }
    }


    public static void main(String[] args) throws IOException {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String localFilePath = "F:\\datafortag\\maimaimai\\quan-" + today;
        String hdfsFilePath = "/user/rec/maimaimai/upload_month=" + today.substring(0,7) + "/upload_date=" + today + "/";
        System.out.println(localFilePath);
        System.out.println(hdfsFilePath);

        //"/user/rec/maimaimai/upload_month=2016-11/upload_date=2016-11-09/"
        //1、遍历指定目录(direPath)下的所有文件
        //getDirectoryFromHdfs("/user/rec/maimaimai");

        //2、新建目录
        //mkdir(hdfsFilePath);

        //3、上传文件
        //uploadFile(localFilePath, hdfsFilePath);
        //getDirectoryFromHdfs(hdfsFilePath);

        //4、读取文件
        //readFile("/user/rec/maimaimai/quan-2016-11-09");

        //5、重命名
//        rename("/user/rec/maimaimai/2016-11/2016-11-09/quan-2016-11-09", "/user/rec/maimaimai/2016-11/2016-11-09/quan-2016-11-08.txt");
//        getDirectoryFromHdfs("/user/rec/maimaimai/2016-11/2016-11-09");

        //6、创建文件，并向文件写入内容
        //byte[] contents =  "hello world 世界你好\n".getBytes();
        //createFile("/user/rec/maimaimai/2016-11/2016-11-09/test.txt",contents);
        //readFile("/user/rec/maimaimai/2016-11/2016-11-09/test.txt");

        //7、删除文件
        //delete("/user/rec/maimaimai/quan-2016-11-08.txt"); //使用相对路径
        //delete("test1");    //删除目录
    }

}