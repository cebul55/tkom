package WebsiteSearch;

public class File {

    private String name;
    private String path;
    private boolean isOpened;

    public File(){}
    public File(String name){}

    public void writeText(String text){
        System.out.println("Invoke File.writeText()");

        return;
    }

    public boolean close(){
        System.out.println("Invoke File.close()");
        return true;
    }

    public boolean open(){
        System.out.println("Invoke File.open()");
        return true;
    }

    public boolean isOpened() {
        System.out.println("IFile.isOpened()");

        return isOpened;
    }
}
