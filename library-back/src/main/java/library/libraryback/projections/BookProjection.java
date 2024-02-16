package library.libraryback.projections;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;

public interface BookProjection {

    Integer getId();

    String getName();

    String getAuthor();

    String getPublisher();

    String getDescription();

    @Value("#{target.category.name}")
    String getCategoryName();

    @Value("#{target.book_date}")
    Timestamp getBookDate();
}
