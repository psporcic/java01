/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import hr.algebra.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Petra
 */
public class MovieParser {

    private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";
    private static final String END_OF_DESCRIPTION = "Početak prikazivanja";
    private static final String REGEX_EXPRESSION = "<.+?>";

    public MovieParser() {

    }

    public static List<Movie> parse() throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();
        //1. otvoriti konekciju na site
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            StartElement startElement = null;
            Movie movie = null;
            Optional<TagType> tagType = Optional.empty();
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT: //item, title..
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        break;
                    case XMLStreamConstants.CHARACTERS: //<![CDATA[ Knjiga ljubavi]]>
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim();
                        if (tagType.isPresent()) { //radi se o tagu koji me zanima!!
                            switch (tagType.get()) {
                                case ITEM:
                                    movie = new Movie();
                                    movies.add(movie);
                                    break;
                                case TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case PUB_DATE:
                                    if (movie != null && !data.isEmpty()) {
                                        LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        movie.setPublishedDate(publishedDate);
                                    }
                                    break;
                                case DESCRIPTION:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDescription(data.substring(0, data.indexOf(END_OF_DESCRIPTION)).replaceAll(REGEX_EXPRESSION, ""));
                                    }
                                    break;
                                case ORIGINAL_TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setOriginalTitle(data);
                                    }
                                    break;case DIRECTOR:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDirectors(Person.getPeopleFromData(data));
                                    }
                                    break;

                                case ACTORS:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setActors(Person.getPeopleFromData(data));
                                    }
                                    break;
                                case LENGTH:                                    
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setLength(Integer.valueOf(data));
                                    }
                                    break;
                                    case GENRE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setGenres(Genre.getGenresFromData(data));
                                    }
                                    break;
                                case PICTURE_PATH:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setPicturePath(data);
                                        handlePicture(movie, data);
                                    }
                                    break;
                                case LINK:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setLink(data);
                                    }
                                    break;
                                case RELEASE_DATE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setReleaseDate(data);
                                    }
                                    break;
                                }
                        }
                        break;
                }
            }
        }

        //2. kreirati parser
        //3. parse
        return movies;
    }
    
    private static void handlePicture(Movie movie, String pictureUrl) throws IOException {
        //1. osigurati da ima .jpg ext
        String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        
        //2. napraviti ime za sliku
        String pictureName = UUID.randomUUID() + ext; //2322.jpg
        String picturePath = DIR + File.separator + pictureName; // assets/2322.jpg
        
        //3. skinut sliku u taj file
        FileUtils.copyFromUrl(pictureUrl, picturePath);
        
        //4. azurirati movie
        movie.setPicturePath(picturePath);
    }    

    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        PUB_DATE("pubDate"),
        DESCRIPTION("description"),
        ORIGINAL_TITLE("orignaziv"),
        DIRECTOR("redatelj"),
        ACTORS("glumci"),
        LENGTH("trajanje"),
        GENRE("zanr"),
        PICTURE_PATH("plakat"),
        LINK("link"),
        RELEASE_DATE("pocetak");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
