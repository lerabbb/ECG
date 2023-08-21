package nsu.lerabbb.wireframe.controllers;

import nsu.lerabbb.wireframe.Logger;
import nsu.lerabbb.wireframe.Main;
import nsu.lerabbb.wireframe.exceptions.ValueException;
import nsu.lerabbb.wireframe.gui.MainPanel;
import nsu.lerabbb.wireframe.logic.Constants;
import nsu.lerabbb.wireframe.logic.algorithms.BSpline;
import nsu.lerabbb.wireframe.logic.utils.Point;
import nsu.lerabbb.wireframe.gui.frames.InfoDialog;
import nsu.lerabbb.wireframe.logic.Config;
import nsu.lerabbb.wireframe.exceptions.FileContentException;
import nsu.lerabbb.wireframe.exceptions.FileExtException;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileController {
    private JFrame frame;
    private JPanel mainPanel;

    public FileController(JFrame frame, JPanel panel){
        this.frame = frame;
        this.mainPanel = panel;
    }

    public void openFile(){
        try {
            FileDialog fd = new FileDialog(frame, "Открыть файл", FileDialog.LOAD);
            fd.setResizable(true);
            fd.setFilenameFilter((dir, name) -> name.toLowerCase().endsWith(".wrf"));
            fd.setFile("*.wrf");
            fd.setVisible(true);
            if (fd.getFile() == null)
                return;
            File inputFile = (fd.getFiles()[0]);
            checkFileExt(inputFile.getName());
            readData(new FileReader(inputFile));
            ((MainPanel)mainPanel).update();
        }
        catch(FileExtException | FileContentException | ValueException e){
            new InfoDialog(e.getMessage());
            Logger.getInstance().error(e.getMessage());
        } catch(IOException e){
            Logger.getInstance().error(e.getMessage());
        }
    }

    public void saveFile() {
        try {
            FileDialog fd = new FileDialog(frame, "Сохранить данные", FileDialog.SAVE);
            fd.setResizable(true);
            fd.setFilenameFilter((dir, name) -> name.toLowerCase().endsWith(".wrf"));
            fd.setFile("Untitled.wrf");
            fd.setVisible(true);
            if (fd.getFile() == null) {
                return;
            }
            File outputFile = (fd.getFiles()[0]);
            checkFileExt(outputFile.getName());
            Logger.getInstance().info(outputFile.getName());
            Logger.getInstance().info(outputFile.getAbsolutePath());
            writeData(new PrintWriter(outputFile.getAbsolutePath()));
        }
        catch(FileExtException e){
            new InfoDialog(e.getMessage());
            Logger.getInstance().error(e.getMessage());
        } catch(IOException e){
            Logger.getInstance().error(e.getMessage());
        }
    }

    public void openInfoDialog(){
        new InfoDialog();
    }

    private void checkFileExt(String fileName) throws FileExtException {
        if(!fileName.matches(".+\\.wrf")){
            throw new FileExtException("invalid file extension");
        }
    }

    private void writeData(PrintWriter writer) throws IOException {
        String record = "K=" + Config.getK() + " N=" + Config.getN() + " M=" + Config.getM() + " M1=" + Config.getM1()+
                " spline:" + Config.getBSpline().getControlPoints().toString();
        writer.print(record);
        writer.close();
        Logger.getInstance().debug("Here data should be saved");
    }

    private void readData(FileReader reader) throws IOException, FileContentException, ValueException {
        StringBuilder content = new StringBuilder();
        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            content.append((char) nextChar);
        }
        checkReading(String.valueOf(content));
    }

    private void checkReading(String str) throws FileContentException, ValueException {
        Logger.getInstance().debug(str);
        Pattern pattern = Pattern.compile("K=[\\d+] N=[\\d+] M=[\\d+] M1=[\\d+] ");
        Matcher matcher = pattern.matcher(str);
        String paramsStr;
        if (matcher.find()) {
            paramsStr = matcher.group();
            str = matcher.replaceAll("");
        } else {
            throw new FileContentException("invalid content in file");
        }
        String[] parts = paramsStr.split(" ");
        for (String part: parts) {
            String[] var = part.split("=");
            int val = validateValue(var[0], var[1]);
            switch (var[0]) {
                case "K" -> Config.setK(val);
                case "N" -> Config.setN(val);
                case "M" -> Config.setM(val);
                case "M1" -> Config.setM1(val);
                default -> throw new FileContentException("Parameter " + var[0] + " doesn't exist");
            }
        }

        str = str.replaceAll("spline:", "");
        str = str.replaceAll("[\\[\\]]", "");
        String[] pairs = str.split(", ");
        List<Point> newControlPoints = new ArrayList<>();
        for(String pair: pairs){
            Logger.getInstance().info(pair);
            String[] nums = pair.split(":");
            int x = Integer.parseInt(nums[0]);
            int y = Integer.parseInt(nums[1]);
            newControlPoints.add(new Point(x, y));
        }
        Config.setBSpline(new BSpline(newControlPoints));
    }

    private int validateValue(String var, String val) throws FileContentException, ValueException {
        if(!val.matches("[\\d+]")){
            throw new FileContentException("invalid parameter value in file");
        }
        int num = Integer.parseInt(val);
        int min, max;
        switch(var){
            case "K" -> {
                min = Constants.MIN_K;
                max = Constants.MAX_K;
            }
            case "N" -> {
                min = Constants.MIN_N;
                max = Constants.MAX_N;
            }
            case "M1" -> {
                min = Constants.MIN_M1;
                max = Constants.MAX_M1;
            }
            case "M" -> {
                min = Constants.MIN_M;
                max = Constants.MAX_M;
            }
            default -> throw new FileContentException("Parameter " + var + " doesn't exist");
        }
        if(num < min || num > max){
            throw new ValueException(var, num, min, max);
        }
        return num;
    }
}
