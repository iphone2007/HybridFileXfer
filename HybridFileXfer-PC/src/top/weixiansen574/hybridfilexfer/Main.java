package top.weixiansen574.hybridfilexfer;

import top.weixiansen574.hybridfilexfer.core.FileTransferClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        if (executeAdbForwardCommand(5740)){
            System.out.println("USB : 5740 端口转发成功！");
        } else {
            System.out.println("USB : 5740 端口转发失败，请检查是否开启USB调试和授权此电脑！");
            return;
        }
        if (executeAdbForwardCommand(5741))          {
            System.out.println("USB : 5741 端口转发成功！");
        } else {
            System.out.println("USB : 5741 端口转发失败，请检查是否开启USB调试和授权此电脑！");
        }
        System.out.println("正在连接手机……");
        new FileTransferClient().startUp();
    }

    public static boolean executeAdbForwardCommand(int port) {
        try {
            // 获取当前jar包所在的目录
            String jarDirectory = System.getProperty("user.dir");

            // 构建adb forward命令
            String adbForwardCommand = "adb.exe forward tcp:" + port + " tcp:" + port;

            // 执行adb forward命令
            Process process = Runtime.getRuntime().exec(adbForwardCommand, null, new java.io.File(jarDirectory));

            BufferedReader bufferedReader = process.errorReader();
            String l;
            while ((l =  bufferedReader.readLine()) != null){
                System.err.println(l);
            }
            // 读取adb forward命令执行的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("adb.exe:"+line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            return exitCode == 0;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false; // 执行失败
        }
    }
}
