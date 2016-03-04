package com.abhishek.wikisearcher;

/**
 * Created by abhishek on 03/03/16 at 5:04 PM.
 */
public class Constants {

    public static final String URL = "https://en.wikipedia.org/w/api.php?action=query" +
            "&prop=pageimages&format=json&piprop=thumbnail&pithumbsize=400&pilimit=50" +
            "&generator=prefixsearch&gpssearch=";

    public static final String HTML_URL = "https://en.wikipedia.org/w/api.php?action=query&prop=info&inprop=url&format=json&pageids=";
}
