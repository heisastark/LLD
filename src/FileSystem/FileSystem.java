package FileSystem;
import java.util.*;

// ================= Base =================
abstract class FileSystemEntity {
    protected String name;
    protected long size; // keep as long

    public FileSystemEntity(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public abstract void ls(String indent);
}

// ================= File =================
class FileEntity extends FileSystemEntity {
    public FileEntity(String name, long size) {
        super(name, size);
    }

    @Override
    public void ls(String indent) {
        System.out.println(indent + name + " (" + size + " MB)");
    }
}

// ================= Folder =================
class FolderEntity extends FileSystemEntity {
    private List<FileSystemEntity> children = new ArrayList<>();

    public FolderEntity(String name) {
        super(name, 0); // size = 0
    }

    public void add(FileSystemEntity entity) {
        children.add(entity);
    }

    public void remove(FileSystemEntity entity) {
        children.remove(entity);
    }

    public FileSystemEntity get(String name) {
        for (FileSystemEntity e : children) {
            if (e.getName().equals(name)) return e;
        }
        return null;
    }

    @Override
    public void ls(String indent) {
        System.out.println(indent + name + "/");
        for (FileSystemEntity e : children) {
            e.ls(indent + "  ");
        }
    }
}

// ================= Partition =================
class Partition {
    private String name;
    private long capacity;
    protected long used;
    private FolderEntity root;

    public Partition(String name, long capacity) {
        this.name = name + ":";
        this.capacity = capacity;
        used = 0;
        root = new FolderEntity("root");
    }

    public boolean createFile(String path, String fileName, long size) {
        if (used + size > capacity) {
            System.out.println("Not enough space on " + name);
            return false;
        }
        FolderEntity folder = navigate(path);
        if (folder != null) {
            folder.add(new FileEntity(fileName, size));
            used += size;
            return true;
        }
        return false;
    }

    public boolean createFolder(String path, String folderName) {
        FolderEntity folder = navigate(path);
        if (folder != null) {
            folder.add(new FolderEntity(folderName));
            return true;
        }
        return false;
    }

    public void delete(String path, String name) {
        FolderEntity folder = navigate(path);
        if (folder != null) {
            FileSystemEntity e = folder.get(name);
            if (e != null) {
                if (e instanceof FileEntity) {
                    used -= e.getSize();
                }
                folder.remove(e);
            }
        }
    }

    public void ls() {
        root.ls("");
    }

    private FolderEntity navigate(String path) {
        String[] parts = path.split("/");
        FolderEntity current = root;

        for (String p : parts) {
            if (p.isEmpty() || p.equals("root")) continue;
            FileSystemEntity e = current.get(p);
            if (e instanceof FolderEntity) {
                current = (FolderEntity) e;
            } else {
                return null;
            }
        }
        return current;
    }
}

// ================= Commands =================
interface Command {
    void execute();
}

class MkdirCommand implements Command {
    private Partition partition;
    private String path, name;

    public MkdirCommand(Partition p, String path, String name) {
        this.partition = p;
        this.path = path;
        this.name = name;
    }

    public void execute() {
        partition.createFolder(path, name);
    }
}

class TouchCommand implements Command {
    private Partition partition;
    private String path, name;
    private long size;

    public TouchCommand(Partition p, String path, String name, long size) {
        this.partition = p;
        this.path = path;
        this.name = name;
        this.size = size;
    }

    public void execute() {
        partition.createFile(path, name, size);
    }
}

class RmCommand implements Command {
    private Partition partition;
    private String path, name;

    public RmCommand(Partition p, String path, String name) {
        this.partition = p;
        this.path = path;
        this.name = name;
    }

    public void execute() {
        partition.delete(path, name);
    }
}

class LsCommand implements Command {
    private Partition partition;

    public LsCommand(Partition p) {
        this.partition = p;
    }

    public void execute() {
        partition.ls();
    }
}

public class FileSystem{
    private String name;
    private long size;
    private long used;
    Map<Partition, Long> partitions;

    public FileSystem(String name, long size){
        this.name = name;
        this.size = size;
        this.used = 0;
        partitions = new HashMap<>();
    }

    public Partition createPartition(String partitionName, long partitionSize){
        if(used + partitionSize > size){
            return null;
        }
        Partition p = new Partition(partitionName, partitionSize);
        used += partitionSize;
        partitions.put(p, partitionSize);
        return p;
    }
}

// ================= Main =================
class FileSystemDemo {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem("WindowsFileSystem", 100);
        Partition p1 = fileSystem.createPartition("C", 80);
        if(p1 == null){
            System.out.println("Partition could not be created!");
            return;
        }

//        Partition p2 = fileSystem.createPartition("D", 90);
//        if(p2 == null){
//            System.out.println("Partition could not be created!");
//            return;
//        }

        Command mkdirDocs = new MkdirCommand(p1, "root", "Documents");
        Command touchFile = new TouchCommand(p1, "root/Documents", "notes.txt", 10);
        Command ls = new LsCommand(p1);
        Command rm = new RmCommand(p1, "root/Documents", "notes.txt");

        mkdirDocs.execute();
        touchFile.execute();
        ls.execute();

        System.out.println("\nAfter deleting notes.txt:");
        rm.execute();
        ls.execute();
    }
}