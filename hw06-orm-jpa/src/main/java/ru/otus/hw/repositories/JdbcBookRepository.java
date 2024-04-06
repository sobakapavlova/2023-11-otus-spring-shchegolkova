package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final GenreRepository genreRepository;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        Book book = namedParameterJdbcOperations.query("""
                        SELECT
                         b.id,
                         b.author_id,
                         b.title,
                         a.full_name,
                         bg.genre_id,
                         g.name
                          FROM books b
                          JOIN authors a ON A.id = b.author_id
                          JOIN books_genres bg ON bg.book_id = b.id
                          JOIN genres g ON g.id = bg.genre_id
                          WHERE b.id = :id
                        """,
                Map.of("id", id),
                new BookResultSetExtractor()
        );
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update(
                "DELETE FROM books WHERE id = :id",
                Map.of("id", id));
    }

    private List<Book> getAllBooksWithoutGenres() {
        return namedParameterJdbcOperations.query("""
                        SELECT b.id,
                         b.author_id,
                         b.title,
                         a.full_name
                         FROM books b
                         JOIN authors a ON A.id = b.author_id
                        """,
                new BookRowMapper());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return namedParameterJdbcOperations.query(
                "SELECT book_id, genre_id FROM books_genres",
                new BookGenreRelationMapper());
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        final Map<Long, Genre> genreMap = genres
                .stream()
                .collect(Collectors.toMap(Genre::getId, genre -> genre));

        final Map<Long, List<BookGenreRelation>> bookToGroup = relations
                .stream()
                .collect(Collectors.groupingBy(relation -> relation.bookId));

        booksWithoutGenres.forEach(book -> {
            List<Genre> genreList = bookToGroup.get(book.getId())
                    .stream()
                    .map(relation -> genreMap.get(relation.genreId)).toList();
            book.setGenres(genreList);
        });
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId());

        namedParameterJdbcOperations.update(
                "INSERT INTO books (title, author_id) VALUES (:title, :author_id)",
                sqlParameterSource,
                keyHolder
        );

        Object id = getId(keyHolder);

        book.setId((long) id);
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId())
                .addValue("id", book.getId());
        var keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update("UPDATE books SET author_id=:author_id, title = :title WHERE id=:id",
                sqlParameterSource, keyHolder);

        Object id = getId(keyHolder);

        if (id == null) {
            throw new EntityNotFoundException("Can't update book with title %s".formatted(book.getTitle()));
        }
        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private static Object getId(GeneratedKeyHolder keyHolder) {
        return Optional.ofNullable(keyHolder.getKeys())
                .flatMap(it -> Optional.ofNullable(it.get("id")))
                .orElseThrow(() -> new NoSuchElementException("No id found in key holder"));
    }

    private void batchInsertGenresRelationsFor(Book book) {
        final List<Long> genreIds = book.getGenres().stream().map(Genre::getId).toList();
        final MapSqlParameterSource[] sqlParameterSource = new MapSqlParameterSource[genreIds.size()];

        for (int i = 0; i < genreIds.size(); i++) {
            sqlParameterSource[i] = new MapSqlParameterSource()
                    .addValue("book_id", book.getId())
                    .addValue("genre_id", genreIds.get(i));
        }

        namedParameterJdbcOperations.batchUpdate(
                "INSERT INTO books_genres (book_id, genre_id) VALUES (:book_id, :genre_id)",
                sqlParameterSource
        );
    }

    private void removeGenresRelationsFor(Book book) {
        namedParameterJdbcOperations.update(
                "DELETE FROM books_genres WHERE book_id = :book_id",
                Map.of("book_id", book.getId()));
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author(
                    rs.getLong("author_id"),
                    rs.getString("full_name")
            );
            return new Book(
                    rs.getLong("id"),
                    rs.getString("title"),
                    author,
                    emptyList()
            );
        }
    }

    private static class BookGenreRelationMapper implements RowMapper<BookGenreRelation> {
        @Override
        public BookGenreRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new BookGenreRelation(
                    rs.getLong("book_id"),
                    rs.getLong("genre_id")
            );
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Genre> genreList = new ArrayList<>();
            Book book = null;
            while (rs.next()) {
                Author author = new Author(
                        rs.getLong("author_id"),
                        rs.getString("full_name")
                );
                Genre genre = new Genre(
                        rs.getLong("genre_id"),
                        rs.getString("name")
                );
                genreList.add(genre);
                book = new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        author,
                        genreList
                );
            }
            if (book != null) {
                book.setGenres(genreList);
            }
            return book;
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}
