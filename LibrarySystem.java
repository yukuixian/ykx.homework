import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class LibrarySystem {
    // 图书总记录（保持添加顺序）
    private final ArrayList<Book> bookInventory;
    // 唯一分类集合（自动去重）
    private final HashSet<String> categoryDictionary;
    // 图书ID检索映射（O(1) 复杂度查找）
    private final HashMap<Integer, Book> bookLookupTable;

    public LibrarySystem() {
        bookInventory = new ArrayList<>();

        categoryDictionary = new HashSet<>();

        bookLookupTable = new HashMap<>();
    }
    // 新增图书
    public void addBook(Book book) {
        bookInventory.add(book);
        categoryDictionary.add(book.getGenre());
        bookLookupTable.put(book.getId(), book);
    }
    // 按书名关键词删除（Iterator 安全删除，避免并发修改异常）
    public void removeBooksByKeyword(String keyword) {
        Iterator<Book> iterator = bookInventory.iterator();
        while (iterator.hasNext()) {
            Book currentBook = iterator.next();
            if (currentBook.getTitle().contains(keyword)) {
                iterator.remove();
                bookLookupTable.remove(currentBook.getId());
            }
        }
    }
    // 展示图书馆当前状态
    public void displayStatus() {
        System.out.println("===== 图书馆藏书清单 =====");
        for (Book book : bookInventory) {
            System.out.println(book);
        }
        System.out.println("\n===== 图书分类（无重复）=====");
        for (String category : categoryDictionary) {
            System.out.println("- " + category);
        }
        System.out.println("\n===== ID 检索测试 =====");
        System.out.println("ID=1001 的图书: " + bookLookupTable.get(1001));
    }
    // 测试主方法
    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        library.addBook(new Book(1001,"Python编程从入门到实践", "Python技术" ));
        library.addBook(new Book(1002, "Java高并发编程详解", "编程技术"));
        library.addBook(new Book(1003, "百年孤独", "外国文学"));
        library.addBook(new Book(1004, "流浪地球", "科幻小说"));

        System.out.println("=== 初始状态 ===");
        library.displayStatus();
        // 删除包含 "Java" 的图书
        library.removeBooksByKeyword("Java");
        System.out.println("\n=== 删除关键词后 ===");
        library.displayStatus();
    }
}
