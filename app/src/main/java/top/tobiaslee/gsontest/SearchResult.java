package top.tobiaslee.gsontest;

import java.util.List;

/**
 * Created by tobiaslee on 2016/12/12.
 */

public class SearchResult {
    private String start ;
    private String total ;
    private String count ;
    private List<Book> books;


    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getCount() {
        return count;
    }

    public String getStart() {
        return start;
    }

    public String getTotal() {
        return total;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            builder.append("title:" + book.getTitle());
            builder.append("price:" + book.getPrice());
            builder.append("    ;");
        }


        return "SearchResult{" +
                "count='" + count + '\'' +
                ", start='" + start + '\'' +
                ", total='" + total + '\'' +
                '}' + builder.toString();
    }
}
