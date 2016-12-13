package top.tobiaslee.gsontest;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tobiaslee on 2016/12/11.
 */

public class Book {
    private String[] author;

    private String image;
    private String title;
    private String price;
    private String author_intro;




    public void setAuthor_intro(String authorIntro) {
        this.author_intro = authorIntro;
    }

    public String getAuthor_intro() {

        if( author_intro != ""){
            return author_intro;
        }else {
            return  "暂无作者信息";
        }
    }

    public String getAuthor(){
        if(author != null ){
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < author.length ; i++) {
                builder.append(author[i]);
                if( i < author.length -1 ){
                    builder.append(",");
                }else{
                    builder.append(".");
                }
            }
            return  builder.toString();
        }else{
            return "暂无作者信息";
        }

    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        if(price != ""){
            return price;
        }else {
            return "暂无价格信息" ;
        }

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String mark) {

        this.price = price;
    }

    public void setImage(String imagePath) {

        this.image = imagePath;
    }

    public void setAuthor(String[] author) {

        this.author = author;
    }


}
