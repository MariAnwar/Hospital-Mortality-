import java.util.*;

class FileSystemObject {
    String name;
    int size;
    String location;
    String protection;

    public FileSystemObject(String name, int size, String location, String protection) {
        this.name = name;
        this.size = size;
        this.location = location;
        this.protection = protection;
    }
}

class File extends FileSystemObject {
    String extension;
    String content;
    Date dateTime;

    public File(String name, int size, String location, String protection,
                String extension, String content, Date dateTime) {
        super(name, size, location, protection);
        this.extension = extension;
        this.content = content;
        this.dateTime = dateTime;
    }
}

class Folder extends FileSystemObject {
    Map<String, FileSystemObject> children;

    public Folder(String name, int size, String location, String protection) {
        super(name, size, location, protection);
        this.children = new HashMap<>();
    }

    public void addChild(FileSystemObject child) {
        children.put(child.name, child);
    }

    public FileSystemObject getChild(String name) {
        return children.get(name);
    }

    public List<FileSystemObject> searchDirectory(String searchName) {
        List<FileSystemObject> searchResults = new ArrayList<>();
        searchDirectoryHelper(this, searchName, searchResults);
        return searchResults;
    }

    private void searchDirectoryHelper(Folder currentFolder, String searchName, List<FileSystemObject> searchResults) {
        for (FileSystemObject child : currentFolder.children.values()) {
            if (child.name.contains(searchName)) {
                searchResults.add(child);
            }

            if (child instanceof Folder) {
                // Recursive call for subfolders
                searchDirectoryHelper((Folder) child, searchName, searchResults);
            }
        }
    }

    public void listFiles() {
        System.out.println("Childs in " + this.name + ":");
        for (FileSystemObject child : children.values()) {
            if (child instanceof File) {
                System.out.println("- " + child.name);
            }
            else if (child instanceof Folder) {
            System.out.println("- Folder: " + child.name);
        }
        }
        if (children.isEmpty()) {
            System.out.println("Folder is empty.");
        }
    }
}

class Partition extends Folder {
    int maxSize;

    public Partition(String name, int size, String location, String protection, int maxSize) {
        super(name, size, location, protection);
        this.maxSize = maxSize;
    }
    @Override
    public void listFiles() {
        System.out.println("Files in " + this.name + ":");
        for (FileSystemObject child : children.values()) {
            if (child instanceof File) {
                System.out.println("- " + child.name);
            }
            else if (child instanceof Folder) {
                System.out.println("- Folder: " + child.name);
            }
        }
        if (children.isEmpty()) {
            System.out.println("Folder is empty.");
        }
    }
}

class User {
    String name;
    String[] allowedOperations;

    public User(String name, String[] allowedOperations) {
        this.name = name;
        this.allowedOperations = allowedOperations;
    }
     public String getName() {
        return name;
    }

    public boolean hasPermission(String operation) {
        for (String allowedOperation : allowedOperations) {
            if (allowedOperation.equals(operation)) {
                return true;
            }
        }
        return false;
    }
}

class AndroidFileSystemSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();

        List<User> users = Arrays.asList(
                new User("mirna", new String[]{"CREATE", "READ", "WRITE", "DELETE"}),
                new User("Alice", new String[]{"READ"}),
                new User("Bob", new String[]{"WRITE"})
        );

        User currentUser = findUserByName(userName, users);

        if (currentUser == null) {
            System.out.println("User not found. Exiting.");
            return;
        }
        // Print welcome message
        System.out.println("Welcome, " + userName + "! You can perform the following operations on files: CREATE, READ, WRITE, DELETE");

        // Create a partition
        Partition rootPartition = new Partition("Root", 0, "/root", "RWX", 1000);

        // Create a folder
        Folder documents = new Folder("Documents", 0, "/root/documents", "RWX");

        // Create files
        File file1 = new File("Document1", 100, "/root/documents/Document1", "RW", "txt", "texttttt", new Date());
        File file2 = new File("Document2", 120, "/root/documents/Document2", "RW", "txt", "another text", new Date());

        // Add files to folder
        documents.addChild(file1);
        documents.addChild(file2);

        // Add folder to partition
        rootPartition.addChild(documents);

        // Prompt the user to choose an operation
        System.out.print("Choose an operation (READ/WRITE): ");
        String operation = scanner.nextLine();

        if (operation.equalsIgnoreCase("READ")) {
            // Prompt the user to enter the file name
            System.out.print("Enter the file name: ");
            String fileName = scanner.nextLine();

            // Search for files in the "Documents" folder with a matching name
            List<FileSystemObject> searchResults = documents.searchDirectory(fileName);

            if (searchResults.isEmpty()) {
                System.out.println("File not found.");
            } else {
                // Display search results
                System.out.println("\nSearch Results:");
                for (int i = 0; i < searchResults.size(); i++) {
                    FileSystemObject result = searchResults.get(i);
                    System.out.println((i + 1) + ". " + result.name);
                }

                // Prompt the user to enter the file number
                System.out.print("Enter the number of the file you want to read: ");
                int fileNumber = scanner.nextInt();

                if (fileNumber >= 1 && fileNumber <= searchResults.size()) {
                    FileSystemObject selectedFile = searchResults.get(fileNumber - 1);
                    if (selectedFile instanceof File) {
                        File fileToRead = (File) selectedFile;
                        if (currentUser.hasPermission("READ")) {
                            System.out.println("\nReading file content: " + fileToRead.content);
                        } else {
                            System.out.println("\nUser does not have permission to read.");
                        }
                    } else {
                        System.out.println("\nSelected item is not a file.");
                    }
                } else {
                    System.out.println("\nInvalid file number.");
                }
            }
        } 
        else if (operation.equalsIgnoreCase("WRITE")) {
            // Prompt the user to enter the file name
            System.out.print("Enter the file name: ");
            String fileName = scanner.nextLine();

            // Search for files in the "Documents" folder with a matching name
            List<FileSystemObject> searchResults = documents.searchDirectory(fileName);

            if (searchResults.isEmpty()) {
                System.out.println("File not found.");
            } else {
                // Display search results
                System.out.println("\nSearch Results:");
                for (int i = 0; i < searchResults.size(); i++) {
                    FileSystemObject result = searchResults.get(i);
                    System.out.println((i + 1) + ". " + result.name);
                }

                // Prompt the user to enter the file number
                System.out.print("Enter the number of the file you want to write: ");
                int fileNumber = scanner.nextInt();
                scanner.nextLine();

                if (fileNumber >= 1 && fileNumber <= searchResults.size()) {
                    FileSystemObject selectedFile = searchResults.get(fileNumber - 1);
                    if (selectedFile instanceof File) {
                        File fileToWrite = (File) selectedFile;
                        if (currentUser.hasPermission("WRITE")) {
                            // Prompt the user to enter the updated content
                            System.out.print("Enter the updated content: ");
                            String newContent = scanner.nextLine();
                            fileToWrite.content = newContent;
                            System.out.println("File content updated: " + fileToWrite.content);

                        } else {
                            System.out.println("\nUser does not have permission to write.");
                        }
                    } else {
                        System.out.println("\nSelected item is not a file.");
                    }
                } else {
                    System.out.println("\nInvalid file number.");
                }
            }
        } else {
            System.out.println("Invalid operation. Exiting.");
        }
    }

    private static User findUserByName(String name, List<User> users) {
        for (User user : users) {
            if (user.name.equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }
    ////ostor ya rb
// Add these methods at the end of your AndroidFileSystemSimulation class

private static void createFile(User currentUser, Folder currentFolder, Scanner scanner) {
    // Prompt the user for file details
    System.out.print("Enter the file name: ");
    String fileName = scanner.nextLine();
    System.out.print("Enter the file size: ");
    int fileSize = scanner.nextInt();
    scanner.nextLine(); // Consume the newline character
    System.out.print("Enter the file extension: ");
    String fileExtension = scanner.nextLine();
    System.out.print("Enter the file content: ");
    String fileContent = scanner.nextLine();

    // Create a new File object
    File newFile = new File(fileName, fileSize, currentFolder.location + "/" + fileName, "RW", fileExtension, fileContent, new Date());

    // Add the new file to the current folder
    currentFolder.addChild(newFile);

    System.out.println("File created: " + newFile.name);
}

private static void createFolder(User currentUser, Folder currentFolder, Scanner scanner) {
    // Prompt the user for folder details
    System.out.print("Enter the folder name: ");
    String folderName = scanner.nextLine();

    // Create a new Folder object
    Folder newFolder = new Folder(folderName, 0, currentFolder.location + "/" + folderName, "RWX");

    // Add the new folder to the current folder
    currentFolder.addChild(newFolder);

    System.out.println("Folder created: " + newFolder.name);
}

   
}