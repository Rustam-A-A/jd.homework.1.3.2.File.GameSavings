import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        GameProgress gameProgress1 = new GameProgress(64, 12, 6, 54.43);
        GameProgress gameProgress2 = new GameProgress(95, 2, 8, 123.03);
        GameProgress gameProgress3 = new GameProgress(24, 87, 12, 1054.02);

        File save1 = new File("/Users/admin/Games/savegames", "save1.dat");
        saveGame(save1,gameProgress1);

        File save2 = new File("/Users/admin/Games/savegames", "save2.dat");
        saveGame(save2,gameProgress2);

        File save3 = new File("/Users/admin/Games/savegames", "save3.dat");
        saveGame(save3,gameProgress3);

        File zipArchiv = new File("/Users/admin/Games/savegames", "archive.zip");
        try (FileOutputStream fos = new FileOutputStream(zipArchiv);
             ZipOutputStream zos = new ZipOutputStream(fos)){
            fillOutArchive(zos, save1, "save1.dat");
            fillOutArchive(zos, save2, "save2.dat");
            fillOutArchive(zos, save3, "save3.dat");
        }

        save1.delete();
        save2.delete();
        save3.delete();
    }

    public static void saveGame(File file, GameProgress gameProgress){
        try(FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void fillOutArchive(ZipOutputStream zipOutputStream,
                                      File fileToPack,
                                      String zipedFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileToPack)){
            ZipEntry entry = new ZipEntry(zipedFile);
            zipOutputStream.putNextEntry(entry);
            byte[] text = new byte[fis.available()];
            zipOutputStream.write(text);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

}
