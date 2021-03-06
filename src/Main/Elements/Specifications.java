package Main.Elements;


import Main.Default.Functions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class Specifications {

    public static ListView<String> list;

    public static void load() {
        SystemInfo info = new SystemInfo();
        CentralProcessor processor = info.getHardware().getProcessor();
        HardwareAbstractionLayer hardware = info.getHardware();
        ListView<String> list = Specifications.list;

        list.getItems().add("------ GPU INFO ------");
        for (int i = 0; i < info.getHardware().getGraphicsCards().size(); i++) {
            list.getItems().add("GPU " + (i + 1) + " : " + hardware.getGraphicsCards().get(i).getName());
            list.getItems().add("VRAM: " + Functions.gigabyte(hardware.getGraphicsCards().get(i).getVRam() * 2) + " GB");
            list.getItems().add("Vendor: " + hardware.getGraphicsCards().get(i).getVendor());
            list.getItems().add("Version: " + hardware.getGraphicsCards().get(i).getVersionInfo());
            list.getItems().add("Device ID: " + hardware.getGraphicsCards().get(i).getDeviceId());
        }

        list.getItems().add("\n------ CPU INFO ------");
        list.getItems().add("Name: " + processor.getProcessorIdentifier().getName());
        list.getItems().add("Processor Family: " + processor.getProcessorIdentifier());
        list.getItems().add("Micro-architecture: " + processor.getProcessorIdentifier().getMicroarchitecture());
        list.getItems().add("Frequency (GHz): " + processor.getProcessorIdentifier().getVendorFreq() / 1000000000.0);
        list.getItems().add("Available processors (physical packages): " + processor.getPhysicalPackageCount());
        list.getItems().add("Available processors (physical cores): " + processor.getPhysicalProcessorCount());
        list.getItems().add("Available processors (logical cores): " + processor.getLogicalProcessorCount());

        list.getItems().add("\n------ MOTHERBOARD INFO ------");
        list.getItems().add("Manufacturer: " + hardware.getComputerSystem().getBaseboard().getManufacturer());
        list.getItems().add("Model: " + hardware.getComputerSystem().getModel());
        list.getItems().add("Serial Number: " + hardware.getComputerSystem().getBaseboard().getSerialNumber());
        list.getItems().add("Hardware UUID: " + hardware.getComputerSystem().getHardwareUUID());

        list.getItems().add("\n------ RAM INFO ------");
        list.getItems().add("Total Size: " + Functions.gigabyte(hardware.getMemory().getTotal()) + " GB");
        list.getItems().add("Available: " + Functions.gigabyte(hardware.getMemory().getAvailable()) + " GB");
        list.getItems().add("Page Size: " + hardware.getMemory().getPageSize() + " KB");

        list.getItems().add("\n------ STORAGE INFO ------");
        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++) {
            list.getItems().add("Drive " + (i + 1) + " :");
            list.getItems().add("File system root: " + roots[i].getAbsolutePath());
            list.getItems().add("Total space: " + Functions.gigabyte(roots[i].getTotalSpace()) + " GB");
            list.getItems().add("Free space: " + Functions.gigabyte(roots[i].getFreeSpace()) + " GB");
            list.getItems().add("Usable space: " + Functions.gigabyte(roots[i].getUsableSpace()) + " GB");
        }

        list.getItems().add("\n------ POWER INFO ------");
        for (int i = 0; i < hardware.getPowerSources().size(); i++) {
            list.getItems().add("Source " + (i + 1) + " :");
            list.getItems().add("Name: " + hardware.getPowerSources().get(i).getName());
            list.getItems().add("Manufacturer: " + hardware.getPowerSources().get(i).getManufacturer());
            list.getItems().add("Serial number: " + hardware.getPowerSources().get(i).getSerialNumber());
            list.getItems().add("Max capacity: " + hardware.getPowerSources().get(i).getMaxCapacity());
            list.getItems().add("Temperature: " + hardware.getPowerSources().get(i).getTemperature());
        }

        if (Options.advancedSettings.isProduce()) {
            try {
                PrintWriter writer = new PrintWriter(System.getProperty("user.home") + "\\Documents\\ComputerSpecs.txt");
                for (String line : list.getItems()) {
                    writer.println(line);
                }
                writer.close();
            } catch (FileNotFoundException fileNotFoundException) {
                Functions.error = "Couldn't write to file";
                try {
                    Functions.openWindow("Main/ErrorUI/error.fxml", "Error");
                } catch (IOException exception) {
                    //ignore
                }
            }
        }
    }

}
