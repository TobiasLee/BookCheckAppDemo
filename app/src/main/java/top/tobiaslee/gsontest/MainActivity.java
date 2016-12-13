package top.tobiaslee.gsontest;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String checkHead = "https://api.douban.com/v2/book/search?q=";
    public static final String checkEnd = "&fields=title,author,image,author_intro,price" ;

    TextView price;
    TextView author;
    TextView title;
    TextView searchResult;
    Button nextBook ;
    Button beforeBook ;
    ImageView bookImage;
    Button getInfo ;
    TextView authorIntro;
    int bookCount ;
    EditText check_book;

    List<Book> bookSearchResult ;
    int bookIndex = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check_book = (EditText) findViewById(R.id.check_book);
        authorIntro = (TextView) findViewById(R.id.author_intro);
        price = (TextView) findViewById(R.id.price);
        author = (TextView) findViewById(R.id.author);
        title = (TextView) findViewById(R.id.title_book);

        beforeBook = (Button) findViewById(R.id.book_before);
        authorIntro.setMovementMethod(ScrollingMovementMethod.getInstance());
        bookImage = (ImageView) findViewById(R.id.book_image);
         getInfo = (Button) findViewById(R.id.check_information);
        getInfo.setOnClickListener(this);
        nextBook = (Button) findViewById(R.id.next_book);
        searchResult = (TextView) findViewById(R.id.search_result);
        nextBook.setOnClickListener(this);
        beforeBook.setOnClickListener(this);

    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(checkHead + check_book.getText().toString() + checkEnd)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    parseJSONWithGSON(responseData);
                    //showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void parseJSONWithGSON(String responseData){
        Gson gson = new Gson();
       // String bookData = dataCut(responseData);
        //Log.d("bookData",bookData);
        SearchResult result = gson.fromJson(responseData,SearchResult.class);
        bookSearchResult = result.getBooks();
        bookIndex = 1 ;
        bookCount = Integer.parseInt(result.getCount());

        generateInfo(bookSearchResult.get(0));

    }


    private  void generateInfo(final Book book){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Log.d("Book",book.toString());
                title.setText(book.getTitle());
                author.setText(book.getAuthor());
                price.setText(book.getPrice());
                authorIntro.setText(book.getAuthor_intro());
                searchResult.setText("共查询到 " + bookCount + " 条结果  " +
                        "显示的是第 1 条");
                Glide.with(MainActivity.this).load(book.getImage()).into(bookImage);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.check_information:
                sendRequestWithOkHttp();
                break;
            case R.id.next_book:
                showNextBookInfo();
                break ;
            case R.id.book_before:
                showBeforeBook();
                break;
            default:
                break;
        }
    }
    private void showNextBookInfo(){
        if(bookIndex < bookSearchResult.size()) {
            generateInfo(bookSearchResult.get(bookIndex));
            searchResult.setText("共查询到 " + bookCount + " 条结果  " +
                    "为您显示第 " + (bookIndex+1 )+ " 条");
            bookIndex++;
        }else{
            Toast.makeText(this, "没有更多", Toast.LENGTH_SHORT).show();
        }
    }

    private  void showBeforeBook(){
        if(bookIndex > 0){
            bookIndex --;
            generateInfo(bookSearchResult.get(bookIndex));
            searchResult.setText("共查询到 " + bookCount + " 条结果  " +
                    "为您显示第 " + (bookIndex+1) + " 条");
        }else{
            Toast.makeText(this, "已经是第一本", Toast.LENGTH_SHORT).show();
        }
    }



    private String dataCut(final String data){
        Pattern p = Pattern.compile("\\{\"image\"[^]}]*\\]{1}\\}{1}?");

        Matcher m = p.matcher(data);
        m.find();

        return m.group();
    }
}
